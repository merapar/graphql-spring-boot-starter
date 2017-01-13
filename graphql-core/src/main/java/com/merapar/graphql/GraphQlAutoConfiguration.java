package com.merapar.graphql;

import com.merapar.graphql.controller.GraphQlControllerImpl;
import com.merapar.graphql.executor.GraphQlExecutorProperties;
import com.merapar.graphql.executor.GraphQlExecutorImpl;
import com.merapar.graphql.schema.GraphQlSchemaBuilderImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties({
        GraphQlProperties.class,
        GraphQlExecutorProperties.class
})
@Import({
        GraphQlExecutorImpl.class,
        GraphQlSchemaBuilderImpl.class,
        GraphQlControllerImpl.class
})
public class GraphQlAutoConfiguration {

}
