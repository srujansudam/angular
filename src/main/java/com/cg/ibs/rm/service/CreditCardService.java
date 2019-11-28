package com.cg.ibs.rm.service;

import java.math.BigInteger;
import java.util.Set;

import com.cg.ibs.rm.exception.IBSExceptions;
import com.cg.ibs.rm.model.CreditCard;

public interface CreditCardService {
	public Set<CreditCard> showCardDetails(BigInteger uci) throws IBSExceptions;

	public boolean validateCardNumber(String creditCardNumber);

	public boolean validateDateOfExpiry(String creditDateOfExpiry) ;

	public boolean validateNameOnCard(String nameOnCreditCard);

	public boolean deleteCardDetails(BigInteger creditCardNumber) throws IBSExceptions;

	public boolean saveCardDetails(BigInteger uci, CreditCard card) throws IBSExceptions;
}
