package com.db.awmd.challenge.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.UserAccountNotFoundException;
import com.db.awmd.challenge.service.AccountsService;
import com.db.awmd.challenge.service.NotificationService;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class TransferRepositoryImpli implements TransferRepository {

	@Autowired
	private AccountsService accountservice;

	private NotificationService notificationService;

	public TransferRepositoryImpli(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	private final Object lock = new Object();

	@Override
	public boolean transferAmount(String fromAccountId, String toAccountId, BigDecimal amount) {
		// TODO Auto-generated method stub

		Account frmaccount = accountservice.getAccount(fromAccountId);
		Account toaccount = accountservice.getAccount(toAccountId);

		if (Objects.equals(fromAccountId, toAccountId)) {
			throw new IllegalArgumentException("payer and payee should be different");
		}

		if (Objects.equals(frmaccount, null)) {
			throw new UserAccountNotFoundException("Payer account not found");
		}

		if (Objects.equals(toaccount, null)) {
			throw new UserAccountNotFoundException("Payee account not found");
		}

		synchronized (lock) {

			Account frmacc = accountservice.getAccount(fromAccountId);
			Account toacc = accountservice.getAccount(toAccountId);

			log.info("Transfer: thread={}, from={}, to={}, amount={}", Thread.currentThread().getName(), frmacc, toacc,
					amount);

			/*
			 * If the balance is less than transfer amount
			 */
			if (frmacc.getBalance().compareTo(amount) < 0) {
				throw new IllegalArgumentException("Insufficient funds");
			}

			frmacc.setBalance(frmacc.getBalance().subtract(amount));
			toacc.setBalance(toacc.getBalance().add(amount));

			DateFormat df = new SimpleDateFormat("dd-MM-yy");
			Date dateobj = new Date();

			notificationService.notifyAboutTransfer(frmacc,
					String.format("Your A/c no %s has been debited by Rs. %s on %s. A/c Bal is Rs.%s.",
							frmacc.getAccountId(), amount.toString(), df.format(dateobj),
							frmacc.getBalance().toString()));
			notificationService.notifyAboutTransfer(frmacc,
					String.format("Your A/c no %s has been credited by Rs. %s on %s. A/c Bal is Rs.%s.",
							toacc.getAccountId(), amount.toString(), df.format(dateobj),
							toacc.getBalance().toString()));
		}

		log.info(frmaccount.toString());

		return true;
	}

}
