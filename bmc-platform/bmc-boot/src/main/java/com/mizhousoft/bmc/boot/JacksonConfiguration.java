package com.mizhousoft.bmc.boot;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude;

import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ext.javatime.deser.LocalDateDeserializer;
import tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateSerializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;
import tools.jackson.databind.module.SimpleModule;

/**
 * 配置
 *
 */
@Configuration
public class JacksonConfiguration
{
	private static final String dateFormat = "yyyy-MM-dd";

	private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

	@Bean
	public JsonMapperBuilderCustomizer jsonCustomizer()
	{
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);

		SimpleModule module = new SimpleModule();
		module.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
		module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
		module.addSerializer(new LocalDateSerializer(dateFormatter));
		module.addSerializer(new LocalDateTimeSerializer(dateTimeFormatter));

		return builder -> {
			builder.changeDefaultPropertyInclusion(incl -> incl.withValueInclusion(JsonInclude.Include.NON_NULL));
			builder.changeDefaultPropertyInclusion(incl -> incl.withContentInclusion(JsonInclude.Include.NON_NULL));
			builder.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			builder.defaultDateFormat(new SimpleDateFormat(dateTimeFormat));
			builder.defaultTimeZone(TimeZone.getTimeZone("GMT+8"));
			builder.addModule(module);
		};
	}
}
