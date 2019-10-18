package kr.supergate.shoppingfolder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        // encodes in about 8 ms on local machine - 8-core(2.7GHz each)
        return new BCryptPasswordEncoder(9);
    }

    @Bean
    public ShaPasswordEncoder sha256PasswordEncoder() {
        return new ShaPasswordEncoder(256);
    }

    @Bean
    public Md5PasswordEncoder md5PasswordEncoder() {
        return new Md5PasswordEncoder();
    }
}