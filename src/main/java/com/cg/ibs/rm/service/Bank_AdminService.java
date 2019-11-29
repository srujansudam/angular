package com.cg.ibs.rm.service;

import java.math.BigInteger;
import java.util.Set;

import com.cg.ibs.rm.exception.IBSExceptions;
import com.cg.ibs.rm.model.Banker;
import com.cg.ibs.rm.model.BankerHistory;
import com.cg.ibs.rm.model.Beneficiary;
import com.cg.ibs.rm.model.CreditCard;

public interface Bank_AdminService {
	public Set<BigInteger> showRequestsForMe(Integer bankerId);

	public boolean saveCreditCardDetails(BigInteger cardNumber) throws IBSExceptions;

	public boolean saveBeneficiaryDetails(BigInteger accountNumber) throws IBSExceptions;

	public boolean disapproveBenficiary(BigInteger accountNumber) throws IBSExceptions;

	public boolean disapproveCreditCard(BigInteger cardNumber) throws IBSExceptions;

	public Banker getBankerDetails(String userId) throws IBSExceptions;

	public Set<Beneficiary> showAllUnapprovedBeneficiaries();

	public Set<Beneficiary> showUnapprovedBeneficiariesForMe(Integer bankerId);

	public Set<CreditCard> showAllUnapprovedCreditCards();

	public Set<CreditCard> showUnapprovedCreditCardsForMe(Integer bankerId);

	public Set<BankerHistory> getBenHistory(Integer bankerId);

	public Set<BankerHistory> getCreditHistory(Integer bankerId);
}
