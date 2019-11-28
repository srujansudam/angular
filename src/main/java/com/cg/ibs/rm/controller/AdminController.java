package com.cg.ibs.rm.controller;

import java.math.BigInteger;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;

import com.cg.ibs.rm.exception.IBSExceptions;
import com.cg.ibs.rm.model.Banker;
import com.cg.ibs.rm.model.Beneficiary;
import com.cg.ibs.rm.model.CreditCard;
import com.cg.ibs.rm.service.Bank_AdminService;

@RestController
@Scope("session")
public class AdminController {

	@Autowired
	private Bank_AdminService service;
	Integer bankerId;
	
	
	@GetMapping("/bankerLogin/{userId}")
	public ResponseEntity<String> bankerLogin(@PathVariable("userId") String userId) {
		ResponseEntity<String> result;
		try {
			 Banker banker = service.getBankerDetails(userId);
			 bankerId = banker.getBankerId();
			 result  = new ResponseEntity<String>("logged in succesfully", HttpStatus.OK);
		} catch (IBSExceptions e) {
			result  = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return result;
	}
	
//	@GetMapping("/cardRequests")
//	public ResponseEntity<Set<CreditCard>> showUnapprovedCardRequests() {
//		Set<CreditCard> cardList = service.showUnapprovedCreditCards(bankerId);
//		ResponseEntity<Set<CreditCard>> result;
//		if (cardList.isEmpty())
//			result = new ResponseEntity<Set<CreditCard>>(cardList, HttpStatus.NO_CONTENT);
//		else
//			result = new ResponseEntity<Set<CreditCard>>(cardList, HttpStatus.OK);
//		return result;
//	}
//
//	@GetMapping("/beneficiaryRequests")
//	public ResponseEntity<Set<Beneficiary>> showUnapprovedBenRequests() {
//		Set<Beneficiary> benList = service.showUnapprovedBeneficiaries(bankerId);
//		ResponseEntity<Set<Beneficiary>> result;
//		if (benList.isEmpty())
//			result = new ResponseEntity<Set<Beneficiary>>(benList, HttpStatus.NO_CONTENT);
//		else
//			result = new ResponseEntity<Set<Beneficiary>>(benList, HttpStatus.OK);
//		return result;
//	}

	@PutMapping("/acceptCards/{cardNumber}/{decision}")
	public ResponseEntity<String> acceptCards(@PathVariable("cardNumber") BigInteger cardNumber,
			@PathVariable("decision") String decision) {
		ResponseEntity<String> result;
		if (cardNumber == null)
			result = new ResponseEntity<String>("No details entered", HttpStatus.NO_CONTENT);
		if (decision.equalsIgnoreCase("approve")) {
			try {
				service.saveCreditCardDetails(cardNumber);
				result = new ResponseEntity<String>("Approved", HttpStatus.OK);

			} catch (IBSExceptions e) {
				result = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		} else if (decision.equalsIgnoreCase("disapprove")) {
			try {
				service.disapproveCreditCard(cardNumber);
				result = new ResponseEntity<String>("Disapproved", HttpStatus.OK);
			} catch (IBSExceptions e) {
				result = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		} else {
			result = new ResponseEntity<String>("inappropriate decision", HttpStatus.UNAUTHORIZED);
		}

		return result;

	}

	@PutMapping("/acceptBeneficiaries/{accountNumber}/{decision}")
	public ResponseEntity<String> acceptBeneficiaries(@PathVariable("accountNumber") BigInteger accountNumber,
			@PathVariable("decision") String decision) {
		ResponseEntity<String> result;
		if (accountNumber == null)
			result = new ResponseEntity<String>("No details entered", HttpStatus.NO_CONTENT);
		if (decision.equalsIgnoreCase("approve")) {
			try {
				service.saveBeneficiaryDetails(accountNumber);
				result = new ResponseEntity<String>("Approved", HttpStatus.OK);

			} catch (IBSExceptions e) {
				result = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		} else if (decision.equalsIgnoreCase("disapprove")) {
			try {
				service.disapproveBenficiary(accountNumber);
				result = new ResponseEntity<String>("Disapproved", HttpStatus.OK);
			} catch (IBSExceptions e) {
				result = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		} else {
			result = new ResponseEntity<String>("inappropriate decision", HttpStatus.UNAUTHORIZED);
		}

		return result;
	}
}