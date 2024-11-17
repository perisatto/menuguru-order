package com.perisatto.fiapprj.menuguru.infra.controllers.dtos;

import java.util.LinkedHashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.perisatto.fiapprj.menuguru.domain.entities.product.Product;
import com.perisatto.fiapprj.menuguru.infra.gateways.dtos.GetProductResponseDTO;


public class GetProductListResponseDTO {
	@JsonProperty(value = "_page")
	private Integer page;
	@JsonProperty(value = "_size")
	private Integer size;
	@JsonProperty(value = "_pageElements")
	private Integer pageElements;
	
	@JsonProperty(value = "_content")
	private Set<GetProductResponseDTO> content = new LinkedHashSet<>();

	public Integer getPage() {
		return page;
	}
	
	public void setPage(Integer page) {
		this.page = page;
	}
		
	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getPageElements() {
		return pageElements;
	}

	public void setPageElements(Integer pageElements) {
		this.pageElements = pageElements;
	}

	public Set<GetProductResponseDTO> getContent() {
		return content;
	}

	public void setContent(Set<GetProductResponseDTO> content) {
		this.content = content;
	}
	
	public void setContent(Set<Product> content, Integer page, Integer size) {
		this.setPage(page);
		this.setSize(size);
		this.setPageElements(content.size());
		ModelMapper customerMapper = new ModelMapper();
		
		for(Product product : content) {
			GetProductResponseDTO newProductResponse = customerMapper.map(product, GetProductResponseDTO.class);
			this.content.add(newProductResponse);
		}
	}
}
