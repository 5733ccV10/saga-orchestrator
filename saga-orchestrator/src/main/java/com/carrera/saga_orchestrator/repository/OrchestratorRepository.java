package com.carrera.saga_orchestrator.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrchestratorRepository extends MongoRepository<Object, Object>{
    
}
