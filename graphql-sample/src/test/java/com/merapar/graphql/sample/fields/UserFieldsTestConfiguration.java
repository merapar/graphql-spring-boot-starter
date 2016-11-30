package com.merapar.graphql.sample.fields;

import com.merapar.graphql.GraphQlAutoConfiguration;
import com.merapar.grapqhql.sample.fields.UserDataFetcher;
import com.merapar.grapqhql.sample.fields.UserFields;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(GraphQlAutoConfiguration.class)
public class UserFieldsTestConfiguration {

    @Bean
    public UserDataFetcher userDataFetcher() {
        return new UserDataFetcher();
    }

    @Bean
    public UserFields userFields() {
        return new UserFields();
    }
}
