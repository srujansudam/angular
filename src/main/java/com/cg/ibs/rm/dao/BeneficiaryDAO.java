package com.cg.ibs.rm.dao;

import java.math.BigInteger;
import java.util.Set;

import com.cg.ibs.rm.exception.IBSExceptions;
import com.cg.ibs.rm.model.Beneficiary;

public interface BeneficiaryDAO {

	public Set<Beneficiary> getDetails(BigInteger uci) throws IBSExceptions ;

	public boolean copyDetails(BigInteger uci,Beneficiary beneficiary)throws IBSExceptions;

	public boolean updateDetails(Beneficiary beneficiary) throws IBSExceptions;
	
	public boolean deleteDetails(BigInteger accountNumber) throws IBSExceptions;
	
	public Beneficiary getBeneficiary(BigInteger accountNumber) throws IBSExceptions ;

}
