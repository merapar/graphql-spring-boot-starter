package com.merapar.graphql;

import graphql.schema.GraphQLFieldDefinition;

import java.util.List;

public interface GraphQlFields {
    List<GraphQLFieldDefinition> getQueryFields();
    List<GraphQLFieldDefinition> getMutationFields();
}
