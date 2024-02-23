import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Block implements IBlock{
	
	private String hash;
	private String previousHash;
	private String data;
	private long timeStamp;
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
		String dataToHash = previousHash 
				+ Long.toString(timeStamp)
				+ Integer.toString(nonce) 
				+ GLIZZ
				+ data;
		byte[] bytes = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			bytes = digest.digest(dataToHash.getBytes("UTF_8"));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		StringBuffer buffer = new StringBuffer();
		for (byte b : bytes) {
			buffer.append(String.format("%02x", b));
		}
		return buffer.toString();
	}
	
	public String mineBlock(int prefix) {
	    String prefixString = new String(new char[prefix]).replace('\0', '0');
	    while (!hash.substring(0, prefix).equals(prefixString)) {
	        nonce++;
	        hash = calculateBlockHash();
	    }
	    return hash;
	}
}
