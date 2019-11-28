package com.cg.ibs.rm.service;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.ibs.rm.dao.AccountDao;
import com.cg.ibs.rm.exception.IBSExceptions;
import com.cg.ibs.rm.model.Account;


@Service("accountServiceImpl")
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	private AccountDao accountDao;
	
	
	@Override
	public Set<Account> getAccountsOfUci(BigInteger uci) throws IBSExceptions {
		return new HashSet<>(accountDao.getAccounts(uci));
	}
	
}
