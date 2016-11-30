package com.merapar.graphql.sample.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String name;
}