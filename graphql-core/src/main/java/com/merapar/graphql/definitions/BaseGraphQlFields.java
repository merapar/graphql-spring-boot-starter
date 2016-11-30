package com.merapar.graphql.definitions;

import graphql.schema.GraphQLFieldDefinition;

import java.util.List;

public interface BaseGraphQlFields {
    List<GraphQLFieldDefinition> getQueryFields();
    List<GraphQLFieldDefinition> getMutationFields();
}
