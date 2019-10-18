<#--<@compress single_line=true>-->
{
    <#if coupons?has_content>
    "offset": "${offset!""}",
    "limit": "${limit!""}",
    "totalCount": "${totalCount?c!"0"}",
    "couponCount": "${coupons?size}",
    "coupons": [
        <#assign couponSize = coupons?size>
        <#assign index=0>
        <#list coupons as coupon>
        {
        "couponId": "${coupon.couponId!"0"}",
        "authCode": "${coupon.authCode!""}",
        "couponName": "${coupon.couponName!""}",
        "description": "${coupon.description!""}",
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
        "usedCount": "${(coupon.usedCount)?c!"0"}",
        "remainedUseCount": "${(coupon.remainedUseCount)?c!"0"}",
        "validStartDate": "${coupon.validStartDate!""}",
        "validEndDate": "${coupon.validEndDate!""}",
        "displayYn": "${coupon.displayYn?c!"0"}",
        "deleteYn": "${coupon.deleteYn?c!"0"}",
        "createdDate": "${(coupon.createdDate)!""}",
        "extraCondition": "${((coupon.extraCondition)!"")?html}"
        }<#rt>
        <#if couponSize-1 != index>
            <#lt>,
        <#else>

        </#if>
        <#assign index = index+1>
        </#list>
    ]
    </#if>
}
<#--</@compress>-->
