package com.cg.ibs.rm.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;

import com.cg.ibs.rm.exception.IBSExceptions;
import com.cg.ibs.rm.model.Account;
import com.cg.ibs.rm.model.AutoPayment;
import com.cg.ibs.rm.model.AutopaymentTransaction;
import com.cg.ibs.rm.model.ServiceProvider;
import com.cg.ibs.rm.model.ServiceProviderId;

public interface AutoPaymentDAO {
	public Set<ServiceProvider> showServiceProviderList();

	public Set<AutoPayment> getAutopaymentDetails(BigInteger uci) throws IBSExceptions;

	public boolean copyDetails(BigInteger uci, AutoPayment autoPayment) throws IBSExceptions;

	public boolean deleteDetails(BigInteger uci, BigInteger serviceProviderId) throws IBSExceptions;


	public boolean setCurrentBalance(BigDecimal currentBalnce, BigInteger accountNumber);
	
	public Account getAccount(BigInteger accountNumber) throws IBSExceptions;

	public boolean setTransaction(BigInteger uci, BigInteger accountNumber, AutoPayment autoPayment);
	
	public boolean updateDetails(AutoPayment autoPayment) throws IBSExceptions;
	
	public AutoPayment getAutopayment(ServiceProviderId providerId) throws IBSExceptions;

	
	public Set<AutopaymentTransaction> getTransaction(BigInteger uci) throws IBSExceptions;

}