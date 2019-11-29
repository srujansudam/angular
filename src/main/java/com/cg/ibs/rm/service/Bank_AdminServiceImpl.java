package com.cg.ibs.rm.service;

import java.math.BigInteger;
import java.util.Set;

//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.ibs.rm.dao.BankAdminDAO;
import com.cg.ibs.rm.exception.IBSExceptions;
import com.cg.ibs.rm.model.Banker;
import com.cg.ibs.rm.model.BankerHistory;
import com.cg.ibs.rm.model.Beneficiary;
import com.cg.ibs.rm.model.CreditCard;

@Service("bankAdminService")
public class Bank_AdminServiceImpl implements Bank_AdminService {
	@Autowired
	private BankAdminDAO bankRepresentativeDAO;
	// private static Logger logger = Logger.getLogger(Bank_AdminServiceImpl.class);

	@Override
	public Set<BigInteger> showRequestsForMe(Integer bankerId) {
		// logger.info("entering into showRequests method of Bank_AdminServiceImpl
		// class");
		return bankRepresentativeDAO.getRequestsForMe(bankerId);

	}

	@Override
	public Set<CreditCard> showUnapprovedCreditCardsForMe(Integer bankerId) {
		// logger.info("entering into showUnapprovedCreditCards method of
		// BankRepresentativeServiceImpl class");
		return bankRepresentativeDAO.getCreditCardDetailsForMe(bankerId);

	}

	@Override
	public Set<CreditCard> showAllUnapprovedCreditCards() {
		// logger.info("entering into showUnapprovedCreditCards method of
		// BankRepresentativeServiceImpl class");
		return bankRepresentativeDAO.getAllCreditCardDetails();

	}

	@Override
	public Set<Beneficiary> showUnapprovedBeneficiariesForMe(Integer bankerId) {
		// logger.info("entering into showUnapprovedBeneficiaries method of
		// BankRepresentativeServiceImpl class");
		return bankRepresentativeDAO.getBeneficiaryDetailsForMe(bankerId);
	}

	@Override
	public Set<Beneficiary> showAllUnapprovedBeneficiaries() {
		// logger.info("entering into showUnapprovedBeneficiaries method of
		// BankRepresentativeServiceImpl class");
		return bankRepresentativeDAO.getAllBeneficiaryDetails();
	}

	@Override
	@Transactional
	public boolean saveCreditCardDetails(BigInteger cardNumber) throws IBSExceptions {
		// logger.info("Entering into checkedCreditCardDetails method of Bank Admin in
		// case of approved cards");
		boolean result = false;
		if (bankRepresentativeDAO.checkedCreditCardDetails(cardNumber)) {
			result = true;
		}
		return result;
	}

	@Override
	@Transactional
	public boolean saveBeneficiaryDetails(BigInteger accountNumber) throws IBSExceptions {
		// logger.info("Entering into checkedBeneficiaryDetails method of Bank Admin in
		// case of approved beneficiaries");
		boolean result = false;
		if (bankRepresentativeDAO.checkedBeneficiaryDetails(accountNumber)) {
			result = true;
		}
		return result;
	}

	@Override
	@Transactional
	public boolean disapproveCreditCard(BigInteger cardNumber) throws IBSExceptions {
		// logger.info("Entering into checkedCreditCardDetails method of Bank Admin in
		// case of disapproved cards");
		boolean result = false;
		if (bankRepresentativeDAO.decliningCreditCardDetails(cardNumber)) {
			result = true;
		}
		return result;
	}

	@Override
	@Transactional
	public boolean disapproveBenficiary(BigInteger accountNumber) throws IBSExceptions {
		// logger.info(
		// "Entering into checkedBeneficiaryDetails method of Bank Admin in case of
		// disapproved beneficiaries");
		boolean result = false;
		if (bankRepresentativeDAO.decliningBeneficiaryDetails(accountNumber)) {
			result = true;
		}
		return result;
	}

	@Override
	public Banker getBankerDetails(String userId) throws IBSExceptions {
		return bankRepresentativeDAO.getAdminDetails(userId);
	}

	@Override
	public Set<BankerHistory> getBenHistory(Integer bankerId) {
		return bankRepresentativeDAO.getBenHistory(bankerId);
	}

	@Override
	public Set<BankerHistory> getCreditHistory(Integer bankerId) {
		return bankRepresentativeDAO.getCreditHistory(bankerId);
	}
}
