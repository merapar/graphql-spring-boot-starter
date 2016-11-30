package com.merapar.graphql.sample.fields;

import com.merapar.graphql.base.AbstractBaseGraphQlFields;
import com.merapar.graphql.definitions.BaseGraphQlFields;
import com.merapar.graphql.definitions.GraphQlFields;
import graphql.schema.GraphQLFieldDefinition;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

@Component
@GraphQlFields
public class HelloWorldFields extends AbstractBaseGraphQlFields implements BaseGraphQlFields {

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
