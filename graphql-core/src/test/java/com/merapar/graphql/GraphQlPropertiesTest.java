package com.merapar.graphql;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = GraphQlPropertiesTestConfiguration.class)
@SpringBootTest(properties = {
        "com.merapar.graphql.rootQueryName=serviceQueries",
        "com.merapar.graphql.rootQueryDescription=The service for queries",
        "com.merapar.graphql.rootMutationName=serviceMutations",
        "com.merapar.graphql.rootMutationDescription=The service for mutations"
})
public class GraphQlPropertiesTest {

    @Autowired
    private GraphQlProperties graphQlProperties;

    @Test
    public void testPropertyInjection() {
        assertThat(graphQlProperties.getRootQueryName()).isEqualTo("serviceQueries");
        assertThat(graphQlProperties.getRootQueryDescription()).isEqualTo("The service for queries");
        assertThat(graphQlProperties.getRootMutationName()).isEqualTo("serviceMutations");
        assertThat(graphQlProperties.getRootMutationDescription()).isEqualTo("The service for mutations");
    }
}