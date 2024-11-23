package com.perisatto.fiapprj.menuguru_order.domain.entities.order;

public enum OrderStatus {
	PENDENTE_PAGAMENTO(1L),
	RECEBIDO(2L),
	EM_PREPARACAO(3L),
	PRONTO(4L),
	FINALIZADO(5L),
	CANCELADO(6L);
	
	private Long id;
	
	OrderStatus(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
}
