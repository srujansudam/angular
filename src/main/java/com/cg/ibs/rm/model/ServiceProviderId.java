package com.cg.ibs.rm.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ServiceProviderId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8053767533517376863L;
	@Column(name = "SPI")
	private BigInteger spi;
	@Column(name = "uci")
	private BigInteger uci;

	public ServiceProviderId() {
		super();
	}

	public ServiceProviderId(BigInteger spi, BigInteger uci) {
		super();
		this.spi = spi;
		this.uci = uci;
	}

	public BigInteger getSpi() {
		return spi;
	}

	public void setSpi(BigInteger spi) {
		this.spi = spi;
	}

	public BigInteger getUci() {
		return uci;
	}

	public void setUci(BigInteger uci) {
		this.uci = uci;
	}

	@Override
	public String toString() {
		return "ServiceProviderId [Spi=" + spi + ", uci=" + uci + "]";
	}

	
}
