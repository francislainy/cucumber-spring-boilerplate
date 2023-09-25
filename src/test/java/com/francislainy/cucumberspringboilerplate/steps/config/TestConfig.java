package com.francislainy.cucumberspringboilerplate.steps.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.francislainy.cucumberspringboilerplate", "com.francislainy.cucumberspringboilerplate.client"})
@EnableAutoConfiguration
public class TestConfig {

}
