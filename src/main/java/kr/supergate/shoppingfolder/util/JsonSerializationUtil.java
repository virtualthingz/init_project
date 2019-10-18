package kr.supergate.shoppingfolder.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import kr.supergate.shoppingfolder.common.CalendarUtils;


import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class JsonSerializationUtil {

    private static ObjectMapper strictObjectMapper = new ObjectMapper()
            .setDateFormat(CalendarUtils.getISO8601Format())
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);


    public static ObjectReader getStrictJsonReader(Class<?> type) {
        return strictObjectMapper.reader(type);
    }

    public static <T> T getString2Object(String value ,Class<? extends T> t) throws IOException {
        return strictObjectMapper.readValue(value, t);
    }

    public static String getObject2String(Object object) {
        try {
            StringWriter stringWriter = new StringWriter();
            strictObjectMapper.writeValue(stringWriter, object);
            return stringWriter.toString();
        } catch (Exception e){
          return null;
        }

    }

    public static Map<String, Object> getMap(String json) {
        Map<String, Object> result = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


}
