package kr.supergate.shoppingfolder.config;

import kr.supergate.shoppingfolder.common.CustomResourceLoader;
import kr.supergate.shoppingfolder.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Created by HangJo on 15. 12. 14..
 */
//@Configuration
public class InitConfig {

    @Autowired
    private RedisCacheService cacheService;

//    @Autowired
//    private InitService initService;


    @Bean
    public CustomResourceLoader resourceLoader() {
        return new CustomResourceLoader();
    }


    @Bean
    public ServletContextInitializer initializer() {
        return new ServletContextInitializer() {
            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {

                String profile = System.getProperty("spring.profiles.active");

//                ApiInfo appInit = initService.getApiInfo();
//                mosaicCacheService.setCache("KR_AppInit_" + profile, appInit);

//                ApiInfo cn_appInit = cn_initService.getApiInfo();
//                mosaicCacheService.setCache("CN_AppInit_" + profile, cn_appInit);
            }
        };
    }
}
