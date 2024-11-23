package com.perisatto.fiapprj.menuguru_order.domain.entities.customer;

import java.util.InputMismatchException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.perisatto.fiapprj.menuguru_order.handler.exceptions.ValidationException;

public class CPF {

	static final Logger logger = LogManager.getLogger(CPF.class);
	
	private final String documentNumber;

	public CPF(String documentNumber) throws Exception {			
		if(!this.isCpfValid(documentNumber)) {
			logger.warn("Error validating customer data");
			throw new ValidationException("cstm-3001", "Invalid document number");
		}

		this.documentNumber = documentNumber;
	}

	private boolean isCpfValid(String documentNumber) {
		
		logger.debug("Starting CPF validation. CPF: " + documentNumber);
		
		if (documentNumber.equals("00000000000") ||
				documentNumber.equals("11111111111") ||
				documentNumber.equals("22222222222") || documentNumber.equals("33333333333") ||
				documentNumber.equals("44444444444") || documentNumber.equals("55555555555") ||
				documentNumber.equals("66666666666") || documentNumber.equals("77777777777") ||
				documentNumber.equals("88888888888") || documentNumber.equals("99999999999") ||
				(documentNumber.length() != 11))
			return(false);

		try {
			char dig10, dig11;
			int i, digit, result;
			int sum = 0;
			int weight = 10;

			for(i=0;i<9;i++) {
				digit = (int)documentNumber.charAt(i) - 48;
				sum = sum + (digit * weight);
				weight = weight - 1;
			}			
			
			result = 11 - (sum % 11);

			if((result==10)||(result==11)) {
				dig10 = (char)48;				
			}else {
				dig10 = (char)(result + 48);
			}
			
			logger.debug("First calculated verification digit: " + dig10);
			
			sum = 0;
			weight = 11;
			for(i=0;i<10;i++) {
				digit = (int)documentNumber.charAt(i) - 48;
				sum = sum + (digit * weight);
				weight = weight - 1;
			}

			result = 11 - (sum % 11);
			if((result==10)||(result==11)) {
				dig11 = (char)48;				
			}else {
				dig11 = (char)(result + 48);
			}

			logger.debug("Second calculated verification digit: " + dig11);
			
			if ((dig10 == documentNumber.charAt(9)) && (dig11 == documentNumber.charAt(10))) {
				return true;								
			} else { 
				return false;
			}
		} catch (InputMismatchException erro) {
			return false;
		}			
	}

	public String getDocumentNumber() {
		return documentNumber;
	}
}
