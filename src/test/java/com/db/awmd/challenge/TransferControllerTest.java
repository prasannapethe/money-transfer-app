package com.db.awmd.challenge;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.db.awmd.challenge.service.AccountsService;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class TransferControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Mock
	private AccountsService accountsService;
	
	@Test
	public void UnvalidAmountTransfer() throws Exception {
		this.mockMvc.perform(
				post("/v1/transfer").contentType(MediaType.APPLICATION_JSON).content("{\"fromAccountId\":\"101\",\"toAccountId\":\"101\",\"amount\":-1000,}"))
				.andExpect(status().isBadRequest());
	}
	
	
	
		
	
}
