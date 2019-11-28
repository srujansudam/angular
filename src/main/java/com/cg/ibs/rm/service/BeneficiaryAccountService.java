package com.cg.ibs.rm.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Set;

import com.cg.ibs.rm.exception.IBSExceptions;
import com.cg.ibs.rm.model.Beneficiary;

public interface BeneficiaryAccountService {

	public Set<Beneficiary> showBeneficiaryAccount(BigInteger uci) throws IBSExceptions;

	public boolean validateBeneficiaryAccountNumber(String accountNumber);

	public boolean validateBeneficiaryAccountNameOrBankName(String name);

	public boolean validateBeneficiaryIfscCode(String ifsc);

	public boolean saveBeneficiaryAccountDetails(BigInteger uci, Beneficiary beneficiary) throws IBSExceptions;

	public boolean modifyBeneficiaryAccountDetails(BigInteger accountNumber, Beneficiary beneficiary)
			throws IBSExceptions, IOException;

	public boolean deleteBeneficiaryAccountDetails(BigInteger accountNumber) throws IBSExceptions;

	public Beneficiary getBeneficiary(BigInteger accountNumber) throws IBSExceptions;

}
