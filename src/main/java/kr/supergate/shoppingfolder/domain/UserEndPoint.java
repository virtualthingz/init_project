package kr.supergate.shoppingfolder.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.supergate.shoppingfolder.domain.user.DeviceType;
import lombok.Data;

import java.util.Date;


@Data
public class UserEndPoint {

    @JsonIgnore
    private String id;

    private String userId;
    private String endPointId;
    private Date updatedDate;
    private DeviceType deviceType;
    private Boolean adYn;
    private Boolean infoYn;
    private Boolean talkInfoYn;
}
