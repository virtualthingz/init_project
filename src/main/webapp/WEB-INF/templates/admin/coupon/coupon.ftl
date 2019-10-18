<#--<@compress single_line=true>-->
{
<#if isSuccess == 'true'>
    "success": "${isSuccess}",
    "message": "${message!""}",
    "realPrice": "${realPrice?c!"0"}",
    "coupon": {
        "couponId": "${coupon.couponId!"0"}",
        "authCode": "${coupon.authCode!""}",
        "couponName": "${coupon.couponName!""}",
        "downloadable": "${coupon.downloadable!"0"}",
        "applicableCountry": "${coupon.applicableCountry!""}",
        "applicableScope": "${coupon.applicableScope!""}",
        "applicableObject": "${coupon.applicableObject!""}",
        "discountType": "${coupon.discountType!""}",
        "merchantId": "${coupon.merchantId!""}",
        "merchantCategoryId": "${coupon.merchantCategoryId!""}",
        "discountPrice": "${(coupon.discountPrice)?c!"0"}",
        "discountRate": "${(coupon.discountRate)?string("0.##")!"0"}",
        "maximumDiscountPrice": "${(coupon.maximumDiscountPrice)?c!"0"}",
        "reusableCount": "${(coupon.reusableCount)?c!"0"}",
        "validMinimumPrice": "${(coupon.validMinimumPrice)?c!"0"}",
        "validUseCount": "${(coupon.validUseCount)?c!"0"}",
        "validStartDate": "${coupon.validStartDate!""}",
        "validEndDate": "${coupon.validEndDate!""}",
        "displayYn": "${coupon.displayYn?c!"0"}",
        "deleteYn": "${coupon.deleteYn?c!"0"}",
        "createdDate": "${(coupon.createdDate)!""}",
        "extraCondition": "${((coupon.extraCondition)!"")?html}"
    }
<#else>
    "success": "${isSuccess}",
    "message": "${message!""}"
</#if>
}
<#--</@compress>-->
