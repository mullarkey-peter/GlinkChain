package Interfaces;

import java.security.PrivateKey;

public interface ITransaction {
	public String caculateTransactionHash();
	public void generateSignature(PrivateKey privateKey);
	public boolean verifySignature();
	public boolean processTransaction();
	public float getInputsValue();
	public float getOutputsValue();
}
