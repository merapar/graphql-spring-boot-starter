package com.merapar.graphql.schema;

import com.merapar.graphql.GraphQlProperties;
import com.merapar.graphql.definitions.BaseGraphQlFields;
import graphql.AssertException;
import graphql.schema.GraphQLFieldDefinition;
import lombok.val;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GraphQlSchemaBuilderImplTest {

    private GraphQlProperties graphQlProperties;
    private GraphQlSchemaBuilderImpl graphQlSchemaBuilder;

    @Before
    public void setup() {
        graphQlProperties = mock(GraphQlProperties.class);

        when(graphQlProperties.getRootQueryName()).thenReturn("queries");
        when(graphQlProperties.getRootMutationName()).thenReturn("mutations");
    }

    @Test
    public void noMutations_shouldGenerateSchemaWithOnlyQueries() {
        // Given
        graphQlSchemaBuilder = new GraphQlSchemaBuilderImpl(graphQlProperties, getGraphQlFields(new DummyGraphQlFieldWithBase(true, false)));

        // When
        graphQlSchemaBuilder.postConstruct();

        // Then
        val schema = graphQlSchemaBuilder.getSchema();

        assertThat(schema).isNotNull();
        assertThat(schema.getQueryType()).isNotNull();
        assertThat(schema.getMutationType()).isNull();
    }

    @Test
    public void noQueries_shouldThrowException() {
        // Given
        graphQlSchemaBuilder = new GraphQlSchemaBuilderImpl(graphQlProperties, getGraphQlFields(new DummyGraphQlFieldWithBase(false, true)));

        // When
        val exception = assertThatThrownBy(() -> graphQlSchemaBuilder.postConstruct());

        // Then
        exception.isExactlyInstanceOf(AssertException.class).hasMessage("queryType can't be null");
    }

    @Test
    public void configuredRootQueryAndMutationDescriptions_shouldBeAppliedOnSchema() {
        // Given
        graphQlSchemaBuilder = new GraphQlSchemaBuilderImpl(graphQlProperties, getGraphQlFields(new DummyGraphQlFieldWithBase(true, true)));
        when(graphQlProperties.getRootQueryDescription()).thenReturn("rootQueryDescription");
        when(graphQlProperties.getRootMutationDescription()).thenReturn("rootMutationDescription");

        // When
        graphQlSchemaBuilder.postConstruct();

        // Then
        val schema = graphQlSchemaBuilder.getSchema();

        assertThat(schema).isNotNull();
        assertThat(schema.getQueryType().getDescription()).isEqualTo("rootQueryDescription");
        assertThat(schema.getMutationType().getDescription()).isEqualTo("rootMutationDescription");
    }

    @Test
    public void noQueriesAndMutations_shouldGenerateGettingStartedGraphQlSchema() {
        // Given
        graphQlSchemaBuilder = new GraphQlSchemaBuilderImpl(graphQlProperties, new ArrayList<>());

        // When
        graphQlSchemaBuilder.postConstruct();

        // Then
        val schema = graphQlSchemaBuilder.getSchema();

        assertThat(schema).isNotNull();
        assertThat(schema.getQueryType().getName()).isEqualTo("gettingStartedQuery");
    }

    private List<BaseGraphQlFields> getGraphQlFields(BaseGraphQlFields... fields)
    {
        return Arrays.asList(fields);
    }

    class DummyGraphQlFieldWithBase extends DummyGraphQlFieldsWithoutBase implements BaseGraphQlFields {

        public DummyGraphQlFieldWithBase(boolean includeQueryField, boolean includeMutationField) {
            super(includeQueryField, includeMutationField);
        }
    }

    class DummyGraphQlFieldsWithoutBase {

        boolean includeQueryField;
        boolean includeMutationField;

        public DummyGraphQlFieldsWithoutBase(boolean includeQueryField, boolean includeMutationField) {
            this.includeQueryField = includeQueryField;
            this.includeMutationField = includeMutationField;
        }

        public List<GraphQLFieldDefinition> getQueryFields() {
            if (includeQueryField) {
                return Collections.singletonList(
                        newFieldDefinition()
                                .type(GraphQLString)
                                .name("dummy")
                                .staticValue("value")
                                .build()
                );
            } else {
                return Collections.emptyList();
            }
        }

        public List<GraphQLFieldDefinition> getMutationFields() {
            if (includeMutationField) {
                return Collections.singletonList(
                        newFieldDefinition()
                                .type(GraphQLString)
                                .name("dummy")
                                .staticValue("value")
                                .build()
                );
            } else {
                return Collections.emptyList();
            }
        }
    }
}