package com.perisatto.fiapprj.menuguru_order.application.interfaces;

import java.util.Optional;

import com.perisatto.fiapprj.menuguru_order.domain.entities.product.Product;

public interface ProductRepository {
	
	Optional<Product> getProductById(Long id) throws Exception;
	
}
