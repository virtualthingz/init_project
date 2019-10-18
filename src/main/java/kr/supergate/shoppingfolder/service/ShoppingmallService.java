package kr.supergate.shoppingfolder.service;

import kr.supergate.shoppingfolder.common.SessionUtils;
import kr.supergate.shoppingfolder.domain.Shoppingmall;
import kr.supergate.shoppingfolder.domain.User;
import kr.supergate.shoppingfolder.domain.account.login.Login;
import kr.supergate.shoppingfolder.domain.account.login.UserBody;
import kr.supergate.shoppingfolder.domain.account.login.UserLogin;
import kr.supergate.shoppingfolder.domain.account.register.RegisterUserBody;
import kr.supergate.shoppingfolder.domain.sign.SignType;
import kr.supergate.shoppingfolder.domain.sign.UserSignHistory;
import kr.supergate.shoppingfolder.domain.user.DeviceType;
import kr.supergate.shoppingfolder.exception.account.AccountPasswordWrongLockException;
import kr.supergate.shoppingfolder.exception.account.SigninAccountException;
import kr.supergate.shoppingfolder.exception.model.ErrorMessage;
import kr.supergate.shoppingfolder.repository.ShoppingmallRepository;
import kr.supergate.shoppingfolder.repository.UserRepository;
import kr.supergate.shoppingfolder.util.HeaderUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ShoppingmallService {

    @Autowired
    ShoppingmallRepository shoppingmallRepository;

    public void insertShoppingmall(Shoppingmall shoppingmall) {
         shoppingmallRepository.insertShoppingmall(shoppingmall);
    }

    public List<Shoppingmall> selectShoppingmalls() {
        return shoppingmallRepository.selectShoppingmalls();
    }

    public void deleteShoppingmall(String id) {
        shoppingmallRepository.deleteShoppingmall(id);
    }

    public Shoppingmall selectShoppingmall(String id) {
        return shoppingmallRepository.selectShoppingmall(id);
    }
}
