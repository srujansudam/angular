package com.cg.ibs.rm.dao;

import java.math.BigInteger;
import java.util.Set;

import com.cg.ibs.rm.exception.IBSExceptions;

public interface CustomerDAO {
	public Set<BigInteger> getUciList() throws IBSExceptions;

	public String returnName(BigInteger uci);
	public BigInteger returnUCI(String userID) throws IBSExceptions;
}
