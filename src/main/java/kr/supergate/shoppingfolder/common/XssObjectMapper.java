package kr.supergate.shoppingfolder.common;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Created by Hang Jo on 2019. 10. 17..
 */
public class XssObjectMapper extends ObjectMapper {

    public XssObjectMapper() {
        final SimpleModule module =
                new SimpleModule("HTML XSS Serializer", new Version(1, 0, 0, "FINAL"));
        module.addSerializer(String.class, new JsonHtmlXssSerializer());
        this.registerModule(module);
    }
}