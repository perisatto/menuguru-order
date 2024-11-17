package com.perisatto.fiapprj.menuguru_order.infra;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.test.context.ActiveProfiles;

import com.perisatto.fiapprj.menuguru_order.infra.gateways.PaymentMongoRepository;
import com.perisatto.fiapprj.menuguru_order.infra.persistences.entities.PaymentDocument;
import com.perisatto.fiapprj.menuguru_order.infra.persistences.repositories.PaymentPersistenceRepository;

@SpringBootTest
@ActiveProfiles(value = "test")
public class PaymentMongoRepositoryTest {
	
	private PaymentPersistenceRepository paymentRepository = new PaymentPersistenceRepository() {
		
		@Override
		public <S extends PaymentDocument> Optional<S> findOne(Example<S> example) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public <S extends PaymentDocument, R> R findBy(Example<S> example,
				Function<FetchableFluentQuery<S>, R> queryFunction) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public <S extends PaymentDocument> Page<S> findAll(Example<S> example, Pageable pageable) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public <S extends PaymentDocument> boolean exists(Example<S> example) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public <S extends PaymentDocument> long count(Example<S> example) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public Page<PaymentDocument> findAll(Pageable pageable) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public List<PaymentDocument> findAll(Sort sort) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public <S extends PaymentDocument> S save(S entity) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Optional<PaymentDocument> findById(String id) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public boolean existsById(String id) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void deleteById(String id) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void deleteAllById(Iterable<? extends String> ids) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void deleteAll(Iterable<? extends PaymentDocument> entities) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void deleteAll() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void delete(PaymentDocument entity) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public long count() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public <S extends PaymentDocument> List<S> saveAll(Iterable<S> entities) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public List<PaymentDocument> findAllById(Iterable<String> ids) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public List<PaymentDocument> findAll() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public <S extends PaymentDocument> List<S> insert(Iterable<S> entities) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public <S extends PaymentDocument> S insert(S entity) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public <S extends PaymentDocument> List<S> findAll(Example<S> example, Sort sort) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public <S extends PaymentDocument> List<S> findAll(Example<S> example) {
			// TODO Auto-generated method stub
			return null;
		}
	};	
	
	@Test
	void givenStringData_thenRegisterPayment() {
		String paymentData = "{\"action\":\"payment.updated\",\"api_version\":\"v1\",\"data\":{\"id\":\"88436747797\"},\"date_created\":\"2024-09-25T01:28:07Z\",\"id\":116049466094,\"live_mode\":true,\"type\":\"payment\",\"user_id\":\"1891840516\"}";
		
		PaymentMongoRepository paymentMongoRepository = new PaymentMongoRepository(paymentRepository);
		Boolean result = paymentMongoRepository.registerPayment(paymentData);
		
		assertThat(result).isTrue();
		
	}
}
