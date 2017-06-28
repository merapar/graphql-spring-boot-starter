package com.merapar.graphql.executor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.merapar.graphql.GraphQlProperties;
import com.merapar.graphql.schema.GraphQlSchemaBuilder;
import graphql.schema.GraphQLSchema;
import lombok.val;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
public class GraphQlExecutorImplTestConfiguration {

    @Bean
    public GraphQlProperties configuration() {
        return new GraphQlProperties();
    }

    @Bean
    public GraphQlSchemaBuilder schema() {
        val graphQlSchemaBuilder = mock(GraphQlSchemaBuilder.class);
        when(graphQlSchemaBuilder.getSchema()).thenReturn(mock(GraphQLSchema.class));

        return graphQlSchemaBuilder;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public GraphQlExecutorProperties graphQlProcessorProperties() { return new GraphQlExecutorProperties();}

    @Bean
    public GraphQlExecutor graphQlProcessor() { return new GraphQlExecutorImpl();}
}
