package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.transfer.AmountTransferRequest;

public interface TransferService {

	boolean transferAmount(AmountTransferRequest amountTransferRequest);
}
