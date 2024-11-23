package com.perisatto.fiapprj.menuguru_order.infra.persistences.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.perisatto.fiapprj.menuguru_order.infra.persistences.entities.PaymentDocument;

public interface PaymentPersistenceRepository extends MongoRepository<PaymentDocument, String >{

}
