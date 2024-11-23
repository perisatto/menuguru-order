package com.perisatto.fiapprj.menuguru_order.application.usecases;

import java.time.Duration;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perisatto.fiapprj.menuguru_order.application.interfaces.CustomerRepository;
import com.perisatto.fiapprj.menuguru_order.application.interfaces.OrderRepository;
import com.perisatto.fiapprj.menuguru_order.application.interfaces.PaymentProcessor;
import com.perisatto.fiapprj.menuguru_order.application.interfaces.PaymentRepository;
import com.perisatto.fiapprj.menuguru_order.application.interfaces.ProductRepository;
import com.perisatto.fiapprj.menuguru_order.domain.entities.customer.Customer;
import com.perisatto.fiapprj.menuguru_order.domain.entities.order.Order;
import com.perisatto.fiapprj.menuguru_order.domain.entities.order.OrderItem;
import com.perisatto.fiapprj.menuguru_order.domain.entities.order.OrderStatus;
import com.perisatto.fiapprj.menuguru_order.domain.entities.payment.Payment;
import com.perisatto.fiapprj.menuguru_order.domain.entities.product.Product;
import com.perisatto.fiapprj.menuguru_order.handler.exceptions.NotFoundException;
import com.perisatto.fiapprj.menuguru_order.handler.exceptions.ValidationException;

public class OrderUseCase {

	static final Logger logger = LogManager.getLogger(OrderUseCase.class);	
	private final OrderRepository orderRepository;
	private final CustomerRepository customerRepository;
	private final ProductRepository productRepository;
	private final PaymentProcessor paymentProcessor;
	private final PaymentRepository paymentRepository;

	public OrderUseCase(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository, 
			PaymentProcessor paymentProcessor, PaymentRepository paymentRepository) {
		this.orderRepository = orderRepository;
		this.customerRepository = customerRepository;
		this.productRepository = productRepository;
		this.paymentProcessor = paymentProcessor;
		this.paymentRepository = paymentRepository;
	}

	public Order createOrder(Long customerId, Set<OrderItem> orderItems) throws Exception {
		logger.info("Creating new order...");		
		if(customerId != null) {
			logger.info("Validating customer...");
			Optional<Customer> customer = customerRepository.getCustomerById(customerId);
			if(customer.isEmpty()) {
				throw new NotFoundException("ordr-2010", "Customer not found");
			}
		}

		Set<OrderItem> items = new LinkedHashSet<OrderItem>();
		Double totalPrice = 0.0;

		logger.info("Validating items...");
		for(OrderItem item : orderItems) {
			Optional<Product> product = productRepository.getProductById(item.getProductId());
			if(product.isPresent()) {
				totalPrice = totalPrice + (product.get().getPrice() * item.getQuantity());
				item.setActualPrice(product.get().getPrice());
				items.add(item);	
			}else {
				throw new NotFoundException("ordr-2000", "Product not found. Id: " + item.getProductId());
			}
		}

		Order newOrder = new Order(OrderStatus.PENDENTE_PAGAMENTO, customerId, items);
		newOrder = orderRepository.createOrder(newOrder);
		logger.info("New order created. Generating payment.");
		
		PaymentUseCase paymentUseCase = new PaymentUseCase(paymentProcessor, paymentRepository);
		Payment payment = paymentUseCase.createPayment(newOrder);
		newOrder.setPaymentLocation(payment.getPaymentLocation());
		orderRepository.updateOrder(newOrder);
		
		logger.info("Payment generated.");
		return newOrder;
	}

	public Order getOrder(Long orderId) throws Exception {
		Optional<Order> order = orderRepository.getOrder(orderId);
		if(order.isPresent()) {
			return order.get();
		} else {
			throw new NotFoundException("ordr-2001", "Order not found");
		}	
	}

	public Set<Order> findAllOrders(Integer limit, Integer page) throws Exception {
		if(limit==null) {
			limit = 10;
		}

		if(page==null) {
			page = 1;
		}

		validateFindAll(limit, page);		

		Set<Order> findResult = orderRepository.findAll(limit, page - 1);		
		return findResult;
	}

	private void validateFindAll(Integer limit, Integer page) throws Exception {
		if (limit < 0 || limit > 50) {
			String message = "Invalid size parameter. Value must be greater than 0 and less than 50. Actual value: " + limit;
			logger.debug("\"validateFindAll\" | limit validation: " + message);
			throw new ValidationException("prdt-2002", message);			
		}

		if (page < 1) {
			String message = "Invalid page parameter. Value must be greater than 0. Actual value: " + page;
			logger.debug("\"validateFindAll\" | offset validation: " + message);
			throw new ValidationException("prdt-2003", message);	
		}
	}

