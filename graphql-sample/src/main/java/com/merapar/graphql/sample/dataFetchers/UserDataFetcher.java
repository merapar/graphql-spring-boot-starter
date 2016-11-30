package com.merapar.graphql.sample.dataFetchers;

import com.merapar.graphql.base.TypedValueMap;
import com.merapar.graphql.sample.domain.Role;
import com.merapar.graphql.sample.domain.User;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserDataFetcher {

    public Map<Integer, User> users = new HashMap<>();

    public List<User> getUsersByFilter(TypedValueMap arguments) {
        Integer id = arguments.get("id");

        if (id != null) {
            return Collections.singletonList(users.get(id));
        } else {
            return new ArrayList<>(users.values());
        }
    }

    public User addUser(TypedValueMap arguments) {
        val user = new User();

        user.setId(arguments.get("id"));
        user.setName(arguments.get("name"));

        users.put(user.getId(), user);

        return user;
    }

    public User updateUser(TypedValueMap arguments) {
        val user = users.get(arguments.get("id"));

        if (arguments.containsKey("name")) {
            user.setName(arguments.get("name"));
        }

        return user;
    }

    public User deleteUser(TypedValueMap arguments) {
        val user = users.get(arguments.get("id"));

        users.remove(user.getId());

        return user;
    }

    public List<Role> getRoles(User user) {
        return Arrays.asList(new Role(1, "Admin"));
    }
}
