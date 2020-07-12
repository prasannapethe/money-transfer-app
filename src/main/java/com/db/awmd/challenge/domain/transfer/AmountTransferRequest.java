package com.db.awmd.challenge.domain.transfer;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmountTransferRequest {

	@NotNull(message = "Sender id is required")
	@NotEmpty
	private String fromAccountId;

	@NotNull(message = "Receiver id is required")
	@NotEmpty
	private String toAccountId;

	@NotNull
	@DecimalMin(value="0.01",message = "amount should be greater than 0")
	private BigDecimal amount;
	
}
