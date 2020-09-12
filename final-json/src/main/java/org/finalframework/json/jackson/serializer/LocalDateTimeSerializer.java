

package org.finalframework.json.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-28 13:05:25
 * @since 1.0
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    private DateTimeFormatter formatter;

    public LocalDateTimeSerializer() {
    }

    public LocalDateTimeSerializer(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }


    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (localDateTime == null) return;
//        DateTimeFormatter formatter = DateTimeFormatterContext.getDateTimeFormatter();
        if (formatter != null) {
            gen.writeString(localDateTime.format(formatter));
        } else {
            gen.writeNumber(localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        }
    }
}