package com.perisatto.fiapprj.menuguru.application.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import com.perisatto.fiapprj.menuguru.application.interfaces.CustomerRepository;
import com.perisatto.fiapprj.menuguru.application.interfaces.OrderRepository;
import com.perisatto.fiapprj.menuguru.application.interfaces.PaymentProcessor;
import com.perisatto.fiapprj.menuguru.application.interfaces.PaymentRepository;
import com.perisatto.fiapprj.menuguru.application.interfaces.ProductRepository;
import com.perisatto.fiapprj.menuguru.application.usecases.OrderUseCase;
import com.perisatto.fiapprj.menuguru.domain.entities.customer.CPF;
import com.perisatto.fiapprj.menuguru.domain.entities.customer.Customer;
import com.perisatto.fiapprj.menuguru.domain.entities.order.Order;
import com.perisatto.fiapprj.menuguru.domain.entities.order.OrderItem;
import com.perisatto.fiapprj.menuguru.domain.entities.order.OrderStatus;
import com.perisatto.fiapprj.menuguru.domain.entities.payment.Payment;
import com.perisatto.fiapprj.menuguru.domain.entities.product.Product;
import com.perisatto.fiapprj.menuguru.domain.entities.product.ProductType;
import com.perisatto.fiapprj.menuguru.handler.exceptions.NotFoundException;
import com.perisatto.fiapprj.menuguru.handler.exceptions.ValidationException;


@ActiveProfiles(value = "test")
public class OrderUseCaseTest {

