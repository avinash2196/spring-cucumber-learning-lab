package com.demo.SpringBootCucumberDemo.filter;

import jakarta.servlet.FilterConfig;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Servlet filter that logs details of every incoming HTTP request.
 *
 * <p>This filter intercepts each request before it reaches a controller, wraps it in a
 * {@link CachedBodyHttpServletRequest} to allow the body to be read multiple times, then
 * logs the HTTP method, URL, all headers, and the request body.</p>
 *
 * <p><b>Where this pattern appears in production:</b> Request logging filters are standard
 * in microservices for audit trails, debugging, and integration with distributed tracing
 * systems (e.g. injecting correlation IDs). They are a classic example of a cross-cutting
 * concern that belongs in a filter rather than in business logic.</p>
 *
 * <p><b>Design note:</b> Logging the full request body is useful for debugging but should
 * be disabled or redacted in production for payloads containing sensitive data (PII, tokens).
 * A real implementation would check a feature flag or log level threshold before logging
 * the body.</p>
 *
 * <p>Registration with Spring is handled by {@link RequestLoggingFilterConfig}.</p>
 */
public class RequestLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialisation needed for this logging filter
    }

    /**
     * Wraps the request to cache the body, logs request details, then continues the chain.
     *
     * @param request  the incoming servlet request
     * @param response the servlet response
     * @param chain    the remaining filter chain
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request instanceof HttpServletRequest httpRequest) {
            CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(httpRequest);

            logger.info("Request Method: {}, URL: {}", cachedRequest.getMethod(), cachedRequest.getRequestURL());

            cachedRequest.getHeaderNames().asIterator().forEachRemaining(header ->
                    logger.info("Header: {} = {}", header, cachedRequest.getHeader(header))
            );

            logger.info("Request Body: {}", cachedRequest.getCachedBody());

            chain.doFilter(cachedRequest, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // No cleanup needed
    }
}