package es.upm.dit.blockchain;

import java.math.BigInteger;

import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import es.upm.dit.model.ParametersSendedToIssuer;

public class Credentials {
	
	// Gas Limit and gas price parameters of the deployed node
	private final static BigInteger GAS_LIMIT = BigInteger.valueOf(0x47b760L);
	private final static BigInteger GAS_PRICE = BigInteger.valueOf(1000000000L);
	ContractGasProvider gasProvider = new StaticGasProvider(GAS_PRICE, GAS_LIMIT);
	
	// Contract addresses stored, change this parameter when new deployments are created
	private final static String contractAddress = "0x406cb623ce36e12155b49be161d2b029a92d5715";
	
	private Issuer contract;
	// We use Admin because we will unlock account through the password
	Admin web3j = Admin.build(new HttpService());
	
	public void newCredential(Wallet wallet, ParametersSendedToIssuer parameters) throws Exception {
		// Whether you want to see the web3 client version is used
		
		/* 
		 * Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
		 * String clientVersion = web3ClientVersion.getWeb3ClientVersion();
		 * System.out.println(clientVersion);
		 */

		PersonalUnlockAccount personalUnlockAccount = web3j.personalUnlockAccount(wallet.getAddress(), parameters.getPassword()).send();
		if (personalUnlockAccount.accountUnlocked()) {
			// We need to know previously where the contract had been deployed
			contract = Issuer.load(contractAddress, web3j, wallet.getCredentials(), gasProvider);
			contract.newCredential(parameters.getName(), parameters.getSurname(), parameters.getGender()).send();
		}
	}
	
	public void authorize(String user, Wallet wallet) throws Exception {
		if (contract == null) {
			contract = Issuer.load(contractAddress, web3j, wallet.getCredentials(), gasProvider);
		}
		contract.authorize(user).send();
	}
	
	public void deleteCredential(BigInteger identifier, Wallet wallet) throws Exception {
		contract = Issuer.load(contractAddress, web3j, wallet.getCredentials(), gasProvider);
		contract.deleteCredential(identifier).send();
	}
	
	public String[] verify(BigInteger identifier, Wallet wallet) throws Exception {
		if (contract == null) {
			contract = Issuer.load(contractAddress, web3j, wallet.getCredentials(), gasProvider);
		}
		Tuple3<String, String, String> tuple = contract.verify(identifier).send();
		String[] results;
		if(!tuple.component1().isEmpty() && !tuple.component2().isEmpty() && !tuple.component3().isEmpty()) {
			results = new String[]{"Your name is "+tuple.component1(),"Your surname is "+tuple.component2(),"Your gender is "+tuple.component3()};
		}else
			results = new String[]{};
		
		return results;
	}
	
	public String[] rawVerify(BigInteger identifier, Wallet wallet) throws Exception {
		contract = Issuer.load(contractAddress, web3j, wallet.getCredentials(), gasProvider);
		Tuple3<String, String, String> tuple = contract.verify(identifier).send();
		String[] results;
		if(!tuple.component1().isEmpty() && !tuple.component2().isEmpty() && !tuple.component3().isEmpty()) {
			results = new String[]{tuple.component1(),tuple.component2(),tuple.component3()};
		}else
			results = new String[]{};
		
		return results;
	}
	
	public BigInteger newID(Wallet wallet) throws Exception {
		if (contract == null) {
			contract = Issuer.load(contractAddress,web3j, wallet.getCredentials(), gasProvider);
		}
		return contract.newID(wallet.getAddress()).send();
	}

}
