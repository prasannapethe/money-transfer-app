package com.db.awmd.challenge.repository;

import java.math.BigDecimal;

public interface TransferRepository {

	boolean transferAmount(String fromAccountId, String toAccountId, BigDecimal amount);
}
