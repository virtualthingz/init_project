package kr.supergate.shoppingfolder.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class ModifiableUser {
    @ApiModelProperty(value = "이름")
    private String name;

    @ApiModelProperty(value = "휴대전화 번호")
    private String mdn;

    @ApiModelProperty(value = "기본 주소")
    private String addressBasic;
    @ApiModelProperty(value = "상세 주소")
    private String addressDetail;
    @ApiModelProperty(value = "우편번호")
    private String zipCode;

    @ApiModelProperty(value = "이메일")
    private String email;

    @ApiModelProperty(value = "기존 패스워드 : [패스워드 변경] 시 사용")
    private String oldPassword;
    @ApiModelProperty(value = "신규 패스워드 : [패스워드 변경] 시 사용")
    private String newPassword;

    @JsonIgnore
    private String userId;

    public boolean isUpdatableBasicData() {
        return StringUtils.isNotBlank(name)
                || StringUtils.isNotBlank(mdn)
                || StringUtils.isNotBlank(addressBasic)
                || StringUtils.isNotBlank(addressDetail)
                || StringUtils.isNotBlank(zipCode)
                || StringUtils.isNotBlank(email);
    }

    public boolean isUpdatablePassword() {
        return StringUtils.isNotBlank(oldPassword)
                && StringUtils.isNotBlank(newPassword);
    }

    public boolean isUpdatable() {
        return isUpdatableBasicData() || isUpdatablePassword();
    }
}