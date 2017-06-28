package com.merapar.graphql.executor;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.merapar.graphql.executor")
public class GraphQlExecutorProperties {

    @Getter
    @Setter
    private Integer minimumThreadPoolSizeQuery = 10;

    @Getter
    @Setter
    private Integer maximumThreadPoolSizeQuery = 20;

    @Getter
    @Setter
    private Integer keepAliveTimeInSecondsQuery = 30;

    @Getter
    @Setter
    private Integer minimumThreadPoolSizeMutation = 10;

    @Getter
    @Setter
    private Integer maximumThreadPoolSizeMutation = 20;

    @Getter
    @Setter
    private Integer keepAliveTimeInSecondsMutation = 30;

    @Getter
    @Setter
    private Integer minimumThreadPoolSizeSubscription = 10;

    @Getter
    @Setter
    private Integer maximumThreadPoolSizeSubscription = 20;

    @Getter
    @Setter
    private Integer keepAliveTimeInSecondsSubscription = 30;
}
