package kr.supergate.shoppingfolder.domain.account.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.supergate.shoppingfolder.domain.User;
import kr.supergate.shoppingfolder.domain.account.register.SsoUser;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel(value="UserBody", description="body data for login user")
public class UserBody {

    @ApiModelProperty(value = "아이디 기반 회원가입 정보. authType = LOGIN_ID 일 때 mandatory")
    private Login loginUser;

    @ApiModelProperty(value = "페이스북 기반 회원가입 정보. authType = FACEBOOK 일 때 mandatory")
    private SsoUser ssoUser;


    @ApiModelProperty(value = "동의한 약관 ID 목록")
    private List<Integer> agreedClauses;

    @NotNull
    @ApiModelProperty(value = "가입 형태", required = true, allowableValues = "LOGIN_ID,GOOGLE")
    private User.AuthType authType;

    public boolean isGoogleUser() {
        return ssoUser != null && User.AuthType.GOOGLE.equals(authType);
    }


    public boolean isLoginableUser() {
        return loginUser != null && User.AuthType.LOGIN_ID.equals(authType);
    }

    public boolean isExternalAuthentication() {
        return (isGoogleUser());
    }
}
