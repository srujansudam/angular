package com.cg.ibs.rm.service;

import java.math.BigInteger;
import java.util.Set;

import com.cg.ibs.rm.exception.IBSExceptions;
import com.cg.ibs.rm.model.AutoPayment;
import com.cg.ibs.rm.model.AutopaymentTransaction;
import com.cg.ibs.rm.model.ServiceProvider;
import com.cg.ibs.rm.model.ServiceProviderId;

public interface AutoPaymentService {
	public Set<ServiceProvider> showIBSServiceProviders();

	public Set<AutoPayment> showAutopaymentDetails(BigInteger uci) throws IBSExceptions;

	public boolean autoDeduction(BigInteger uci, BigInteger accountNumber, AutoPayment autoPayment) throws IBSExceptions;

	public boolean deleteAutopayment(BigInteger uci, BigInteger spi) throws IBSExceptions;
	
	public boolean validEndDate(String endDate, String startDate);
	
	public boolean updateDetails(ServiceProviderId providerId, AutoPayment autoPayment) throws IBSExceptions;
	
	public AutoPayment getAutopayment(ServiceProviderId providerId) throws IBSExceptions;

	Set<AutopaymentTransaction> getTransactions(BigInteger uci) throws IBSExceptions;
}
