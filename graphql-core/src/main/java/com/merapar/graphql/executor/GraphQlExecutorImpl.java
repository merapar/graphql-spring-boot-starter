package com.merapar.graphql.executor;

import com.merapar.graphql.schema.GraphQlSchemaBuilder;
import graphql.GraphQL;
import graphql.execution.ExecutorServiceExecutionStrategy;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@ConditionalOnMissingBean(GraphQlExecutor.class)
@Component
@Slf4j
public class GraphQlExecutorImpl implements GraphQlExecutor {

    @Autowired
    private GraphQlExecutorProperties configuration;

    @Autowired
    private GraphQlSchemaBuilder schema;

    @Getter
    private GraphQL graphQl;

    @PostConstruct
    public void construct() {
        val queue = new LinkedBlockingQueue<Runnable>() {
            @Override
            public boolean offer(Runnable e) {
                /* queue that always rejects tasks */
                return false;
            }
        };

        val threadPoolExecutor = new ThreadPoolExecutor(
                configuration.getMinimumThreadPoolSize(), /* core pool size 2 thread */
                configuration.getMaximumThreadPoolSize(), /* max pool size 2 thread */
                configuration.getKeepAliveTimeInSeconds(), TimeUnit.SECONDS,
                /*
                 * Do not use the queue to prevent threads waiting on enqueued tasks.
                 */
                queue,
                /*
                 *  If all the threads are working, then the caller thread
                 *  should execute the code in its own thread. (serially)
                 */
                new ThreadPoolExecutor.CallerRunsPolicy());

        graphQl = new GraphQL(schema.getSchema(), new ExecutorServiceExecutionStrategy(threadPoolExecutor));
    }
}