package com.cg.ibs.rm.service;

import java.math.BigInteger;
import java.util.Set;

import com.cg.ibs.rm.exception.IBSExceptions;
import com.cg.ibs.rm.model.Account;

public interface AccountService {
	
	public Set<Account> getAccountsOfUci(BigInteger uci) throws IBSExceptions;
}
