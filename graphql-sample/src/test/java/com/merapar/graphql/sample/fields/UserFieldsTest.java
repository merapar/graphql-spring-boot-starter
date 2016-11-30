package com.merapar.graphql.sample.fields;

import com.merapar.graphql.controller.GraphQlControllerImpl;
import com.merapar.grapqhql.sample.fields.User;
import com.merapar.grapqhql.sample.fields.UserDataFetcher;
import lombok.val;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = UserFieldsTestConfiguration.class)
@WebMvcTest(GraphQlControllerImpl.class)
public class UserFieldsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDataFetcher userDataFetcher;

    @Before
    public void setup() {
        userDataFetcher.users = new LinkedHashMap<>();
        userDataFetcher.users.put(1, new User(1, "Jan"));
        userDataFetcher.users.put(2, new User(2, "Peter"));
    }

    @Test
    public void getUsers() throws Exception {
        // Given
        val query = "{" +
                "users {" +
                "  id" +
                "  name" +
                "}}";

        // When
        val postResult = performGraphQlPost(query);

        // Then
        postResult.andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.data.users[0].id").value("1"))
                .andExpect(jsonPath("$.data.users[0].name").value("Jan"))
                .andExpect(jsonPath("$.data.users[1].id").value("2"))
                .andExpect(jsonPath("$.data.users[1].name").value("Peter"));
    }

    @Test
    public void addUser() throws Exception {
        // Given
        val query = "mutation addUser($input: addUserInput!) {" +
                "  addUser(input: $input) {" +
                "    id" +
                "    name" +
                "  }\n" +
                "}";

        val variables = new LinkedHashMap<>();
        variables.put("id", 1234);
        variables.put("name", "added user");

        // When
        val postResult = performGraphQlPost(query, variables);

        // Then
        postResult.andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.data.addUser.id").value(1234))
                .andExpect(jsonPath("$.data.addUser.name").value("added user"));

        assertThat(userDataFetcher.users).containsKeys(1234);
    }

    @Test
    public void updateUser() throws Exception {
        // Given
        val query = "mutation updateUser($input: updateUserInput!) {" +
                "  updateUser(input: $input) {" +
                "    id" +
                "    name" +
                "  }\n" +
                "}";

        val variables = new LinkedHashMap<>();
        variables.put("id", 1);
        variables.put("name", "updated user");

        // When
        val postResult = performGraphQlPost(query, variables);

        // Then
        postResult.andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.data.updateUser.id").value(1))
                .andExpect(jsonPath("$.data.updateUser.name").value("updated user"));

        assertThat(userDataFetcher.users.get(1).getName()).isEqualTo("updated user");
    }

    @Test
    public void deleteUser() throws Exception {
        // Given
        val query = "mutation deleteUser($input: deleteUserInput!) {" +
                "  deleteUser(input: $input) {" +
                "    id" +
                "    name" +
                "  }\n" +
                "}";

        val variables = new LinkedHashMap<>();
        variables.put("id", 2);

        // When
        val postResult = performGraphQlPost(query, variables);

        // Then
        postResult.andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.data.deleteUser.id").value(2))
                .andExpect(jsonPath("$.data.deleteUser.name").value("Peter"));

        assertThat(userDataFetcher.users).containsKeys(1);
    }

    private ResultActions performGraphQlPost(String query) throws Exception {
        return performGraphQlPost(query, null);
    }

    private ResultActions performGraphQlPost(String query, Map variables) throws Exception {
        return mockMvc.perform(post("/v1/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .content(generateRequest(query, variables))
        );
    }

    private String generateRequest(String query, Map variables) {
        val jsonObject = new JSONObject();

        jsonObject.put("query", query);

        if (variables != null) {
            jsonObject.put("variables", Collections.singletonMap("input", variables));
        }

        return jsonObject.toString();
    }
}
