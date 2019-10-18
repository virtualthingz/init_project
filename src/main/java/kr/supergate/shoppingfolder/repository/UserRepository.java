package kr.supergate.shoppingfolder.repository;

import kr.supergate.shoppingfolder.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface UserRepository {
    List<User> selectUserMdnById(String id);
    User selectUser(String id);
    User selectLoginUser(String id);
    User selectUserByCi(String ci);
    List<User> selectUserByMdn(String mdn);
    List<User> selectUserByName(String name);
    User selectUserByLoginId(String loginId);
    User selectUserBySsoSerial(String ssoSerial);
    User selectUserByNameAndLoginId(@Param("name") String name, @Param("loginId") String loginId);
    List<User> selectUserByNameAndMdn(@Param("name") String name, @Param("mdn") String mdn);
    User selectLoginUserByMdn(@Param("mdn") String mdn);
    void insertUser(User user);
    void insertSsoUser(User user);
    void insertLoginUser(User user);
    void updateUser(User user);
    void updateLoginUser(User user);
    void deleteUser(String id);
    String selectSsoSerialByUserId(String userId);

}

