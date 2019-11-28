package com.cg.ibs.rm.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cg.ibs.rm.exception.IBSExceptions;
import com.cg.ibs.rm.model.Account;
import com.cg.ibs.rm.model.AutoPayment;
import com.cg.ibs.rm.model.Beneficiary;
import com.cg.ibs.rm.model.CreditCard;
import com.cg.ibs.rm.model.ServiceProvider;
import com.cg.ibs.rm.model.ServiceProviderId;
import com.cg.ibs.rm.service.AccountService;
import com.cg.ibs.rm.service.AutoPaymentService;
import com.cg.ibs.rm.service.BeneficiaryAccountService;
import com.cg.ibs.rm.service.CreditCardService;
import com.cg.ibs.rm.service.CustomerService;

@RestController
@Scope("session")
public class UserController {
	@Autowired
	private AutoPaymentService autoPaymentService;

	BigInteger uci;
	@Autowired
	CustomerService customerService;
	@Autowired
	private CreditCardService creditCard;
	@Autowired
	private AccountService accountService;
	@Autowired
	private BeneficiaryAccountService beneficiaryservice;

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

	@RequestMapping(method = RequestMethod.GET, value = "/addcard")
	public String addCardGo() {
		return "addcard";
	}

	@PostMapping("/addcard")
	public ResponseEntity<String> addCard(@RequestBody CreditCard card) {
		ResponseEntity<String> result = null;
		LocalDate date = LocalDate.of(card.getYear(), card.getMonth(), 27);
		
		if (creditCard.validateCardNumber(card.getCardNumber().toString())
				&& creditCard.validateDateOfExpiry(date.toString())
				&& creditCard.validateNameOnCard(card.getNameOnCard())) {

			card.setDateOfExpiry(date);
			card.setTimestamp(LocalDateTime.now());
			try {
				creditCard.saveCardDetails(uci, card);
				result = new ResponseEntity<String>("Card gone for approval", HttpStatus.OK);

			} catch (IBSExceptions e) {
				result = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		} else {
			result = new ResponseEntity<String>("format incorrect", HttpStatus.NOT_ACCEPTABLE);
		}
		return result;
	}

	@GetMapping("/viewcard")
	public ResponseEntity<Set<CreditCard>> viewCardDetails() {
		ResponseEntity<Set<CreditCard>> result = null;

		try {
			Set<CreditCard> cards = creditCard.showCardDetails(uci);
			result = new ResponseEntity<Set<CreditCard>>(cards, HttpStatus.OK);
		} catch (IBSExceptions e) {
			result = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return result;
	}

	@DeleteMapping("/deletecard/{cardNumber}")
	public ResponseEntity<String> deleteCreditCard(@PathVariable("cardNumber") BigInteger cardNumber) {
		ResponseEntity<String> result = null;
		try {
			creditCard.deleteCardDetails(cardNumber);
			result = new ResponseEntity<String>("Card deleted successfully", HttpStatus.OK);

		} catch (IBSExceptions e) {
			result = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return result;

	}

	@PostMapping("/samebank")
	public ResponseEntity<String> addSameBeneficiary(@RequestBody Beneficiary beneficiary) {
		ResponseEntity<String> result = null;
		if (beneficiaryservice.validateBeneficiaryAccountNameOrBankName(beneficiary.getAccountName())
				&& beneficiaryservice.validateBeneficiaryAccountNumber(beneficiary.getAccountNumber().toString())) {
			beneficiary.setBankName("IBS");
			beneficiary.setIfscCode("IBS45623778");
			beneficiary.setTimestamp(LocalDateTime.now());
			try {
				beneficiaryservice.saveBeneficiaryAccountDetails(uci, beneficiary);
				result = new ResponseEntity<String>("Beneficiary gone for approval", HttpStatus.OK);

			} catch (IBSExceptions e) {
				result = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		} else {
			result = new ResponseEntity<String>("format incorrect", HttpStatus.NOT_ACCEPTABLE);
		}
		return result;
	}

	@PostMapping("/otherbank")
	public ResponseEntity<String> addOtherBeneficiary(@RequestBody Beneficiary beneficiary) {
		ResponseEntity<String> result = null;
		if (beneficiaryservice.validateBeneficiaryAccountNameOrBankName(beneficiary.getAccountName())
				&& beneficiaryservice.validateBeneficiaryAccountNumber(beneficiary.getAccountNumber().toString())
				&& beneficiaryservice.validateBeneficiaryIfscCode(beneficiary.getIfscCode())
				&& beneficiaryservice.validateBeneficiaryAccountNameOrBankName(beneficiary.getBankName())) {
			beneficiary.setTimestamp(LocalDateTime.now());
			try {
				beneficiaryservice.saveBeneficiaryAccountDetails(uci, beneficiary);
				result = new ResponseEntity<String>("Beneficiary gone for approval", HttpStatus.OK);
			} catch (IBSExceptions e) {
				result = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		} else {
			result = new ResponseEntity<String>("format incorrect", HttpStatus.NOT_ACCEPTABLE);
		}
		return result;
	}

	@GetMapping("/viewben")
	public ResponseEntity<Set<Beneficiary>> viewBeneficiaries() {
		ResponseEntity<Set<Beneficiary>> result = null;
		Set<Beneficiary> beneficiaries;
		try {
			beneficiaries = beneficiaryservice.showBeneficiaryAccount(uci);
			result = new ResponseEntity<Set<Beneficiary>>(beneficiaries, HttpStatus.OK);
		} catch (IBSExceptions e) {
			result = new ResponseEntity<Set<Beneficiary>>(HttpStatus.BAD_REQUEST);
		}
		return result;
	}

	@DeleteMapping("/deleteben/{accountNumber}")
	public ResponseEntity<String> deletebeneficiary(@PathVariable("accountNumber") BigInteger accountNumber) {
		ResponseEntity<String> result = null;
		try {
			beneficiaryservice.deleteBeneficiaryAccountDetails(accountNumber);
			result = new ResponseEntity<String>("Account deleted successfully", HttpStatus.OK);

		} catch (IBSExceptions e) {
			result = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return result;
	}

	@PutMapping("/modifyinother/{accountNumber}")
	public ResponseEntity<String> modifybeneficiary(@PathVariable("accountNumber") BigInteger accountNumber,
			@RequestBody Beneficiary beneficiary) {
		ResponseEntity<String> result = null;
		if (beneficiaryservice.validateBeneficiaryAccountNameOrBankName(beneficiary.getAccountName())
				&& beneficiaryservice.validateBeneficiaryAccountNumber(beneficiary.getAccountNumber().toString())
				&& beneficiaryservice.validateBeneficiaryIfscCode(beneficiary.getIfscCode())
				&& beneficiaryservice.validateBeneficiaryAccountNameOrBankName(beneficiary.getBankName())) {
			beneficiary.setTimestamp(LocalDateTime.now());
			try {
				beneficiaryservice.modifyBeneficiaryAccountDetails(accountNumber, beneficiary);
				result = new ResponseEntity<String>("Account gone for modification", HttpStatus.OK);

			} catch (IBSExceptions | IOException e) {
				result = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		} else {
			result = new ResponseEntity<String>("format incorrect", HttpStatus.NOT_ACCEPTABLE);
		}
		return result;

	}

	@PutMapping("/modifyinibs/{accountNumber}")
	public ResponseEntity<String> modifyIbsBeneficiary(@PathVariable("accountNumber") BigInteger accountNumber,
			@RequestBody Beneficiary beneficiary) {
		ResponseEntity<String> result = null;
		if (beneficiaryservice.validateBeneficiaryAccountNameOrBankName(beneficiary.getAccountName())
				&& beneficiaryservice.validateBeneficiaryAccountNumber(beneficiary.getAccountNumber().toString())) {
			beneficiary.setTimestamp(LocalDateTime.now());
			try {
				beneficiary.setBankName("IBS");
				beneficiary.setIfscCode("IBS45623778");
				beneficiaryservice.modifyBeneficiaryAccountDetails(accountNumber, beneficiary);
				result = new ResponseEntity<String>("Account gone for modification", HttpStatus.OK);

			} catch (IBSExceptions | IOException e) {
				result = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		} else {
			result = new ResponseEntity<String>("format incorrect", HttpStatus.NOT_ACCEPTABLE);
		}
		return result;
	}

	@GetMapping("/addautopaymentaccounts")
	public ResponseEntity<Set<Account>> showAccounts() {
		Set<Account> accounts = null;
		ResponseEntity<Set<Account>> result;
		try {
			accounts = accountService.getAccountsOfUci(uci);
			result = new ResponseEntity<Set<Account>>(accounts, HttpStatus.OK);
		} catch (IBSExceptions e) {
			result = new ResponseEntity<Set<Account>>(accounts, HttpStatus.NO_CONTENT);
		}
		return result;
	}

	@GetMapping("/addautopaymentproviders")
	public ResponseEntity<Set<ServiceProvider>> showServiceProviders() {
		Set<ServiceProvider> providers = null;
		ResponseEntity<Set<ServiceProvider>> result;

		providers = autoPaymentService.showIBSServiceProviders();
		if (providers.isEmpty()) {
			result = new ResponseEntity<Set<ServiceProvider>>(providers, HttpStatus.NO_CONTENT);
		} else {
			result = new ResponseEntity<Set<ServiceProvider>>(providers, HttpStatus.OK);
		}

		return result;
	}

	@PostMapping("/addautopayment/{accountNumber}")
	public ResponseEntity<String> addAutopayment(@PathVariable("accountNumber") BigInteger accountNumber,
			@RequestBody AutoPayment autoPayment) {
		ResponseEntity<String> result = null;
		BigInteger spId = null;
		int count = 1;
		Set<ServiceProvider> serviceProviders = autoPaymentService.showIBSServiceProviders();
		for (ServiceProvider serviceProvider : serviceProviders) {

			if (serviceProvider.getNameOfCompany().equalsIgnoreCase(autoPayment.getServiceName())) {
				count++;
				spId = serviceProvider.getSpi();
			}
		}
		if (autoPaymentService.validEndDate(autoPayment.getDateOfEnd(), autoPayment.getDateOfStart()) && count != 1) {

			autoPayment.setServiceProviderId(new ServiceProviderId(spId, uci));

			try {
				autoPaymentService.autoDeduction(uci, accountNumber, autoPayment);
				result = new ResponseEntity<String>("Autopayment added successfully", HttpStatus.OK);
			} catch (IBSExceptions e) {
				result = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		} else {
			result = new ResponseEntity<String>("format incorrect", HttpStatus.NOT_ACCEPTABLE);
		}
		return result;

	}

	@GetMapping("/viewautopayment")
	public ResponseEntity<Set<AutoPayment>> viewAutoPayments() {

		Set<AutoPayment> autoPayments = null;
		ResponseEntity<Set<AutoPayment>> result;
		try {
			autoPayments = autoPaymentService.showAutopaymentDetails(uci);
			result = new ResponseEntity<Set<AutoPayment>>(autoPayments, HttpStatus.OK);

		} catch (IBSExceptions e) {
			result = new ResponseEntity<Set<AutoPayment>>(autoPayments, HttpStatus.NO_CONTENT);
		}
		return result;
	}

	@PutMapping(value = "/modifyautopayment/{spi}")
	public ResponseEntity<String> modifyautopaymentDetails(@RequestBody AutoPayment autoPayment,
			@PathVariable("spi") BigInteger spi) {
		ResponseEntity<String> result;

		if (autoPaymentService.validEndDate(autoPayment.getDateOfEnd(), autoPayment.getDateOfStart())) {
			try {
				if ((autoPayment == null) || (spi == null))
					result = new ResponseEntity<String>("Values not entered", HttpStatus.NO_CONTENT);
				if (autoPaymentService.updateDetails(new ServiceProviderId(spi, uci), autoPayment))
					result = new ResponseEntity<String>("Modified autopayment", HttpStatus.OK);
				else
					result = new ResponseEntity<String>("Not Modified", HttpStatus.BAD_REQUEST);
			} catch (IBSExceptions e) {
				result = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		} else {
			result = new ResponseEntity<String>("format incorrect", HttpStatus.NOT_ACCEPTABLE);
		}
		return result;
	}

	@DeleteMapping("/deleteautopayment/{spi}")
	public ResponseEntity<String> deleteAutoPayment(@PathVariable("spi") BigInteger spId1) {
		ResponseEntity<String> result;
		try {
			if (spId1 == null)
				result = new ResponseEntity<String>("No details entered", HttpStatus.NO_CONTENT);
			if (autoPaymentService.deleteAutopayment(uci, spId1))
				result = new ResponseEntity<String>("Deleted", HttpStatus.OK);
			else
				result = new ResponseEntity<String>("Not Deleted", HttpStatus.BAD_REQUEST);
		} catch (IBSExceptions e) {
			result = new ResponseEntity<String>(e.getMessage(), HttpStatus.NO_CONTENT);
		}

		return result;

	}

	@RequestMapping("/exceptionuser")
	public ModelAndView exception() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", "Under Maintenance");
		mv.setViewName("exceptionpage");
		return mv;
	}

}
