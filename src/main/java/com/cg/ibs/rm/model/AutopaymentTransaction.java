package com.cg.ibs.rm.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Autopayment_Transactions")
public class AutopaymentTransaction extends TransactionBean implements Serializable{

	private static final long serialVersionUID = -1662669084707820716L;
	
	

}
