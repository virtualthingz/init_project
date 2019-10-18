package kr.supergate.shoppingfolder.domain.admin;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Admin {

    private String adminId;

    private String name;

    private String mid;

    private String session;

    private String loginId;

    private String password;

    private AdminRole role;

}