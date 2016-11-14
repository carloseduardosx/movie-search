package com.carloseduardo.movie.search.data.source.remote.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class GsonDateSerializer implements JsonSerializer<Date>, JsonDeserializer<Date> {

    private final DateTimeFormatter dateTimeFormatter;
    private final DateFormat dateFormat;

    public GsonDateSerializer() {

        DateTimeParser[] timeParsers = {
                DateTimeFormat.forPattern("yyyy-MM-dd").getParser(),
                DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ").getParser(),
                DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss").getParser(),
                DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").getParser(),
                DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ").getParser(),
                DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").getParser(),
                DateTimeFormat.forPattern("MM/dd/yyyy'T'HH:mm:ss.SSS'Z'").getParser(),
                DateTimeFormat.forPattern("MM/dd/yyyy'T'HH:mm:ss.SSS").getParser(),
                DateTimeFormat.forPattern("MM/dd/yyyy'T'HH:mm:ss.SSSZ").getParser(),
                DateTimeFormat.forPattern("MM/dd/yyyy'T'HH:mm:ssZ").getParser(),
                DateTimeFormat.forPattern("dd-MM-yyyy").getParser(),
                DateTimeFormat.forPattern("MM-dd-yyyy").getParser(),
                DateTimeFormat.forPattern("yyyy/MM/dd").getParser(),
                DateTimeFormat.forPattern("dd/MM/yyyy").getParser(),
                DateTimeFormat.forPattern("MM/dd/yyyy").getParser()
        };

        dateTimeFormatter = new DateTimeFormatterBuilder()
                .append(null, timeParsers)
                .toFormatter()
                .withZoneUTC();

        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public synchronized JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {

        return new JsonPrimitive(dateFormat.format(date));
    }

    @Override
    public synchronized Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {

        String date = jsonElement.getAsString();

        if (date != null && !date.isEmpty()) {

            return dateTimeFormatter.parseDateTime(jsonElement.getAsString()).toDate();
        } else {

            return null;
        }
    }
}