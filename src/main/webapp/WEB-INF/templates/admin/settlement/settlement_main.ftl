{
    <#if resultList?has_content><#t>
    "startDate": "${headInfo.startDate!""}",
    "endDate": "${headInfo.endDate!""}",
    "merchantCount": "${resultList?size}",
    "merchants": [
        <#assign settleSize = resultList?size>
        <#assign index=0>
        <#list resultList as item>
        {
            "merchantId": "${item.merchantId?c!""}",
            "merchantName": "${item.merchantName!""}",
            "completeCount": "${item.completeCount?c!"0"}",
            "cancelCount": "${item.cancelCount?c!"0"}",
            "uncollectedAmount": ${item.uncollectedAmount?c!"0"},
            "totalPaid": "${item.totalPaid?c!"0"}"
        }<#rt>
        <#if settleSize-1 != index>
            <#lt>,
        <#else>

        </#if>
        <#assign index = index+1>
        </#list>
    ]
    </#if>
}