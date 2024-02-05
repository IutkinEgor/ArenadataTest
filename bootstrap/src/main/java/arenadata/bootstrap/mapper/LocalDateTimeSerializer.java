package arenadata.bootstrap.mapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeSerializer extends StdSerializer<LocalDateTime> {

    public LocalDateTimeSerializer(Class<LocalDateTime> t) {
        super(t);
    }

    // Custom serializer for LocalDateTime to serialize it as a timestamp
    @Override
    public void serialize(LocalDateTime value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String formattedDate = value.format(DateTimeFormatter.ISO_DATE_TIME);
        jsonGenerator.writeString(formattedDate);
    }

}
