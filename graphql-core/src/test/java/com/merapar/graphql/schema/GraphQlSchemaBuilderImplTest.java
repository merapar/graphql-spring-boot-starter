package com.merapar.graphql.schema;

import com.merapar.graphql.GraphQlException;
import com.merapar.graphql.GraphQlProperties;
import com.merapar.graphql.definitions.BaseGraphQlFields;
import com.merapar.graphql.definitions.GraphQlFields;
import graphql.AssertException;
import graphql.schema.GraphQLFieldDefinition;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.Collections;
import java.util.List;

import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GraphQlSchemaBuilderImplTest {

    private ApplicationContext applicationContext;
    private GraphQlProperties graphQlProperties;
    private GraphQlSchemaBuilderImpl graphQlSchemaBuilder;

    @Before
    public void setup() {
        applicationContext = mock(ApplicationContext.class);
        graphQlProperties = mock(GraphQlProperties.class);

        when(graphQlProperties.getRootQueryName()).thenReturn("queries");
        when(graphQlProperties.getRootMutationName()).thenReturn("mutations");

        graphQlSchemaBuilder = new GraphQlSchemaBuilderImpl(applicationContext, graphQlProperties);
    }

    @Test
    public void noMutations_shouldGenerateSchemaWithOnlyQueries() {
        // Given
        when(applicationContext.getBeansWithAnnotation(GraphQlFields.class))
                .thenReturn(Collections.singletonMap("dummy", new DummyGraphQlFieldWithBase(true, false)));

        // When
        graphQlSchemaBuilder.postContruct();

        // Then
        val schema = graphQlSchemaBuilder.getSchema();

        assertThat(schema).isNotNull();
        assertThat(schema.getQueryType()).isNotNull();
        assertThat(schema.getMutationType()).isNull();
    }

    @Test
    public void noQueries_shouldThrowException() {
        // Given
        when(applicationContext.getBeansWithAnnotation(GraphQlFields.class))
                .thenReturn(Collections.singletonMap("dummy", new DummyGraphQlFieldWithBase(false, true)));

        // When
        val exception = assertThatThrownBy(() -> graphQlSchemaBuilder.postContruct());

        // Then
        exception.isExactlyInstanceOf(AssertException.class).hasMessage("queryType can't be null");
    }

    @Test
    public void graphQlFieldsClassWithoutInterface_shouldThrowException() {
        // Given
        when(applicationContext.getBeansWithAnnotation(GraphQlFields.class))
                .thenReturn(Collections.singletonMap("dummy", new DummyGraphQlFieldsWithoutBase(false, true)));

        // When
        val exception = assertThatThrownBy(() -> graphQlSchemaBuilder.postContruct());

        // Then
        exception.isExactlyInstanceOf(GraphQlException.class).hasMessage("\"GraphQlFields\" annotation found on class \"dummy\" without implementing interface \"BaseGraphQlFields\"");
    }

    @Test
    public void configuredRootQueryAndMutationDescriptions_shouldBeAppliedOnSchema() {
        // Given
        when(graphQlProperties.getRootQueryDescription()).thenReturn("rootQueryDescription");
        when(graphQlProperties.getRootMutationDescription()).thenReturn("rootMutationDescription");
        when(applicationContext.getBeansWithAnnotation(GraphQlFields.class))
                .thenReturn(Collections.singletonMap("dummy", new DummyGraphQlFieldWithBase(true, true)));

        // When
        graphQlSchemaBuilder.postContruct();

        // Then
        val schema = graphQlSchemaBuilder.getSchema();

        assertThat(schema).isNotNull();
        assertThat(schema.getQueryType().getDescription()).isEqualTo("rootQueryDescription");
        assertThat(schema.getMutationType().getDescription()).isEqualTo("rootMutationDescription");
    }

    @Test
    public void noQueriesAndMutations_shouldGenerateGettingStartedGraphQlSchema() {
        // When
        graphQlSchemaBuilder.postContruct();

        // Then
        val schema = graphQlSchemaBuilder.getSchema();

        assertThat(schema).isNotNull();
        assertThat(schema.getQueryType().getName()).isEqualTo("gettingStartedQuery");
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