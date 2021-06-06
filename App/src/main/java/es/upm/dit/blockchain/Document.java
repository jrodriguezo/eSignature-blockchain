package es.upm.dit.blockchain;

import java.math.BigInteger;

import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

public class Document {

	// Gas Limit and gas price parameters of the deployed node
	private final static BigInteger GAS_LIMIT = BigInteger.valueOf(0x47b760L);
	private final static BigInteger GAS_PRICE = BigInteger.valueOf(1000000000L);
	ContractGasProvider gasProvider = new StaticGasProvider(GAS_PRICE, GAS_LIMIT);

	// Contract addresses stored, change this parameter when new deployments are created
	private final static String contractAddress = "0x124d2e69668be271808cc5a22534172a28faa09c";
	
	private Verifier contract;
	// We use Admin because we will unlock account through the password
	Admin web3j = Admin.build(new HttpService());
	
	public void newDocument(Wallet wallet) throws Exception {
		contract = Verifier.load(contractAddress, web3j, wallet.getCredentials(), gasProvider);
		contract.newDocument().send();
	}
	
	public void deleteDocument(BigInteger identifier, Wallet wallet) throws Exception {
		contract = Verifier.load(contractAddress, web3j, wallet.getCredentials(), gasProvider);
		contract.deleteDocument(identifier).send();
	}
	
	public String[] verify(BigInteger identifier, Wallet wallet) throws Exception {
		if (contract == null) {
			contract = Verifier.load(contractAddress, web3j, wallet.getCredentials(), gasProvider);
		}
		BigInteger ssNumber = contract.verify(identifier).send();
		String[] results;
		if(ssNumber.intValue() != 0) {
			results = new String[]{"Your social security number is "+ssNumber};
		}else
			results = new String[]{};
		
		return results;
	}
	
	public BigInteger newID(Wallet wallet) throws Exception {
		if (contract == null) {
			contract = Verifier.load(contractAddress,web3j, wallet.getCredentials(), gasProvider);
		}
		return contract.newID(wallet.getAddress()).send();
	}
}
