package kr.supergate.shoppingfolder.service;

import kr.supergate.shoppingfolder.domain.User;
import kr.supergate.shoppingfolder.domain.admin.Admin;
import kr.supergate.shoppingfolder.domain.user.DeviceType;
import kr.supergate.shoppingfolder.exception.UnauthorizedException;
import kr.supergate.shoppingfolder.util.ActiveProfileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionRedisService {

    @Autowired
    RedisCacheService cacheService;

    private static final String KEY_PREFIX_SESSION_MERCHANT = "SM_" + ActiveProfileUtil.sessionProfile();
    private static final String KEY_PREFIX_SESSION_ADMIN = "SA_" + ActiveProfileUtil.sessionProfile();
    private static final String KEY_PREFIX_SESSION_USER = "SU_" + ActiveProfileUtil.sessionProfile();

    public void setUser(String session, User user, DeviceType deviceType) {

        if (deviceType != null && deviceType == DeviceType.WEB){  // 2 hours.
            cacheService.setCache(KEY_PREFIX_SESSION_USER + session, user, cacheService.EXPIRE_TIME_WEBAPP);
        } else {
            cacheService.setCache(KEY_PREFIX_SESSION_USER + session, user, cacheService.EXPIRE_TIME_APP);
        }
    }


    public User getUser(String session) {
        String user = cacheService.getCache(KEY_PREFIX_SESSION_USER + session);
        if (user == null) {
            throw new UnauthorizedException("user session is wrong. : " + session);
        }

        return cacheService.convertStringToObject(user, User.class);
    }

    public void setAdmin(String session, Admin admin) {
        cacheService.setCache(KEY_PREFIX_SESSION_ADMIN + session, admin, cacheService.EXPIRE_TIME_WEB);
    }


    public Admin getAdmin(String session) {
        String cache = cacheService.getCache(KEY_PREFIX_SESSION_ADMIN + session);
        if (cache == null) {
            throw new UnauthorizedException("admin session is wrong. : " + session);
        }

        return cacheService.convertStringToObject(cache, Admin.class);
    }


    public void deleteAdmin(String session) {
        cacheService.flush(KEY_PREFIX_SESSION_ADMIN + session);
    }

}