	public Order updateOrder(Long id, String status) throws Exception {
		logger.info("Updating order...");
		Optional<Order> order = orderRepository.getOrder(id);
		if(order.isPresent()) {

			Order updateOrder = order.get();
			try {

				if(updateOrder.getStatus() == OrderStatus.valueOf(status)) {
					throw new ValidationException("ordr-2001","Order is already in " + status + " status");
				}

				updateOrder.setStatus(OrderStatus.valueOf(status));

				Optional<Order> updatedOrder = orderRepository.updateOrder(updateOrder);
				if(updatedOrder.isPresent()) {
					logger.info("Order updated...");
					return updatedOrder.get();
				} else {
					throw new NotFoundException("ordr-2004", "Order not found");
				}
			} catch (IllegalArgumentException e) {
				if(e.getMessage().contains("No enum constant")) {
					throw new ValidationException("ordr-2001","Invalid status");
				}else {
					logger.info("Error creating a new product: "+ e.getMessage());
					throw e;
				}
			} catch (NullPointerException e) {
				logger.info("Error creating a new product: "+ e.getMessage());
				throw new ValidationException("ordr-2001","Invalid status");
			}
		} else {
			throw new NotFoundException("ordr-2005", "Order not found");
		}
	}

	public Order confirmPayment(Long id, String paymentData) throws Exception {
		logger.info("Checkouting order...");
		Optional<Order> order = orderRepository.getOrder(id);
		if(order.isPresent()) {

			Order checkoutOrder = order.get();

			if(checkoutOrder.getStatus() != OrderStatus.PENDENTE_PAGAMENTO) {
				throw new ValidationException("ordr-2006", "Order is already checkout");
			}

			Date currentDate = new Date();
			checkoutOrder.setReadyToPrepare(currentDate);
			checkoutOrder.setStatus(OrderStatus.RECEBIDO);
			
			ObjectMapper mapper = new ObjectMapper();
			String paymentId = "";
			try {
				JsonNode jsonNode = mapper.readTree(paymentData);		
				paymentId = jsonNode.get("data").get("id").textValue();		
			}catch (Exception e) {
				throw new ValidationException("ordr-2010", "Invalid payment id");
			}			
			
			PaymentUseCase paymentUseCase = new PaymentUseCase(paymentProcessor, paymentRepository);
			
			if(!paymentUseCase.registerPayment(paymentData)) {
				throw new ValidationException("ordr-2020", "Paymet register fail");
			}
			
			checkoutOrder.setPaymentIdentifier(paymentId);			

			Optional<Order> updatedOrder = orderRepository.updateOrder(checkoutOrder);
			if(updatedOrder.isPresent()) {
				logger.info("Order checkouted...");
				return updatedOrder.get();
			} else {
				throw new NotFoundException("ordr-2008", "Order not found");
			}
		} else {
			throw new NotFoundException("ordr-2009", "Order not found");
		}
	}

	public Order cancelOrder(Long id) throws Exception {
		logger.info("Canceling order...");
		Optional<Order> order = orderRepository.getOrder(id);
		if(order.isPresent()) {
			Order cancelOrder = order.get();

			if((cancelOrder.getStatus() == OrderStatus.PRONTO) || 
					(cancelOrder.getStatus() == OrderStatus.FINALIZADO) || 
					(cancelOrder.getStatus() == OrderStatus.CANCELADO)) {
				throw new ValidationException("ordr-2010", "Order can't be canceled. Cause: status = " + cancelOrder.getStatus().toString());
			}

			cancelOrder.setStatus(OrderStatus.CANCELADO);

			Optional<Order> canceledOrder = orderRepository.updateOrder(cancelOrder);

			if(canceledOrder.isPresent()) {
				logger.info("Order canceled...");
				return canceledOrder.get();
			} else {
				throw new NotFoundException("ordr-2011", "Order not found");
			}
		} else {
			throw new NotFoundException("ordr-2012", "Order not found");
		}
	}

	public Set<Order> listPreparationQueue(Integer limit, Integer page) throws Exception {
		if(limit==null) {
			limit = 10;
		}

		if(page==null) {
			page = 1;
		}

		validateFindAll(limit, page);		

		Set<Order> findResult = orderRepository.listPreparationQueue(limit, page - 1);	

		Date currentDate = new Date();

		for(Order order : findResult) {			
			Duration duration = Duration.between(order.getReadyToPrepare().toInstant(), currentDate.toInstant());
			order.setWaitingTime(duration);
		}
		return findResult;
	}
}
