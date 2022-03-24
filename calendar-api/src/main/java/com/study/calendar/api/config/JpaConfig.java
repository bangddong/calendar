package com.study.calendar.api.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EntityScan("com.study.calendar.core")
@EnableJpaRepositories("com.study.calendar.core")
@Configuration
public class JpaConfig {
}
