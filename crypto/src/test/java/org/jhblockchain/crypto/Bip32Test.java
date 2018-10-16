package org.jhblockchain.crypto;

import java.security.Security;
import java.util.List;

import org.jhblockchain.crypto.bip32.ExtendedKey;
import org.jhblockchain.crypto.bip39.MnemonicCode;
import org.jhblockchain.crypto.bip39.MnemonicException.MnemonicLengthException;
import org.jhblockchain.crypto.bip39.RandomSeed;
import org.jhblockchain.crypto.bip39.Words;
import org.jhblockchain.crypto.exceptions.ValidationException;
import org.jhblockchain.crypto.utils.HexUtils;

public class Bip32Test {
	public Bip32Test() {
	}

	public ExtendedKey testRandomExtendedKey() {
		Log.log("testRandomExtendedKey--------->");

		ExtendedKey ekey = ExtendedKey.createNew();
		Key master1 = ekey.getMaster();
		Log.log(HexUtils.toHex(master1.getRawPrivateKey()));// 获取十六进制私钥
		Log.log(HexUtils.toHex(master1.getRawPublicKey()));// 获取十六进制压缩公钥
		Log.log(HexUtils.toHex(master1.getRawPublicKey(false)));// 获取十六进制公钥
			
		return ekey;
	}

	public ExtendedKey testByBip39() throws MnemonicLengthException, ValidationException {
		Log.log("testByBip39--------->");
		MnemonicCode mnemonicCode = new MnemonicCode();
		byte[] random = RandomSeed.random(Words.TWELVE);
		Log.log(HexUtils.toHex(random));
		List<String> mnemonicWordsInAList = mnemonicCode.toMnemonic(random);
		Log.log(mnemonicWordsInAList.toString());
		byte[] seed = MnemonicCode.toSeed(mnemonicWordsInAList, "password");
		Log.log(HexUtils.toHex(seed));
		ExtendedKey ekey = ExtendedKey.create(seed);
		Key master1 = ekey.getMaster();
		Log.log(HexUtils.toHex(master1.getRawPrivateKey()));// 获取十六进制私钥
		Log.log(HexUtils.toHex(master1.getRawPublicKey()));// 获取十六进制压缩公钥
		Log.log(HexUtils.toHex(master1.getRawPublicKey(false)));// 获取十六进制公钥
			
		return ekey;
	}
}
