package kr.supergate.shoppingfolder.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by skplanet on 2015. 3. 12..
 */
@Data
public class ApiInfo {
    public enum ServiceType {
        APP, PARTNER,
    }

    private String apiId;
    private String forcedAndroidVersion;
    private String lastAndroidVersion;
    private String forcedIOSVersion;
    private String lastIOSVersion;
    private String apiVersion;
    private String webVersion;
    private ServiceType serviceType;

    private Date updatedDate;

    private Integer expirationDateForVisit;

    private String leaveUrl;
    private String termsOfUseUrl;
    private String privacyUrl;
    private String termsprivatecollentuseMUrl;
    private String termsprivatecollentuseOUrl;
    private String termsprivatecollentuseOEventUrl;
    private String noticeUrl; //공지사항 URL
    private String termsOfLocationUrl;
    private String pushPolicyUrl;
    private String termsOfOpenSourceForAndroid;
    private String termsOfOpenSourceForIOS;
    private String androidUpdateUri;
    private String iosUpdateUri;

    private String welcomeMessage;
    private String mgmInvitationMessage;
    private String mgmInvitationImgUrl;
    private String mgmInvitationEventUrl;

    @JsonIgnore
    private String status;

    @JsonIgnore
    private String messages;


    // ad-hoc, experimental
    @JsonIgnore
    private String extraJson;
    private Map extra;
    private BigDecimal exchangeRate;

    private String country;

    private Integer shippingRefValue;


    private boolean isFirstPurchase = false;
}
