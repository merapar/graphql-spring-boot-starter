package com.merapar.graphql.sample.fields;

import com.merapar.graphql.base.AbstractGraphQlFields;
import graphql.schema.GraphQLFieldDefinition;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

@Component
public class HelloWorldFields extends AbstractGraphQlFields {

    @Override
    public List<GraphQLFieldDefinition> getQueryFields() {
        return Collections.singletonList(
                newFieldDefinition()
                        .type(GraphQLString)
                        .name("hello")
                        .staticValue("world")
                        .build()
        );
    }

    @Override
    public List<GraphQLFieldDefinition> getMutationFields() {
        return Collections.emptyList();
    }
}
