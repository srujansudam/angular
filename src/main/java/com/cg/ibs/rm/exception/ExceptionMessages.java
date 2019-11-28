package com.cg.ibs.rm.exception;

public class ExceptionMessages {
	public static final String CARD_ALREADY_ADDED="Card has already been approved and added";
	public static final String CARD_DOESNT_EXIST="Credit card doesn't exist";
	public static final String BENEFICIARY_ALREDY_ADDED="Beneficiary present in your BENEFICIARY LIST";
	public static final String BENEFICIARY_DOESNT_EXIST="Beneficiary has either been deleted or not added yet!";
	public static final String ERROR5="customer doesn't exist";
	public static final String AUTOPAYMENT_DOESNT_EXIST="Auto payment service doesn't exist";
	public static final String ERROR7="Enter a valid date format";
	public static final String ERROR8="Invalid choice";
	public static final String ERROR9="Card not added";
	public static final String ERROR10="The accessed data does not exist";
	public static final String CARD_UNDER_PENDING="Your Card has already been sent for approval";
	public static final String AUTOPAYMENT_ALREADY_ADDED="Auto payment service has already been opted.";
	public static final String ACCOUNT_UNDER_PENDING="Your Account has already been sent for approval. Can not modify until its approved";
	public static final String NO_CARDS = "No credit cards present.. please go on and add a credit card to enjoy the services";
	public static final String NO_CUSTOMERS ="No customer for entered Userid";
	public static final String NO_BENEFICIARIES = "No beneficiaries present.. please go on and add a beneficiary to enjoy the services";
	
	private ExceptionMessages() {
		super();
	}
}
