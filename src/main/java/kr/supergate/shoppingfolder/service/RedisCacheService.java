package kr.supergate.shoppingfolder.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import kr.supergate.shoppingfolder.common.ClassDispatcher;
import kr.supergate.shoppingfolder.common.XssObjectMapper;
import kr.supergate.shoppingfolder.config.RedisConfig;
import kr.supergate.shoppingfolder.domain.User;
import kr.supergate.shoppingfolder.util.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

/**
 * Created by Hang Jo on 2019. 10. 11..
 */
@Component
public class RedisCacheService {

    private static final TimeUnit EXPIRE_UNIT = TimeUnit.SECONDS;
    public static final long DEFAULT_EXPIRE_TIME = 60 * 60 * 24 * 1; // 1일
    public static final long EXPIRE_TIME_WEB = 60 * 60 * 12; // 12 hours
    public static final long EXPIRE_TIME_APP = 60 * 60 * 24 * 90; // 90 days
    public static final long EXPIRE_TIME_WEBAPP = 60 * 60 * 2; // 2 hours

    @Autowired
    private XssObjectMapper xssObjectMapper;

//    RedisTemplate<String, Object> client = (RedisTemplate) ClassDispatcher.getApplicationContext().getBean("redisTemplate");

//    public RedisCacheService() {
//        RedisTemplate<String, Object> client = (RedisTemplate) ClassDispatcher.getApplicationContext().getBean("redisTemplate");
//        this.client = client;
//        System.out.println("client : " + client);
//    }

    //    @Autowired
//    private RedisTemplate client;

    @Autowired
    private StringRedisTemplate client;

//    RedisCacheService (StringRedisTemplate client) {
//        StringRedisSerializer stringSerializer = new StringRedisSerializer();
//        client.setKeySerializer(stringSerializer);
//        client.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        this.client = client;
//    }

    /**
     * Setting the cache with the key which is automatically made by system.
     * @param obj
     */
    public void setCache(Object obj) {
        String value = null;
        if (!(obj instanceof String)) {
            value = convertObjectToString(obj, obj.getClass());
        } else {
            value = (String)obj;
        }
        String key = null;
        try {
            key = getKey();
        } catch (Exception e) {
            return;
        }
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) return;
        flush(key);
        client.convertAndSend(key, value);
    }


    /**
     * Setting the cache with the key which is automatically made by system.
     * @param obj
     * @param expireTime
     */
    public void setCache(Object obj, long expireTime) {
        String value = null;
        if (!(obj instanceof String)) {
            value = convertObjectToString(obj, obj.getClass());
        } else {
            value = (String)obj;
        }
        String key = null;
        try {
            key = getKey();
        } catch (Exception e) {
            return;
        }
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) return;
        flush(key);
        client.opsForValue().set(key, value, expireTime);
    }


    /**
     * Setting the cache with the key which is manually made by user.
     * @param key
     * @param obj
     */
    public void setCache(String key, Object obj) {
        String value = null;
        if (!(obj instanceof String)) {
            value = convertObjectToString(obj, obj.getClass());
        } else {
            value = (String)obj;
        }
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) return;
        flush(key);
        client.opsForValue().set(key, value, EXPIRE_TIME_APP);
    }


    /**
     * Setting the cache with the key which is manually made by user.
     * @param key
     * @param obj
     * @param expireTime
     */
    public void setCache(String key, Object obj, long expireTime) {
        String value = null;
        if (!(obj instanceof String)) {
            value = convertObjectToString(obj, obj.getClass());
        } else {
            value = (String)obj;
        }
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) return;
//        flush(key);
        client.opsForValue().set(key, value, expireTime);
    }


    /**
     * Setting the cache with the key which is automatically made by system.
     * @param obj
     */
    public void setCacheForUser(Object obj) {
        String value = null;
        if (!(obj instanceof String)) {
            value = convertObjectToString(obj, obj.getClass());
        } else {
            value = (String)obj;
        }
        String key = null;
        try {
            key = getKeyForUser();
        } catch (Exception e) {
            return;
        }
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) return;
        flush(key);
        client.opsForValue().set(key, value, EXPIRE_TIME_APP);
//        client.set(key, value, "NX", "EX", DEFAULT_EXPIRE_TIME);
    }


    /**
     * Setting the cache with the key which is automatically made by system.
     * @param obj
     * @param expireTime
     */
    public void setCacheForUser(Object obj, long expireTime) {
        String value = null;
        if (!(obj instanceof String)) {
            value = convertObjectToString(obj, obj.getClass());
        } else {
            value = (String)obj;
        }
        String key = null;
        try {
            key = getKeyForUser();
        } catch (Exception e) {
            return;
        }
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) return;
        flush(key);
        client.opsForValue().set(key, value, expireTime);
