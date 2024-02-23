import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.GsonBuilder;


public class GlinkChain {
	
	public static ArrayList<Block> glinkChain = new ArrayList<Block>();
	public static int prefix = 0;
	
	public static boolean isChainValid(Logger logger) {
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
		Logger logger = Logger.getLogger(GlinkChain.class.getName());

		//add our blocks to the blockchain ArrayList:
		glinkChain.add(new Block("Hi im the first block", "0", new Date().getTime(),logger));
		glinkChain.get(0).mineBlock(prefix);
		glinkChain.add(new Block("Yo im the second block",glinkChain.get(glinkChain.size()-1).getHash(), new Date().getTime(),logger));
		glinkChain.get(1).mineBlock(prefix);
		glinkChain.add(new Block("Hey im the third block",glinkChain.get(glinkChain.size()-1).getHash(), new Date().getTime(),logger));
		glinkChain.get(2).mineBlock(prefix);
		
		System.out.println("\nBlockchain is Valid: " + isChainValid(logger));

		
		String blockchainJson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create().toJson(glinkChain);		
		System.out.println(blockchainJson);
	}
	
}
