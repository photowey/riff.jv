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
package io.github.photowey.riff.startup.app.logging;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.StopWatch;

import io.github.photowey.riff.startup.core.domain.logging.context.DocumentContext;
import io.github.photowey.riff.startup.core.event.ApplicationStartedLocalEvent;
import io.github.photowey.riff.startup.printer.logging.StartupPrinter;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code StartupApplication}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/11
 */
@Slf4j
public class StartupApplication {

    public static final String SPRING_APPLICATION_NAME = "spring.application.name";

    public static <T> ConfigurableApplicationContext run(Class<T> mainClass, String[] args) {
        return run(mainClass, args, StartupApplication::nothing, DocumentContext::swaggerContext);
    }

    public static <T> ConfigurableApplicationContext run(
        Class<T> primarySource,
        String[] args,
        Supplier<DocumentContext.DocumentContextBuilder> dfx) {
        return run(primarySource, args, StartupApplication::nothing, dfx);
    }

    public static <T> ConfigurableApplicationContext run(
        Class<T> primarySource,
        String[] args,
        Consumer<SpringApplicationBuilder> fx) {
        return run(primarySource, args, fx, DocumentContext::swaggerContext);
    }

    public static <T> ConfigurableApplicationContext run(
        Class<T> primarySource,
        String[] args,
        Consumer<SpringApplicationBuilder> fx,
        Supplier<DocumentContext.DocumentContextBuilder> dfx) {
        StopWatch watch = new StopWatch("setup");
        watch.start();

        SpringApplicationBuilder applicationBuilder = new SpringApplicationBuilder(primarySource)
            .bannerMode(Banner.Mode.CONSOLE)
            .logStartupInfo(true);

        fx.accept(applicationBuilder);

        ConfigurableApplicationContext applicationContext = applicationBuilder
            .build(args)
            .run(args);

        StartupPrinter.print(applicationContext, dfx.get());
        watch.stop();
        publishEvent(applicationContext);

        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        log.info("report: '{}' webapp started, took [{}] ms",
            environment.getProperty(SPRING_APPLICATION_NAME),
            watch.getTotalTimeMillis()
        );

        return applicationContext;
    }

    public static void publishEvent(ConfigurableApplicationContext applicationContext) {
        applicationContext.publishEvent(new ApplicationStartedLocalEvent());
    }

    // @formatter:off

    public static <T> void nothing(T t) { }

    // @formatter:on
}

