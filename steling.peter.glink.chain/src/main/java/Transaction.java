import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

import Interfaces.ITransaction;

public class Transaction implements ITransaction{
	
	private String transactionId; // transaction hash
	private PublicKey sender;
	private PublicKey reciepient;
	private float value;
	private byte[] signature;
	
	public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
	public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
	
	private static int sequence = 0; // number of transactions that have been generated
	
	public Transaction(PublicKey from, PublicKey to, float value,  ArrayList<TransactionInput> inputs) {
		this.setSender(from);
		this.setReciepient(to);
		this.setValue(value);
		this.inputs = inputs;
	}

	@Override
	public String caculateTransactionHash() {
		sequence++; //increase the sequence to avoid 2 identical transactions having the same hash
		String dataToHash = getSender().toString() 
				+ getReciepient().toString() 
				+ Float.toString(getValue()) 
				+ sequence;
		
		return StringUtil.applySHA256(dataToHash);
	}


	@Override
	public void generateSignature(PrivateKey privateKey) {
		// TODO: sign input/outputs and time stamp
		String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value);
		signature = StringUtil.applyECDSASig(privateKey,data);	
	}

	@Override
	public boolean verifySignature() {
		String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value)	;
		return StringUtil.verifyECDSASig(sender, data, signature);
	}

	@Override
	public boolean processTransaction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getInputsValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getOutputsValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public PublicKey getSender() {
		return sender;
	}

	public void setSender(PublicKey sender) {
		this.sender = sender;
	}

	public PublicKey getReciepient() {
		return reciepient;
	}

	public void setReciepient(PublicKey reciepient) {
		this.reciepient = reciepient;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public byte[] getSignature() {
		return signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

}
