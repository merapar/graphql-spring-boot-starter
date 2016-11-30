package com.merapar.graphql.schema;

import com.merapar.graphql.GraphQlException;
import com.merapar.graphql.GraphQlProperties;
import com.merapar.graphql.definitions.BaseGraphQlFields;
import com.merapar.graphql.definitions.GraphQlFields;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

import static graphql.schema.GraphQLObjectType.newObject;

@ConditionalOnMissingBean(GraphQlSchemaBuilder.class)
@Component
@Slf4j
public class GraphQlSchemaBuilderImpl implements GraphQlSchemaBuilder {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private GraphQlProperties configuration;

    @Getter
    private GraphQLSchema schema;

    @PostConstruct
    public void GraphQlSchemaBuilderImpl() {

        log.debug("Start building graphql schema");

        GraphQLObjectType.Builder queryBuilder = newObject().name(configuration.getRootQueryName());
        GraphQLObjectType.Builder mutationBuilder = newObject().name(configuration.getRootMutationName());

        if (StringUtils.hasText(configuration.getRootQueryDescription())) {
            queryBuilder = queryBuilder.description(configuration.getRootQueryDescription());
        }

        if (StringUtils.hasText(configuration.getRootMutationDescription())) {
            mutationBuilder = mutationBuilder.description(configuration.getRootMutationDescription());
        }

        val fieldDefinitionBeans = applicationContext.getBeansWithAnnotation(GraphQlFields.class);

        log.debug("Found following field definition components " + fieldDefinitionBeans.keySet());

        boolean foundMutationDefinitions = false;
        boolean foundQueryDefinitions = false;

        for (val fieldDefinition : fieldDefinitionBeans.entrySet()) {

            if (!(fieldDefinition.getValue() instanceof BaseGraphQlFields)) {
                throw new GraphQlException(String.format("\"GraphQlFields\" annotation found on class \"%s\" without implementing interface \"BaseGraphQlFields\"", fieldDefinition.getKey()));
            }

            val fieldDefinitionInterface = (BaseGraphQlFields) fieldDefinition.getValue();

            if (fieldDefinitionInterface.getQueryFields() != null) {
                val queryFields = fieldDefinitionInterface.getQueryFields();
                if (queryFields != null && queryFields.size() > 0) {
                    queryBuilder = queryBuilder.fields(queryFields);
                    foundQueryDefinitions = true;
                }
            }

            if (fieldDefinitionInterface.getMutationFields() != null) {
                val mutationFields = fieldDefinitionInterface.getMutationFields();
                if (mutationFields != null && mutationFields.size() > 0) {
                    mutationBuilder = mutationBuilder.fields(mutationFields);
                    foundMutationDefinitions = true;
                }
            }
        }

        GraphQLSchema.Builder schemaBuilder = GraphQLSchema.newSchema();

        if (foundQueryDefinitions) {
            schemaBuilder = schemaBuilder.query(queryBuilder.build());
        }

        if (foundMutationDefinitions) {
            schemaBuilder = schemaBuilder.mutation(mutationBuilder.build());
        }

        schema = schemaBuilder.build();
    }
}
