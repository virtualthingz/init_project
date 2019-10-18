package kr.supergate.shoppingfolder.domain.account.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class Login {
    @NotBlank
    @ApiModelProperty(value = "로그인 아이디", required = true)
    private String loginId;
    @NotBlank
    @ApiModelProperty(value = "평문 패스워드", required = true)
    private String password;
}
