package org.jhblockchain.crypto.ethereum;

import java.util.ArrayList;
import java.util.List;

import org.jhblockchain.crypto.CoinTypes;
import org.jhblockchain.crypto.ECKeyPair;
import org.jhblockchain.crypto.bip32.ExtendedKey;
import org.jhblockchain.crypto.bip39.MnemonicCode;
import org.jhblockchain.crypto.bip39.MnemonicException.MnemonicLengthException;
import org.jhblockchain.crypto.bip44.AddressIndex;
import org.jhblockchain.crypto.bip44.BIP44;
import org.jhblockchain.crypto.bip44.CoinPairDerive;
import org.jhblockchain.crypto.exceptions.ValidationException;

public class EthAddress {

	public static String getAddressByMnemonicWords(Integer i) throws MnemonicLengthException, ValidationException {
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
	
		AddressIndex address = BIP44.m().purpose44().coinType(CoinTypes.Ethereum).account(0).external().address(i);
		ECKeyPair master = coinKeyPair.derive(address);
		return master.getAddress();
//		try {
//			System.out.println("address"+"..."+master.getAddress());
//			System.out.println("privateKey"+"..."+master.getPrivateKey());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
