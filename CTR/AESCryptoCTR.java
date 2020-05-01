import javax.crypto.*;
import javax.crypto.spec.*;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import java.util.Random;

public class AESCryptoCTR {

	private static byte[] key;
	
	// Funcao para converter uma String em hexadecimal para
	// um array de bytes
	public static byte[] toByteArray(String s) throws DecoderException {
		return Hex.decodeHex(s.toCharArray());
	}
	
	// Metodo que cifra uma mensagem
	public static String encrypt(String value) {
	    try {
	    	byte[] array = new byte[16];
		    new Random().nextBytes(array);
	        IvParameterSpec iv = new IvParameterSpec(array);
	        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
	        
	        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
	 
	        byte[] encrypted = cipher.doFinal(value.getBytes());
	        String a = Hex.encodeHexString(array);
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
	    	byte[] cifr = toByteArray(encrypted);
	    	byte[] ivarray = new byte[16];
	    	for(int i = 0; i < ivarray.length; i++) {
	    		ivarray[i] = cifr[i];
	    	}
	    	byte[] msg = new byte[cifr.length-16];
	    	int ctr = 16;
	    	for(int j = 0; j < msg.length; j++) {
	    		msg[j] = cifr[ctr];
	    		ctr+=1;
	    	}
	        IvParameterSpec iv = new IvParameterSpec(ivarray);
	        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
	        
	        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
	        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
	        byte[] original = cipher.doFinal(msg);
	        return new String(original);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	 
	    return null;
	}
	
	// Recebe tres parametros: operacao, senha e uma string alvo da operacao
	public static void main(String[] args) throws Exception {
	
		String operation = args[0];
		key = toByteArray(args[1]);
		
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
