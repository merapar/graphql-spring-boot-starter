package com.merapar.graphql;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.merapar.graphql")
public class GraphQlProperties {

    @Getter
    @Setter
    private Integer executorThreadPoolSize = 20;

    @Getter
    @Setter
    private String rootQueryName = "queries";

    @Getter
    @Setter
    private String rootQueryDescription = "";

    @Getter
    @Setter
    private String rootMutationName = "mutations";

    @Getter
    @Setter
    private String rootMutationDescription = "";

}
