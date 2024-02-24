package Interfaces;

public interface IWallet {
	public void generateKeyPair();
	public float getBalance();
	public ITransaction sendFunds();
}
