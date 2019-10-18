package kr.supergate.shoppingfolder.config;

//import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
//import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.RequestMethod;
//import springfox.documentation.PathProvider;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.service.ResponseMessage;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("kr.supergate.shoppingfolder.controller"))
//                .paths(PathSelectors.ant("/app/**"))
//                .build()
//                .apiInfo(apiInfo());
////                .useDefaultResponseMessages(false);
////                .globalResponseMessage(RequestMethod.GET, responseMessages())
////                .globalResponseMessage(RequestMethod.POST, responseMessages())
////                .globalResponseMessage(RequestMethod.PUT, responseMessages())
////                .globalResponseMessage(RequestMethod.DELETE, responseMessages());
////                .paths(PathSelectors.any())
//
//    }
//
//
//    private List<ResponseMessage> responseMessages() {
//        List<ResponseMessage> responseMessages = new ArrayList();
//
//        responseMessages.add(responseMessage(HttpStatus.OK));
//        responseMessages.add(responseMessage(HttpStatus.NO_CONTENT));
//
//        responseMessages.add(responseMessage(HttpStatus.BAD_REQUEST));
//        responseMessages.add(responseMessage(HttpStatus.UNAUTHORIZED));
//        responseMessages.add(responseMessage(HttpStatus.NOT_FOUND));
//
//        responseMessages.add(responseMessage(HttpStatus.INTERNAL_SERVER_ERROR));
//        responseMessages.add(responseMessage(HttpStatus.SERVICE_UNAVAILABLE));
//
//        return responseMessages;
//    }
//
//    private ResponseMessage responseMessage(HttpStatus httpStatus) {
//        return new ResponseMessage(httpStatus.value(), httpStatus.getReasonPhrase(), null, null, null);
//    }
//
//
//    private ApiInfo apiInfo() {
//        ApiInfo apiInfo = new ApiInfo("Shopping Folder APIs", "ShoppingFolder Rest API", "0.1", "", new Contact("", "", ""), "", "", Collections.emptyList());
//        return apiInfo;
//    }
//}
