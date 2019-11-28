package com.cg.ibs.rm.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

//import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cg.ibs.rm.exception.ExceptionMessages;
import com.cg.ibs.rm.exception.IBSExceptions;
import com.cg.ibs.rm.model.Account;
import com.cg.ibs.rm.model.AutoPayment;
import com.cg.ibs.rm.model.AutopaymentTransaction;
import com.cg.ibs.rm.model.Customer;
import com.cg.ibs.rm.model.ServiceProvider;
import com.cg.ibs.rm.model.ServiceProviderId;
import com.cg.ibs.rm.ui.TransactionMode;
import com.cg.ibs.rm.ui.TransactionType;

@Repository("AutoPaymentDao")
public class AutoPaymentDAOImpl implements AutoPaymentDAO {

	@PersistenceContext
	EntityManager manager;

	// private static final Logger LOGGER =
	// Logger.getLogger(AutoPaymentDAOImpl.class);

	public AutoPaymentDAOImpl() {
		super();
	}

	@Override
	public Set<AutoPayment> getAutopaymentDetails(BigInteger uci) throws IBSExceptions {
		// LOGGER.info("entered getAutopaymentDetails in AutoPaymentDAOImpl");
		TypedQuery<AutoPayment> query = manager
				.createQuery("SELECT a FROM AutoPayment a  WHERE a.serviceProviderId.uci = ?1", AutoPayment.class);
		query.setParameter(1, uci);
		List<AutoPayment> list = query.getResultList();
		if (!list.isEmpty()) {
			return new HashSet<>(list);
		} else {
			throw new IBSExceptions("No autopayments added.");
		}
	}

	@Override
	public boolean copyDetails(BigInteger uci, AutoPayment autoPayment) throws IBSExceptions {
		// LOGGER.info("entered copyDetails in AutoPaymentDAOImpl");
		boolean result = false;
		if (null == manager.find(AutoPayment.class, autoPayment.getServiceProviderId())) {
			Customer customerBean = manager.find(Customer.class, uci);
			customerBean.getAutoPayments().add(autoPayment);
			manager.merge(autoPayment);// why not customer bean
			result = true;
		} else {
			throw new IBSExceptions(ExceptionMessages.AUTOPAYMENT_ALREADY_ADDED);
		}
		return result;
	}

	@Override
	public boolean deleteDetails(BigInteger uci, BigInteger spi) throws IBSExceptions {
		boolean result = false;
		// LOGGER.info("entered deleteDetails in AutoPaymentDAOImpl");
		AutoPayment autoPayment = manager.find(AutoPayment.class, new ServiceProviderId(spi, uci));
		if (null != autoPayment) {
			manager.remove(autoPayment);
			result=true;
		} else {
			throw new IBSExceptions(ExceptionMessages.AUTOPAYMENT_DOESNT_EXIST);
		}
		return result;
	}

	@Override
	public Set<ServiceProvider> showServiceProviderList() {
		// LOGGER.info("entered showServiceProviderList in AutoPaymentDAOImpl");
		TypedQuery<ServiceProvider> query = manager.createQuery("SELECT a FROM ServiceProvider a",
				ServiceProvider.class);
		return new HashSet<>(query.getResultList());
	}

	@Override
	public boolean setCurrentBalance(BigDecimal currentBalance, BigInteger accountNumber) {
		// LOGGER.info("entered setCurrentBalance in AutoPaymentDAOImpl");
		boolean result = false;
		Account account = manager.find(Account.class, accountNumber);
		account.setBalance(currentBalance);
		if (manager.merge(account) != null) {
			result = true;
		}
		return result;
	}

	@Override
	public Account getAccount(BigInteger accountNumber) throws IBSExceptions {
		Account account = manager.find(Account.class, accountNumber);
		if (null == account) {
			throw new IBSExceptions("Account doesn't exist");
		} else {
			return account;
		}
	}

	@Override
	public boolean setTransaction(BigInteger uci, BigInteger accountNumber, AutoPayment autoPayment) {
		AutopaymentTransaction transactionBean = new AutopaymentTransaction();
		transactionBean.setTransactionDate(LocalDateTime.now());
		transactionBean.setTransactionAmount(autoPayment.getAmount());
		transactionBean.setTransactionDescription("Auto Payment");
		transactionBean.setTransactionMode(TransactionMode.ONLINE);
		transactionBean.setTransactionType(TransactionType.DEBIT);
		Customer customer = manager.find(Customer.class, uci);
		transactionBean.setAccount(manager.find(Account.class, accountNumber));
		autoPayment.getTransactions().add(transactionBean);
		customer.getAutoPayments().add(autoPayment);
		manager.merge(customer);
		manager.merge(transactionBean);
		manager.merge(autoPayment);
		return true;
	}
	
	@Override
	public Set<AutopaymentTransaction> getTransaction(BigInteger uci) throws IBSExceptions {
		
		TypedQuery<AutopaymentTransaction> query = manager
				.createQuery("SELECT a FROM AutopaymentTransaction a WHERE a.serviceProviderId.uci = ?1", AutopaymentTransaction.class);
		query.setParameter(1, uci);
		List<AutopaymentTransaction> list = query.getResultList();
		if (!list.isEmpty()) {
			return new HashSet<>(list);
		} else {
			throw new IBSExceptions("No transactions.");
		}
		
	}

	@Override
	public boolean updateDetails(AutoPayment autoPayment) throws IBSExceptions {
		boolean result = false;
		AutoPayment autoPayment2 = manager.find(AutoPayment.class, autoPayment.getServiceProviderId());
		if (null == autoPayment2) {
			throw new IBSExceptions(ExceptionMessages.AUTOPAYMENT_DOESNT_EXIST);
		} else {
			manager.merge(autoPayment);
			result = true;
		}
		return result;
	}

	@Override
	public AutoPayment getAutopayment(ServiceProviderId providerId) throws IBSExceptions {

		AutoPayment autoPayment = manager.find(AutoPayment.class, providerId);
		if (null == autoPayment) {
			throw new IBSExceptions(ExceptionMessages.AUTOPAYMENT_DOESNT_EXIST);
		} else {
			return autoPayment;
		}

	}
}
