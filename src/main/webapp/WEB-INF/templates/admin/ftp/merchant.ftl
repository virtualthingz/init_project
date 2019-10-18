<#--<@compress single_line=true>-->
<#if merchants?has_content>
[
    <#assign productSize = merchants?size>
    <#assign index=0>
    <#list merchants as merchant>
    {
    "U_TYPE":"U",
    "id":"${merchant.merchantId!"0"}",
    "merchant_category_id_1":"${merchant.merchantCategoryId!"0"}",
    "merchant_category_id_2":"0",
    "merchant_category_name_1":"",
    "merchant_category_name_2":"",
    "merchant_name":"${merchant.title!""}",
    "titles":"${(merchant.titles!"")?js_string?replace("\\'","\'")}",
    "location":"${merchant.location!""}",
    "shipping_option":"${merchant.shippingOption?string("1", "0")}",
    "image_version":"${merchant.imageVersion!""}",
    "business_registration_number":"${merchant.brn!""}",
    "mobile_phone":"${merchant.mobilePhone!""}",
    "addresses":"${(merchant.addresses!"")?js_string?replace("\\'","\'")}",
    "opening_time":"${merchant.openingTime!""}",
    "holiday_opening_time":"${merchant.holidayOpeningTime!""}",
    "closing_time":"${merchant.closingTime!""}",
    "holiday_closing_time":"${merchant.holidayClosingTime!""}",
    "mid":"${merchant.mid!""}",
    "name":"${merchant.managerName!""}",
    "email":"${merchant.email!""}",
    "phone":"${merchant.mid!""}",
    "shipping_charge":"${merchant.shippingCharge!""}",
    "special_shipping_charge":"${merchant.specialShippingCharge!""}",
    "closed_days":"${(merchant.closedDays!"")?js_string?replace("\\'","\'")}",
    "latitude_tmap":"${merchant.latitudeTmap!""}",
    "longitude_tmap":"${merchant.longitudeTmap!""}",
    "merchant_owner_name":"${merchant.merchantOwnerName!""}",
    "merchant_owner_birthday":"${merchant.merchantOwnerBirthday!""}",
    "bank_name":"${merchant.bankName!""}",
    "bank_account":"${merchant.bankAccount!""}",
    "account_holder":"${merchant.accountHolder!""}",
    "cooperate_status":"${merchant.cooperateStatus!""}",
    "business_type":"${merchant.businessType!""}",
    "resident_reg_no":"${merchant.residentRegNo!""}",
    "business_category":"${merchant.businessCategory!""}",
    "business_conditions":"${merchant.businessConditions!""}",
    "mail_order_reg_no":"${merchant.mailOrderRegNo!""}",
    "manager_name":"${merchant.managerName!""}",
    "manager_email":"${merchant.managerEmail!""}",
    "manager_phone":"${merchant.managerPhone!""}",
    "commissions":"${(merchant.commissions!"")?js_string?replace("\\'","\'")}",
    "merchant_phone":"${merchant.merchantPhone!""}",
    "merchant_customer_phone":"${merchant.merchantCustomerPhone!""}",
    "notification_agreements":"${(merchant.notificationAgreements!"")?js_string?replace("\\'","\'")}",
    "shipping_ref_value":"${merchant.shippingRefValue!""}",
    "sales_channels":"${(merchant.salesChannels!"")?js_string?replace("\\'","\'")}",
    "created_date":"${merchant.createdDate?datetime!""}",
    "updated_date":"${merchant.updatedDate?datetime!""}",
    "title":"${merchant.title!""}",
    "zip_code":"${merchant.zipCode!""}",
    "closed_day":"${merchant.closedDay!""}",
    "address":"${merchant.address!""}"
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
