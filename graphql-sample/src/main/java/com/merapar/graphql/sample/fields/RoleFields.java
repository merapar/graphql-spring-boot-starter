package com.merapar.graphql.sample.fields;

import com.merapar.graphql.base.AbstractBaseGraphQlFields;
import com.merapar.graphql.definitions.BaseGraphQlFields;
import com.merapar.graphql.definitions.GraphQlFields;
import com.merapar.graphql.sample.dataFetchers.RoleDataFetcher;
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
@GraphQlFields
public class RoleFields extends AbstractBaseGraphQlFields implements BaseGraphQlFields {

    @Autowired
    private RoleDataFetcher roleDataFetcher;

    @Getter
    private GraphQLObjectType roleType;

    private GraphQLInputObjectType addRoleInputType;
    private GraphQLInputObjectType updateRoleInputType;
    private GraphQLInputObjectType deleteRoleInputType;

    private GraphQLInputObjectType filterRoleInputType;

    private GraphQLFieldDefinition rolesField;
    private GraphQLFieldDefinition addRoleField;
    private GraphQLFieldDefinition updateRoleField;
    private GraphQLFieldDefinition deleteRoleField;

    @Getter
    private List<GraphQLFieldDefinition> queryFields;

    @Getter
    private List<GraphQLFieldDefinition> mutationFields;

    @PostConstruct
    public void postConstruct() {
        createTypes();
        createFields();
        queryFields = Collections.singletonList(rolesField);
        mutationFields = Arrays.asList(addRoleField, updateRoleField, deleteRoleField);
    }

    private void createTypes() {
        roleType = newObject().name("role").description("A role")
                .field(newFieldDefinition().name("id").description("The id").type(GraphQLInt).build())
                .field(newFieldDefinition().name("name").description("The name").type(GraphQLString).build())
                .build();

        addRoleInputType = newInputObject().name("addRoleInput")
                .field(newInputObjectField().name("id").type(new GraphQLNonNull(GraphQLInt)).build())
                .field(newInputObjectField().name("name").type(new GraphQLNonNull(Scalars.GraphQLString)).build())
                .build();

        updateRoleInputType = newInputObject().name("updateRoleInput")
                .field(newInputObjectField().name("id").type(new GraphQLNonNull(GraphQLInt)).build())
                .field(newInputObjectField().name("name").type(GraphQLString).build())
                .build();

        deleteRoleInputType = newInputObject().name("deleteRoleInput")
                .field(newInputObjectField().name("id").type(new GraphQLNonNull(GraphQLInt)).build())
                .build();

        filterRoleInputType = newInputObject().name("filterRoleInput")
                .field(newInputObjectField().name("id").type(GraphQLInt).build())
                .build();
    }

    private void createFields() {
        rolesField = newFieldDefinition()
                .name("roles").description("Provide an overview of all roles")
                .type(new GraphQLList(roleType))
                .argument(newArgument().name(FILTER).type(filterRoleInputType).build())
                .dataFetcher(environment -> roleDataFetcher.getRolesByFilter(getFilterMap(environment)))
                .build();

        addRoleField = newFieldDefinition()
                .name("addRole").description("Add new role")
                .type(roleType)
                .argument(newArgument().name(INPUT).type(new GraphQLNonNull(addRoleInputType)).build())
                .dataFetcher(environment -> roleDataFetcher.addRole(getInputMap(environment)))
                .build();

        updateRoleField = newFieldDefinition()
                .name("updateRole").description("Update existing role")
                .type(roleType)
                .argument(newArgument().name(INPUT).type(new GraphQLNonNull(updateRoleInputType)).build())
                .dataFetcher(environment -> roleDataFetcher.updateRole(getInputMap(environment)))
                .build();

        deleteRoleField = newFieldDefinition()
                .name("deleteRole").description("Delete existing role")
                .type(roleType)
                .argument(newArgument().name(INPUT).type(new GraphQLNonNull(deleteRoleInputType)).build())
                .dataFetcher(environment -> roleDataFetcher.deleteRole(getInputMap(environment)))
                .build();
    }
}
