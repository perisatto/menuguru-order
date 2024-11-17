package com.perisatto.fiapprj.menuguru.application.interfaces;

import java.util.Optional;
import java.util.Set;

import com.perisatto.fiapprj.menuguru.domain.entities.product.Product;

public interface ProductRepository {
	
	Optional<Product> getProductById(Long id) throws Exception;
	
}
