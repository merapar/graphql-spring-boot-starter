package com.merapar.graphql.controller;

import com.merapar.graphql.processor.GraphQlProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@ConditionalOnMissingBean(GraphQlController.class)
@Controller
public class GraphQlControllerImpl implements GraphQlController {

    @Autowired
    private GraphQlProcessor graphQlProcessor;

    @CrossOrigin
    @RequestMapping(
            path = "${com.merapar.graphql.requestMapping.path:v1/graphql}",
            method = RequestMethod.POST
    )
    @ResponseBody
    public Object executeOperation(@RequestBody Map body) {
        return graphQlProcessor.processRequest(body);
    }
}
