package com.common.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


public class MessageConfig {

    private static final String UTF_8 = "UTF-8";
    private static final String BASE_NAME = "classpath:common-messages";

    @Bean
    public MessageSource commonMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(BASE_NAME);
        messageSource.setDefaultEncoding(UTF_8);
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean commonValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(commonMessageSource());
        return bean;
    }
}
