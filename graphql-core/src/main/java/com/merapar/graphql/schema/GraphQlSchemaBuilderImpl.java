package com.merapar.graphql.schema;

import com.merapar.graphql.GraphQlProperties;
import com.merapar.graphql.definitions.GraphQlFields;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.List;

import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

@ConditionalOnMissingBean(GraphQlSchemaBuilder.class)
@Component
@Slf4j
public class GraphQlSchemaBuilderImpl implements GraphQlSchemaBuilder {

    private GraphQlProperties properties;
    private List<GraphQlFields> graphQlFieldsDefinitions;

    @Getter
    private GraphQLSchema schema;

    @Autowired
    public GraphQlSchemaBuilderImpl(GraphQlProperties properties, List<GraphQlFields> graphQlFieldsDefinitions) {
        this.properties = properties;
        this.graphQlFieldsDefinitions = graphQlFieldsDefinitions;
    }

    @PostConstruct
    public void postConstruct() {

        GraphQLObjectType.Builder queryBuilder = newObject().name(properties.getRootQueryName());
        GraphQLObjectType.Builder mutationBuilder = newObject().name(properties.getRootMutationName());

        if (StringUtils.hasText(properties.getRootQueryDescription())) {
            queryBuilder = queryBuilder.description(properties.getRootQueryDescription());
        }

        if (StringUtils.hasText(properties.getRootMutationDescription())) {
            mutationBuilder = mutationBuilder.description(properties.getRootMutationDescription());
        }

        buildSchemaFromDefinitions(queryBuilder, mutationBuilder);
    }

    private void buildSchemaFromDefinitions(GraphQLObjectType.Builder queryBuilder, GraphQLObjectType.Builder mutationBuilder) {
        boolean foundQueryDefinitions = false;
        boolean foundMutationDefinitions = false;

        for(val graphQlFieldsDefinition : graphQlFieldsDefinitions){

            val queryFields = graphQlFieldsDefinition.getQueryFields();
            if (queryFields != null && queryFields.size() > 0) {
                queryBuilder = queryBuilder.fields(queryFields);
                foundQueryDefinitions = true;
            }

            val mutationFields = graphQlFieldsDefinition.getMutationFields();
            if (mutationFields != null && mutationFields.size() > 0) {
                mutationBuilder = mutationBuilder.fields(mutationFields);
                foundMutationDefinitions = true;
            }
        }

        buildSchema(queryBuilder, mutationBuilder, foundQueryDefinitions, foundMutationDefinitions);
    }

    private void buildSchema(GraphQLObjectType.Builder queryBuilder, GraphQLObjectType.Builder mutationBuilder, boolean foundQueryDefinitions, boolean foundMutationDefinitions) {
        log.debug("Start building graphql schema");

        GraphQLSchema.Builder schemaBuilder = GraphQLSchema.newSchema();

        if (foundQueryDefinitions) {
            schemaBuilder = schemaBuilder.query(queryBuilder.build());
        }

        if (foundMutationDefinitions) {
            schemaBuilder = schemaBuilder.mutation(mutationBuilder.build());
        }

        if (foundQueryDefinitions || foundMutationDefinitions) {
            schema = schemaBuilder.build();
        } else {
            schema = generateGettingStartedGraphQlSchema();
        }
    }

    private GraphQLSchema generateGettingStartedGraphQlSchema() {
        val gettingStartedType = newObject()
                .name("gettingStartedQuery")
                .field(newFieldDefinition()
                        .type(GraphQLString)
                        .name("gettingStarted")
                        .staticValue("Create a component and extend the AbstractGraphQlFields class."))
                .build();

        return GraphQLSchema.newSchema()
                .query(gettingStartedType)
                .build();
    }
}
