package com.merapar.graphql.base;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
public class TypedValueMap {
    private Map<String, Object> map;

    public TypedValueMap(Map<String, Object> map) {
        this.map = map == null ? new HashMap<>() : map;
    }

    public boolean containsKey(String name) {
        return map.containsKey(name);
    }

    public <T> T get(String name) {
        return (T) this.map.get(name);
    }
}