	private CustomerRepository orderCustomerPort = new CustomerRepository() {

		@Override
		public Optional<Customer> getCustomerById(Long id) throws Exception {
			if(id==10L) {
				String customerName = "Roberto Machado";
				String customerEmail = "roberto.machado@bestmail.com";
				String documentNumber = "90779778057";

				CPF customerCPF = new CPF(documentNumber);
				Customer customer = new Customer(10L, customerCPF, customerName, customerEmail);
				return Optional.of(customer);
			} else {
				throw new NotFoundException("tst-0000", "Customer not found");
			}
		}
	};
	private ProductRepository orderProductPort = new ProductRepository() {

		@Override
		public Optional<Product> getProductById(Long id) throws Exception {
			String productName = "X-Bacon";
			ProductType productType = ProductType.LANCHE;
			String productDescription = "O x-bacon é um sanduíche irresistível que une o sabor intenso do bacon crocante com queijo derretido"
					+ ", alface, tomate e um suculento hambúrguer, tudo envolto em um pão macio e tostado. "
					+ "Uma explosão de sabores em cada mordida!";
			Double productPrice = 35.50;
			String productImage = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNk+A8AAQUBAScY42YAAAAASUVORK5CYII=";			
			Product product = new Product(productName, productType, productDescription, productPrice, productImage);
			product.setId(1L);
			return Optional.of(product);
		}

		@Override
		public Product createProduct(Product product) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<Product> updateProduct(Product product) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Boolean deleteProduct(Long id) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<Product> findAll(Integer limit, Integer offset, String type) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

	};
	private OrderRepository manageOrderPort = new OrderRepository() {

		@Override
		public Order createOrder(Order order) throws Exception {
			Order newOrder = order;
			newOrder.setId(1L);
			return newOrder;
		}

		@Override
		public Optional<Order> getOrder(Long orderId) throws Exception {
			if(orderId==1L) {
				OrderStatus orderStatus = OrderStatus.PENDENTE_PAGAMENTO;
				Long customerId = 1L;

				Long firstProductId = 1L;
				Double firstProductActualPrice = 35.50;
				Integer firstProductQuantity = 1;		
				OrderItem firstItem = new OrderItem(firstProductId, firstProductActualPrice, firstProductQuantity);

				Long secondProductId = 1L;
				Double secondProductActualPrice = 35.50;
				Integer secondProductQuantity = 1;		
				OrderItem secondItem = new OrderItem(secondProductId, secondProductActualPrice, secondProductQuantity);		

				Set<OrderItem> orderItems = new LinkedHashSet<OrderItem>();
				orderItems.add(firstItem);
				orderItems.add(secondItem);

				Order order = new Order(orderStatus, customerId, orderItems);
				order.setId(orderId);
				return Optional.of(order);
			}else {
				return Optional.empty();
			}
		}

		@Override
		public Set<Order> findAll(Integer limit, Integer page) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<Order> updateOrder(Order order) {
			Order newOrder = order;
			newOrder.setId(1L);
			return Optional.of(newOrder);
		}

		@Override
		public Set<Order> listPreparationQueue(Integer limit, Integer page) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}		
	};

	private PaymentProcessor paymentProcessor = new PaymentProcessor() {

		@Override
		public Payment createPayment(Payment payment) {
			// TODO Auto-generated method stub
			return null;
		}
	};

	private PaymentRepository paymentRepository = new PaymentRepository() {

		@Override
		public Boolean registerPayment(String paymentData) {
			return true;
		}
	};

	@Test
	void givenValidParameters_thenCreateOrder() throws Exception {
		Long customerId = 10L;
		Long firstProductId = 1L;
		Double firstProductActualPrice = 35.50;
		Integer firstProductQuantity = 1;		
		OrderItem firstItem = new OrderItem(firstProductId, null, firstProductQuantity);

		Long secondProductId = 1L;
		Double secondProductActualPrice = 35.50;
		Integer secondProductQuantity = 1;		
		OrderItem secondItem = new OrderItem(secondProductId, null, secondProductQuantity);		

		Set<OrderItem> orderItems = new LinkedHashSet<OrderItem>();
		orderItems.add(firstItem);
		orderItems.add(secondItem);	

		OrderUseCase OrderUseCase = new OrderUseCase(manageOrderPort, orderCustomerPort, orderProductPort, paymentProcessor, paymentRepository);

		Order order = OrderUseCase.createOrder(customerId, orderItems);

		assertThat(order.getId()).isGreaterThan(0);
		assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDENTE_PAGAMENTO);
		assertThat(order.getTotalPrice()).isEqualTo(firstProductActualPrice + secondProductActualPrice);
	}

	@Test
	void givenNoCustomerId_thenCreateOrder() throws Exception {
		Long firstProductId = 10L;
		Double firstProductActualPrice = 35.50;
		Integer firstProductQuantity = 1;		
		OrderItem firstItem = new OrderItem(firstProductId, null, firstProductQuantity);

		Long secondProductId = 1L;
		Double secondProductActualPrice = 35.50;
		Integer secondProductQuantity = 1;		
		OrderItem secondItem = new OrderItem(secondProductId, null, secondProductQuantity);		

		Set<OrderItem> orderItems = new LinkedHashSet<OrderItem>();
		orderItems.add(firstItem);
		orderItems.add(secondItem);	


		OrderUseCase OrderUseCase = new OrderUseCase(manageOrderPort, orderCustomerPort, orderProductPort, paymentProcessor, paymentRepository);

		Order order = OrderUseCase.createOrder(null, orderItems);

		assertThat(order.getId()).isGreaterThan(0);
		assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDENTE_PAGAMENTO);
		assertThat(order.getTotalPrice()).isEqualTo(firstProductActualPrice + secondProductActualPrice);		
	}

	@Test
	void givenInvalidCustomerId_thenRefusesToCreateOrder() {
		try { 
			Long customerId = 0L;
			Long firstProductId = 1L;
			Integer firstProductQuantity = 1;		
			OrderItem firstItem = new OrderItem(firstProductId, null, firstProductQuantity);

			Long secondProductId = 1L;
			Integer secondProductQuantity = 1;		
			OrderItem secondItem = new OrderItem(secondProductId, null, secondProductQuantity);		

			Set<OrderItem> orderItems = new LinkedHashSet<OrderItem>();
			orderItems.add(firstItem);
			orderItems.add(secondItem);	


			OrderUseCase OrderUseCase = new OrderUseCase(manageOrderPort, orderCustomerPort, orderProductPort, paymentProcessor, paymentRepository);

			Order order = OrderUseCase.createOrder(customerId, orderItems);

			assertTrue(false);
		} catch (Exception e) {
			assertThatExceptionOfType(ValidationException.class);
		}
	}

	@Test
	void givenInexistentCustomerId_thenRefusesToCreateOrder() {
		try { 
			Long customerId = 20L;
			Long firstProductId = 1L;
			Integer firstProductQuantity = 1;		
			OrderItem firstItem = new OrderItem(firstProductId, null, firstProductQuantity);

			Long secondProductId = 1L;
			Integer secondProductQuantity = 1;		
			OrderItem secondItem = new OrderItem(secondProductId, null, secondProductQuantity);		

			Set<OrderItem> orderItems = new LinkedHashSet<OrderItem>();
			orderItems.add(firstItem);
			orderItems.add(secondItem);	


			OrderUseCase OrderUseCase = new OrderUseCase(manageOrderPort, orderCustomerPort, orderProductPort, paymentProcessor, paymentRepository);

			Order order = OrderUseCase.createOrder(customerId, orderItems);

			assertTrue(false);
		} catch (Exception e) {
			assertThatExceptionOfType(NotFoundException.class);
		}
	}

	@Test
	void givenInvalidProductId_thenRefusesToCreateOrder() {
		try { 
			Long customerId = 20L;
			Long firstProductId = 0L;
			Integer firstProductQuantity = 1;		
			OrderItem firstItem = new OrderItem(firstProductId, null, firstProductQuantity);

			Long secondProductId = null;
			Integer secondProductQuantity = 1;		
			OrderItem secondItem = new OrderItem(secondProductId, null, secondProductQuantity);		

			Set<OrderItem> orderItems = new LinkedHashSet<OrderItem>();
			orderItems.add(firstItem);
			orderItems.add(secondItem);	


			OrderUseCase OrderUseCase = new OrderUseCase(manageOrderPort, orderCustomerPort, orderProductPort, paymentProcessor, paymentRepository);

			Order order = OrderUseCase.createOrder(customerId, orderItems);

			assertTrue(false);
		} catch (Exception e) {
			assertThatExceptionOfType(ValidationException.class);
		}
	}


	@Test
	void givenInexistentProductId_thenRefusesToCreateOrder() {
		try { 
			Long customerId = 20L;
			Long firstProductId = 1L;
			Integer firstProductQuantity = 1;		
			OrderItem firstItem = new OrderItem(firstProductId, null, firstProductQuantity);

			Long secondProductId = 3L;
			Integer secondProductQuantity = 1;		
			OrderItem secondItem = new OrderItem(secondProductId, null, secondProductQuantity);		

			Set<OrderItem> orderItems = new LinkedHashSet<OrderItem>();
			orderItems.add(firstItem);
			orderItems.add(secondItem);	


			OrderUseCase OrderUseCase = new OrderUseCase(manageOrderPort, orderCustomerPort, orderProductPort, paymentProcessor, paymentRepository);

			Order order = OrderUseCase.createOrder(customerId, orderItems);

			assertTrue(false);
		} catch (Exception e) {
			assertThatExceptionOfType(NotFoundException.class);
		}
	}


	@Test
	void givenInvalidQuantity_thenRefusesToCreateOrder() {
		try { 
			Long customerId = 20L;
			Long firstProductId = 1L;
			Integer firstProductQuantity = 0;		
			OrderItem firstItem = new OrderItem(firstProductId, null, firstProductQuantity);

			Long secondProductId = 3L;
			Integer secondProductQuantity = 1;		
			OrderItem secondItem = new OrderItem(secondProductId, null, secondProductQuantity);		

			Set<OrderItem> orderItems = new LinkedHashSet<OrderItem>();
			orderItems.add(firstItem);
			orderItems.add(secondItem);	


			OrderUseCase OrderUseCase = new OrderUseCase(manageOrderPort, orderCustomerPort, orderProductPort, paymentProcessor, paymentRepository);

			Order order = OrderUseCase.createOrder(customerId, orderItems);			

			assertTrue(false);
		} catch (Exception e) {
			assertThatExceptionOfType(NotFoundException.class);
		}
	}

	@Test
	void givenValidOrderId_thenRetrieveOrder() throws Exception {
		Long orderId = 1L;

		OrderUseCase OrderUseCase = new OrderUseCase(manageOrderPort, orderCustomerPort, orderProductPort, paymentProcessor, paymentRepository);

		Order order = OrderUseCase.getOrder(orderId);

		assertThat(order.getId()).isEqualTo(orderId);
		assertThat(order.getItems()).isNotEmpty();
		assertThat(order.getTotalPrice()).isGreaterThan(0.0);		
	}

	@Test
	void givenInexistentOrderId_thenRefusesToRetrieveOrder() {
		try { 
			Long orderId = 2L;

			OrderUseCase OrderUseCase = new OrderUseCase(manageOrderPort, orderCustomerPort, orderProductPort, paymentProcessor, paymentRepository);

			Order order = OrderUseCase.getOrder(orderId);

			assertTrue(false);
		}catch (Exception e) {
			assertThatExceptionOfType(NotFoundException.class);
		}

	}

	@Test
	void givenParameters_thenListAllOrders() throws Exception {
		OrderUseCase OrderUseCase = new OrderUseCase(manageOrderPort, orderCustomerPort, orderProductPort, paymentProcessor, paymentRepository);

		Set<Order> orderList = OrderUseCase.findAllOrders(10, 1);

		assertThat(orderList);	
	}	

	@Test
	void givenNullParameters_thenListAllOrders() throws Exception {
		OrderUseCase OrderUseCase = new OrderUseCase(manageOrderPort, orderCustomerPort, orderProductPort, paymentProcessor, paymentRepository);

		Set<Order> orderList = OrderUseCase.findAllOrders(null, null);

		assertThat(orderList);	
	}	

	@Test
	void givenInvalidLimit_thenRefusesListAllOrders()  {
		OrderUseCase OrderUseCase = new OrderUseCase(manageOrderPort, orderCustomerPort, orderProductPort, paymentProcessor, paymentRepository);

		try {
			Set<Order> orderList = OrderUseCase.findAllOrders(100, 1);
		}catch (Exception e) {
			assertThatExceptionOfType(ValidationException.class);
		}
	}

	@Test
	void givenInvalidPage_thenRefusesListAllOrders()  {
		OrderUseCase OrderUseCase = new OrderUseCase(manageOrderPort, orderCustomerPort, orderProductPort, paymentProcessor, paymentRepository);

		try {
			Set<Order> orderList = OrderUseCase.findAllOrders(10, 0);
		}catch (Exception e) {
			assertThatExceptionOfType(ValidationException.class);
		}
	}

	@Test
	void givenParameters_thenUpdateOrders() throws Exception {
		OrderUseCase orderUseCase = new OrderUseCase(manageOrderPort, orderCustomerPort, orderProductPort, paymentProcessor, paymentRepository);

		Order order = orderUseCase.getOrder(1L);

		order = orderUseCase.updateOrder(1L, "FINALIZADO");

		assertThat(order);	
	}

	@Test
	void givenInvalidParameters_thenRefusesUpdateOrders() {
		OrderUseCase orderUseCase = new OrderUseCase(manageOrderPort, orderCustomerPort, orderProductPort, paymentProcessor, paymentRepository);

		try {
			Order order = orderUseCase.getOrder(1L);

			order = orderUseCase.updateOrder(1L, "RECEBIDO");

		}catch (Exception e) {
			assertThatExceptionOfType(ValidationException.class);
		}	
	}

	@Test 
	void givenOrder_thenConfirmPayment () throws Exception {
		OrderUseCase orderUseCase = new OrderUseCase(manageOrderPort, orderCustomerPort, orderProductPort, paymentProcessor, paymentRepository);

		Order order = orderUseCase.confirmPayment(1L, "{\"action\":\"payment.updated\",\"api_version\":\"v1\",\"data\":{\"id\":\"88436747797\"},\"date_created\":\"2024-09-25T01:28:07Z\",\"id\":116049466094,\"live_mode\":true,\"type\":\"payment\",\"user_id\":\"1891840516\"}");

		assertThat(order);
	}
}
