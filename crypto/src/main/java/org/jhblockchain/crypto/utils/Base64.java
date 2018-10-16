package org.jhblockchain.crypto.utils;

import java.math.BigInteger;

import org.spongycastle.util.Strings;

/**
 * @author QuincySx
 * @date 2018/3/1 下午5:59
 */
public class Base64 {
    public static String decode(String input) {
        return Strings.fromByteArray(org.spongycastle.util.encoders.Base64.decode(input));
    }

    public static String encode(BigInteger input) {
        return String.format("%064x", input);
    }

    public static String encode(byte[] input) {
        return Strings.fromByteArray(org.spongycastle.util.encoders.Base64.encode(input));
    }
}
