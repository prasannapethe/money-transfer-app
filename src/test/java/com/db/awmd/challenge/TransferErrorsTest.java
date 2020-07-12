package com.db.awmd.challenge;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.service.AccountsService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TransferErrorsTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountsService accountsService;

	@Test
	public void UnvalidAmountTransfer() throws Exception {
		this.mockMvc
				.perform(post("/v1/transfer").contentType(MediaType.APPLICATION_JSON)
						.content("{\"fromAccountId\":\"BOND-421\",\"toAccountId\":\"BOND-420\",\"amount\":-1000,}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void InvalidFromAccountId() throws Exception {
		this.mockMvc
				.perform(post("/v1/transfer").contentType(MediaType.APPLICATION_JSON)
						.content("{\"fromAccountId\":null,\"toAccountId\":\"BOND-420\",\"amount\":1000,}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void AccoundNotExist() throws Exception {
		this.mockMvc
				.perform(post("/v1/transfer").contentType(MediaType.APPLICATION_JSON)
						.content("{\"fromAccountId\":\"BOND-421\",\"toAccountId\":null,\"amount\":1000,}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void InvalidToAccountId() throws Exception {
		this.mockMvc
				.perform(post("/v1/transfer").contentType(MediaType.APPLICATION_JSON)
						.content("{\"fromAccountId\":\"BOND-421\",\"toAccountId\":BOND-420,\"amount\":1000,}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void InsufficiantBalanceInAccount() throws Exception {

		Account account = new Account("BOND-421");
		account.setBalance(new BigDecimal(1000));
		this.accountsService.createAccount(account);

		account = new Account("BOND-420");
		account.setBalance(new BigDecimal(1000));
		this.accountsService.createAccount(account);

		this.mockMvc
				.perform(post("/v1/transfer").contentType(MediaType.APPLICATION_JSON)
						.content("{\"fromAccountId\":\"BOND-421\",\"toAccountId\":BOND-420,\"amount\":5000,}"))
				.andExpect(status().isBadRequest());
	}

}
