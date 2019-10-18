package kr.supergate.shoppingfolder.service;

import kr.supergate.shoppingfolder.domain.User;
import kr.supergate.shoppingfolder.domain.user.ModifiableUser;
import kr.supergate.shoppingfolder.exception.NoContentException;
import kr.supergate.shoppingfolder.exception.account.DuplicateMdnAccountException;
import kr.supergate.shoppingfolder.exception.model.ErrorMessage;
import kr.supergate.shoppingfolder.exception.user.InvalidUpdateException;
import kr.supergate.shoppingfolder.repository.UserEndPointRepository;
import kr.supergate.shoppingfolder.repository.UserRepository;

import kr.supergate.shoppingfolder.util.PasswordUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Hang Jo on 2019.10.06
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserEndPointRepository userEndPointRepository;


    public List<User> getUserByMdn(String mdn){
        return userRepository.selectUserByMdn(mdn);
    }

    public List<User> getUserByNameAndMdn(String name, String mdn){
        return userRepository.selectUserByNameAndMdn(name, mdn);
    }

    public boolean isLoginableUser(User.AuthType authType) {
        return User.AuthType.LOGIN_ID.equals(authType);
    }

//    public User getUserBySsoSerial(String ssoSerial) {
//        return userRepository.selectUserBySsoSerial(ssoSerial);
//    }
    public User getUserBySsoSerial(String ssoSerial) {
    return userRepository.selectUserBySsoSerial(ssoSerial);
}

    public boolean isGoogleUser(User.AuthType authType) {
        return User.AuthType.GOOGLE.equals(authType);
    }


    public boolean isExternalAuthentication(String userId) {
        User user = getUser(userId);
        return (isGoogleUser(user.getAuthType())) ;
    }

    public User getUser(String id){

        User userResult = userRepository.selectUser(id);

        if (userResult == null) {
            System.out.println("user :"+userResult);
            throw new NoContentException("user about #"+id+" not exist");
        }

        return userResult;
    }

    @Transactional
    public User insert(User user) {

        try {
            userRepository.insertUser(user);
        } catch (Exception e){ /***/ }
        return userRepository.selectUser(user.getUserId());
    }

    @Transactional
    public User update(ModifiableUser modifiableUser) {
        if(modifiableUser.isUpdatablePassword())
            updatePassword(modifiableUser);

        User user = new User();
        BeanUtils.copyProperties(modifiableUser, user);
        if(modifiableUser.isUpdatableBasicData())
            user = update(user);

        return getUser(user.getUserId());
    }

    @Transactional
    public User update(User user) {
        if(StringUtils.isEmpty(user.getUserId()))
            throw new NoContentException("user id not exist");

        if(user.getDeletedDate() != null) {
            userEndPointRepository.deleteUserEndPoint(user.getUserId());
        }

        if(!StringUtils.isEmpty(user.getMdn())) {
            User originUser = getUser(user.getUserId());

            if(!StringUtils.isEmpty(originUser.getName())) {
                List<User> users = getUserByNameAndMdn(originUser.getName(), user.getMdn());;
                User nameAndMdnUser = users.stream().findFirst().orElse(null);
                if (nameAndMdnUser != null && !nameAndMdnUser.getUserId().equals(user.getUserId())) {
                    validateDuplicateUpdateOriginUserType(nameAndMdnUser);
                }

            } else { // WEIBO 경우에는 이름이 없다
                List<User> mdnUsers  = getUserByMdn(user.getMdn());
                if(mdnUsers.stream().findFirst().isPresent()) {
                    User mdnUser = mdnUsers.stream().findFirst().get();
                    if(!mdnUser.getUserId().equals(user.getUserId())) {
                        validateDuplicateUpdateOriginUserType(mdnUser);
                    }
                }
            }
        }


        userRepository.updateUser(user);
        return userRepository.selectUser(user.getUserId());
    }

    @Transactional
    public User updatePassword(ModifiableUser modifiableUser) {
        validatePassword(modifiableUser);

        User user = new User();
        BeanUtils.copyProperties(modifiableUser, user);
        user.setPassword(bCryptPasswordEncoder.encode(modifiableUser.getNewPassword()));
        userRepository.updateLoginUser(user);

        return user;
    }

    private boolean validatePassword(ModifiableUser modifiableUser) {
        User user = userRepository.selectLoginUser(modifiableUser.getUserId());
        if(user == null)
            throw new NoContentException("계정이 존재하지 않습니다.");

        String encryptedOldPassword = user.getPassword();
        if(StringUtils.isEmpty(encryptedOldPassword))
            throw new InvalidUpdateException(ErrorMessage.UNAVAILABLE_PASSWORD_OPERATION);

        if(!bCryptPasswordEncoder.matches(modifiableUser.getOldPassword(), encryptedOldPassword))
            throw new InvalidUpdateException(ErrorMessage.INVALID_OLD_PASSWORD);

        if(!PasswordUtil.isValidPassword(modifiableUser.getNewPassword()))
            throw new InvalidUpdateException(ErrorMessage.INVALID_NEW_PASSWORD_PATTERN);

        return true;
    }

    public void validateDuplicateUpdateOriginUserType(User user){
        switch (user.getAuthType()) {
            case FACEBOOK:
                throw new DuplicateMdnAccountException(ErrorMessage.INVALID_EXIST_MDN_FACEBOOK);
            case LOGIN_ID:
                throw new DuplicateMdnAccountException(ErrorMessage.INVALID_EXIST_MDN_LOGIN_ID);
            default:
                throw new DuplicateMdnAccountException(ErrorMessage.INVALID_EXIST_MDN_LOGIN_ID);
        }
    }


    public User getUserByloginId(String loginId) {
        User user = userRepository.selectUserByLoginId(loginId);
        if(user == null)
            throw new NoContentException("Can't find user for loginId : " + loginId);
        return user;
    }

}
