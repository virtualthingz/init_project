package kr.supergate.shoppingfolder.service;


import kr.supergate.shoppingfolder.domain.User;
import kr.supergate.shoppingfolder.domain.account.login.Login;
import kr.supergate.shoppingfolder.domain.account.login.UserLogin;
import kr.supergate.shoppingfolder.domain.account.register.LoginUser;
import kr.supergate.shoppingfolder.domain.account.register.RegisterUserBody;
import kr.supergate.shoppingfolder.domain.account.register.SsoUser;
import kr.supergate.shoppingfolder.exception.account.DuplicateAccountException;
import kr.supergate.shoppingfolder.exception.account.RegisterAccountException;
import kr.supergate.shoppingfolder.exception.model.ErrorMessage;
import kr.supergate.shoppingfolder.repository.UserRepository;
import kr.supergate.shoppingfolder.util.ActiveProfileUtil;
import kr.supergate.shoppingfolder.util.HeaderUtil;
import kr.supergate.shoppingfolder.util.LoginIdUtil;
import kr.supergate.shoppingfolder.util.PasswordUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service("AccountRegisterService")
public class AccountRegisterService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

//    @Autowired
//    private CacheService cacheService;

    @Autowired
    private AgreementService agreementService;


    @Autowired
    private UserService userService;


//    @Autowired
//    private UserSignHistoryService userSignHistoryService;



    @Transactional
    public User register(RegisterUserBody registerUserBody) {
        User user = null;

        if(registerUserBody.isExternalAuthentication()) {
            SsoUser ssoUser = registerUserBody.getSsoUser();
            ssoUser.setAuthType(registerUserBody.getAuthType());
            user = register(ssoUser);

        } else if(registerUserBody.isLoginableUser()) {
            user = register(registerUserBody.getLoginUser());
        }

        if(user == null)
            throw new RegisterAccountException(ErrorMessage.INVALID_REGISTER_FORM);

        List<Integer> agreedClauses = registerUserBody.getAgreedClauses();
        if(agreedClauses == null || agreedClauses.isEmpty())
            throw new RegisterAccountException(ErrorMessage.INVALID_REGISTER_FORM);

        agreementService.agreeClauses(user.getUserId(), agreedClauses);

        return user;
    }

    @Transactional
    public User register(SsoUser ssoUser) {
        if(!ssoUser.isValidForm())
            throw new RegisterAccountException(ErrorMessage.INVALID_REGISTER_FORM);

        User user = new UserLogin();
        BeanUtils.copyProperties(ssoUser, user);
        user.setUserType(User.UserType.REAL);

        User userBySsoSerial = userService.getUserBySsoSerial(ssoUser.getSsoSerial());

        // register
        if(userBySsoSerial == null) {
            ((UserLogin)user).setFirstLogin(true);
            userService.insert(user);
            userRepository.insertSsoUser(user);

            // signin
        } else {
            user = userBySsoSerial;
        }

        return user;
    }

    @Transactional
    public User register(LoginUser loginUser) {
        if(!loginUser.isValidForm())
            throw new RegisterAccountException(ErrorMessage.INVALID_REGISTER_FORM);

        if(!isVerifiedCode(loginUser.getMdn(), loginUser.getMdnVerificationCode()))
            throw new RegisterAccountException(ErrorMessage.INVALID_REGISTER_FORM);

        if(!LoginIdUtil.isValidLoginId(loginUser.getLoginId()))
            throw new RegisterAccountException(ErrorMessage.INVALID_LOGIN_ID_PATTERN);

        User user = new UserLogin();
        BeanUtils.copyProperties(loginUser, user);

        user.setUserType(User.UserType.REAL);
        String password = loginUser.getPassword();
        if(!PasswordUtil.isValidPassword(password))
            throw new RegisterAccountException(ErrorMessage.INVALID_PASSWORD_PATTERN);

        user.setPassword(bCryptPasswordEncoder.encode(loginUser.getPassword()));

        User userByLoginId = userRepository.selectUserByLoginId(user.getLoginId());
        if(userByLoginId != null)
            throw new DuplicateAccountException(ErrorMessage.LOGIN_ID_DUPLICATION);

        List<User> loginUserFromNameAndMdn = userService.getUserByNameAndMdn(loginUser.getName(), loginUser.getMdn());
        if(!loginUserFromNameAndMdn.isEmpty())
            throw new DuplicateAccountException(ErrorMessage.LOGIN_ID_USER_DUPLICATION);

        userService.insert(user);
        userRepository.insertLoginUser(user);

        return user;
    }



    public boolean isVerifiedCode(String mdn, String verificationCode) {
        // bypass code verification on development mode
        if(ActiveProfileUtil.isDevelopment()) {
            if("1234".equals(verificationCode))
                return  true;
        }

//        if(StringUtils.isEmpty(verificationCode))
//            throw new RegisterAccountException(ErrorMessage.INVALID_VERIFICATION_CODE);

//        if(!verificationCode.equals(cacheService.getMdnCacheForVerification(mdn)))
//            throw new RegisterAccountException(ErrorMessage.INVALID_VERIFICATION_CODE);

        return true;
    }




}

