package kr.supergate.shoppingfolder.domain.account.register;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.supergate.shoppingfolder.domain.User;
import kr.supergate.shoppingfolder.service.UserService;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Hang Jo on 2019.10.06
 */
@Data
@ApiModel(value = "RegisterUserBody", description = "registering user body")
public class RegisterUserBody {

    @ApiModelProperty(value = "아이디 기반 회원가입 정보. authType = LOGIN_ID 일 때 mandatory")
    private LoginUser loginUser;

    @ApiModelProperty(value = "페이스북 기반 회원가입 정보. authType = GOOGLE 일 때 mandatory")
    private SsoUser ssoUser;

    @ApiModelProperty(value = "동의한 약관 ID 목록")
    private List<Integer> agreedClauses;

    @NotNull
    @ApiModelProperty(value = "가입 형태", required = true, allowableValues = "LOGIN_ID,GOOGLE")
    private User.AuthType authType;

    @JsonIgnore
    private UserService userService = new UserService();

    @JsonIgnore
    public boolean isLoginableUser() {
        return loginUser != null && userService.isLoginableUser(authType);
    }

    @JsonIgnore
    public boolean isGoogleUser() {
        return ssoUser != null && userService.isGoogleUser(authType);
    }

    @JsonIgnore
    public boolean isExternalAuthentication() {
        return (isGoogleUser()) ;
    }

}
