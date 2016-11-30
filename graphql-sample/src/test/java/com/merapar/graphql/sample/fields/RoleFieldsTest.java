package com.merapar.graphql.sample.fields;

import com.merapar.graphql.controller.GraphQlControllerImpl;
import com.merapar.graphql.sample.dataFetchers.RoleDataFetcher;
import com.merapar.graphql.sample.domain.Role;
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
@ContextConfiguration(classes = RoleFieldsTestConfiguration.class)
@WebMvcTest(GraphQlControllerImpl.class)
public class RoleFieldsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoleDataFetcher roleDataFetcher;

    @Before
    public void setup() {
        roleDataFetcher.roles = new LinkedHashMap<>();
        roleDataFetcher.roles.put(1, new Role(1, "Admin"));
        roleDataFetcher.roles.put(2, new Role(2, "User"));
    }

    @Test
    public void getRoles() throws Exception {
        // Given
        val query = "{" +
                "roles {" +
                "  id" +
                "  name" +
                "}}";

        // When
        val postResult = performGraphQlPost(query);

        // Then
        postResult.andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.data.roles[0].id").value("1"))
                .andExpect(jsonPath("$.data.roles[0].name").value("Admin"))
                .andExpect(jsonPath("$.data.roles[1].id").value("2"))
                .andExpect(jsonPath("$.data.roles[1].name").value("User"));
    }

    @Test
    public void getRolesById() throws Exception {
        // Given
        val query = "{" +
                "roles(filter: { id: 2 }) {" +
                "  id" +
                "  name" +
                "}}";

        // When
        val postResult = performGraphQlPost(query);

        // Then
        postResult.andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.data.roles[0].id").value("2"))
                .andExpect(jsonPath("$.data.roles[0].name").value("User"));
    }

    @Test
    public void addRole() throws Exception {
        // Given
        val query = "mutation addRole($input: addRoleInput!) {" +
                "  addRole(input: $input) {" +
                "    id" +
                "    name" +
                "  }\n" +
                "}";

        val variables = new LinkedHashMap<>();
        variables.put("id", 1234);
        variables.put("name", "added role");

        // When
        val postResult = performGraphQlPost(query, variables);

        // Then
        postResult.andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.data.addRole.id").value(1234))
                .andExpect(jsonPath("$.data.addRole.name").value("added role"));

        assertThat(roleDataFetcher.roles).containsKeys(1234);
    }

    @Test
    public void updateRole() throws Exception {
        // Given
        val query = "mutation updateRole($input: updateRoleInput!) {" +
                "  updateRole(input: $input) {" +
                "    id" +
                "    name" +
                "  }\n" +
                "}";

        val variables = new LinkedHashMap<>();
        variables.put("id", 1);
        variables.put("name", "updated role");

        // When
        val postResult = performGraphQlPost(query, variables);

        // Then
        postResult.andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.data.updateRole.id").value(1))
                .andExpect(jsonPath("$.data.updateRole.name").value("updated role"));

        assertThat(roleDataFetcher.roles.get(1).getName()).isEqualTo("updated role");
    }

    @Test
    public void deleteRole() throws Exception {
        // Given
        val query = "mutation deleteRole($input: deleteRoleInput!) {" +
                "  deleteRole(input: $input) {" +
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
                .andExpect(jsonPath("$.data.deleteRole.id").value(2))
                .andExpect(jsonPath("$.data.deleteRole.name").value("User"));

        assertThat(roleDataFetcher.roles).containsKeys(1);
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
