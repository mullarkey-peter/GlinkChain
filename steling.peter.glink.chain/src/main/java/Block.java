import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.annotations.Expose;

import Interfaces.IBlock;

public class Block implements IBlock{
	
	@Expose private String hash;
	@Expose private String previousHash;
	@Expose private String data;
	@Expose private long timeStamp;
	private int nonce;
	private final String GLIZZ = "glizzy";
	private Logger logger;
	
	public Block(String data, String previousHash, long timeStamp, Logger logger) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = timeStamp;
		this.logger = logger;
		this.hash = calculateBlockHash();
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getNonce() {
		return nonce;
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}
	
	public String calculateBlockHash() {
		
		logger.log(Level.FINE, "Entering calculateBlockHash().");
		String dataToHash = previousHash 
				+ Long.toString(timeStamp)
				+ Integer.toString(nonce) 
				+ GLIZZ
				+ data;
		
		return StringUtil.applySHA256(dataToHash);
	}
	
	public String mineBlock(int prefix) {
		logger.log(Level.FINE, "Entering mineBlock().");
		logger.log(Level.INFO, "Mining block with prefix" + prefix);
	    String prefixString = new String(new char[prefix]).replace('\0', '0');
	    while (!hash.substring(0, prefix).equals(prefixString)) {
	        nonce++;
	        hash = calculateBlockHash();
	    }
	    prefix++;
	    return hash;
	}
	
	public static void main(String[] args) {
		Logger logger = Logger.getLogger(Block.class.getName());
		Block genesisBlock = new Block("Genises", "0", new Date().getTime(), logger);
		logger.log(Level.INFO, "Hash for block 1: " + genesisBlock.hash);
		
		Block secondBlock = new Block("Block two", genesisBlock.hash, new Date().getTime(), logger);
		logger.log(Level.INFO,"Hash for block 2: " + secondBlock.hash);
	}
}
