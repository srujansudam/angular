package com.cg.ibs.rm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Account_holding")
public class AccountHolding implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8657885082899327732L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ahid")
	private Long aHId;
	@ManyToOne(fetch = FetchType.LAZY)
	private Customer customer;
	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;

	
	public AccountHolding() {
		super();
	}

	public Long getaHId() {
		return aHId;
	}

	public void setaHId(Long aHId) {
		this.aHId = aHId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
}