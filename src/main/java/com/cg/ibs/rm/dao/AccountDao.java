package com.cg.ibs.rm.dao;

import java.math.BigInteger;
import java.util.Set;

import com.cg.ibs.rm.exception.IBSExceptions;
import com.cg.ibs.rm.model.Account;

public interface AccountDao {
	public Set<Account> getAccounts(BigInteger uci) throws IBSExceptions;
}
