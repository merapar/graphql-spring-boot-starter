package com.merapar.graphql.definitions;

import graphql.schema.GraphQLFieldDefinition;

import java.util.List;

public interface GraphQlFields {
    List<GraphQLFieldDefinition> getQueryFields();
    List<GraphQLFieldDefinition> getMutationFields();
}
