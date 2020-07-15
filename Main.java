import java.io.File;
import encrypto.Encrypto;

public class Main {
    public static void main(String[] args) {
        
        try {
            Long encryptStart = System.currentTimeMillis();

            String password = "Hello";
            String testString = "This is a test string created for testing this program";

            // Encrypto.encrypt(new File(args[0]), password, new File("encrypted"));
            String encryptedString = Encrypto.encrypt(testString, password);

            Long encryptEnd = System.currentTimeMillis();
            Long decryptStart = System.currentTimeMillis();
            
            // Encrypto.decrypt(new File("encrypted"), password, new File("decrypted"));
            String decryptedString = Encrypto.decrypt(encryptedString, password);

            Long decryptEnd = System.currentTimeMillis();

            System.out.println(":SUCCESS:");
            System.out.printf("Encryption Time: %d ms\n", encryptEnd-encryptStart);
            System.out.printf("Decryption Time: %d ms\n", decryptEnd-decryptStart);

            System.out.println("Original String  : "+testString);
            System.out.println("Encrpted String  : "+encryptedString);
            System.out.println("Decrypted String : "+decryptedString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}