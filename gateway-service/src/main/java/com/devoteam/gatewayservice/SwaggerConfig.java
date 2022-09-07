package com.devoteam.gatewayservice;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    @Lazy(false)
    public List<GroupedOpenApi> apis(SwaggerUiConfigParameters swaggerUiConfigParameters, RouteDefinitionLocator locator) {

        List<GroupedOpenApi> groups = new ArrayList<>();

        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();

        definitions.stream().filter(routeDefinition -> routeDefinition.getId().matches(".*-SERVICE")).forEach(routeDefinition -> {

            String name = routeDefinition.getId().toLowerCase().replace("reactivecompositediscoveryclient_", "");

            swaggerUiConfigParameters.addGroup(name);

            groups.add(GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build());

        });

        return groups;
    }
}
