package kr.supergate.shoppingfolder.exception.model;

import lombok.Getter;

public enum ErrorMessage {

    // 400
    INVALID_SIGNIN_INFO("error.message.invalid.signin.info"),
    INVALID_LOGIN_ID_PATTERN("error.message.invalid.loginid.pattern"),
    INVALID_PASSWORD_PATTERN("error.message.invalid.password.pattern"),
    INVALID_REGISTER_FORM("error.message.invalid.register.form"),
    INVALID_VERIFICATION_CODE("error.message.invalid.verification.code"),
    INVALID_JOIN_RECOMMENDATION_CODE("error.message.invalid.join.recommendation.code"),
    INVALID_JOIN_RECOMMENDATION_MDN("error.message.invalid.join.recommendation.mdn"),
    INVALID_NOT_EXIST_MDN("error.message.invalid.not.exist.mdn"),
    INVALID_EXIST_MDN_MANY("error.message.invalid.exist.mdn.many"),
    INVALID_EXIST_MDN("error.message.invalid.exist.mdn"),
    INVALID_EXIST_MDN_FACEBOOK("error.message.invalid.exist.mdn.facebook"),
    INVALID_EXIST_MDN_LOGIN_ID("error.message.invalid.exist.mdn.login"),
    INVALID_EXIST_MDN_WEIBO("error.message.invalid.exist.mdn.weibo"),
    INVALID_NOT_EXIST_OAUTH_TOKEN("error.message.invalid.not.exist.oauth.token"),
    INVALID_EXIST_MDN_SYRUP("error.message.invalid.exist.mdn.syrup"),
    INVALID_RESET_PASSWORD_FORM("error.message.invalid.notify.reset.password.form"),
    INVALID_LOGIN_ID_OR_PASSWORD("error.message.invalid.loginid.or.password"),
    INVALID_LOGIN_PASSWORD_WRONG_LOCK("error.message.invalid.password.lock"),
    INVALID_LOGIN_PASSWORD_WRONG_WARNING_LOCK("error.message.invalid.password.warning.lock"),
    INVALID_LOGIN_ID("error.message.invalid.loginid"),
    INVALID_NEW_PASSWORD_PATTERN("error.message.invalid.new.password.pattern"),
    UNAVAILABLE_PASSWORD_OPERATION("error.message.unavailable.password.operation"),
    INVALID_SUPPORT_MERCHANT_TRANSFER_DONE("error.message.support.merchant.transfer.done"),
    SALES_CANCEL_SHIPPING_STATUS("error.message.sales.cancel.shipping.status"),
    SALES_INSUFFICIENT_TICKET("error.message.sales.insufficient.ticket"),
    SALES_LOTTERY_EVENT_ERROR("error.message.sales.lottery.event.msg"),
    SHOPPINGCART_ITEM_LIMIT("error.message.shoppingcart.limit.count"),
    PRODUCT_LIKE_ITEM_LIMIT("error.message.product.like.limit.count"),

    INVALID_CLAUSE("error.message.invalid.clause"),

    AUTHCODE("error.message.coupon.authcode"),
    PROMOTION("error.message.coupon.promotion"),
    VALID_START_END_DATE("error.message.coupon.valid_start_end_date"),
    VALID_USE_COUNT("error.message.coupon.valid_use_count"),
    ONETIME_USE("error.message.coupon..onetime_use"),
    ONETIME_DOWNLOAD("error.message.coupon..onetime_download"),
    BIGGER_THAN_REALPRICE("error.message.coupon.bigger_than_realprice"),
    FIRST_PURCHASE_ONLY("error.message.coupon.first_purchase_only"),
    COUPON_CHECK_LIMIT_COUNT("error.message.coupon.check.limit.count"),
    COUPON_CHECK_LIMIT_PRICE("error.message.coupon.check.limit.price"),
    COUPON_CHECK_MERCHANT("error.message.coupon.check.merchant"),

    SHIPPING_SALES_NOT_CALCULATION("error.message.sales.shipping.not.calculation"),

    ACCOUNT_LEAVE_EXIST_SALES("error.message.account.leave.exist.sales"),

    SEARCH_KEYWORD_NOT_FOUND("error.message.search.keyword.not.found"),

    PAYMENT_CANCEL_ERROR("error.message.cancel.not.found"),

    // 401
    INVALID_OLD_PASSWORD("error.message.invalid.old.password"),
    ELEVENTALK_INVALID_TOKEN("error.message.eleventalk.invalid.token"),
    ELEVENTALK_NOT_VALID_USER("error.message.eleventalk.not.valid.user"),
    ELEVENTALK_NOT_VALID_MERCHANT("error.message.eleventalk.not.valid.merchant"),
    ELEVENTALK_USER_NOT_LOGIN("error.message.eleventalk.user.not.login"),
    ELEVENTALK_MERCHANT_NOT_LOGIN("error.message.eleventalk.merchant.not.login"),

    // 408
    PAYMENT_AGENCY_TIMEOUT("timeout.message.payment.agency"),

    // 404
    ALREADY_EVENT_LOG("error.message.already.event.log"),
    SERVER_TIME_CHECK("error.message.server.time.check.log"),
    EVENT_INFO_NOT_FOUND("error.event.info.not.found"),

    // 409
    LOGIN_ID_DUPLICATION("error.message.loginid.duplicate"),
    LOGIN_ID_APPROVAL("error.message.loginid.user.approval"),
    LOGIN_ID_APPROVAL_STATUS_CHECK("error.message.loginid.user.approval.check"),
    LOGIN_ID_APPROVAL_STATUS_CLOSE("error.message.loginid.user.approval.close"),
    LOGIN_ID_USER_DUPLICATION("error.message.loginid.user.duplicate"),

    // 503
    DELAYS_SENDING_VERIFICATION_CODE("error.message.delays.sending.verification.code"),
    OUT_OF_STOCK("error.message.out.of.stock"),
    OUT_OF_STOCK_SHOPPINGCART("error.message.out.of.stock.shoppingcart"),
    JUSO_API_UNAVALIABLE("error.message.juso.api.unavailable"),
    DATA_NOT_FOUND("error.message.data.not.found"),
    LABELING_INFO_SCARCE("error.message.lebeling.info.scarce"),
    CATEGORY_NOT_FOUND("error.message.notfound.category"),
    PRICE_MANIPULATED("error.message.sales.price.manipulated"),
    SEARCH_SERVER_CHECK("error.message.search.server.check"),

    ELEVENTALK_PUSH_UNAVALIABLE("error.message.eleventalk.push.unavailable"),


    // 500
    INVALID_TALKPLUS_RESULT("error.message.talkplus.invalid.result"),
    
    EXCEL_UPDATE_SHIPPING_ID_NOT_FOUND("error.message.excel.update.shipping.id.not.found"),
    EXCEL_UPDATE_SHIPPING_CODE_NOT_FOUND("error.message.excel.update.shipping.code.not.found"),
    EXCEL_UPDATE_SHIPPING_CODE_FORMAT("error.message.excel.update.shipping.code.format"),
    EXCEL_UPDATE_DIFF_MERCHANT("error.message.excel.update.diff.merchant.id"),
    PAYMENT_AGENCY_ERROR("error.message.payment.agency"),


    NOT_SUPPORT_IEXPRESS("error.iexpress.not.support");



    @Getter
    private String value;

    ErrorMessage(String value) {
        this.value = value;
    }
}
