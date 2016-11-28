package com.merapar.graphql.controller;

import com.merapar.graphql.GraphQlAutoConfiguration;
import com.merapar.graphql.controller.data.UserDataFetcher;
import com.merapar.graphql.controller.data.UserFields;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(GraphQlAutoConfiguration.class)
public class GraphQlControllerTestConfiguration {

    @Bean
    public UserDataFetcher userDataFetcher() {
        return new UserDataFetcher();
    }

    @Bean
    public UserFields userFields() {
        return new UserFields();
    }
}
