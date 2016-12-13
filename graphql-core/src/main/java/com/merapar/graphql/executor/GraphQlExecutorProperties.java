package com.merapar.graphql.executor;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.merapar.graphql.executor")
public class GraphQlExecutorProperties {

    @Getter
    @Setter
    private Integer minimumThreadPoolSize = 10;

    @Getter
    @Setter
    private Integer maximumThreadPoolSize = 20;

    @Getter
    @Setter
    private Integer keepAliveTimeInSeconds = 30;

}
