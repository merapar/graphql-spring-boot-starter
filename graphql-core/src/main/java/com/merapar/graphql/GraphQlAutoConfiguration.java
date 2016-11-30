package com.merapar.graphql;

import com.merapar.graphql.controller.GraphQlControllerImpl;
import com.merapar.graphql.processor.GraphQlProcessorImpl;
import com.merapar.graphql.schema.GraphQlSchemaBuilderImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(GraphQlProperties.class)
@Import({
        GraphQlProcessorImpl.class,
        GraphQlSchemaBuilderImpl.class,
        GraphQlControllerImpl.class
})
public class GraphQlAutoConfiguration {

}
