package com.cg.ibs.rm.controller;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cg.ibs.rm.exception.IBSExceptions;
import com.cg.ibs.rm.service.CustomerService;

@RestController
@Scope("session")
public class UserController {

	private BigInteger uci;
	@Autowired
	CustomerService customerService;

	public BigInteger getUci() {
		return uci;
	}

	public void setUci(BigInteger uci) {
		this.uci = uci;
	}

	@GetMapping("/userinput/{userId}")
	public ResponseEntity<String> getName(@PathVariable("userId") String userId) {
		ResponseEntity<String> result;
		try {
			if (userId == null) {
				result = new ResponseEntity<>("No User Details Received", HttpStatus.BAD_REQUEST);
			} else {
				uci = customerService.returnUCI(userId);
				result = new ResponseEntity<>(
						"welcome " + customerService.returnName(customerService.returnUCI(userId)), HttpStatus.OK);
			}
		} catch (IBSExceptions e) {
			result = new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
			System.out.println(e.getMessage());
		}
		return result;
	}

}