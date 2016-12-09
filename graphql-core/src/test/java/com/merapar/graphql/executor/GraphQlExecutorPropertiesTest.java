package com.merapar.graphql.executor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = GraphQlExecutorPropertiesTestConfiguration.class)
@SpringBootTest(properties = {
        "com.merapar.graphql.executor.minimumThreadPoolSize=40",
        "com.merapar.graphql.executor.maximumThreadPoolSize=50",
        "com.merapar.graphql.executor.keepAliveTimeInSeconds=60"
})
public class GraphQlExecutorPropertiesTest {

    @Autowired
    private GraphQlExecutorProperties graphQlExecutorProperties;

    @Test
    public void testPropertyInjection() {
        assertThat(graphQlExecutorProperties.getMinimumThreadPoolSize()).isEqualTo(40);
        assertThat(graphQlExecutorProperties.getMaximumThreadPoolSize()).isEqualTo(50);
        assertThat(graphQlExecutorProperties.getKeepAliveTimeInSeconds()).isEqualTo(60);
    }
}