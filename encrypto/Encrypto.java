package encrypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

public class Encrypto{

    static final int FILE_READ_BUFFER_SIZE = 1024;

    public static void encrypt(File inputFile, String password, File outputFile) throws Exception {
        FileInputStream fin = null;
        FileOutputStream fout = null;

        try {
            //open stream for reading and writing data
            fin = new FileInputStream(inputFile);
            fout = new FileOutputStream(outputFile);
        
            //SHA3-512 key
            byte[] key = KeyManager.getKey(password);

            //color from key 
            Color color = getColorFromKey(key);

            //buffer to store data from file
            byte[] data = new byte[FILE_READ_BUFFER_SIZE];
            int n = -1;
            
            //main encryption loop
            while ((n=fin.read(data)) != -1) {
                encryptionPass1(data, n, key);
                encryptionPass2(data, n, color);

                fout.write(data, 0, n);
            }

        } catch (Exception e) {
            throw new Exception("ERROR: Fail to encrypt { "+ inputFile+" }\n"+e.getMessage());
        }finally {
            fin.close();
            fout.close();    
        }
    }

    public static String encrypt(String inputString, String password) throws Exception{
        byte[] key = KeyManager.getKey(password);

        Color color = getColorFromKey(key);

        byte[] data = inputString.getBytes(StandardCharsets.ISO_8859_1);

        encryptionPass1(data, data.length, key);
        encryptionPass2(data, data.length, color);

        return new String(data,StandardCharsets.ISO_8859_1);
    }

    public static String decrypt(String encryptedString, String password) throws Exception{
        byte[] key = KeyManager.getKey(password);

        Color color = getColorFromKey(key);

        byte[] data = encryptedString.getBytes(StandardCharsets.ISO_8859_1);

        decryptionPass2(data, data.length, color);
        decryptionPass1(data, data.length, key);

        return new String(data, StandardCharsets.ISO_8859_1);
    }

    public static void decrypt(File inputFile, String password, File outputFile) throws Exception {
        FileInputStream fin = null;
        FileOutputStream fout = null;

        try {
            //open stream for reading and writing data
            fin = new FileInputStream(inputFile);
            fout = new FileOutputStream(outputFile);
        
            //SHA3-512 key
            byte[] key = KeyManager.getKey(password);

            //color from key 
            Color color = getColorFromKey(key);

            //buffer to store data from file
            byte[] data = new byte[FILE_READ_BUFFER_SIZE];
            int n = -1;
            
            //main encryption loop
            while ((n=fin.read(data)) != -1) {
                decryptionPass2(data, n, color);
                decryptionPass1(data, n, key);

                fout.write(data, 0, n);
            }

        } catch (Exception e) {
            throw new Exception("ERROR: Fail to encrypt { "+ inputFile+" }\n"+e.getMessage());
        }finally {
            fin.close();
            fout.close();    
        }
    }

    private static void encryptionPass1(byte[] data, int n, byte[] key) {
        for(int i=0; i<n; ++i){
            data[i] = (byte)(data[i] ^ key[i%key.length]);
        }
    }

    private static void encryptionPass2(byte[] data, int n, Color color) {
        int bandIndex = 0;
        
        for(int i=0; i<n; ++i) {
            int row = data[i] >> 4;
            int column = data[i] & 0x0f;

            data[i] = (byte)((color.getBand(bandIndex) + 16*row + column)%256); 

            bandIndex = (++bandIndex) % Color.TOTAL_COLOR_BANDS;
        }
    }

    private static void decryptionPass2(byte[] data, int n, Color color){
        int bandIndex = 0;
        
        for(int i=0; i<n; ++i) {
            int dataInint = (int)data[i] & 0xff;
            int temp = (dataInint - color.getBand(bandIndex) + 256) % 256;
            data[i] = (byte)temp;

            bandIndex = (++bandIndex) % Color.TOTAL_COLOR_BANDS;
        }
    }

    private static void decryptionPass1(byte[] data, int n, byte[] key) {
        encryptionPass1(data, n, key);
    }

    public static Color getColorFromKey(byte[] key) {
        int[] colorBands = new int[Color.TOTAL_COLOR_BANDS];
        
        int n = key.length;
        //if & is not used then 1 in msb convert number greater than 127 to bigger negative integer
        int ind = ((int)key[0] & 0xff) % n; 

        for(int i=0 ;i<colorBands.length; ++i) {
            colorBands[i] = (int)key[ind] & 0xff;
            ind = colorBands[i] % n;
        }
        return new Color(colorBands);
    }
}