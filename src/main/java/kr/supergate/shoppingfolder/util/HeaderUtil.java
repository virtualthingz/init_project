package kr.supergate.shoppingfolder.util;

import kr.supergate.shoppingfolder.common.XHttpHeader;
import kr.supergate.shoppingfolder.domain.user.Country;
import kr.supergate.shoppingfolder.domain.user.DeviceType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Locale;

@Component
public class HeaderUtil {

    public static String getValue(String httpHeader){
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        String countryCode = sra.getRequest().getHeader(httpHeader);
        return countryCode;
    }

    public static DeviceType getDeviceType() {
        try {
            return DeviceType.valueOf(getValue(XHttpHeader.X_OS));
        } catch (Exception e) {
            return DeviceType.IOS;
        }
    }

    public static Country getCountry() {
        String value = getValue(XHttpHeader.X_COUNTRY_CODE);
        if(value != null)
            return Country.valueOf(value);
        else return Country.KR;
    }

    public static Locale getLocal() {
        try {
            String countryCode = getValue(XHttpHeader.X_COUNTRY_CODE);
            return getLocal(countryCode);
        }catch(Exception e){
            return Locale.KOREA;
        }
    }

    public static Locale getLocal(String countryCode) {
        if (countryCode == null)
            return Locale.KOREA;

        if(countryCode.equals(Country.KR.name())){
            return Locale.KOREA;
        }else if(countryCode.equals(Country.CN.name())){
            return Locale.CHINA;
        }
        return Locale.KOREA;
    }


    public static int getVersion() {
        try {
            return Integer.parseInt(getValue(XHttpHeader.X_APP_VERSION_CODE));
        } catch (Exception e) {
            return 0;
        }
    }

}
