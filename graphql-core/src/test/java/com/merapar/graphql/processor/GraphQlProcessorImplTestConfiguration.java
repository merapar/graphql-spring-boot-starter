package com.merapar.graphql.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.merapar.graphql.GraphQlProperties;
import com.merapar.graphql.executor.GraphQlExecutor;
import com.merapar.graphql.executor.GraphQlExecutorImpl;
import com.merapar.graphql.executor.GraphQlExecutorProperties;
import com.merapar.graphql.schema.GraphQlSchemaBuilder;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphQlProcessorImplTestConfiguration {

    @Bean
    public GraphQlProperties configuration() {
        return new GraphQlProperties();
    }

    @MockBean
    private GraphQlSchemaBuilder schema;

    @Bean
    public GraphQlExecutor executor() { return new GraphQlExecutorImpl(); }

    @Bean
    public GraphQlExecutorProperties executorConfiguration() {
        return new GraphQlExecutorProperties();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public GraphQlProcessor graphQlProcessor() {
        return new GraphQlProcessorImpl();
    }
}
