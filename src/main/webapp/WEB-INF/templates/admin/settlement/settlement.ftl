<#--<@compress single_line=true>-->
{
<#if shippingGroupList?has_content>
"merchantId": "${headInfo.merchantId?c!""}",
"merchantName": "${headInfo.merchantName!""}",
"commissionTotal": "${headInfo.commissionTotal?c!"0"}",
"taxTotal": "${headInfo.taxTotal?c!"0"}",
"couponPriceTotal": "${headInfo.couponPriceTotal?c!"0"}",
"merchantCouponPriceTotal": "${headInfo.merchantCouponPriceTotal?c!"0"}",
"bankAccount": "${headInfo.bankAccount!""}",
"accountHolder": "${headInfo.accountHolder!""}",
"uncollectedAmount": ${uncollectedAmount?c!"0"},
"dueDate": "",
"shippingGroupsCount": "${shippingGroupList?size}",
"shippingGroups": [
    <#assign shippingGroupSize = shippingGroupList?size>
    <#assign indexA=0>
    <#list shippingGroupList as shippingGroup>
    {
        <#assign settleSize = shippingGroup.settlements?size>
        <#assign indexB=0>
        "shippingCode": "${shippingGroup.shippingCode}",
        "settlementCount": "${settleSize}",
        "settlements": [
        <#if shippingGroup.settlements?has_content>
            <#list shippingGroup.settlements as settle>
            {
                "salesId": "${(settle.salesId?c)!""}",
                "country": "${(settle.country)!""}",
                "productId": "${(settle.productId?c)!""}",
                "userId": "${(settle.userId?c)!""}",
                "productName": "${(settle.productName)!""}",
                "isHotDeal": "${settle.promotionEnabled!""}",
                "regularPrice": "${settle.regularPrice?c!"0"}",
                "discountPrice": "${settle.discountPrice?c!"0"}",
                "promotionPrice": "${settle.promotionPrice?c!"0"}",
                "couponPrice": "${settle.couponPrice?c!"0"}",
                "isMerchantCoupon": "${settle.merchantCoupon?c}",
                "totalPaid": "${settle.totalPaid?c!"0"}",
                "realPrice": "${settle.realPrice?c!"0"}",
                "skpSupportPrice": "${settle.skpSupportPrice?c!"0"}",
                "shippingCharge": "${settle.shippingCharge?c!"0"}",
                "commission": "${settle.commission?c!"0"}",
                "selectedCommissionRate": "${settle.selectedCommissionRate!"0"}",
                "tax": "${settle.tax?c!"0"}",
                "settlementPrice": "${settle.settlementPrice?c!"0"}",
                "currentStatus": "${settle.currentStatus!""}",
                "memo": "",
                "createdDate": "${(settle.createdDate)!""}"
            }<#rt>
            <#if settleSize-1 != indexB>
                <#lt>,
            <#else>

            </#if>
            <#assign indexB = indexB+1>
            </#list>
        </#if>
        ]
    }<#rt>
    <#if shippingGroupSize-1 != indexA>
        <#lt>,
    <#else>

    </#if>
    <#assign indexA = indexA+1>
    </#list>
]
</#if>
}
<#--</@compress>-->
<#--
{
<#if shippingGroupList?has_content>
    "shippingGroupsCount": ${shippingGroupList?size},
    "shippingGroups": [
        <#list shippingGroupList as shippingGroup>
        {
            "shippingCode": "${shippingGroup.shippingCode}",
            "settlementCount": ${shippingGroup.settlements?size},
            "settlements": [
            <#if shippingGroup.settlements?has_content>
            <#list shippingGroup.settlements as settle>
                <#assign regularPrice = settle.regularPrice!"0"/>
                <#assign discountPrice = settle.discountPrice!"0"/>
                <#assign promotionPrice = settle.promotionPrice!"0"/>
                <#assign shippingCharge = settle.shippingCharge!"0"/>
                <#assign skpSuportPrice = 0 />
                <#assign currentPrice = 0 />
                <#assign isHotDeal = false />
                <#if settle.promotionEnabled?default("0") == "1">
                    <#assign isHotDeal = true/>
                    <#assign skpSuportPrice = (promotionPrice?number)/2 />
                </#if>
                {
                    "id": "${(settle.id)!""}",
                    "createdDate": "${(settle.createDate)!""}",
                    "salesCode": "${(settle.salesCode)!""}",
                    "productId": "${(settle.productId)!""}",
                    "isHotDeal": ${isHotDeal?string},
                    "regularPrice": ${regularPrice!""},
                    "discountPrice": ${discountPrice!""},
                    "promotionPrice": ${promotionPrice!""},
                    "totalPaid": ${settle.totalPaid!""},
                    "skpSupportPrice": ${skpSuportPrice},
                    "shippingCharge": ${shippingCharge!""},
                    <#if isHotDeal>
                        <#assign currentPrice = promotionPrice?number/>
                    <#elseif discountPrice != "0">
                        <#assign currentPrice = discountPrice?number/>
                    <#else>
                        <#assign currentPrice = regularPrice?number />
                    </#if>
                    <#assign settlementPrice = currentPrice + shippingCharge?number + skpSuportPrice />
                    "settlementPrice": ${settlementPrice?c},
                    "userComment": "${(settle.userComment)!""}"
                },
            </#list>
            </#if>
            ]
        },
</#list>
</#if>
    ]
}
-->