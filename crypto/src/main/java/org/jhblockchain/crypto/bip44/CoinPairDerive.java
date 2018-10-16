package org.jhblockchain.crypto.bip44;

import java.util.Map;
import java.util.WeakHashMap;

import org.jhblockchain.crypto.CoinTypes;
import org.jhblockchain.crypto.ECKeyPair;
import org.jhblockchain.crypto.bip32.ExtendedKey;
import org.jhblockchain.crypto.bip32.Index;
import org.jhblockchain.crypto.bitcoin.BitCoinECKeyPair;
import org.jhblockchain.crypto.ethereum.EthECKeyPair;
import org.jhblockchain.crypto.exceptions.ValidationException;
import org.jhblockchain.crypto.utils.HexUtils;
import org.jhblockchain.crypto.utils.SHA256;

/**
 * @author QuincySx
 * @date 2018/3/5 下午3:48
 */
public class CoinPairDerive {
    private static Map<String, ExtendedKey> sExtendedKeyMap = new WeakHashMap<String, ExtendedKey>();

    private ExtendedKey mExtendedKey;

    public CoinPairDerive(ExtendedKey extendedKey) {
        mExtendedKey = extendedKey;
    }

    public ExtendedKey deriveByExtendedKey(AddressIndex addressIndex) throws ValidationException {
        String keyStr = HexUtils.toHex(mExtendedKey.getChainCode()) + HexUtils.toHex(mExtendedKey.getMaster().getRawPublicKey()) + addressIndex.toString();
        byte[] byteKey = SHA256.sha256(keyStr.getBytes());
        ExtendedKey extendedKey = sExtendedKeyMap.get(HexUtils.toHex(byteKey));
        if (extendedKey != null) {
            return extendedKey;
        }
        int address = addressIndex.getValue();
        int change = addressIndex.getParent().getValue();
        int account = addressIndex.getParent().getParent().getValue();
        CoinTypes coinType = addressIndex.getParent().getParent().getParent().getValue();
        int purpose = addressIndex.getParent().getParent().getParent().getParent().getValue();

        ExtendedKey child = mExtendedKey
                .getChild(Index.hard(purpose))
                .getChild(Index.hard(coinType.coinType()))
                .getChild(Index.hard(account))
                .getChild(change)
                .getChild(address);
        sExtendedKeyMap.put(HexUtils.toHex(byteKey), child);
        return child;
    }

    public ECKeyPair derive(AddressIndex addressIndex) throws ValidationException {
        CoinTypes coinType = addressIndex.getParent().getParent().getParent().getValue();
        ExtendedKey child = deriveByExtendedKey(addressIndex);
        ECKeyPair ecKeyPair = convertKeyPair(child, coinType);
        return ecKeyPair;
    }

    public ECKeyPair convertKeyPair(ExtendedKey child, CoinTypes coinType) throws ValidationException {
        switch (coinType) {
            case BitcoinTest:
                return BitCoinECKeyPair.parse(child.getMaster(), true);// convertBitcoinKeyPair(new BigInteger(1, child.getMaster().getPrivate()), true);
            case Ethereum:
                return EthECKeyPair.parse(child.getMaster());//convertEthKeyPair(new BigInteger(1, child.getMaster().getPrivate()));
            case Bitcoin:
            default:
                return BitCoinECKeyPair.parse(child.getMaster(), false);//convertBitcoinKeyPair(new BigInteger(1, child.getMaster().getPrivate()), false);
        }
    }
}
