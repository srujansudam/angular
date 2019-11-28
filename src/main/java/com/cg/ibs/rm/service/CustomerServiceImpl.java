package com.cg.ibs.rm.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.ibs.rm.dao.CustomerDAO;
import com.cg.ibs.rm.exception.IBSExceptions;

@Service("customerServiceImpl")
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	CustomerDAO customerDAO;

	public String returnName(BigInteger uci) {
		return customerDAO.returnName(uci);
	}

	public boolean checkUciList(BigInteger uci) throws IBSExceptions {// check whether uci is present or not
		boolean checkUci = false;
		if (customerDAO.getUciList().contains(uci)) {
			checkUci = true;
		}
		return checkUci;
	}
	
	public BigInteger returnUCI(String userID) throws IBSExceptions {
		return customerDAO.returnUCI(userID);
	}
}
