package org.jhblockchain.crypto;

import org.jhblockchain.crypto.exceptions.NotSupportException;

public enum CoinTypes {
	Bitcoin(0, "BTC"), 
	BitcoinTest(1, "BTC"), 
	Litecoin(2, "LTC"), 
	Dogecoin(3, "DOGE"), 
	Ethereum(60, "ETH"), 
	EOS(194, "EOS");

	private int coinType;
	private String coinName;

	CoinTypes(int coinType, String coinName) {
		this.coinType = coinType;
		this.coinName = coinName;
	}

	public int coinType() {
		return coinType;
	}

	public String coinName() {
		return coinName;
	}

	public static CoinTypes parseCoinType(int type) throws NotSupportException {
		for(CoinTypes e : CoinTypes.values()) {
			if(e.coinType == type) {
				return e;
			}
		}
		throw new NotSupportException("The currency is not supported for the time being");
	}
}
