package com.db.awmd.challenge.service.Impli;

import org.springframework.stereotype.Service;

import com.db.awmd.challenge.domain.transfer.AmountTransferRequest;
import com.db.awmd.challenge.repository.TransferRepository;
import com.db.awmd.challenge.service.TransferService;

@Service
public class TransferServiceImpli implements TransferService {

	private final TransferRepository transferRepo;

	public TransferServiceImpli(TransferRepository transferRepo) {
		this.transferRepo = transferRepo;
	}

	public boolean transferAmount(AmountTransferRequest request) {
		return transferRepo.transferAmount(request.getFromAccountId(), request.getToAccountId(), request.getAmount());
	}
}
