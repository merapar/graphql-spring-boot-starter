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
        "com.merapar.graphql.executor.minimumThreadPoolSizeQuery=40",
        "com.merapar.graphql.executor.maximumThreadPoolSizeQuery=50",
        "com.merapar.graphql.executor.keepAliveTimeInSecondsQuery=60",
        "com.merapar.graphql.executor.minimumThreadPoolSizeMutation=41",
        "com.merapar.graphql.executor.maximumThreadPoolSizeMutation=51",
        "com.merapar.graphql.executor.keepAliveTimeInSecondsMutation=61",
        "com.merapar.graphql.executor.minimumThreadPoolSizeSubscription=42",
        "com.merapar.graphql.executor.maximumThreadPoolSizeSubscription=52",
        "com.merapar.graphql.executor.keepAliveTimeInSecondsSubscription=62",
})
public class GraphQlExecutorPropertiesTest {

    @Autowired
    private GraphQlExecutorProperties graphQlExecutorProperties;

    @Test
    public void testPropertyInjection() {
        assertThat(graphQlExecutorProperties.getMinimumThreadPoolSizeQuery()).isEqualTo(40);
        assertThat(graphQlExecutorProperties.getMaximumThreadPoolSizeQuery()).isEqualTo(50);
        assertThat(graphQlExecutorProperties.getKeepAliveTimeInSecondsQuery()).isEqualTo(60);

        assertThat(graphQlExecutorProperties.getMinimumThreadPoolSizeMutation()).isEqualTo(41);
        assertThat(graphQlExecutorProperties.getMaximumThreadPoolSizeMutation()).isEqualTo(51);
        assertThat(graphQlExecutorProperties.getKeepAliveTimeInSecondsMutation()).isEqualTo(61);

        assertThat(graphQlExecutorProperties.getMinimumThreadPoolSizeSubscription()).isEqualTo(42);
        assertThat(graphQlExecutorProperties.getMaximumThreadPoolSizeSubscription()).isEqualTo(52);
        assertThat(graphQlExecutorProperties.getKeepAliveTimeInSecondsSubscription()).isEqualTo(62);
    }
}