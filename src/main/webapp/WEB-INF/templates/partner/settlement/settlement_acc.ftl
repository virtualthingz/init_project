<#--<@compress single_line=true>-->
{
<#if settlements?has_content>
"merchantId": "${headInfo.merchantId?c!""}",
"merchantName": "${headInfo.merchantName!""}",
"commissionTotal": "${headInfo.commissionTotal?c!"0"}",
"completeCount": "${headInfo.completeCount?c!"0"}",
"completePriceTotal": "${headInfo.completePriceTotal?c!"0"}",
"cancelCount": "${headInfo.cancelCount?c!"0"}",
"cancelPriceTotal": "${headInfo.cancelPriceTotal!"0"}",
"skpSupportPriceTotal": "${headInfo.skpSupportPriceTotal?c!"0"}",
"shippingChargeTotal": "${headInfo.shippingChargeTotal?c!"0"}",
"couponPriceTotal": "${headInfo.couponPriceTotal?c!"0"}",
"merchantCouponPriceTotal": "${headInfo.merchantCouponPriceTotal?c!"0"}",
"taxTotal": "${headInfo.taxTotal?c!"0"}",
"settlementPriceTotal": "${headInfo.settlementPriceTotal?c!"0"}",
"startDate": "${headInfo.startDate!""}",
"endDate": "${headInfo.endDate!""}",
"settlementCount": "${settlements?size}",
"settlements": [
    <#if settlements?has_content>
        <#assign settleSize = settlements?size>
        <#assign index=0>
        <#list settlements as settle>
        {
        "settleId": "${(settle.settleId?c)!""}",
        "salesId": "${(settle.salesId?c)!""}",
        "country": "${(settle.country)!""}",
        "productId": "${(settle.productId?c)!""}",
        "productName": "${(settle.productName)!""}",
        "isHotDeal": "${settle.promotionEnabled!""}",
        "productCategoryName": "${settle.productCategoryName!""}",
        "merchantCategoryName": "${settle.merchantCategoryName!""}",
        "merchantName": "${settle.merchantName!""}",
        "userName": "${settle.userName!""}",
        "realPrice": "${settle.realPrice?c!"0"}",
        "skpSupportPrice": "${settle.skpSupportPrice?c!"0"}",
        "couponPrice": "${settle.couponPrice?c!"0"}",
        "isMerchantCoupon": "${settle.merchantCoupon?c}",
        "shippingCharge": "${settle.shippingCharge?c!"0"}",
        "commission": "${settle.commission!"0"}",
        "tax": "${settle.tax?c!"0"}",
        "settlementPrice": "${settle.settlementPrice?c!"0"}",
        "currentStatus": "${settle.currentStatus!""}",
        "memo": "${settle.memo!""}",
        "dueDate": "${settle.dueDate!""}",
        "createdDate": "${(settle.createdDate)!""}"
        }<#rt>
        <#if settleSize-1 != index>
            <#lt>,
        <#else>

        </#if>
        <#assign index = index+1>
        </#list>
    </#if>
]
</#if>
}
<#--</@compress>-->
