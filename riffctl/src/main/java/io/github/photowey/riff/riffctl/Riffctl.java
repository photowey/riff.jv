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
package io.github.photowey.riff.riffctl;

import io.github.photowey.riff.riffctl.cmder.Cmder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;

/**
 * {@code Riffctl}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/11
 */
@SpringBootApplication
public class Riffctl implements CommandLineRunner, ExitCodeGenerator {

    private int exitCode;

    @Autowired
    private IFactory factory;

    @Autowired
    private Cmder cmder;

    @Override
    public void run(String... args) {
        this.exitCode = new CommandLine(cmder, factory).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Riffctl.class)
                .bannerMode(Banner.Mode.OFF)
                .logStartupInfo(true)
                .build(args)
                .run(args);

        System.exit(SpringApplication.exit(ctx));
    }

}
