package com.merapar.graphql.processor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.merapar.graphql.GraphQlException;
import com.merapar.graphql.GraphQlProperties;
import com.merapar.graphql.executor.GraphQlExecutor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

@ConditionalOnMissingBean(GraphQlProcessor.class)
@Component
@Slf4j
public class GraphQlProcessorImpl implements GraphQlProcessor {

    @Autowired
    private GraphQlProperties configuration;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Autowired
    private GraphQlExecutor graphQlExecutor;

    private TypeReference<HashMap<String, Object>> typeRefReadJsonString = new TypeReference<HashMap<String, Object>>() {
    };

    @Override
    public Object processRequest(Map requestBody) {
        val query = (String) requestBody.get("query");
        val operationName = (String) requestBody.get("operationName");
        val variables = getVariablesFromRequest(requestBody);

        val executionResult = graphQlExecutor.getGraphQl().execute(query, operationName, null, variables);

        val result = new LinkedHashMap<String, Object>();

        if (executionResult.getErrors().size() > 0) {
            result.put("errors", executionResult.getErrors());
            log.error("Errors: {}", executionResult.getErrors());
        }
        result.put("data", executionResult.getData());

        return result;
    }

    private Map<String, Object> getVariablesFromRequest(Map requestBody) {
        val variablesFromRequest = requestBody.get("variables");

        if (variablesFromRequest == null) {
            return Collections.emptyMap();
        }

        if (variablesFromRequest instanceof String) {
            if (StringUtils.hasText((String) variablesFromRequest)) {
                return getVariablesMapFromString((String) variablesFromRequest);
            }
        } else if (variablesFromRequest instanceof Map) {
            return (Map<String, Object>) variablesFromRequest;
        } else {
            throw new GraphQlException("Incorrect variables");
        }

        return Collections.emptyMap();
    }

    private Map<String, Object> getVariablesMapFromString(String variablesFromRequest) {
        try {
            return jacksonObjectMapper.readValue(variablesFromRequest, typeRefReadJsonString);
        } catch (IOException exception) {
            throw new GraphQlException("Cannot parse variables", exception);
        }
    }
}