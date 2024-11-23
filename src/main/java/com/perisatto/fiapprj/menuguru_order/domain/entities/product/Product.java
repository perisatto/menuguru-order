package com.perisatto.fiapprj.menuguru_order.domain.entities.product;

import java.io.ByteArrayInputStream;
import java.net.URLConnection;
import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.perisatto.fiapprj.menuguru_order.handler.exceptions.ValidationException;


public class Product {

	static final Logger logger = LogManager.getLogger(Product.class);

	private Long id;
	private String name;
	private ProductType type;
	private String description;
	private Double price;
	private String image;

	private String messageValidation;

	public Product(String name, ProductType type, String description, Double price, String image) throws Exception {
		if(!validate(name, type, description, price, image)) {
			logger.warn(messageValidation);
			throw new ValidationException("prdt-1001", messageValidation);
		}

		this.name = name;
		this.type = type;
		this.description = description;
		this.price = price;
		this.image = image;		
	}

	private boolean validate(String name, ProductType type, String description, Double price, String image) {
		messageValidation = "Error validating product data: ";		
		Boolean valid = true;

		if ((name == null) || (name.isEmpty()) || (name.isBlank())) {
			logger.debug("Error validating product data: null or equals zero price");
			messageValidation = messageValidation + "empty, null or blank name |";
			valid = false;			
		}
		
		if (type == null) {
			logger.debug("Error validating product data: null type");
			messageValidation = messageValidation + "blank type |";
			valid = false;			
		}

		if ((description == null) || (description.isEmpty()) || (description.isBlank())) {
			logger.debug("Error validating product data: null or equals zero price");
			messageValidation = messageValidation + "empty, null or blank description |";
			valid = false;			
		} else if(description.length() > 250) {
			logger.debug("Error validating product data: null or equals zero price");
			messageValidation = messageValidation + "description too large (greater than 250 caracters) |";
			valid = false;
		}

		if((price == null) || (price == 0.0)) {
			logger.debug("Error validating product data: null or equals zero price");
			messageValidation = messageValidation + "null or equals zero price |";
			valid = false;			
		}

		if((image == null) || (image.isEmpty()) || (image.isBlank())){
			logger.debug("Error validating product data: empty, null or blank image");
			messageValidation = messageValidation + "empty, null or blank image |";
			valid = false;			
		} else {
			try	{
				byte[] decodedBytes = Base64.getDecoder().decode(image);
		        
		        ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedBytes);
		        String mimeType = URLConnection.guessContentTypeFromStream(inputStream);
		        if((mimeType == null) || (!mimeType.startsWith("image"))) {
					logger.debug("Error validating product data: invalid Base64 file type (must be a image)");
					messageValidation = messageValidation + "invalid Base64 file type (must be a image) |";
					valid = false;		        	
		        }
			} catch (Exception e) {
				logger.debug("Error validating product data: invalid Base64 image file");
				messageValidation = messageValidation + "invalid Base64 image file |";
				valid = false;
			}
		}

		return valid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductType getProductType() {
		return this.type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {		
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
