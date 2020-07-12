package com.db.awmd.challenge.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.awmd.challenge.domain.transfer.AmountTransferRequest;
import com.db.awmd.challenge.service.TransferService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/v1")
public class TransferController {

	@Autowired
	private TransferService transferService;

	@PostMapping(path = "transfer", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> transferAmmount(@RequestBody @Valid AmountTransferRequest request) {
		log.info("Transfer call");

		transferService.transferAmount(request);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
