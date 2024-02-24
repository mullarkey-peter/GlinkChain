
public class TransactionInput {

	private String transactionOutputId;
	private TransactionOutput unspentOutput;
	
	public TransactionInput(String transcationOutputId) {
		this.setTransactionOutputId(transactionOutputId);
	}

	public String getTransactionOutputId() {
		return transactionOutputId;
	}

	public void setTransactionOutputId(String transactionOutputId) {
		this.transactionOutputId = transactionOutputId;
	}

	public TransactionOutput getUnspentOutput() {
		return unspentOutput;
	}

	public void setUnspentOutput(TransactionOutput unspentOutput) {
		this.unspentOutput = unspentOutput;
	}
}
