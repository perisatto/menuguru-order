package com.perisatto.fiapprj.menuguru_order.bdd;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.perisatto.fiapprj.menuguru_order.infra.controllers.dtos.ChecktoutOrderResponseDTO;
import com.perisatto.fiapprj.menuguru_order.infra.controllers.dtos.CreateOrderRequestDTO;
import com.perisatto.fiapprj.menuguru_order.infra.controllers.dtos.CreateOrderResponseDTO;
import com.perisatto.fiapprj.menuguru_order.infra.controllers.dtos.OrderItemDTO;
import com.perisatto.fiapprj.menuguru_order.infra.controllers.dtos.UpdateOrderRequestDTO;

import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class StepDefinition {
	
	private Response response;
	String newStatus;
	private List<CreateOrderRequestDTO> createOrderRequests = new ArrayList<>();
	private CreateOrderResponseDTO createOrderResponse;
	private final String ENDPOINT_CUSTOMER_API = "http://localhost:8080/menuguru-orders/v1/orders";
	
	@DataTableType
	public CreateOrderRequestDTO orderEntry(Map<String, String> entry) {
		CreateOrderRequestDTO orderEntry = new CreateOrderRequestDTO();
		
		orderEntry.setCustomerId(Long.parseLong(entry.get("customer id")));
		
		OrderItemDTO orderItem = new OrderItemDTO();
		orderItem.setId(Long.parseLong(entry.get("item id")));
		orderItem.setQuantity(Integer.parseInt(entry.get("item quantity")));
		
		Set<OrderItemDTO> orderItems = new LinkedHashSet<OrderItemDTO>();
		orderItems.add(orderItem);
		
		orderEntry.setItems(orderItems);
		return orderEntry;
	}
	
	@Given("order has the following attributes:")
	public void order_has_the_following_attributes(List<CreateOrderRequestDTO> customerDataTable) {
	    createOrderRequests = customerDataTable;
	}

	@When("register a new order")
	public CreateOrderResponseDTO register_a_new_order() {
	    var createOrderRequest = createOrderRequests.get(0);
	    
		response = given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(createOrderRequest)
				.when()
				.post(ENDPOINT_CUSTOMER_API);
		return response.then().extract().as(CreateOrderResponseDTO.class);
	}

	@Then("the order is successfully registered")
	public void the_order_is_successfully_registered() {
	    response.then()
	    .statusCode(HttpStatus.CREATED.value());
	}

	@Then("should be showed")
	public void should_be_showed() {
		response.then()
		.body(matchesJsonSchemaInClasspath("./schemas/CreateOrderResponse.json"));
	}

	@Given("order is already registered with the following attributes")
	public void order_is_already_registered_with_the_following_attributes(List<CreateOrderRequestDTO> customerDataTable) {
		createOrderRequests = customerDataTable;
		createOrderResponse = register_a_new_order();
	}

	@When("ask for order information")
	public void ask_for_order_information() {
	    response = given()
	    		.contentType(MediaType.APPLICATION_JSON_VALUE)
	    		.when()
	    		.get(ENDPOINT_CUSTOMER_API + "/{orderId}", createOrderResponse.getId().toString());
	}

	@Then("the order information is retrieved")
	public void the_order_information_is_retrieved() {
	    response.then()
	    .statusCode(HttpStatus.OK.value())
	    .body(matchesJsonSchemaInClasspath("./schemas/GetOrderResponse.json"));
	}

	@When("the status of the order is changed")
	public ChecktoutOrderResponseDTO the_status_of_the_order_is_changed() {
		newStatus = "EM_PREPARACAO";
			
		UpdateOrderRequestDTO updateOrderRequestDTO = new UpdateOrderRequestDTO();
		updateOrderRequestDTO.setStatus(newStatus);
		
		response = given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(updateOrderRequestDTO)
				.when()
				.patch(ENDPOINT_CUSTOMER_API + "/{orderId}", createOrderResponse.getId().toString());
		return response.then().extract().as(ChecktoutOrderResponseDTO.class);
	}

	@Then("updates the order information with new status")
	public void updates_the_order_information_with_new_status() {
	    response.then()
	    .statusCode(HttpStatus.OK.value())
	    .body(matchesJsonSchemaInClasspath("./schemas/UpdateOrderResponse.json"))
	    .body("status", equalTo(newStatus));
	}
}
