package com.perisatto.fiapprj.menuguru_order.infra.controllers;

import java.net.URI;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perisatto.fiapprj.menuguru_order.application.usecases.OrderUseCase;
import com.perisatto.fiapprj.menuguru_order.domain.entities.order.Order;
import com.perisatto.fiapprj.menuguru_order.domain.entities.order.OrderItem;
import com.perisatto.fiapprj.menuguru_order.infra.controllers.dtos.ChecktoutOrderResponseDTO;
import com.perisatto.fiapprj.menuguru_order.infra.controllers.dtos.CreateOrderRequestDTO;
import com.perisatto.fiapprj.menuguru_order.infra.controllers.dtos.CreateOrderResponseDTO;
import com.perisatto.fiapprj.menuguru_order.infra.controllers.dtos.GetOrderListResponseDTO;
import com.perisatto.fiapprj.menuguru_order.infra.controllers.dtos.GetOrderPraparationQueueResponseDTO;
import com.perisatto.fiapprj.menuguru_order.infra.controllers.dtos.GetOrderResponseDTO;
import com.perisatto.fiapprj.menuguru_order.infra.controllers.dtos.OrderItemDTO;
import com.perisatto.fiapprj.menuguru_order.infra.controllers.dtos.UpdateOrderRequestDTO;
import com.perisatto.fiapprj.menuguru_order.infra.gateways.mappers.PaymentConfirmJsonParser;


@RestController
public class OrderRestController {

	private final OrderUseCase orderUseCase;
	private final Properties requestProperties;
	
	public OrderRestController(OrderUseCase orderUseCase, Properties requestProperties) {
		this.orderUseCase = orderUseCase;
		this.requestProperties = requestProperties;
	}
	
	@PostMapping(value = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CreateOrderResponseDTO> createOrder(@RequestBody CreateOrderRequestDTO createRequest) throws Exception {
		requestProperties.setProperty("resourcePath", "/orders");
		ModelMapper orderMapper = new ModelMapper();
		Set<OrderItem> orderItems = new LinkedHashSet<OrderItem>();
		for(OrderItemDTO requestItem : createRequest.getItems()) {
			OrderItem item = new OrderItem(requestItem.getId(), null, requestItem.getQuantity());
			orderItems.add(item);
		}		
		Order order = orderUseCase.createOrder(createRequest.getCustomerId(), orderItems);		
		CreateOrderResponseDTO response = orderMapper.map(order, CreateOrderResponseDTO.class);
		response.setQrData(order.getPaymentLocation());
		URI location = new URI("/orders/" + response.getId());
		return ResponseEntity.status(HttpStatus.CREATED).location(location).body(response);
	}
	
	
	@GetMapping(value = "/orders/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetOrderResponseDTO> get(@PathVariable(value = "orderId") Long orderId) throws Exception {
		requestProperties.setProperty("resourcePath", "/order/" + orderId.toString());
		Order order = orderUseCase.getOrder(orderId);
		ModelMapper orderMapper = new ModelMapper();
		GetOrderResponseDTO response = orderMapper.map(order, GetOrderResponseDTO.class);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetOrderListResponseDTO> getAll(@RequestParam(value = "_page", required = true) Integer page,
			@RequestParam(value = "_size", required = true) Integer size) throws Exception {
		requestProperties.setProperty("resourcePath", "/orders");
		Set<Order> orders = orderUseCase.findAllOrders(size, page);
		GetOrderListResponseDTO response = new GetOrderListResponseDTO();
		response.setContent(orders, page, size);		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping(value = "/preparationQueue", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetOrderPraparationQueueResponseDTO> getPreparationQueue(@RequestParam(value = "_page", required = true) Integer page,
			@RequestParam(value = "_size", required = true) Integer size) throws Exception {
		requestProperties.setProperty("resourcePath", "/preparationQueue");
		Set<Order> orders = orderUseCase.listPreparationQueue(size, page);
		GetOrderPraparationQueueResponseDTO response = new GetOrderPraparationQueueResponseDTO();
		response.setContent(orders, page, size);		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping(value = "/orders/{orderId}/confirmPayment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ChecktoutOrderResponseDTO> checkoutOrder(@PathVariable(value = "orderId") Long orderId, @RequestBody String checkoutRequest) throws Exception {		
		requestProperties.setProperty("resourcePath", "/orders/" + orderId +"/checkout");
		ChecktoutOrderResponseDTO response = new ChecktoutOrderResponseDTO();
		PaymentConfirmJsonParser paymentConfirmJsonParser = new PaymentConfirmJsonParser();
		if(paymentConfirmJsonParser.isPaymentCreated(checkoutRequest)) {
			Order checkoutedOrder = orderUseCase.confirmPayment(orderId, checkoutRequest);
			response.setStatus(checkoutedOrder.getStatus().toString());			
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping(value = "/orders/{orderId}/cancel")
	public ResponseEntity<Object> cancelOrder(@PathVariable(value = "orderId") Long orderId) throws Exception {
		requestProperties.setProperty("resourcePath", "/orders/" + orderId +"/cancel");
		orderUseCase.cancelOrder(orderId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
	
	@PatchMapping(value = "/orders/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ChecktoutOrderResponseDTO> patch(@PathVariable(value = "orderId") Long orderId, @RequestBody UpdateOrderRequestDTO updateRequest) throws Exception {
		requestProperties.setProperty("resourcePath", "/orders/" + orderId.toString());			
		Order order = orderUseCase.updateOrder(orderId, updateRequest.getStatus());
		ChecktoutOrderResponseDTO response = new ChecktoutOrderResponseDTO();
		response.setStatus(order.getStatus().toString());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
