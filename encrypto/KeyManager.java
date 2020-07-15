package encrypto;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

class KeyManager {

    static final String algorithm = "SHA3-512";

	static byte[] getKey(String input) {
        MessageDigest md = null;
        byte[] keyBytes = null;
        try {
            md = MessageDigest.getInstance(algorithm);
            keyBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return keyBytes;
	}

	static String toHex(byte[] hash, int keyLength) {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < keyLength)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
	}

}