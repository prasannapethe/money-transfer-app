package com.db.awmd.challenge;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.transfer.AmountTransferRequest;
import com.db.awmd.challenge.repository.AccountsRepositoryInMemory;
import com.db.awmd.challenge.service.AccountsService;
import com.db.awmd.challenge.service.TransferService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransferWithConcurrencyTest {

	private static final int NUM_OF_THREADS = 1000;

	@Autowired
	private AccountsRepositoryInMemory accountrepo;

	@Autowired
	private AccountsService accountsService;

	@Autowired
	private TransferService transferService;

	@Test
	public void CheckConcurrency() throws Exception {

		accountrepo.clearAccounts();
		Account frmaccount = new Account("BOND-421");
		frmaccount.setBalance(new BigDecimal(1000000.55));
		this.accountsService.createAccount(frmaccount);

		Account toAccount = new Account("BOND-420");
		toAccount.setBalance(new BigDecimal(500000.55));
		this.accountsService.createAccount(toAccount);

		AmountTransferRequest firstToSecound = new AmountTransferRequest("BOND-421", "BOND-420", new BigDecimal(1.00F));
		AmountTransferRequest secondToFirst = new AmountTransferRequest("BOND-420", "BOND-421", new BigDecimal(2.00F));

		Collection<Callable<Boolean>> tasks = new ArrayList<>(NUM_OF_THREADS);

		for (int i = 0; i < NUM_OF_THREADS; i++) {
			tasks.add(() -> transferService.transferAmount(firstToSecound));
			tasks.add(() -> transferService.transferAmount(secondToFirst));
		}
		Executors.newFixedThreadPool(4).invokeAll(tasks);

		System.out.println(accountsService.getAccount("BOND-421"));

		assertThat(frmaccount.getBalance().equals(new BigDecimal(1001000.55)));
		assertThat(toAccount.getBalance().equals(new BigDecimal(499000.55)));
		assertThat(accountsService.getAccount("BOND-421").getBalance().equals(new BigDecimal(1001000.55)));
		assertThat(accountsService.getAccount("BOND-420").getBalance().equals(new BigDecimal(499000.55)));
	}
}
