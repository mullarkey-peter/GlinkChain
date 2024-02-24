import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;

import Interfaces.ITransaction;
import Interfaces.IWallet;

public class Wallet implements IWallet{
	
	private PrivateKey privateKey;
	private PublicKey publicKey;
	
	public Wallet() {
		generateKeyPair();
	}

	// TODO: Custom exception
	public void generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			// Initialize the key generator and generate a KeyPair
			keyGen.initialize(ecSpec, random);   //256 bytes provides an acceptable security level
	        	KeyPair keyPair = keyGen.generateKeyPair();
	        	// Set the public and private keys from the keyPair
	        	setPrivateKey(keyPair.getPrivate());
	        	setPublicKey(keyPair.getPublic());
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public float getBalance() {
		// TODO Auto-generated method stub
		return 0;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	@Override
	public ITransaction sendFunds() {
		// TODO Auto-generated method stub
		return null;
	}

}
