package com.merapar.graphql.executor;

import java.util.Map;

public interface GraphQlExecutor {
    Object executeRequest(Map requestBody);
}
