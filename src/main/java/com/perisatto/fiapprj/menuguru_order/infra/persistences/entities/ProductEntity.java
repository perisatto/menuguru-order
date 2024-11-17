package com.perisatto.fiapprj.menuguru_order.infra.persistences.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "Product")
public class ProductEntity {	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProduct;
	private String name;
	private Long idProductType;
	private String description;
	private Double price;
	private Long idProductStatus;
	
	@Lob
    @Column(name = "image", columnDefinition="BLOB")
    private byte[] image;
	
	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getIdProductType() {
		return idProductType;
	}
	
	public void setIdProductType(Long idProductType) {
		this.idProductType = idProductType;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Long getIdProductStatus() {
		return idProductStatus;
	}

	public void setIdProductStatus(Long idProductStatus) {
		this.idProductStatus = idProductStatus;
	}
}
