package com.merapar.graphql.executor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.merapar.graphql.GraphQlProperties;
import com.merapar.graphql.schema.GraphQlSchemaBuilder;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphQlExecutorImplTestConfiguration {

    @Bean
    public GraphQlProperties configuration() {
        return new GraphQlProperties();
    }

    @MockBean
    private GraphQlSchemaBuilder schema;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public GraphQlExecutorProperties graphQlProcessorProperties() { return new GraphQlExecutorProperties();}

    @Bean
    public GraphQlExecutor graphQlProcessor() { return new GraphQlExecutorImpl();}
}
