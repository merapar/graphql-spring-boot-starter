package com.merapar.graphql.base;

import graphql.schema.DataFetchingEnvironment;

import java.util.Collections;
import java.util.Map;

public abstract class AbstractBaseGraphQlFields {
    protected static String INPUT = "input";
    protected static String FILTER = "filter";

    protected TypedValueMap getInputMap(DataFetchingEnvironment environment) {
        return new TypedValueMap(environment.getArgument(INPUT));
    }

    protected TypedValueMap getFilterMap(DataFetchingEnvironment environment) {
        Map<String, Object> filterMap = environment.getArgument(FILTER);

        if (filterMap == null) {
            return new TypedValueMap(Collections.emptyMap());
        }

        return new TypedValueMap(filterMap);
    }
}

