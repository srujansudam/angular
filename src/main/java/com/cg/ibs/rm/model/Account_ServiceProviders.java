package com.cg.ibs.rm.model;

import java.util.Set;

public class Account_ServiceProviders {

	private Set<Account> accounts;

	private Set<ServiceProvider> serviceProviders;

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public Set<ServiceProvider> getServiceProviders() {
		return serviceProviders;
	}

	public void setServiceProviders(Set<ServiceProvider> serviceProviders) {
		this.serviceProviders = serviceProviders;
	}

	public Account_ServiceProviders(Set<Account> accounts, Set<ServiceProvider> serviceProviders) {
		super();
		this.accounts = accounts;
		this.serviceProviders = serviceProviders;
	}

	public Account_ServiceProviders() {
		super();
	}


}
