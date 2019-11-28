package com.cg.ibs.rm.dao;

import java.math.BigInteger;
import java.util.Set;

import com.cg.ibs.rm.exception.IBSExceptions;
import com.cg.ibs.rm.model.CreditCard;

public interface CreditCardDAO {
	public Set<CreditCard> getDetails(BigInteger uci) throws IBSExceptions;

	public boolean copyDetails(BigInteger uci, CreditCard card) throws IBSExceptions;

	public boolean deleteDetails( BigInteger cardNumber) throws IBSExceptions;
}
