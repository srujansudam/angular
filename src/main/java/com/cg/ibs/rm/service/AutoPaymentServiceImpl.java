package com.cg.ibs.rm.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.regex.Pattern;

import javax.persistence.EntityTransaction;

//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.ibs.rm.dao.AutoPaymentDAO;
import com.cg.ibs.rm.exception.IBSExceptions;
import com.cg.ibs.rm.model.AutoPayment;
import com.cg.ibs.rm.model.AutopaymentTransaction;
import com.cg.ibs.rm.model.ServiceProvider;
import com.cg.ibs.rm.model.ServiceProviderId;

@Service("autoPaymentService")
public class AutoPaymentServiceImpl implements AutoPaymentService {
	EntityTransaction transaction;
	@Autowired
	private AutoPaymentDAO autoPaymentDao;
	// private static final Logger LOGGER =
	// Logger.getLogger(AutoPaymentServiceImpl.class.getName());

	@Override
	public Set<AutoPayment> showAutopaymentDetails(BigInteger uci) throws IBSExceptions {
		// LOGGER.info("entered showAutopaymentDetails in autopaymentservimpl");
		return autoPaymentDao.getAutopaymentDetails(uci);

	}

	@Override
	public Set<ServiceProvider> showIBSServiceProviders() {
		// LOGGER.info("entered showIBSServiceProviders in autopaymentservimpl");
		return autoPaymentDao.showServiceProviderList();

	}

	@Override
	@Transactional
	public boolean autoDeduction(BigInteger uci, BigInteger accountNumber, AutoPayment autoPayment)
			throws IBSExceptions {
		// LOGGER.info("entered autodeduction in autopaymentservimpl");
		LocalDate today = LocalDate.now();
		boolean validAutoDeduct = false;
		// LOGGER.debug(autoPaymentDao.getAccount(accountNumber).getBalance());		
		DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startOfAutoPayment = LocalDate.parse(autoPayment.getDateOfStart(), dtFormatter);
		if (!(startOfAutoPayment.isBefore(today))) {
			autoPaymentDao.copyDetails(uci, autoPayment);
			if (today.equals(startOfAutoPayment) && (0 <= autoPaymentDao.getAccount(accountNumber).getBalance()
					.compareTo(autoPayment.getAmount()))) {
				BigDecimal balance = autoPaymentDao.getAccount(accountNumber).getBalance()
						.subtract(autoPayment.getAmount());
				autoPaymentDao.setCurrentBalance(balance, accountNumber);
				autoPaymentDao.setTransaction(uci, accountNumber, autoPayment);
				startOfAutoPayment.plusMonths(1);
				// LOGGER.debug(autoPaymentDao.getAccount(accountNumber).getBalance());

			}
			validAutoDeduct = true;
		}

		return validAutoDeduct;
	}
	
	@Override
	@Transactional
	public Set<AutopaymentTransaction> getTransactions(BigInteger uci) throws IBSExceptions
	{
		return autoPaymentDao.getTransaction(uci);
	}

	@Override
	@Transactional
	public boolean deleteAutopayment(BigInteger uci, BigInteger spi) throws IBSExceptions {
		boolean result = false;
		// LOGGER.info("entered updateRequirements in autopaymentservimpl");
		result = autoPaymentDao.deleteDetails(uci, spi);
		return result;
	}

	@Override
	public boolean validEndDate(String endDate, String startDate) {
		boolean validDate = false;
		if (Pattern.matches("^((?:[0-9]{2})?[0-9]{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01]))$", endDate)) {
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate endDate1 = LocalDate.parse(endDate, formatter);
			LocalDate startDate1 = LocalDate.parse(startDate, formatter);
			if (!endDate1.isBefore(startDate1)) {
				validDate = true;
			} else {
				validDate = false;
			}
		}
		return validDate;
	}

	@Override
	@Transactional
	public boolean updateDetails(ServiceProviderId providerId, AutoPayment autoPayment) throws IBSExceptions {
		boolean validModify = false;
		AutoPayment autoPayment2 = autoPaymentDao.getAutopayment(providerId);
		if (autoPayment.getAmount() != null) {
			autoPayment2.setAmount(autoPayment.getAmount());
		}
		if (autoPayment.getDateOfEnd() != null) {
			autoPayment2.setDateOfEnd(autoPayment.getDateOfEnd());
		}
		if (autoPayment.getDateOfStart() != null) {
			autoPayment2.setDateOfStart(autoPayment.getDateOfStart());
		}
		if (autoPaymentDao.updateDetails(autoPayment2)) {
			validModify = true;
		}
		return validModify;
	}

	@Override
	public AutoPayment getAutopayment(ServiceProviderId providerId) throws IBSExceptions {
		return autoPaymentDao.getAutopayment(providerId);
	}

}