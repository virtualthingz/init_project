package kr.supergate.shoppingfolder.domain.account.register;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.supergate.shoppingfolder.domain.User;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

/**
 * Created by Hang Jo on 2019.10.06
 */
@Data
@ApiModel(value="SsoUser", description="form bean for registering new user")
public class SsoUser {
    @ApiModelProperty(value = "이름")
    private String name;
    @NotBlank
    @ApiModelProperty(value = "user unique key for sso", required = true)
    private String ssoSerial;

    @JsonIgnore
    @ApiModelProperty(value = "가입 형태", required = true, allowableValues = "LOGIN_ID,FACEBOOK")
    private User.AuthType authType;

    public boolean isValidForm() {
        return !StringUtils.isEmpty(ssoSerial);
    }
}

