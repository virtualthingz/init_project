package kr.supergate.shoppingfolder.domain.sign;

import lombok.Data;

import java.util.Date;

@Data
public class UserSignHistory {
    private String userId;
    private String id;
    private SignType type;
    private Date signDate;
}
