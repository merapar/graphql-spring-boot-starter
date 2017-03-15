package com.merapar.graphql.sample;

import com.merapar.graphql.executor.GraphQlExecutorImpl;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomGraphQlExecutorImpl extends GraphQlExecutorImpl {
    @Override
    protected void beforeExecuteRequest(String query, String operationName, Map<String, Object> context, Map<String, Object> variables) {
        // Integrate own authentication logic
        context.put("User", "Username");
    }
}
