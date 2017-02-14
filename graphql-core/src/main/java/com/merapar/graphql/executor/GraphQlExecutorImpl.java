package com.merapar.graphql.executor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.merapar.graphql.schema.GraphQlSchemaBuilder;
import graphql.GraphQL;
import graphql.GraphQLException;
import graphql.execution.ExecutorServiceExecutionStrategy;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@ConditionalOnMissingBean(GraphQlExecutor.class)
@Component
@Slf4j
public class GraphQlExecutorImpl implements GraphQlExecutor {

    private GraphQlExecutorProperties processorProperties;
    private ObjectMapper jacksonObjectMapper;
    private GraphQlSchemaBuilder schemaBuilder;

    private TypeReference<HashMap<String, Object>> typeRefReadJsonString = new TypeReference<HashMap<String, Object>>() {};

    private GraphQL graphQL;

    @Autowired
    public GraphQlExecutorImpl(ObjectMapper jacksonObjectMapper, GraphQlSchemaBuilder schemaBuilder, GraphQlExecutorProperties processorProperties) {
        this.jacksonObjectMapper = jacksonObjectMapper;
        this.schemaBuilder = schemaBuilder;
        this.processorProperties = processorProperties;
    }

    @PostConstruct
    private void postConstruct() {
        graphQL = new GraphQL(schemaBuilder.getSchema(), createExecutionStrategy());
    }

    private ExecutorServiceExecutionStrategy createExecutionStrategy() {
        val queue = new LinkedBlockingQueue<Runnable>() {
            @Override
            public boolean offer(Runnable e) {
                    /* queue that always rejects tasks */
                return false;
            }
        };

        return new ExecutorServiceExecutionStrategy(new ThreadPoolExecutor(
                processorProperties.getMinimumThreadPoolSize(), /* core pool size 2 thread */
                processorProperties.getMaximumThreadPoolSize(), /* max pool size 2 thread */
                processorProperties.getKeepAliveTimeInSeconds(),
                TimeUnit.SECONDS,
                /*
                 * Do not use the queue to prevent threads waiting on enqueued tasks.
                 */
                queue,
                /*
                 *  If all the threads are working, then the caller thread
                 *  should execute the code in its own thread. (serially)
                 */
                new ThreadPoolExecutor.CallerRunsPolicy()));
    }

    @Override
    public Object executeRequest(Map requestBody) {
        val query = (String) requestBody.get("query");
        val operationName = (String) requestBody.get("operationName");
        val variables = getVariablesFromRequest(requestBody);
        val context = new HashMap<String, Object>();
        val executionResult = graphQL.execute(query, operationName, context, variables);

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
            throw new GraphQLException("Incorrect variables");
        }

        return Collections.emptyMap();
    }

    private Map<String, Object> getVariablesMapFromString(String variablesFromRequest) {
        try {
            return jacksonObjectMapper.readValue(variablesFromRequest, typeRefReadJsonString);
        } catch (IOException exception) {
            throw new GraphQLException("Cannot parse variables", exception);
        }
    }
}