package com.perisatto.fiapprj.menuguru_order.infra.persistences.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perisatto.fiapprj.menuguru_order.infra.persistences.entities.OrderEntity;

@Repository
public interface OrderPersistenceRepository extends JpaRepository<OrderEntity, Long> {

	Page<OrderEntity> findByIdOrderStatusBetween(Long id, Long id2, Pageable pageable);

}
