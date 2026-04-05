package com.demo.SpringBootCucumberDemo.filter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A servlet request wrapper that caches the request body so it can be read multiple times.
 *
 * <p>By default, an HTTP request's input stream can only be read once. This wrapper reads
 * the entire body upfront and stores it as a {@code String}, then re-exposes it through
 * {@link #getInputStream()} and {@link #getReader()} on every call.</p>
 *
 * <p><b>Why this is needed:</b> The {@link RequestLoggingFilter} needs to log the request
 * body before passing control downstream. Without caching, the downstream controller would
 * receive an empty stream because the body was already consumed by the filter.</p>
 *
 * <p><b>Design note:</b> In production systems this pattern is common but carries a memory
 * cost proportional to the request body size. Large payloads (e.g. file uploads) should be
 * excluded from body-caching filters.</p>
 */
public class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {

    private final String cachedBody;

    /**
     * Constructs the wrapper, eagerly reading and caching the original request body.
     *
     * @param request the original HTTP request
     * @throws IOException if the request body cannot be read
     */
    public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        }
        cachedBody = stringBuilder.toString();
    }

    /**
     * Returns a fresh {@link ServletInputStream} backed by the cached body bytes.
     * Safe to call multiple times.
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(cachedBody.getBytes());
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                // Non-blocking I/O not required for this synchronous learning example
            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

    /** Returns a {@link BufferedReader} over the cached body. Safe to call multiple times. */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    /** Returns the raw cached request body as a {@code String}. */
    public String getCachedBody() {
        return cachedBody;
    }
}
