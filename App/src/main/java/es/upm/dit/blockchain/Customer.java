package es.upm.dit.blockchain;

import java.math.BigInteger;

import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

public class Customer {
	
	// Gas Limit and gas price parameters of the deployed node
	private final static BigInteger GAS_LIMIT = BigInteger.valueOf(0x47b760L);
	private final static BigInteger GAS_PRICE = BigInteger.valueOf(1000000000L);
	ContractGasProvider gasProvider = new StaticGasProvider(GAS_PRICE, GAS_LIMIT);

	// Contract addresses stored, change this parameter when new deployments are created
	private final static String contractAddress = "0xfac417c8c22cf4e0267c9a234686be0786410270";

	private Bank contract;
	// We use Admin because we will unlock account through the password
	Admin web3j = Admin.build(new HttpService());
	
	public void newCustomer(Wallet wallet) throws Exception {
		contract = Bank.load(contractAddress, web3j, wallet.getCredentials(), gasProvider);
		contract.newCustomer().send();
	} 
	
	public void deleteCustomer(BigInteger identifier, Wallet wallet) throws Exception {
		contract = Bank.load(contractAddress, web3j, wallet.getCredentials(), gasProvider);
		contract.deleteCustomer(identifier).send();
	}
	
	public String[] verify(BigInteger identifier, Wallet wallet) throws Exception {
		if (contract == null) {
			contract = Bank.load(contractAddress, web3j, wallet.getCredentials(), gasProvider);
		}
		BigInteger customerNumber = contract.verify(identifier).send();
		String[] results;
		
		if(customerNumber.intValue() != 0 && customerNumber != null) {
			results = new String[]{"Your bank customer number is "+customerNumber};
			return results;
		}else
			results = new String[]{};
			
		return results;
	}
	
	
	public BigInteger newID(Wallet wallet) throws Exception {
		if (contract == null) {
			contract = Bank.load(contractAddress,web3j, wallet.getCredentials(), gasProvider);
		}
		return contract.newID(wallet.getAddress()).send();
	}
}
