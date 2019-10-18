package kr.supergate.shoppingfolder.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by skplanet on 2015. 1. 20..
 */
@Component
@Data
@ConfigurationProperties(prefix = "image")
public class Image {
	
	public static String BASE_URL;
	
	
	public void setBaseURL(String baseURL) {
		BASE_URL = baseURL;
	}
}
