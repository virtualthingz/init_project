package kr.supergate.shoppingfolder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(exclude = { MultipartAutoConfiguration.class })
public class ShoppingfolderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingfolderApplication.class, args);
    }

}
