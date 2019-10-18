<#--<@compress single_line=true>-->
<#if products?has_content>
[
    <#assign productSize = products?size>
    <#assign index=0>
    <#list products as product>
    {
    "U_TYPE":"U",
    "id": "${product.productId!"0"}",
    "merchant_id": "${product.merchantId!""}",
    "product_category_id_1": "${product.productCategoryId!""}",
    "product_category_id_2": "${product.productParentCategoryId!""}",
    "product_category_name_1": "${product.productCategoryId!"0"}",
    "product_category_name_2": "${product.productParentCategoryId!""}",
    "product_main_image_id": "${product.productMainImageId!""}",
    "name": "${product.productName!""}",
    "sku": "${product.sku!""}",
    "code": "${product.code!""}",
    "priority": "${product.priority?c!""}",
    "display_status": "${product.displayStatus!""}",
    "confirm_status": "${product.confirmStatus!""}",
    "sales_status": "${product.salesStatus!""}",
    "shipping_status": "${product.validMinimumPrice!""}",
    "regular_price": "${product.regularPrice!"0"}",
    "discount_price": "${product.discountPrice!"0"}",
    "shipping_charge": "${product.shippingCharge!"0"}",
    "shipping_charge_yn": "${product.shippingChargeYn!""}",
    "created_date": "${product.createdDate!""}",
    "updated_date": "${product.updatedDate!""}",
    "confirmed_date": "${product.confirmedDate!""}",
    "confirm_comment": "${(product.confirmComment)!""}",
    "fabric": "${(product.fabric)!""}",
    "promotion_enabled": "${(product.promotionEnabled)!""}",
    "sales_channels": "${(product.salesChannels)!""}",
    "made_in": "${(product.madeIn)!""}"
    }<#rt>
        <#if productSize-1 != index>
            <#lt>,
        <#else>

        </#if>
        <#assign index = index+1>
    </#list>
]
</#if>
<#--</@compress>-->
