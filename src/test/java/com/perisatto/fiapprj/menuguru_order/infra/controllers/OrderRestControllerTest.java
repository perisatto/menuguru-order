package com.perisatto.fiapprj.menuguru_order.infra.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Duration;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perisatto.fiapprj.menuguru_order.application.usecases.OrderUseCase;
import com.perisatto.fiapprj.menuguru_order.domain.entities.order.Order;
import com.perisatto.fiapprj.menuguru_order.domain.entities.order.OrderItem;
import com.perisatto.fiapprj.menuguru_order.domain.entities.order.OrderStatus;
import com.perisatto.fiapprj.menuguru_order.infra.controllers.dtos.ChecktoutOrderResponseDTO;
import com.perisatto.fiapprj.menuguru_order.infra.controllers.dtos.CreateOrderRequestDTO;
import com.perisatto.fiapprj.menuguru_order.infra.controllers.dtos.OrderItemDTO;
import com.perisatto.fiapprj.menuguru_order.infra.controllers.dtos.UpdateOrderRequestDTO;

public class OrderRestControllerTest {

	private MockMvc mockMvc;
	
	@Mock
	private OrderUseCase orderUseCase;
	
	@Mock
	private Properties requestProperties;
	
	AutoCloseable openMocks;
	
	@BeforeEach
	void setUp() {
		openMocks = MockitoAnnotations.openMocks(this);
		OrderRestController orderRestController = new OrderRestController(orderUseCase, requestProperties);

		mockMvc = MockMvcBuilders.standaloneSetup(orderRestController)				
				.addFilter((request, response, chain) -> {
					response.setCharacterEncoding("UTF-8");
					chain.doFilter(request, response);					
				}, "/*")
				.build();
	}

	@AfterEach
	void tearDown() throws Exception {
		openMocks.close();
	}
	
	@Nested
	class RegisterOrder {
		
		@Test
		void givenValidData_thenCreateOrder() throws Exception {
			Order order = getOrder();
			
			when(orderUseCase.createOrder(any(Long.class), any()))
			.thenReturn(order);
			
			CreateOrderRequestDTO requestMessage = new CreateOrderRequestDTO();
			requestMessage.setCustomerId(order.getCustomerId());
			
			OrderItemDTO orderItemDto = new OrderItemDTO();
			for (OrderItem orderItem : order.getItems()) {
				orderItemDto.setId(orderItem.getProductId());
				orderItemDto.setActualPrice(orderItem.getActualPrice());
				orderItemDto.setQuantity(orderItem.getQuantity());
			}
			
			Set<OrderItemDTO> orderItems = new LinkedHashSet<OrderItemDTO>(); 
			
			orderItems.add(orderItemDto);
			
			requestMessage.setItems(orderItems);
			
			mockMvc.perform(post("/orders")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(requestMessage)))
			.andExpect(status().isCreated());

			verify(orderUseCase, times(1)).createOrder(any(Long.class), any());
			
		}
	}
	
	@Nested
	class RetrieveOrder {
		
		@Test
		void givenValidId_thenRetrieveOrder() throws Exception {
			Order order = getOrder();
			
			when(orderUseCase.getOrder(any(Long.class)))
			.thenReturn(order);
						
			String orderId = "10";
			
			mockMvc.perform(get("/orders/{orderId}", orderId)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());

			verify(orderUseCase, times(1)).getOrder(any(Long.class));
		}
		
		@Test
		void listOrders() throws Exception {
			when(orderUseCase.findAllOrders(any(Integer.class), any(Integer.class)))
			.thenAnswer(i -> {
				Set<Order> result = new LinkedHashSet<Order>();
				Order order1 = getOrder();
				Order order2 = getOrder();
				result.add(order1);
				result.add(order2);
				return result;
			});
			
			mockMvc.perform(get("/orders")
					.contentType(MediaType.APPLICATION_JSON)
					.param("_page", "1")
					.param("_size", "50"))
			.andExpect(status().isOk());

			verify(orderUseCase, times(1)).findAllOrders(any(Integer.class), any(Integer.class));			
		}
		
		@Test
		void getPreparationQueue() throws Exception {
			when(orderUseCase.listPreparationQueue(any(Integer.class), any(Integer.class)))
			.thenAnswer(i -> {
				Set<Order> result = new LinkedHashSet<Order>();
				Order order1 = getOrder();
				Order order2 = getOrder();
				result.add(order1);
				result.add(order2);
				return result;
			});
			
			mockMvc.perform(get("/preparationQueue")
					.contentType(MediaType.APPLICATION_JSON)
					.param("_page", "1")
					.param("_size", "50"))
			.andExpect(status().isOk());

			verify(orderUseCase, times(1)).listPreparationQueue(any(Integer.class), any(Integer.class));			
		}
	}
	
	@Nested
	class UpdateOrder {
		
		@Test
		void givenValidIdAndStatus_thenUpdateOrder() throws Exception {
			Order order = getOrder();
			
			when(orderUseCase.updateOrder(any(Long.class), any(String.class)))
			.thenReturn(order);
			
			String orderId = "10";
			
			UpdateOrderRequestDTO requestMessage = new UpdateOrderRequestDTO();
			requestMessage.setStatus("PRONTO");
						
			mockMvc.perform(patch("/orders/{orderId}", orderId)
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(requestMessage)))
			.andExpect(status().isOk());

			verify(orderUseCase, times(1)).updateOrder(any(Long.class), any(String.class));
		}
		
		@Test
		void givenValidaData_thenConfirmPayment() throws Exception {
			Order order = getOrder();
			
			when(orderUseCase.confirmPayment(any(Long.class), any(String.class)))
			.thenReturn(order);
			
			String orderId = "10";
			
			String requestMessage = "{\"data\":{\"id\":10}, \"action\":\"payment.created\"}";
						
			mockMvc.perform(post("/orders/{orderId}/confirmPayment", orderId)
					.contentType(MediaType.APPLICATION_JSON)
					.content(requestMessage))
			.andExpect(status().isOk());

			verify(orderUseCase, times(1)).confirmPayment(any(Long.class), any(String.class));
		}
		
		@Test
		void givenValidId_thenCancelOrder() throws Exception {
			Order order = getOrder();
			
			when(orderUseCase.cancelOrder(any(Long.class)))
			.thenReturn(order);
			
			String orderId = "10";
						
			mockMvc.perform(post("/orders/{orderId}/cancel", orderId)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());

			verify(orderUseCase, times(1)).cancelOrder(any(Long.class));
		}
	}
	
	public static Order getOrder() throws Exception {
		Set<OrderItem> orderItems = new LinkedHashSet<OrderItem>();

		OrderItem orderItem = new OrderItem(10L, 10.0, 1);

		orderItems.add(orderItem);

		Order order = new Order(OrderStatus.EM_PREPARACAO, 10L, orderItems);
		order.setReadyToPrepare(new Date());
		order.setWaitingTime(Duration.ZERO);
		return order;
	}
	
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
