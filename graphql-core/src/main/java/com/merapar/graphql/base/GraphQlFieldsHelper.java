package com.merapar.graphql.base;

import graphql.schema.DataFetchingEnvironment;

import java.util.Collections;
import java.util.Map;

public class GraphQlFieldsHelper {
    public static String INPUT = "input";
    public static String FILTER = "filter";

    public static TypedValueMap getInputMap(DataFetchingEnvironment environment) {
        return new TypedValueMap(environment.getArgument(INPUT));
    }

    public static TypedValueMap getFilterMap(DataFetchingEnvironment environment) {
        Map<String, Object> filterMap = environment.getArgument(FILTER);

        if (filterMap == null) {
            return new TypedValueMap(Collections.emptyMap());
        }

        return new TypedValueMap(filterMap);
    }
}

