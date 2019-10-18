package kr.supergate.shoppingfolder.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.google.common.collect.ImmutableList;
import kr.supergate.shoppingfolder.common.CalendarUtils;
import kr.supergate.shoppingfolder.common.XssObjectMapper;
import kr.supergate.shoppingfolder.domain.address.Address;
import kr.supergate.shoppingfolder.interceptor.AppUpgradeRequiredInterceptor;
import kr.supergate.shoppingfolder.interceptor.SessionAdminInterceptor;
import kr.supergate.shoppingfolder.interceptor.SessionUserInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableWebMvc
@EnableAsync
@ComponentScan(
        basePackages = "kr.supergate.shoppingfolder",
        excludeFilters = @ComponentScan.Filter(Configuration.class)
)
public class WebMvcConfig implements WebMvcConfigurer{


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(appUpgradeRequiredInterceptor())
//                .addPathPatterns("/app/**");

//        registry.addInterceptor(sessionAdminInterceptor())
//                .addPathPatterns("/admin/**");
//                .excludePathPatterns("/admin/address")
//                .excludePathPatterns("/admin/test/loadTest")
//                .excludePathPatterns("/admin/accounts/signin");

        registry.addInterceptor(sessionUserInterceptor())
//                .addPathPatterns("/app/**")
//                .addPathPatterns("/app/accounts/signin")
//                .addPathPatterns("/app/like")
//                .addPathPatterns("/app/sales/**")
//                .addPathPatterns("/app/salesgroups/**")
//                .addPathPatterns("/app/accounts/leave")
//                .addPathPatterns("/app/event/fortunedeal")
//                .addPathPatterns("/app/support")
//                .addPathPatterns("/app/support/{id}")
                .addPathPatterns("/app/user/**");
//                .addPathPatterns("/app/coupons/**")
//                .addPathPatterns("/app/couponbox/**")
//                .addPathPatterns("/app/syruppay/**")
//                .addPathPatterns("/app/address/**")
//                .addPathPatterns("/app/shipping/address/**")
//                .addPathPatterns("/app/shoppingcart/**")
//                .addPathPatterns("/app/11talk/user/**")
//                .addPathPatterns("/web/accounts/isLogin")
//                .excludePathPatterns("/app/accounts/register");
    }

    @Bean
    public AppUpgradeRequiredInterceptor appUpgradeRequiredInterceptor() {
        return new AppUpgradeRequiredInterceptor();
    }

//    @Bean
//    public SessionAdminInterceptor sessionAdminInterceptor() {
//        return new SessionAdminInterceptor();
//    }

    @Bean
    public SessionUserInterceptor sessionUserInterceptor() {
        return new SessionUserInterceptor();
    }



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/**").addResourceLocations("/");
    }



    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(true).
                ignoreAcceptHeader(true).
                useJaf(false).
                 defaultContentType(MediaType.APPLICATION_JSON).
                mediaType("html", MediaType.TEXT_HTML).
                mediaType("json", MediaType.APPLICATION_JSON).
                mediaType("jsp", MediaType.TEXT_PLAIN);
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setOrder(2);
        viewResolver.setPrefix("/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        commonsMultipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024); //5 * 1024 * 1024 (5mb)
        return commonsMultipartResolver;
    }


    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasenames("messages/messages", "messages/error_messages");

        return messageSource;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // converters.addAll(getMessageConverters());
        converters.add(jsonConverter());
        converters.add(new StringHttpMessageConverter());
    }


    private List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(jsonConverter());

        return messageConverters;
    }

    private MappingJackson2HttpMessageConverter jsonConverter() {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        jackson2HttpMessageConverter.setPrettyPrint(true);

        XssObjectMapper objectMapper = xssObjectMapper();

        // (de)serialize java8 LocalDate to json
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        objectMapper.registerModule(javaTimeModule);

        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        return jackson2HttpMessageConverter;
    }

    @Bean
    public XssObjectMapper xssObjectMapper() {
        XssObjectMapper objectMapper = (XssObjectMapper) new XssObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(CalendarUtils.getISO8601Format());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }

    @Bean
    public RestTemplate restFormTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(ImmutableList.<HttpMessageConverter<?>>builder()
                .add(new MarshallingHttpMessageConverter(jaxb2Marshaller(), jaxb2Marshaller()))
                .add(new FormHttpMessageConverter())
                .add(new StringHttpMessageConverter(Charset.forName("UTF-8"))).build());
        return restTemplate;
    }

    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setClassesToBeBound(Address.class);
        return jaxb2Marshaller;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
