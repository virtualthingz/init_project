package kr.supergate.shoppingfolder.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain=true)
@ApiModel(value = "User", description = "user ")
public class User {

    public enum UserType {
        REAL, FAKE
    }

    public enum AuthType {
        INVALID, FACEBOOK, LOGIN_ID, GOOGLE
    }



    private String userId;

    @JsonIgnore
    private String ci;
    private String name;

    private String mdn;
    private Date birthday;


    private String addressBasic;
    private String addressDetail;
    private String zipCode;

    private String email;

    private String gender;
    private Date createdDate;

    private String session;

    private UserType userType;

    private String ssoSerial;

    private String loginId;
    @JsonIgnore
    private String password;

    private AuthType authType ;

    private Date deletedDate;

    private Date dataDeletedDate;

    private String recommendationCode;

    private String joinRecommendationUserId;
}

