package kr.supergate.shoppingfolder.domain.account.register;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.supergate.shoppingfolder.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

/**
 * Created by Hang Jo on 2019.10.06
 */
@Data
@ApiModel(value = "LoginUser", description = "form bean for registering new user")
public class LoginUser {
    @NotBlank
    @ApiModelProperty(value = "회원의 이름", required = true)
    private String name;
    @NotBlank
    @ApiModelProperty(value = "로그인 아이디", required = true)
    private String loginId;
    @NotBlank
    @ApiModelProperty(value = "평문 패스워드", required = true)
    private String password;
    @NotBlank
    @ApiModelProperty(value = "휴대전화 번호", required = true)
    private String mdn;
    @NotBlank
    @ApiModelProperty(value = "휴대폰 점유인증 번호 [3분 후 만료]", required = true)
    private String mdnVerificationCode;
    @JsonIgnore
    @ApiModelProperty(value = "가입 형태", required = true, allowableValues = "LOGIN_ID,FACEBOOK")
    private User.AuthType authType = User.AuthType.LOGIN_ID;

    @ApiModelProperty(value = "MGM 추천인 코드", required = false)
    private String joinRecommendationUserCode;


    public boolean isValidForm() {
        return !StringUtils.isEmpty(name)
                && !StringUtils.isEmpty(loginId)
                && !StringUtils.isEmpty(password)
                && !StringUtils.isEmpty(mdn);
    }

    public boolean isValidFormForChina() {
        return  !StringUtils.isEmpty(loginId)
                && !StringUtils.isEmpty(password)
                && !StringUtils.isEmpty(mdn);
    }
}

