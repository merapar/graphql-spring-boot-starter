package com.merapar.graphql.base;

import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class TypedValueMapTest {

    @Test
    public void equalsImplemented() {
        // Given
        val inputMap = new TypedValueMap(Collections.singletonMap("key", "value"));
        val inputMap2 = new TypedValueMap(Collections.singletonMap("key", "value"));

        // Wen
        val result = inputMap.equals(inputMap2);

        // Then
        Assertions.assertThat(result).isTrue();
    }
}