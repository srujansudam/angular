package com.cg.ibs.rm.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "Remittance_History" )
public class BankerHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="Req_Id")
	private Integer requestId;
	@Column(name="Card_No.")
	private BigInteger cardNumber;
	@Column(name= "Acc_no.")
	private BigInteger accNumber;
	@Column(name="Decision")
	private String decision;
	@Column(name="Remarks")
	private String remarks;
	@Column(name="Banker_Id")
	private Integer bankerId;
	public Integer getBankerId() {
		return bankerId;
	}
	public void setBankerId(Integer bankerId) {
		this.bankerId = bankerId;
	}
	public Integer getRequestId() {
		return requestId;
	}
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}
	public BigInteger getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(BigInteger cardNumber) {
		this.cardNumber = cardNumber;
	}
	public BigInteger getAccNumber() {
		return accNumber;
	}
	public void setAccNumber(BigInteger accNumber) {
		this.accNumber = accNumber;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	

}
