/*
 * Copyright (c) 2025-present
 * the original author(photowey<photowey@gmail.com>) or authors All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.photowey.riff.startup.printer.logging;

import java.net.InetAddress;
import java.util.Optional;
import java.util.function.Supplier;

import io.github.photowey.riff.startup.core.domain.logging.context.DocumentContext;
import io.github.photowey.riff.startup.core.util.AssertionErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.StringUtils;

/**
 * {@code StartupPrinter}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/11
 */
@SuppressWarnings("all")
public final class StartupPrinter {

    private StartupPrinter() {
        AssertionErrors.throwz(StartupPrinter.class);
    }

    private static final Logger log = LoggerFactory.getLogger(StartupPrinter.class);

    private static final String SERVER_SSL_KEY = "server.ssl.key-store";
    private static final String APPLICATION_NAME = "spring.application.name";
    private static final String SERVER_PORT = "server.port";
    private static final String SERVLET_CONTEXT_PATH = "server.servlet.context-path";
    private static final String HTTPS = "https";
    private static final String HTTP = "http";
    private static final String LOCAL_IP = "LOCAL_IP";

    public static void print(ConfigurableApplicationContext applicationContext) {
        print(applicationContext, DocumentContext.defaultContext());
    }

    public static void print(
        ConfigurableApplicationContext applicationContext,
        DocumentContext.DocumentContextBuilder builder) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        print(applicationContext, builder, () ->
            Optional.ofNullable(environment.getProperty(SERVLET_CONTEXT_PATH))
                .filter(StringUtils::hasText)
                .orElse("")
        );
    }

    public static void print(
        ConfigurableApplicationContext applicationContext,
        DocumentContext.DocumentContextBuilder builder,
        Supplier<String> contextPathFunc) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String protocol =
            Optional.ofNullable(environment.getProperty(SERVER_SSL_KEY)).map(key -> HTTPS)
                .orElse(HTTP);
        String app = environment.getProperty(APPLICATION_NAME);
        String port = environment.getProperty(SERVER_PORT);
        String[] profileActives = environment.getActiveProfiles().length == 0
            ? environment.getDefaultProfiles()
            : environment.getActiveProfiles();

        String host = tryAcquireLocalHost();

        String contextPath = contextPathFunc.get();
        String healthContextPath = Optional
            .ofNullable(environment.getProperty(SERVLET_CONTEXT_PATH))
            .filter(StringUtils::hasText)
            .orElse("");

        DocumentContext ctx = builder.protocol(protocol)
            .app(app)
            .host(host)
            .port(port)
            .profileActive(StringUtils.arrayToCommaDelimitedString(profileActives))
            .contextPath(contextPath)
            .healthContextPath(healthContextPath)
            .build();

        if (ctx.isSwaggerEnabled()) {
            withSwagger(ctx);

            return;
        }

        withoutSwagger(ctx);
    }

    private static void withoutSwagger(DocumentContext ctx) {
        log.info("\n----------------------------------------------------------\n\t"
                + "Bootstrap: the '{}' is Success!\n\t"
                + "Application: '{}' is running! Access URLs:\n\t"
                + "Local: \t\t{}://localhost:{}{}\n\t"
                + "External: \t{}://{}:{}{}\n\t"
                + "Health: \t{}://{}:{}{}/healthy\n\t"
                + "Actuator: \t{}://{}:{}{}/actuator/health\n\t"
                + "Profile(s): {}\n----------------------------------------------------------",
            ctx.getApp() + " Context",
            ctx.getApp(),
            ctx.getProtocol(), ctx.getPort(), ctx.getContextPath(),
            ctx.getProtocol(), ctx.getHost(), ctx.getPort(), ctx.getContextPath(),
            ctx.getProtocol(), ctx.getHost(), ctx.getPort(), ctx.getContextPath(),
            ctx.getProtocol(), ctx.getHost(), ctx.getPort(), ctx.getHealthContextPath(),
            ctx.getProfileActive()
        );
    }

    private static void withSwagger(DocumentContext ctx) {
        log.info("\n----------------------------------------------------------\n\t"
                + "Bootstrap: the '{}' is Success!\n\t"
                + "Application: '{}' is running! Access URLs:\n\t"
                + "Local: \t\t{}://localhost:{}{}\n\t"
                + "External: \t{}://{}:{}{}\n\t"
                + "Health: \t{}://{}:{}{}/healthy\n\t"
                + "Swagger: \t{}://{}:{}{}/{}\n\t"
                + "Actuator: \t{}://{}:{}{}/actuator/health\n\t"
                + "Profile(s): {}\n----------------------------------------------------------",
            ctx.getApp() + " Context",
            ctx.getApp(),
            ctx.getProtocol(), ctx.getPort(), ctx.getContextPath(),
            ctx.getProtocol(), ctx.getHost(), ctx.getPort(), ctx.getContextPath(),
            ctx.getProtocol(), ctx.getHost(), ctx.getPort(), ctx.getContextPath(),
            ctx.getProtocol(), ctx.getHost(), ctx.getPort(), ctx.getContextPath(),
            ctx.getSwaggerPath(),
            ctx.getProtocol(), ctx.getHost(), ctx.getPort(), ctx.getHealthContextPath(),
            ctx.getProfileActive()
        );
    }

    private static String tryAcquireLocalHost() {
        String localIp = System.getenv(LOCAL_IP);
        if (StringUtils.hasText(localIp)) {
            return localIp;
        }

        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }

        return "localhost";
    }
}

