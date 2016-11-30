package com.merapar.graphql.sample.dataFetchers;

import com.merapar.graphql.base.TypedValueMap;
import com.merapar.graphql.sample.domain.Role;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RoleDataFetcher {

    public Map<Integer, Role> roles = new HashMap<>();

    public List<Role> getRolesByFilter(TypedValueMap arguments) {
        Integer id = arguments.get("id");

        if (id != null) {
            return Collections.singletonList(roles.get(id));
        } else {
            return new ArrayList<>(roles.values());
        }
    }

    public Role addRole(TypedValueMap arguments) {
        val role = new Role();

        role.setId(arguments.get("id"));
        role.setName(arguments.get("name"));

        roles.put(role.getId(), role);

        return role;
    }

    public Role updateRole(TypedValueMap arguments) {
        val role = roles.get(arguments.get("id"));

        if (arguments.containsKey("name")) {
            role.setName(arguments.get("name"));
        }

        return role;
    }

    public Role deleteRole(TypedValueMap arguments) {
        val role = roles.get(arguments.get("id"));

        roles.remove(role.getId());

        return role;
    }
}
