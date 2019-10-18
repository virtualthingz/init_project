package kr.supergate.shoppingfolder.common;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

/**
 * Created by Hang Jo on 19. 10. 17..
 */
@org.springframework.stereotype.Service
public class ClassDispatcher implements ApplicationContextAware {

    public static enum TYPE {
        VERSION_AND_COUNTRY, VERSION_ONLY, COUNTRY_ONLY
    }

    static ApplicationContext applicationContext;


    public static Object forward(Object obj) {

        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        String beanName = null;
        String versionCode = null;
        String countryCode = null;

        if (sra.getAttribute("type", RequestAttributes.SCOPE_REQUEST) == TYPE.VERSION_ONLY) {
            versionCode = sra.getRequest().getHeader(XHttpHeader.X_APP_VERSION_CODE);
            versionCode = StringUtils.isEmpty(versionCode) ? "0" : versionCode;
        } else if (sra.getAttribute("type", RequestAttributes.SCOPE_REQUEST) == TYPE.COUNTRY_ONLY) {
            countryCode = sra.getRequest().getHeader(XHttpHeader.X_COUNTRY_CODE);
            countryCode = (countryCode == null) ? "KR" : countryCode; // 없으면 KR (default for legacy)
        } else {
            versionCode = sra.getRequest().getHeader(XHttpHeader.X_APP_VERSION_CODE);
            countryCode = sra.getRequest().getHeader(XHttpHeader.X_COUNTRY_CODE);
            versionCode = StringUtils.isEmpty(versionCode) ? "0" : versionCode;
            countryCode = (countryCode == null) ? "KR" : countryCode; // 없으면 KR (default for legacy)
        }

        System.out.println("################################ X_COUNTRY_CODE (Country) ################################ " + countryCode);
        System.out.println("############################## X_APP_VERSION_CODE (Version) ############################## " + versionCode);

        int version = (versionCode == null) ? 0 : Integer.parseInt(versionCode);
        String country = (countryCode == null || countryCode.equalsIgnoreCase("KR")) ? "" : countryCode + "_";

        beanName = getBeanName(obj);

        // COUNTRY_ONLY
        if (countryCode != null && versionCode == null) {
            return applicationContext.getBean(country + beanName);
        }

        // VERSION_ONLY && VERSION_AND_COUNTRY (version separator로 구분)
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[2];

        StringBuilder sb = new StringBuilder();
        sb.append(country);
        sb.append(e.getFileName().substring(0, e.getFileName().indexOf(".")));
        sb.append("_").append(e.getMethodName());
        System.out.println("distributed to " + sb.toString());

        String versionSeparator = getVersion(sb.toString());
        System.out.println("version seperator : " + versionSeparator);
        if (versionSeparator != null && version >= Integer.parseInt(versionSeparator)) {
            System.out.println("bean : " + country + beanName);
            return applicationContext.getBean(country + beanName);
        } else {
            System.out.println("bean : " + country + beanName + "_old");
            return applicationContext.getBean(country + beanName + "_old");
        }
    }

    public static String getBeanName(Object obj) {
        String beanName = obj.getClass().getSimpleName();
        // Auto Proxy class
        if (obj.getClass().getSimpleName().indexOf("$$") != -1) {
            beanName = beanName.substring(0, obj.getClass().getSimpleName().indexOf("$$"));
        }
        return beanName;
    }

    public static String getVersion(String name) {
        String version = null;
        try {
            Field field = VersionSeparator.class.getDeclaredField(name);
            Class<?> targetType = field.getType();
            Object objectValue = targetType.newInstance();
            version = (String)field.get(objectValue);
        } catch (Exception e) { /***/ }
        return version;
    }

    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return sra.getRequest();
    }

    public static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 한국은 기본으로 레거시 처리를 위해 공백으로 처리하고 이외 국가는 "(국가코드)_(클래스명)" 형식으로 변경한다
     * @param country
     * @return
     */
    public static String countryWrapper(String country) {
        return StringUtils.isEmpty(country) || "KR".equalsIgnoreCase(country) ? "" : country + "_";
    }

    public static String getCountry() {
        String country = getHttpServletRequest().getHeader(XHttpHeader.X_COUNTRY_CODE);
        return StringUtils.isEmpty(country) || "KR".equalsIgnoreCase(country) ? "KR" : country;
    }
}
