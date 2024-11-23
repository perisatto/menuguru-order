package com.perisatto.fiapprj.menuguru_order.infra.gateways.mappers;

import com.perisatto.fiapprj.menuguru_order.domain.entities.product.Product;
import com.perisatto.fiapprj.menuguru_order.domain.entities.product.ProductType;
import com.perisatto.fiapprj.menuguru_order.infra.gateways.dtos.GetProductResponseDTO;

public class ProductMapper {	
	public Product mapToDomainEntity(GetProductResponseDTO product) throws Exception {
		Product productDomainEntity = new Product(product.getName(), ProductType.valueOf(product.getProductType()), product.getDescription(), product.getPrice(), product.getImage());
		productDomainEntity.setId(product.getId());
		return productDomainEntity;
	}
}
