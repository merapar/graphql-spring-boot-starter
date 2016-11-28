package com.merapar.graphql.processor;

import com.merapar.graphql.GraphQlException;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = GraphQlProcessorImplTestConfiguration.class)
public class GraphQlProcessorImplTest {

    @Autowired
    private GraphQlProcessor graphQlProcessor;

    @Test
    public void processRequest_invalidVariablesJson_shouldThrowException() {
        // Given
        val request = new LinkedHashMap<String, Object>();
        request.put("query", "dummy");
        request.put("operationName", "dummy");
        request.put("variables", "no JSON object");

        // When
        val exception = assertThatThrownBy(() -> graphQlProcessor.processRequest(request));

        // Then
        exception.isExactlyInstanceOf(GraphQlException.class).hasMessage("Cannot parse variables");
    }

    @Test
    public void processRequest_invalidVariablesType_shouldThrowException() {
        // Given
        val request = new LinkedHashMap<String, Object>();
        request.put("query", "dummy");
        request.put("operationName", "dummy");
        request.put("variables", 1234);

        // When
        val exception = assertThatThrownBy(() -> graphQlProcessor.processRequest(request));

        // Then
        exception.isExactlyInstanceOf(GraphQlException.class).hasMessage("Incorrect variables");
    }
}