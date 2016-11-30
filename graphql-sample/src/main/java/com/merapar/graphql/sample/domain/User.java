package com.merapar.graphql.sample.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String name;

    private List<Role> roles;
}