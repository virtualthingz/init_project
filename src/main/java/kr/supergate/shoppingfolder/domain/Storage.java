package kr.supergate.shoppingfolder.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@Data
@ConfigurationProperties(prefix = "storage")
public class Storage {

	/**
	 * Folder location for storing files
	 */
	private String url;

	private String location;

}
