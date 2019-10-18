package kr.supergate.shoppingfolder.interceptor;


import kr.supergate.shoppingfolder.common.ClassDispatcher;
import kr.supergate.shoppingfolder.common.SessionUtils;
import kr.supergate.shoppingfolder.common.VersionUtils;
import kr.supergate.shoppingfolder.common.XHttpHeader;
import kr.supergate.shoppingfolder.domain.ApiInfo;
import kr.supergate.shoppingfolder.exception.UpgradeRequiredException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppUpgradeRequiredInterceptor extends HandlerInterceptorAdapter {
    final Logger logger = LoggerFactory.getLogger(AppUpgradeRequiredInterceptor.class);

    private static final String DELIMITER = "\t";

//    @Autowired
//    private ApiService apiService;

//    @Autowired
//    private InitService initService;

//    @Autowired
//    private MosaicCacheService mosaicCacheService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

       // String reqUrl = request.getRequestURL().toString();
//        if (reqUrl.indexOf("/external/11talk") != -1) {
//            Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
//            String merchantId = (String) pathVariables.get("merchantId");
//            pathVariables.put("merchantId", "99991");
//            return super.preHandle(request, response, handler);
//        }

        String lang = null;
        String appVersion = null;
        String device = null;

        appVersion = request.getHeader(XHttpHeader.X_APP_VERSION);
        device = request.getHeader(XHttpHeader.X_DEVICE);

        printAccessLog(request);

        request.setAttribute(XHttpHeader.X_APP_VERSION, appVersion);
        request.setAttribute(XHttpHeader.X_DEVICE, device);

        ApiInfo apiInit = null;
        //String userAgentHeader = request.getHeader(HttpHeaders.USER_AGENT);
        if(StringUtils.isNotBlank(appVersion) && StringUtils.isNotBlank(device)) {
//            if (request.getRequestURI().startsWith("/partner/")) {
//                apiInit = apiService.getApiWithCache(ApiInfo.ServiceType.PARTNER, ClassDispatcher.getCountry());
//            } else {
//                apiInit = apiService.getApiWithCache(ApiInfo.ServiceType.APP, ClassDispatcher.getCountry());
//            }
        }

        if (apiInit != null) { // PO에서 들어왔을 경우가 아니면

            if (StringUtils.isNotEmpty(device) && device.contains("Android")) {
                if (VersionUtils.isNewer(apiInit.getForcedAndroidVersion(), appVersion)) {
                    throw new UpgradeRequiredException("Upgrade for this android version is required");
                }
            }

            if (StringUtils.isNotEmpty(device) && device.contains("ios")) {
                if (VersionUtils.isNewer(apiInit.getForcedIOSVersion(), appVersion)) {
                    throw new UpgradeRequiredException("Upgrade for this ios version is required");
                }
            }
        }
        return super.preHandle(request, response, handler);
    }

    private void printAccessLog(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();

        sb.append(request.getRequestURI()).append(DELIMITER);
        sb.append(request.getRemoteAddr()).append(DELIMITER);
        sb.append(request.getHeader(XHttpHeader.X_APP_VERSION)).append(DELIMITER);
        sb.append(request.getHeader(XHttpHeader.X_DEVICE)).append(DELIMITER);


        sb.append(SessionUtils.getKey(request));


        logger.info(sb.toString());
    }
}