//        client.set(key, value, "NX", "EX", expireTime);
    }


    /**
     * Getting the key which is made by system automatically to use internal method.
     * @return
     */
    private String getKeyForUser() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("/");
        sb.append(System.getProperty("spring.profiles.active"));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        User user = null;
        if ((user = (User)request.getAttribute("X-User")) != null) {
            sb.append("/");
            sb.append(user.getUserId());
        }

        sb.append(request.getRequestURI());
        sb.append("/");
        Enumeration<String> enums = request.getParameterNames();
        while (enums.hasMoreElements()) {
            String paramName = enums.nextElement();
            String[] parameters = request.getParameterValues(paramName);

            if (parameters.length > 1) {
                for (String param : parameters) {
                    sb.append(param);
                }
            } else {
                sb.append(paramName).append("=");
                sb.append(parameters[0]);
            }
            sb.append(":");
        }

        System.out.println("cache_key : " + sb.toString());
        return sb.toString();
    }


    /**
     * Getting the key which is made by system automatically for common usage.
     * @return
     */
    private String getKey() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("/");
        sb.append(System.getProperty("spring.profiles.active"));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        sb.append(request.getRequestURI());
        sb.append("/");
        Enumeration<String> enums = request.getParameterNames();
        while (enums.hasMoreElements()) {
            String paramName = enums.nextElement();
            String[] parameters = request.getParameterValues(paramName);

            if (parameters.length > 1) {
                for (String param : parameters) {
                    sb.append(param);
                }
            } else {
                sb.append(paramName).append("=");
                sb.append(parameters[0]);
            }
            sb.append(":");
        }

        System.out.println("cache_key : " + sb.toString());
        return sb.toString();
    }


    /**
     * Getting the cache without key
     * @return
     */
    public String getCache() {
        String key = null;
        try {
            key = getKey();
        } catch (Exception e) {
            return null;
        }
        String plandasValue = client.opsForValue().get(key);
        return plandasValue;
    }


    /**
     * Getting the cache with key.
     * @param key
     * @return
     */
    public String getCache(String key) {
        String plandasValue = (String)client.opsForValue().get(key);
        return plandasValue;
    }


    /**
     * Getting the cache without key
     * @return
     */
    public String getCacheForUser() {
        String key = null;
        try {
            key = getKeyForUser();
        } catch (Exception e) {
            return null;
        }
        String plandasValue = (String)client.opsForValue().get(key);
        return plandasValue;
    }


    /**
     * Flushing the cache with pattern key [*,?].
     * @param pattern
     */
//    public void flushByPattern(String pattern) {
//        client.delete(pattern);
//    }


    /**
     * Flushing the cache with key.
     * @param key
     */
    public void flush(String key) {
        client.delete(key);
    }


    /**
     * Flushing the cache without key.
     */
    public void flush() {
        String key = null;
        try {
            key = getKey();
        } catch (Exception e) {
            return;
        }
        client.delete(key);
    }


    /**
     * Making the key which would be put in the cache.
     * [country]/[USER,MERCHANT]/[uri]/params+defaultvalue
     * @param defaultValue
     * @return
     */
    public String makeKeyForUser(String defaultValue) {
        StringBuilder sb = new StringBuilder();
        sb.append("/");
        sb.append(System.getProperty("spring.profiles.active"));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        User user = null;
        if ((user = (User)request.getAttribute("X-User")) != null) {
            sb.append("/");
            sb.append(user.getUserId());
        }

        sb.append(request.getRequestURI());
        sb.append("/");
        Enumeration<String> enums = request.getParameterNames();
        while (enums.hasMoreElements()) {
            String paramName = enums.nextElement();
            String[] parameters = request.getParameterValues(paramName);

            if (parameters.length > 1) {
                sb.append(parameters);
            } else {
                sb.append(parameters[0]);
            }
        }
        sb.append(defaultValue);

        System.out.println("cache_key : " + sb.toString());
        return sb.toString();
    }


    /**
     * Making the key which would be put in the cache.
     * [country]/[uri]/params+defaultvalue
     * @param defaultValue
     * @return
     */
    public String makeKey(String defaultValue) {
        StringBuilder sb = new StringBuilder();
        sb.append("/");
        sb.append(System.getProperty("spring.profiles.active"));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        sb.append(request.getRequestURI());
        sb.append("/");
        Enumeration<String> enums = request.getParameterNames();
        while (enums.hasMoreElements()) {
            String paramName = enums.nextElement();
            String[] parameters = request.getParameterValues(paramName);

            if (parameters.length > 1) {
                sb.append(parameters);
            } else {
                sb.append(parameters[0]);
            }
        }
        sb.append(defaultValue);

        System.out.println("cache_key : " + sb.toString());
        return sb.toString();
    }


    /**
     * Responsing the result value from the cache.
     * @param cache
     * @return
     */
    public boolean returnCache(Object cache) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        if (cache != null) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter out = null;
            try {
                out = response.getWriter();
                out.print(cache);
            } catch (IOException e) {
                return false;
            } finally {
                out.flush();
            }
            return true;
        } else {
            return false;
        }
    }


    /**
     * Converting Object to String
     * @param obj
     * @return String
     */
    public <T> String convertObjectToString(Object obj, Class<T> type) {
        String s = null;
        try {
            s = xssObjectMapper.writeValueAsString((T)obj).trim();
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
        return s;
    }


    /**
     * Converting String to Object
     * @param s
     * @param object
     * @param <T>
     * @return Object
     */
    public <T> T convertStringToObject(String s, Class<T> object) {
        T obj = null;
        try {
            obj = xssObjectMapper.readValue(s.trim(), object);
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
        return obj;
    }

}
