package com.merapar.graphql.processor;

import java.util.Map;

public interface GraphQlProcessor {
    Object processRequest(Map requestBody);
}
