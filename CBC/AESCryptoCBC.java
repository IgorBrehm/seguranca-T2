import javax.crypto.*;
import javax.crypto.spec.*;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import java.util.Random;

public class AESCryptoCBC {
 
	private static byte[] key;
	private static byte[] initVector;
	
	// Funcao para converter uma String em hexadecimal para
	// um array de bytes
	public static byte[] toByteArray(String s) throws DecoderException {
		return Hex.decodeHex(s.toCharArray());
	}
	
	// Metodo que cifra uma mensagem
	public static String encrypt(String value) {
	    try {
	        IvParameterSpec iv = new IvParameterSpec(initVector);
	        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
	        
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
	 
	        byte[] encrypted = cipher.doFinal(value.getBytes());
	        String a = Hex.encodeHexString(initVector);
	        String b = Hex.encodeHexString(encrypted);

	        return a+b;
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}
	
	// Metodo que decifra uma mensagem
	public static String decrypt(String encrypted) {
	    try {
	        IvParameterSpec iv = new IvParameterSpec(initVector);
	        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
	        
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
	        byte[] original = cipher.doFinal(toByteArray(encrypted));
	        byte[] aux = new byte[original.length-initVector.length];
	        int j = 0;
	        for(int i = initVector.length; i < original.length; i++) {
	        	aux[j] = original[i];
	        	j+=1;
	        }
	        return new String(aux);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	 
	    return null;
	}
	
	// Recebe tres parametros: operacao, senha e uma string alvo da operacao
	public static void main(String[] args) throws Exception {
	
		String operation = args[0];
		key = toByteArray(args[1]);
		byte[] array = new byte[16];
	    new Random().nextBytes(array);
	    initVector = array;
		
		if(operation.equalsIgnoreCase("cifrar")) {
			System.out.println("Iniciando cifra da mensagem:");
			System.out.println("Mensagem: "+ args[2]);
			System.out.println("Senha: "+ args[1]);
			System.out.println("Mensagem cifrada: " + encrypt(args[2]));
		}
		else {
			System.out.println("Iniciando decifragem da mensagem:");
			System.out.println("Cifra: "+ args[2]);
			System.out.println("Senha: "+ args[1]);
			System.out.println("Mensagem: " + decrypt(args[2]));
		}
	 }
}
