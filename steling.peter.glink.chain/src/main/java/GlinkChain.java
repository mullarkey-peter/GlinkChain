import java.security.Security;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.GsonBuilder;


public class GlinkChain {
	
	public static ArrayList<Block> glinkChain = new ArrayList<Block>();
	public static int prefix = 0;
	public static Wallet wallet1;
	public static Wallet wallet2;
	
	public static boolean isChainValid(Logger logger) {
		
		logger.log(Level.FINE, "Entering isChainValid().");
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[prefix]).replace('\0', '0');

		
		// loop through block and validate all hashes
		for(int i=1; i < glinkChain.size(); i++) {
			currentBlock = glinkChain.get(i);
			previousBlock = glinkChain.get(i-1);
			
			// compare registered hash and calculated hash:
			String registeredHash = currentBlock.getHash();
			if(!registeredHash.equals(currentBlock.calculateBlockHash())) {
				logger.log(Level.INFO, "Current Hashes not equal" + registeredHash);
				return false;
			}
			
			// compare previous hash and registered previous hash
			String previousRegisteredHash  = currentBlock.getPreviousHash();
			if(!previousRegisteredHash.equals(previousBlock.calculateBlockHash())) {
				logger.log(Level.INFO, "Current Hashes not equal" + previousRegisteredHash);
				return false;
			}
			
			// check if hash is solved
			if(!registeredHash.substring(0,prefix).equals(hashTarget)) {
				logger.log(Level.INFO, "Block has not been mined");
				return false;                                                                                                                                                                                                                                       
			}
		}
		return true;
	}
	
	public static void main(String[] args) {	
		/*
		 * Logger logger = Logger.getLogger(GlinkChain.class.getName());
		 * 
		 * //add our blocks to the block chain ArrayList: glinkChain.add(new
		 * Block("Hi im the first block", "0", new Date().getTime(),logger));
		 * glinkChain.get(0).mineBlock(prefix); glinkChain.add(new
		 * Block("Yo im the second block",glinkChain.get(glinkChain.size()-1).getHash(),
		 * new Date().getTime(),logger)); glinkChain.get(1).mineBlock(prefix);
		 * glinkChain.add(new
		 * Block("Hey im the third block",glinkChain.get(glinkChain.size()-1).getHash(),
		 * new Date().getTime(),logger)); glinkChain.get(2).mineBlock(prefix);
		 * 
		 * logger.log(Level.INFO, "\nBlockchain is Valid: " + isChainValid(logger));
		 * 
		 * 
		 * String blockchainJson = new
		 * GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().
		 * create().toJson(glinkChain); logger.log(Level.INFO, blockchainJson);
		 */
		
		//Setup Bouncy castle as a Security Provider
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); 
		//Create the new wallets
		wallet1 = new Wallet();
		wallet2 = new Wallet();
		//Test public and private keys
		System.out.println("Private and public keys:");
		System.out.println(StringUtil.getStringFromKey(wallet1.getPrivateKey()));
		System.out.println(StringUtil.getStringFromKey(wallet1.getPublicKey()));
		//Create a test transaction from WalletA to walletB 
		Transaction transaction = new Transaction(wallet1.getPublicKey(), wallet1.getPublicKey(), 5, null);
		transaction.generateSignature(wallet1.getPrivateKey());
		//Verify the signature works and verify it from the public key
		System.out.println("Is signature verified");
		System.out.println(transaction.verifySignature());
	}
	
}
