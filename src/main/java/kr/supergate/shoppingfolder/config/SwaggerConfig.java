package kr.supergate.shoppingfolder.config;


import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.models.dto.ResponseMessage;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import scala.Option;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger
@ComponentScan("kr.supergate.shoppingfolder.controller")
public class SwaggerConfig {

    @Autowired
    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean //Don't forget the @Bean annotation
    public SwaggerSpringMvcPlugin customImplementation(){
//        this.springSwaggerConfig.defaultSwaggerPathProvider().setApiResourcePrefix("api");
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(apiInfo())
                .globalResponseMessage(RequestMethod.GET, responseMessages())
                .globalResponseMessage(RequestMethod.POST, responseMessages())
                .globalResponseMessage(RequestMethod.PUT, responseMessages())
                .globalResponseMessage(RequestMethod.DELETE, responseMessages())
                .swaggerGroup("default")
                .includePatterns(".*")
                .build();
    }

    @Bean //Don't forget the @Bean annotation
    public SwaggerSpringMvcPlugin customImplementationApp(){
//        this.springSwaggerConfig.defaultSwaggerPathProvider().setApiResourcePrefix("api");
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(apiInfo())
                .globalResponseMessage(RequestMethod.GET, responseMessages())
                .globalResponseMessage(RequestMethod.POST, responseMessages())
                .globalResponseMessage(RequestMethod.PUT, responseMessages())
                .globalResponseMessage(RequestMethod.DELETE, responseMessages())
                .swaggerGroup("app")
                .includePatterns(".*app.*")
                .build();
    }

    @Bean //Don't forget the @Bean annotation
    public SwaggerSpringMvcPlugin customImplementationWeb(){
//        this.springSwaggerConfig.defaultSwaggerPathProvider().setApiResourcePrefix("api");
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(apiInfo())
                .globalResponseMessage(RequestMethod.GET, responseMessages())
                .globalResponseMessage(RequestMethod.POST, responseMessages())
                .globalResponseMessage(RequestMethod.PUT, responseMessages())
                .globalResponseMessage(RequestMethod.DELETE, responseMessages())
                .swaggerGroup("web")
                .includePatterns(".*web.*")
                .build();
    }


    @Bean //Don't forget the @Bean annotation
    public SwaggerSpringMvcPlugin customImplementationPartner(){
//        this.springSwaggerConfig.defaultSwaggerPathProvider().setApiResourcePrefix("api");
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(apiInfo())
                .globalResponseMessage(RequestMethod.GET, responseMessages())
                .globalResponseMessage(RequestMethod.POST, responseMessages())
                .globalResponseMessage(RequestMethod.PUT, responseMessages())
                .globalResponseMessage(RequestMethod.DELETE, responseMessages())
                .swaggerGroup("partner")
                .includePatterns(".*partner.*")
                .build();
    }

    @Bean //Don't forget the @Bean annotation
    public SwaggerSpringMvcPlugin customImplementationAdmin(){
//        this.springSwaggerConfig.defaultSwaggerPathProvider().setApiResourcePrefix("api");
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(apiInfo())
                .globalResponseMessage(RequestMethod.GET, responseMessages())
                .globalResponseMessage(RequestMethod.POST, responseMessages())
                .globalResponseMessage(RequestMethod.PUT, responseMessages())
                .globalResponseMessage(RequestMethod.DELETE, responseMessages())
                .swaggerGroup("admin")
                .includePatterns(".*admin.*")
                .build();
    }

    @Bean //Don't forget the @Bean annotation
    public SwaggerSpringMvcPlugin customImplementationTest(){
//        this.springSwaggerConfig.defaultSwaggerPathProvider().setApiResourcePrefix("api");
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(apiInfo())
                .globalResponseMessage(RequestMethod.GET, responseMessages())
                .globalResponseMessage(RequestMethod.POST, responseMessages())
                .globalResponseMessage(RequestMethod.PUT, responseMessages())
                .globalResponseMessage(RequestMethod.DELETE, responseMessages())
                .swaggerGroup("test")
                .includePatterns(".*test.*")
                .build();
    }

    @Bean //Don't forget the @Bean annotation
    public SwaggerSpringMvcPlugin customImplementationExternal(){
//        this.springSwaggerConfig.defaultSwaggerPathProvider().setApiResourcePrefix("api");
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(apiInfo())
                .globalResponseMessage(RequestMethod.GET, responseMessages())
                .globalResponseMessage(RequestMethod.POST, responseMessages())
                .globalResponseMessage(RequestMethod.PUT, responseMessages())
                .globalResponseMessage(RequestMethod.DELETE, responseMessages())
                .swaggerGroup("external")
                .includePatterns(".*external.*")
                .build();
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "ShoppingFolders Documentation",
                "",
                "",
                "",
                "",
                ""
        );

        return apiInfo;
    }

    private List<ResponseMessage> responseMessages() {
        List<ResponseMessage> responseMessages = new ArrayList();

        responseMessages.add(responseMessage(HttpStatus.OK));
        responseMessages.add(responseMessage(HttpStatus.NO_CONTENT));

        responseMessages.add(responseMessage(HttpStatus.BAD_REQUEST));
        responseMessages.add(responseMessage(HttpStatus.UNAUTHORIZED));
        responseMessages.add(responseMessage(HttpStatus.NOT_FOUND));

        responseMessages.add(responseMessage(HttpStatus.INTERNAL_SERVER_ERROR));
        responseMessages.add(responseMessage(HttpStatus.SERVICE_UNAVAILABLE));

        return responseMessages;
    }

    private ResponseMessage responseMessage(HttpStatus httpStatus) {
        return new ResponseMessage(httpStatus.value(), httpStatus.getReasonPhrase(), "");
    }

}
