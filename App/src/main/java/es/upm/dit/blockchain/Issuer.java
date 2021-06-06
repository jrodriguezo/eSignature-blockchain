package es.upm.dit.blockchain;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.6.4.
 */
@SuppressWarnings("rawtypes")
public class Issuer extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610c8e380380610c8e8339818101604052602081101561003357600080fd5b810190808051604051939291908464010000000082111561005357600080fd5b90830190602082018581111561006857600080fd5b825164010000000081118282018810171561008257600080fd5b82525081516020918201929091019080838360005b838110156100af578181015183820152602001610097565b50505050905090810190601f1680156100dc5780820380516001836020036101000a031916815260200191505b506040525050600080546001600160a01b0319163317905550805161010890600190602084019061010f565b50506101a2565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061015057805160ff191683800117855561017d565b8280016001018555821561017d579182015b8281111561017d578251825591602001919060010190610162565b5061018992915061018d565b5090565b5b80821115610189576000815560010161018e565b610add806101b16000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c80638753367f1161005b5780638753367f146102d25780638da5cb5b14610433578063b6a5d7de14610457578063f3751c7d1461047d5761007d565b80630134e706146100825780632f8c2f6c146100a15780636cdbbc6614610255575b600080fd5b61009f6004803603602081101561009857600080fd5b50356104b5565b005b61009f600480360360608110156100b757600080fd5b8101906020810181356401000000008111156100d257600080fd5b8201836020820111156100e457600080fd5b8035906020019184600183028401116401000000008311171561010657600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929594936020810193503591505064010000000081111561015957600080fd5b82018360208201111561016b57600080fd5b8035906020019184600183028401116401000000008311171561018d57600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092959493602081019350359150506401000000008111156101e057600080fd5b8201836020820111156101f257600080fd5b8035906020019184600183028401116401000000008311171561021457600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610511945050505050565b61025d6105b8565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561029757818101518382015260200161027f565b50505050905090810190601f1680156102c45780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6102ef600480360360208110156102e857600080fd5b5035610645565b60405180806020018060200180602001848103845287818151815260200191508051906020019080838360005b8381101561033457818101518382015260200161031c565b50505050905090810190601f1680156103615780820380516001836020036101000a031916815260200191505b50848103835286518152865160209182019188019080838360005b8381101561039457818101518382015260200161037c565b50505050905090810190601f1680156103c15780820380516001836020036101000a031916815260200191505b50848103825285518152855160209182019187019080838360005b838110156103f45781810151838201526020016103dc565b50505050905090810190601f1680156104215780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b61043b610894565b604080516001600160a01b039092168252519081900360200190f35b61009f6004803603602081101561046d57600080fd5b50356001600160a01b03166108a3565b6104a36004803603602081101561049357600080fd5b50356001600160a01b031661092e565b60408051918252519081900360200190f35b6104be3361092e565b81146104c957600080fd5b6000818152600360205260408120906104e28282610990565b6104f06001830160006109b1565b6104fe6002830160006109b1565b61050c6003830160006109b1565b505050565b600083511161051f57600080fd5b600082511161052d57600080fd5b6105363361092e565b600281905560009081526003602090815260409091208451610560926001909201918601906109f5565b5060028054600090815260036020908152604090912084516105899391909101918501906109f5565b50600254600090815260036020818152604090922083516105b2939190920191908401906109f5565b50505050565b60018054604080516020600284861615610100026000190190941693909304601f8101849004840282018401909252818152929183018282801561063d5780601f106106125761010080835404028352916020019161063d565b820191906000526020600020905b81548152906001019060200180831161062057829003601f168201915b505050505081565b60608060606106533361092e565b84148061066a57506000546001600160a01b031633145b80610699575060025460009081526004602090815260408083203384529091529020546001600160a01b031615155b1561088d57600360008581526020019081526020016000206001018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156107485780601f1061071d57610100808354040283529160200191610748565b820191906000526020600020905b81548152906001019060200180831161072b57829003601f168201915b5050506000878152600360209081526040918290206002908101805484516001821615610100026000190190911692909204601f81018490048402830184019094528382529598509493509091508301828280156107e75780601f106107bc576101008083540402835291602001916107e7565b820191906000526020600020905b8154815290600101906020018083116107ca57829003601f168201915b5050506000878152600360208181526040928390209091018054835160026001831615610100026000190190921691909104601f810184900484028201840190945283815295975093509091508301828280156108855780601f1061085a57610100808354040283529160200191610885565b820191906000526020600020905b81548152906001019060200180831161086857829003601f168201915b505050505090505b9193909250565b6000546001600160a01b031681565b604080516bffffffffffffffffffffffff19606084901b166020820152815180820360140181526034909101909152516108dc57600080fd5b6108e53361092e565b600281905560408051602080820183526001600160a01b0394851680835260009485526004825283852090855290529120905181546001600160a01b0319169216919091179055565b60006001600160a01b038216331461094557600080fd5b50600054604080516bffffffffffffffffffffffff19606085811b821660208085019190915294901b1660348201528151602881830301815260489091019091528051910120919050565b50805460008255906000526020600020908101906109ae9190610a73565b50565b50805460018160011615610100020316600290046000825580601f106109d757506109ae565b601f0160209004906000526020600020908101906109ae9190610a92565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610a3657805160ff1916838001178555610a63565b82800160010185558215610a63579182015b82811115610a63578251825591602001919060010190610a48565b50610a6f929150610a92565b5090565b5b80821115610a6f5780546001600160a01b0319168155600101610a74565b5b80821115610a6f5760008155600101610a9356fea2646970667358221220d15028552b0d7a92ce789edbca81256cb43025e209f039ee72af51c695d8edb864736f6c63430007010033";

    public static final String FUNC_AUTHORIZE = "authorize";

    public static final String FUNC_DELETECREDENTIAL = "deleteCredential";

    public static final String FUNC_ENTITY = "entity";

    public static final String FUNC_NEWCREDENTIAL = "newCredential";

    public static final String FUNC_NEWID = "newID";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_VERIFY = "verify";

    @Deprecated
    protected Issuer(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Issuer(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Issuer(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Issuer(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> authorize(String _user) {
        final Function function = new Function(
                FUNC_AUTHORIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _user)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> deleteCredential(BigInteger _identifier) {
        final Function function = new Function(
                FUNC_DELETECREDENTIAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_identifier)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> entity() {
        final Function function = new Function(FUNC_ENTITY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> newCredential(String _name, String _surname, String _gender) {
        final Function function = new Function(
                FUNC_NEWCREDENTIAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Utf8String(_surname), 
                new org.web3j.abi.datatypes.Utf8String(_gender)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> newID(String _address) {
        final Function function = new Function(FUNC_NEWID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple3<String, String, String>> verify(BigInteger _identifier) {
        final Function function = new Function(FUNC_VERIFY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_identifier)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple3<String, String, String>>(function,
                new Callable<Tuple3<String, String, String>>() {
                    @Override
                    public Tuple3<String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue());
                    }
                });
    }

    @Deprecated
    public static Issuer load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Issuer(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Issuer load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Issuer(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Issuer load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Issuer(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Issuer load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Issuer(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Issuer> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _entity) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_entity)));
        return deployRemoteCall(Issuer.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Issuer> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _entity) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_entity)));
        return deployRemoteCall(Issuer.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Issuer> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _entity) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_entity)));
        return deployRemoteCall(Issuer.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Issuer> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _entity) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_entity)));
        return deployRemoteCall(Issuer.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
