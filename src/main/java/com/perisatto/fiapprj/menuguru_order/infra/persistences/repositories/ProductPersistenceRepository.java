package com.perisatto.fiapprj.menuguru_order.infra.persistences.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perisatto.fiapprj.menuguru_order.infra.persistences.entities.ProductEntity;

@Repository
public interface ProductPersistenceRepository extends JpaRepository<ProductEntity, Long> {

	Page<ProductEntity> findByIdProductType(Long id, Pageable pageable);

	Page<ProductEntity> findByIdProductTypeAndIdProductStatus(Long id, Long idStatus, Pageable pageable);

	Page<ProductEntity> findByIdProductStatus(Long idStatus, Pageable pageable);

	Optional<ProductEntity> findByIdProductAndIdProductStatus(Long id, Long idStatus);

}
