package com.etnetera.hr.configuration;

import com.etnetera.hr.specification.converter.StringValueConverterFactory;
import com.etnetera.hr.specification.converter.ValueConverterFactory;
import com.etnetera.hr.specification.factory.JScriptFrameWorkSpecificationFactory;
import com.etnetera.hr.specification.field.FieldsDefinition;
import com.etnetera.hr.specification.field.JScriptFrameworkFieldsDefinition;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of Spring beans
 */
@Configuration
public class ApplicationConfiguration {

    /**
     * Create Jackson ObjectMapper bean
     * @return ObjectMapper bean
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }


    /**
     * Create bean factory building String value converters  to another types
     * @param objectMapper instance of Object Mapper
     * @return bean factory building String value converters  to another types
     */
    @Bean
    public ValueConverterFactory<String> stringValueConverterFactory(ObjectMapper objectMapper) {
        return new StringValueConverterFactory(objectMapper);
    }

    /**
     * Create bean of JavaScriptFrameworksSpecification factory for seaching data (Spring Data)
     * @param converterFactory String value converter  to another types
     * @param fieldsDefinition Class describing available fields for search
     * @return JavaScriptFrameworksSpecification factory for seaching data
     */
    @Bean
    public JScriptFrameWorkSpecificationFactory jScriptFrameWorkSpecificationFactory(ValueConverterFactory<String> converterFactory,
                                                                                     @Qualifier("jscriptFrameWork")
                                                                                             FieldsDefinition fieldsDefinition) {
        return new JScriptFrameWorkSpecificationFactory(converterFactory, fieldsDefinition);
    }

    /**
     * Class describing available fields for searching in JavaScriptFramework
     * @return Class describing available fields for searching
     */
    @Bean(name = {"jscriptFrameWork"})
    public FieldsDefinition jscriptFrameworkFieldsDefinition() {
        return new JScriptFrameworkFieldsDefinition();
    }
}
