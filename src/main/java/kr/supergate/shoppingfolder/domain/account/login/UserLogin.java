package kr.supergate.shoppingfolder.domain.account.login;


import kr.supergate.shoppingfolder.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserLogin extends User {
    private boolean isFirstLogin;

}
