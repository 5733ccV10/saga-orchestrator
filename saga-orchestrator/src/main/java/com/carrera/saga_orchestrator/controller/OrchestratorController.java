package com.carrera.saga_orchestrator.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrera.saga_orchestrator.dto.OrchestratorRequest;
import com.carrera.saga_orchestrator.model.Orchestrator;
import com.carrera.saga_orchestrator.service.OrchestratorService;


@RestController
@RequestMapping("/")
public class OrchestratorController {
    private final OrchestratorService orchestratorService;

    public OrchestratorController (OrchestratorService orchestratorService) {
        this.orchestratorService = orchestratorService;
    }

    @PostMapping("/order")
    public Orchestrator placeOrder(@RequestBody OrchestratorRequest request) {
        return orchestratorService.placeOrder(request);
    }
    
}
