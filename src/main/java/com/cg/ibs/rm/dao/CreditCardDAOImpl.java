package com.cg.ibs.rm.dao;

import java.math.BigInteger;
import java.util.HashSet;
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
import com.cg.ibs.rm.model.CreditCard;
import com.cg.ibs.rm.model.Customer;
import com.cg.ibs.rm.ui.CardStatus;

@Repository("CreditCardDao")
public class CreditCardDAOImpl implements CreditCardDAO {
	//private static Logger logger = Logger.getLogger(CreditCardDAOImpl.class);
	@PersistenceContext
	private EntityManager manager;

	public CreditCardDAOImpl() {
		super();
	}

	public Set<CreditCard> getDetails(BigInteger uci) throws IBSExceptions {
		//logger.info("entering into getDetails method of CreditCardDAOImpl class");
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<CreditCard> query = builder.createQuery(CreditCard.class);
		Root<Customer> custRoot = query.from(Customer.class);
		Join<Customer, CreditCard> creditCards = custRoot.join("creditCards");
		query.select(creditCards)
				.where(builder.and(builder.equal(custRoot.get("uci"), uci),
						builder.or(builder.equal(creditCards.get("cardStatus"), CardStatus.ACTIVE),
								(builder.equal(creditCards.get("cardStatus"), CardStatus.PENDING)))));
		java.util.List<CreditCard> list = manager.createQuery(query).getResultList();
		if (list.isEmpty()) {
			throw new IBSExceptions(ExceptionMessages.NO_CARDS);
		} else {
			return new HashSet<>(list);
			}
	}

	public boolean copyDetails(BigInteger uci, CreditCard card) throws IBSExceptions {// copying details for bank_admin
																						// check
		//logger.info("entering into copyDetails method of CreditCardDAOImpl class");
		boolean result = false;
		//logger.info("entering into copyDetails method of BeneficiaryDAOImpl class");
		CreditCard card2 = manager.find(CreditCard.class, card.getCardNumber());
		if (card2 == null || card2.getCardStatus().equals(CardStatus.BLOCKED)) {
			Customer customer = manager.find(Customer.class, uci);
			card.setCustomer(customer);
			manager.merge(card);
			result = true;
		} else if ((card.getCardStatus().equals(CardStatus.ACTIVE))) {
			throw new IBSExceptions(ExceptionMessages.CARD_ALREADY_ADDED);
		} else if ((card.getCardStatus().equals(CardStatus.PENDING))) {
			throw new IBSExceptions(ExceptionMessages.CARD_UNDER_PENDING);
		}
		return result;
	}

	@Override
	public boolean deleteDetails(BigInteger cardNumber) throws IBSExceptions {// delete method for customer use
		//logger.info("entering into deleteDetails method of BeneficiaryDAOImpl class");
		boolean check = false;
		CreditCard card = manager.find(CreditCard.class, cardNumber);
		if (null == card) {
			throw new IBSExceptions(ExceptionMessages.CARD_DOESNT_EXIST);
		} else if ((card.getCardStatus().equals(CardStatus.ACTIVE)) || (card.getCardStatus().equals(CardStatus.PENDING))) {
			manager.remove(card);
			check = true;
		}
		return check;
	}
}