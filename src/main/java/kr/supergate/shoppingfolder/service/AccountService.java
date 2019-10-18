package kr.supergate.shoppingfolder.service;

import kr.supergate.shoppingfolder.common.SessionUtils;
import kr.supergate.shoppingfolder.domain.User;
import kr.supergate.shoppingfolder.domain.account.login.Login;
import kr.supergate.shoppingfolder.domain.account.login.UserBody;
import kr.supergate.shoppingfolder.domain.account.login.UserLogin;
import kr.supergate.shoppingfolder.domain.account.register.RegisterUserBody;
import kr.supergate.shoppingfolder.domain.admin.Admin;
import kr.supergate.shoppingfolder.domain.sign.SignType;
import kr.supergate.shoppingfolder.domain.sign.UserSignHistory;
import kr.supergate.shoppingfolder.domain.user.DeviceType;
import kr.supergate.shoppingfolder.exception.UnauthorizedException;
import kr.supergate.shoppingfolder.exception.account.AccountPasswordWrongLockException;
import kr.supergate.shoppingfolder.exception.account.SigninAccountException;
import kr.supergate.shoppingfolder.exception.model.ErrorMessage;
import kr.supergate.shoppingfolder.repository.UserRepository;
import kr.supergate.shoppingfolder.util.HeaderUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Date;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountRegisterService accountRegisterService;

    @Autowired
    SessionRedisService sessionRedisService;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private UserSignHistoryService userSignHistoryService;


    public User getUserBySession(String session) {
        User user = sessionRedisService.getUser(session);
        user.setSession(session);

        return user;
    }


    public User signin(UserBody userBody) {
        if (userBody.isExternalAuthentication()) {
            RegisterUserBody registerUserBody = new RegisterUserBody();
            BeanUtils.copyProperties(userBody, registerUserBody);
            return signin(accountRegisterService.register(registerUserBody));
        }
        if (userBody.isLoginableUser()) {
            return signin(userBody.getLoginUser());
        }

        throw new SigninAccountException(ErrorMessage.INVALID_SIGNIN_INFO);
    }

    public User signin(Login loginUser) {

        User user = userRepository.selectUserByLoginId(loginUser.getLoginId());
        if (user == null)
            throw new SigninAccountException(ErrorMessage.INVALID_LOGIN_ID_OR_PASSWORD);

        loginPasswordCheck(user, loginUser);


        return signin(user);
    }

    public User signin(User user) {
        String session = SessionUtils.generate();
        DeviceType deviceType = HeaderUtil.getDeviceType();
        sessionRedisService.setUser(session, user, deviceType);

        User userLogin = new UserLogin();
        BeanUtils.copyProperties(user, userLogin);

        if(userSignHistoryService.getSignHistoryCountBySignTypeAndUserId(SignType.IN, user.getUserId()) == 0L)
            ((UserLogin)userLogin).setFirstLogin(true);

        userSignHistoryService.addSignHistory(SignType.IN, user.getUserId());
        userLogin.setSession(session);

        return userLogin;
    }


    public void loginPasswordCheck(User user, Login loginUser){
        List<UserSignHistory> userSignHistories = userSignHistoryService.selectSignHistoryCountByUserIdOrderByIdDescLimit30(user.getUserId());

        int passwordWrongCount = 0;
        Date lastPasswordWrongDate = null;
        for(UserSignHistory userSignHistory : userSignHistories) {
            if(userSignHistory.getType() == SignType.IN_PASSWORD_WRONG) {
                passwordWrongCount++;
                if(lastPasswordWrongDate == null)
                    lastPasswordWrongDate = userSignHistory.getSignDate();
            }
            else break;
        }

        if(passwordWrongCount > 10) {
            long diff = new Date().getTime() - lastPasswordWrongDate.getTime();
            long min = diff/60000;
            if(min < 10) { //10분미만
                throw new AccountPasswordWrongLockException(ErrorMessage.INVALID_LOGIN_PASSWORD_WRONG_LOCK);
            }
        }

        if (!isValidPassword(user.getPassword(), loginUser.getPassword())) {
            userSignHistoryService.addSignHistory(SignType.IN_PASSWORD_WRONG, user.getUserId());
            //4번의 기록과 위에 1번이 추가되어 5번이다
            //5번째 패스워드 틀리면 안내문구가 나간다
            if(passwordWrongCount == 4){
                throw new AccountPasswordWrongLockException(ErrorMessage.INVALID_LOGIN_PASSWORD_WRONG_WARNING_LOCK);
            }
            throw new SigninAccountException(ErrorMessage.INVALID_LOGIN_ID_OR_PASSWORD);
        }
    }

    public boolean isValidPassword(String encryptedActualPassword, String inputPassword) {
        return bCryptPasswordEncoder.matches(inputPassword, encryptedActualPassword);
    }


    public Admin getAdminBySession(String session) {
//        Admin admin = sessionRedisService.getAdmin(session);
//        admin.setSession(session);
//
//        System.out.println(session);
//        System.out.println(admin.getAdminId());
//
//        return admin;
        return null;
    }

    public Admin signinAdmin(String accountId, String password) {

//        Admin admin = adminService.getAdminIdByLoginId(accountId);
//        if(admin == null ) {
//
//            if (result == null) {
//                throw new UnauthorizedException("Admin does not exist : id " + accountId);
//            }
//
//            admin = adminService.getAdminIdByMid(result.getSyrupStoreAdminResult().getMemberId());
//            admin.setLoginId(accountId);
//            admin.setPassword(password);
//            admin = adminService.updateAdmin(admin);
//        } else {
//            if(!isValidPassword(admin.getPassword(),password))
//                throw new UnauthorizedException("Admin does not exist : id " + accountId);
//        }
//
//
//        admin.setPassword(null);
//
//
//        String session = SessionUtils.generate();
//
//        sessionRedisService.setAdmin(session, admin);
//
//        admin.setSession(session);
//
//        return admin;
        return null;
    }


    public void signoutAdmin(String session) {

//        sessionRedisService.deleteAdmin(session);
    }
}
