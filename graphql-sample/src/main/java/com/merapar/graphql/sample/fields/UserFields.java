package com.merapar.graphql.sample.fields;

import com.merapar.graphql.base.GraphQlFieldsHelper;
import com.merapar.graphql.GraphQlFields;
import com.merapar.graphql.sample.dataFetchers.UserDataFetcher;
import com.merapar.graphql.sample.domain.User;
import graphql.Scalars;
import graphql.schema.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static graphql.Scalars.GraphQLInt;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLInputObjectField.newInputObjectField;
import static graphql.schema.GraphQLInputObjectType.newInputObject;
import static graphql.schema.GraphQLObjectType.newObject;

@Component
public class UserFields implements GraphQlFields {

    @Autowired
    private UserDataFetcher userDataFetcher;

    @Autowired
    private RoleFields roleFields;

    private GraphQLObjectType userType;

    private GraphQLInputObjectType addUserInputType;
    private GraphQLInputObjectType updateUserInputType;
    private GraphQLInputObjectType deleteUserInputType;

    private GraphQLInputObjectType filterUserInputType;

    private GraphQLFieldDefinition usersField;
    private GraphQLFieldDefinition addUserField;
    private GraphQLFieldDefinition updateUserField;
    private GraphQLFieldDefinition deleteUserField;

    @Getter
    private List<GraphQLFieldDefinition> queryFields;

    @Getter
    private List<GraphQLFieldDefinition> mutationFields;

    @PostConstruct
    public void postConstruct() {
        createTypes();
        createFields();
        queryFields = Collections.singletonList(usersField);
        mutationFields = Arrays.asList(addUserField, updateUserField, deleteUserField);
    }

    private void createTypes() {
        userType = newObject().name("user").description("A user")
                .field(newFieldDefinition().name("id").description("The id").type(GraphQLInt).build())
                .field(newFieldDefinition().name("name").description("The name").type(GraphQLString).build())
                .field(newFieldDefinition().name("roles").description("The roles").type(new GraphQLList(roleFields.getRoleType()))
                        .dataFetcher(environment -> userDataFetcher.getRoles((User) environment.getSource()))
                        .build())
                .build();

        addUserInputType = newInputObject().name("addUserInput")
                .field(newInputObjectField().name("id").type(new GraphQLNonNull(GraphQLInt)).build())
                .field(newInputObjectField().name("name").type(new GraphQLNonNull(Scalars.GraphQLString)).build())
                .build();

        updateUserInputType = newInputObject().name("updateUserInput")
                .field(newInputObjectField().name("id").type(new GraphQLNonNull(GraphQLInt)).build())
                .field(newInputObjectField().name("name").type(GraphQLString).build())
                .build();

        deleteUserInputType = newInputObject().name("deleteUserInput")
                .field(newInputObjectField().name("id").type(new GraphQLNonNull(GraphQLInt)).build())
                .build();

        filterUserInputType = newInputObject().name("filterUserInput")
                .field(newInputObjectField().name("id").type(GraphQLInt).build())
                .build();
    }

    private void createFields() {
        usersField = newFieldDefinition()
                .name("users").description("Provide an overview of all users")
                .type(new GraphQLList(userType))
                .argument(newArgument().name(GraphQlFieldsHelper.FILTER).type(filterUserInputType).build())
                .dataFetcher(environment -> userDataFetcher.getUsersByFilter(GraphQlFieldsHelper.getFilterMap(environment)))
                .build();

        addUserField = newFieldDefinition()
                .name("addUser").description("Add new user")
                .type(userType)
                .argument(newArgument().name(GraphQlFieldsHelper.INPUT).type(new GraphQLNonNull(addUserInputType)).build())
                .dataFetcher(environment -> userDataFetcher.addUser(GraphQlFieldsHelper.getInputMap(environment)))
                .build();

        updateUserField = newFieldDefinition()
                .name("updateUser").description("Update existing user")
                .type(userType)
                .argument(newArgument().name(GraphQlFieldsHelper.INPUT).type(new GraphQLNonNull(updateUserInputType)).build())
                .dataFetcher(environment -> userDataFetcher.updateUser(GraphQlFieldsHelper.getInputMap(environment)))
                .build();

        deleteUserField = newFieldDefinition()
                .name("deleteUser").description("Delete existing user")
                .type(userType)
                .argument(newArgument().name(GraphQlFieldsHelper.INPUT).type(new GraphQLNonNull(deleteUserInputType)).build())
                .dataFetcher(environment -> userDataFetcher.deleteUser(GraphQlFieldsHelper.getInputMap(environment)))
                .build();
    }
}
