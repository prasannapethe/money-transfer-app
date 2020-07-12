package com.db.awmd.challenge;

import static org.assertj.core.api.Assertions.assertThat;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.transfer.AmountTransferRequest;
import com.db.awmd.challenge.repository.AccountsRepositoryInMemory;
import com.db.awmd.challenge.service.AccountsService;
import com.db.awmd.challenge.service.TransferService;

import java.math.BigDecimal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransferServiceTest {

	@Autowired
	private AccountsRepositoryInMemory accountrepo;

	@Autowired
	private AccountsService accountsService;

	@Autowired
	private TransferService transferService;

	@Test
	public void TransferAmount() throws Exception {

		accountrepo.clearAccounts();
		Account account = new Account("BOND-421");
		account.setBalance(new BigDecimal(1000));
		this.accountsService.createAccount(account);

		account = new Account("BOND-420");
		account.setBalance(new BigDecimal(1000));
		this.accountsService.createAccount(account);

		AmountTransferRequest request = new AmountTransferRequest("BOND-421", "BOND-420", new BigDecimal(1000));

		Boolean response = transferService.transferAmount(request);

		System.out.println(accountsService.getAccount("BOND-421"));
		assertThat(response);
		assertThat(accountsService.getAccount("BOND-421").getBalance().equals(new BigDecimal(0)));
		assertThat(accountsService.getAccount("BOND-420").getBalance().equals(new BigDecimal(2000)));

	}

}
