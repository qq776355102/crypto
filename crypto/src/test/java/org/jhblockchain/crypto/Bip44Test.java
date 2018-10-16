package org.jhblockchain.crypto;

import java.security.Security;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jhblockchain.crypto.bip32.ExtendedKey;
import org.jhblockchain.crypto.bip39.MnemonicCode;
import org.jhblockchain.crypto.bip39.MnemonicException.MnemonicLengthException;
import org.jhblockchain.crypto.bip44.AddressIndex;
import org.jhblockchain.crypto.bip44.BIP44;
import org.jhblockchain.crypto.bip44.CoinPairDerive;
import org.jhblockchain.crypto.exceptions.ValidationException;

public class Bip44Test {
	static CoinPairDerive coinKeyPair;
	static {
	    Security.addProvider(new BouncyCastleProvider());
	}
	public Bip44Test() {
		ExtendedKey extendedKey = new Bip32Test().testRandomExtendedKey();
		coinKeyPair = new CoinPairDerive(extendedKey);
	}

	public static void testbip44EthereumExtendedKey() throws ValidationException {
		Log.log("testbip44EthereumExtendedKey--------->");

		AddressIndex address0 = BIP44.m().purpose44().coinType(CoinTypes.Ethereum).account(0).external().address(0);
		Log.log("address0:" + address0.toString());
		ExtendedKey key0 = coinKeyPair.deriveByExtendedKey(address0);
		AddressIndex address1 = BIP44.m().purpose44().coinType(CoinTypes.Ethereum).account(0).external().address(1);
		Log.log("address1:" + address1.toString());
		ExtendedKey key1 = coinKeyPair.deriveByExtendedKey(address1);
		Log.log(String.valueOf(key0.getParent()));
		Log.log(String.valueOf(key1.getParent()));
	}

	public static void testbip44EthereumEcKey() throws MnemonicLengthException, ValidationException {
		List<String> mnemonicWordsInAList = new ArrayList<>();
		mnemonicWordsInAList.add("cupboard");
		mnemonicWordsInAList.add("shed");
		mnemonicWordsInAList.add("accident");
		mnemonicWordsInAList.add("simple");
		mnemonicWordsInAList.add("marble");
		mnemonicWordsInAList.add("drive");
		mnemonicWordsInAList.add("put");
		mnemonicWordsInAList.add("crew");
		mnemonicWordsInAList.add("marine");
		mnemonicWordsInAList.add("mistake");
		mnemonicWordsInAList.add("shop");
		mnemonicWordsInAList.add("chimney");
		mnemonicWordsInAList.add("plate");
		mnemonicWordsInAList.add("throw");
		mnemonicWordsInAList.add("cable");
		byte[] seed = MnemonicCode.toSeed(mnemonicWordsInAList, "");
		ExtendedKey extendedKey = ExtendedKey.create(seed);
		CoinPairDerive coinKeyPair = new CoinPairDerive(extendedKey);
	
		AddressIndex address = BIP44.m().purpose44().coinType(CoinTypes.Ethereum).account(0).external().address(0);
		ECKeyPair master = coinKeyPair.derive(address);
		try {
			System.out.println("address"+"..."+master.getAddress());
			System.out.println("privateKey"+"..."+master.getPrivateKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
//		Log.log("address0:" + address0.toString());
//		ECKeyPair eckey0 = coinKeyPair.derive(address0);
//		Log.log("eckey0: pub=" + eckey0.getPublicKey());
		
		
		
		
//		AddressIndex address1 = BIP44.m().purpose44().coinType(CoinTypes.Ethereum).account(0).external().address(1);
//		Log.log("address1:" + address1.toString());
//		ECKeyPair eckey1 = coinKeyPair.derive(address1);
//		Log.log("eckey1: pub=" + eckey1.getPublicKey());
//		Log.log("eckey1: pri=" + eckey1.getPrivateKey());

	}
	public static void main(String[] args) throws MnemonicLengthException, ValidationException {
		testbip44EthereumEcKey();
	}
}
