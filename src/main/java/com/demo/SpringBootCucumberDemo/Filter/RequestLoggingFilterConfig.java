package com.demo.SpringBootCucumberDemo.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration that registers the {@link RequestLoggingFilter} with the
 * embedded servlet container.
 *
 * <p>Using {@link FilterRegistrationBean} gives fine-grained control over URL patterns,
 * filter order, and whether the filter is enabled or disabled — without relying on
 * the {@code @WebFilter} annotation, which requires additional servlet scanning setup.</p>
 *
 * <p><b>Learning note:</b> Filters registered via {@code FilterRegistrationBean} are part
 * of the full servlet filter chain and execute before Spring's {@code DispatcherServlet}.
 * This makes them ideal for cross-cutting concerns like logging, authentication checks,
 * and CORS handling.</p>
 */
@Configuration
public class RequestLoggingFilterConfig {

    /**
     * Registers {@link RequestLoggingFilter} to intercept all URL patterns.
     *
     * @return the configured filter registration bean
     */
    @Bean
    public FilterRegistrationBean<RequestLoggingFilter> requestLoggingFilter() {
        FilterRegistrationBean<RequestLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestLoggingFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
