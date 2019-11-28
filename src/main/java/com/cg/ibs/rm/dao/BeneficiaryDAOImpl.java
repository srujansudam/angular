package com.cg.ibs.rm.dao;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

//import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cg.ibs.rm.exception.ExceptionMessages;
import com.cg.ibs.rm.exception.IBSExceptions;
import com.cg.ibs.rm.model.Beneficiary;
import com.cg.ibs.rm.model.Customer;
import com.cg.ibs.rm.ui.CardStatus;

@Repository("BeneficiaryDao")
public class BeneficiaryDAOImpl implements BeneficiaryDAO {
	public BeneficiaryDAOImpl() {
		super();
	}

	//private static Logger logger = Logger.getLogger(BeneficiaryDAOImpl.class);
	
	@PersistenceContext
	EntityManager manager;

	@Override
	public Set<Beneficiary> getDetails(BigInteger uci) throws IBSExceptions {// returns set of beneficiaries for a given
		// uci
		//logger.info("entering into getDetails method of BeneficiaryDAOImpl class");
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Beneficiary> query = builder.createQuery(Beneficiary.class);
		Root<Customer> custRoot = query.from(Customer.class);
		Join<Customer, Beneficiary> beneficiaries = custRoot.join("beneficiaries");
		query.select(beneficiaries)
				.where(builder.and((builder.equal(custRoot.get("uci"), uci)),
						(builder.or(builder.equal(beneficiaries.get("status"), CardStatus.ACTIVE),
								builder.equal(beneficiaries.get("status"), CardStatus.PENDING)))));
		List<Beneficiary> list = manager.createQuery(query).getResultList();
		if (list.isEmpty()) {
			throw new IBSExceptions(ExceptionMessages.NO_BENEFICIARIES);
		} else {
			return new HashSet<>(list);
			}
	}
	
	@Override
	public boolean copyDetails(BigInteger uci, Beneficiary beneficiary) throws IBSExceptions {// copying values into the
		// final beneficiary
		// database
		boolean result = false;
		//logger.info("entering into copyDetails method of BeneficiaryDAOImpl class");
		Beneficiary beneficiary2 = manager.find(Beneficiary.class, beneficiary.getAccountNumber());
		if (beneficiary2 == null || beneficiary2.getStatus().equals(CardStatus.BLOCKED)) {
			Customer customer = manager.find(Customer.class, uci);
			beneficiary.setCustomer(customer);
			manager.merge(beneficiary);
			result = true; // must include WHERE
		} else {
			throw new IBSExceptions(ExceptionMessages.BENEFICIARY_ALREDY_ADDED);
		}
		return result;
	}

	@Override
	public Beneficiary getBeneficiary(BigInteger accountNumber) throws IBSExceptions {// returns beneficiary
		// object
		//logger.info("entering into getBeneficiary method of BeneficiaryDAOImpl class");
		Beneficiary beneficiary = manager.find(Beneficiary.class, accountNumber);
		if (null == beneficiary) {
			throw new IBSExceptions(ExceptionMessages.BENEFICIARY_DOESNT_EXIST);
		} else {
			return beneficiary;
		}
	}

	@Override
	public boolean updateDetails(Beneficiary beneficiary1) throws IBSExceptions {
		//logger.info("entering into updateDetails method of BeneficiaryDAOImpl class");
		boolean check = false;
		Beneficiary beneficiary = manager.find(Beneficiary.class, beneficiary1.getAccountNumber());
		if (null == beneficiary) {
			throw new IBSExceptions(ExceptionMessages.BENEFICIARY_DOESNT_EXIST);
		} else if (beneficiary.getStatus().equals(CardStatus.PENDING)) {
			throw new IBSExceptions(ExceptionMessages.ACCOUNT_UNDER_PENDING);
		} else if (beneficiary.getStatus().equals(CardStatus.ACTIVE)) {
			beneficiary.setStatus(CardStatus.PENDING);
			manager.merge(beneficiary);
			check = true;
		}
		return check;

	}

	@Override
	public boolean deleteDetails(BigInteger accountNumber) throws IBSExceptions {
		//logger.info("entering into deleteDetails method of BeneficiaryDAOImpl class");
		boolean check = false;
		Beneficiary beneficiary = manager.find(Beneficiary.class, accountNumber);
		if (null == beneficiary) {
			throw new IBSExceptions(ExceptionMessages.BENEFICIARY_DOESNT_EXIST);
		} else if ((beneficiary.getStatus().equals(CardStatus.ACTIVE) || beneficiary.getStatus().equals(CardStatus.PENDING))) {
			manager.remove(beneficiary);
			check = true;
		}
		return check;

	}

}