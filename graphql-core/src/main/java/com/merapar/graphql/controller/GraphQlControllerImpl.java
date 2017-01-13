package com.merapar.graphql.controller;

import com.merapar.graphql.executor.GraphQlExecutor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@ConditionalOnMissingBean(GraphQlController.class)
@Controller
@Slf4j
public class GraphQlControllerImpl implements GraphQlController {

    @Autowired
    private GraphQlExecutor graphQlExecutor;

    @CrossOrigin
    @RequestMapping(
            path = "${com.merapar.graphql.requestMapping.path:v1/graphql}",
            method = RequestMethod.POST
    )
    @ResponseBody
    public Object executeOperation(@RequestBody Map body) {
        val uuid = UUID.randomUUID().toString();

        log.debug("Start processing graphQL request {}", uuid);
        val requestResult = graphQlExecutor.executeRequest(body);
        log.debug("Finished processing graphQL request {}", uuid);

        return requestResult;
    }
}
