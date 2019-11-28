package com.cg.ibs.rm.dao;

import java.math.BigInteger;
import java.util.Set;

import com.cg.ibs.rm.exception.IBSExceptions;
import com.cg.ibs.rm.model.Banker;
import com.cg.ibs.rm.model.Beneficiary;
import com.cg.ibs.rm.model.CreditCard;

public interface BankAdminDAO {
	public Set<BigInteger> getRequestsForMe(Integer bankerId);

	public Set<BigInteger> getAllRequests();

	public Set<CreditCard> getCreditCardDetailsForMe(Integer bankerId);

	public Set<CreditCard> getAllCreditCardDetails();

	public Set<Beneficiary> getBeneficiaryDetailsForMe(Integer bankerId);

	public Set<Beneficiary> getAllBeneficiaryDetails();

	public boolean checkedCreditCardDetails(BigInteger cardNumber) throws IBSExceptions;

	public boolean checkedBeneficiaryDetails(BigInteger accountNumber) throws IBSExceptions;

	public boolean decliningCreditCardDetails(BigInteger cardNumber) throws IBSExceptions;

	public boolean decliningBeneficiaryDetails(BigInteger accountNumber) throws IBSExceptions;

	public Banker getAdminDetails(String userId) throws IBSExceptions;
}
