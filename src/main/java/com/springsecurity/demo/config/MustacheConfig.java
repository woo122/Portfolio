package com.springsecurity.demo.config;

import com.samskivert.mustache.Mustache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MustacheConfig {

    @Bean
    public Mustache.Compiler mustacheCompiler(Mustache.TemplateLoader templateLoader) {
        return Mustache.compiler()
                .defaultValue("")
                .withLoader(templateLoader);
    }
}
