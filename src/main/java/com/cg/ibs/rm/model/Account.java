package com.cg.ibs.rm.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Accounts")
public class Account implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5477311237380010719L;
	@Id
	@Column(name = "account_number", nullable = false, length = 11)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACC_SQ")
	@SequenceGenerator(sequenceName = "ACCOUNT_SEQUENCE", allocationSize = 1, name = "ACC_SQ")
	private BigInteger accNo;
	@Column(name = "balance", nullable = false, length = 20)
	private BigDecimal balance;
	@Column(name = "transac_pass", nullable = false, length = 15)
	private String trans_Pwd;
	@Column(name = "acc_creation_date", nullable = false)
	private LocalDate accCreationDate;
	@Column(name = "open_balance", nullable = false, length = 20)
	private BigDecimal openBalance;
//	@Column(name = "acc_status", nullable = false, length = 17)
//	@Enumerated(EnumType.STRING)
//	private AccountStatus accStatus;
//	@Column(name = "account_type", nullable = false, length = 17)
//	@Enumerated(EnumType.STRING)
//	private AccountType accType;
//	@Column(name = "tenure", length = 7)
//	private double tenure = -1;
	@Column(name = "maturity_amt", length = 20)
	private BigDecimal maturityAmt = new BigDecimal(-1);
	
	@JsonIgnore
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<AccountHolding> accountHoldings = new HashSet<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	private Set<TransactionBean> transaction = new HashSet<>();

	public Account() {
		super();
	}
//
//	public double getTenure() {
//		return tenure;
//	}
//
//	public void setTenure(double tenure) {
//		this.tenure = tenure;
//	}

	public BigDecimal getMaturityAmt() {
		return maturityAmt;
	}

	public Set<TransactionBean> getTransaction() {
		return transaction;
	}

	public void setTransaction(Set<TransactionBean> transaction) {
		this.transaction = transaction;
	}

	public void setMaturityAmt(BigDecimal maturityAmt) {
		this.maturityAmt = maturityAmt;
	}

	public BigInteger getAccNo() {
		return accNo;
	}

	public void setAccNo(BigInteger accNo) {
		this.accNo = accNo;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getTrans_Pwd() {
		return trans_Pwd;
	}

	public void setTrans_Pwd(String trans_Pwd) {
		this.trans_Pwd = trans_Pwd;
	}

	public LocalDate getAccCreationDate() {
		return accCreationDate;
	}

	public void setAccCreationDate(LocalDate accCreationDate) {
		this.accCreationDate = accCreationDate;
	}

	public BigDecimal getOpenBalance() {
		return openBalance;
	}

	public void setOpenBalance(BigDecimal openBalance) {
		this.openBalance = openBalance;
	}

//	public AccountStatus getAccStatus() {
//		return accStatus;
//	}
//
//	public void setAccStatus(AccountStatus accStatus) {
//		this.accStatus = accStatus;
//	}
//
//	public AccountType getAccType() {
//		return accType;
//	}
//
//	public void setAccType(AccountType accType) {
//		this.accType = accType;
//	}

	public Set<AccountHolding> getAccountHoldings() {
		return accountHoldings;
	}

	public void setAccountHoldings(Set<AccountHolding> accountHoldings) {
		this.accountHoldings = accountHoldings;
	}

//	public Set<TransactionBean> getTransaction() {
//		return transaction;
//	}
//
//	public void setTransaction(Set<TransactionBean> transaction) {
//		this.transaction = transaction;
//	}

}
