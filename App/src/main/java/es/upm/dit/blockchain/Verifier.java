package es.upm.dit.blockchain;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
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
public class Verifier extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161053e38038061053e8339818101604052602081101561003357600080fd5b810190808051604051939291908464010000000082111561005357600080fd5b90830190602082018581111561006857600080fd5b825164010000000081118282018810171561008257600080fd5b82525081516020918201929091019080838360005b838110156100af578181015183820152602001610097565b50505050905090810190601f1680156100dc5780820380516001836020036101000a031916815260200191505b506040525050600080546001600160a01b03191633179055508051610108906001906020840190610118565b5050641cbe8d10006002556101ab565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061015957805160ff1916838001178555610186565b82800160010185558215610186579182015b8281111561018657825182559160200191906001019061016b565b50610192929150610196565b5090565b5b808211156101925760008155600101610197565b610384806101ba6000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c80638da5cb5b1161005b5780638da5cb5b14610136578063a0fb14971461015a578063a72c7cef14610179578063f3751c7d146101815761007d565b806361bc221a146100825780636cdbbc661461009c5780638753367f14610119575b600080fd5b61008a6101a7565b60408051918252519081900360200190f35b6100a46101ad565b6040805160208082528351818301528351919283929083019185019080838360005b838110156100de5781810151838201526020016100c6565b50505050905090810190601f16801561010b5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61008a6004803603602081101561012f57600080fd5b503561023a565b61013e610278565b604080516001600160a01b039092168252519081900360200190f35b6101776004803603602081101561017057600080fd5b5035610287565b005b6101776102c1565b61008a6004803603602081101561019757600080fd5b50356001600160a01b03166102ec565b60025481565b60018054604080516020600284861615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156102325780601f1061020757610100808354040283529160200191610232565b820191906000526020600020905b81548152906001019060200180831161021557829003601f168201915b505050505081565b6000610245336102ec565b82148061025c57506000546001600160a01b031633145b61026557600080fd5b5060009081526004602052604090205490565b6000546001600160a01b031681565b610290336102ec565b8114806102a757506000546001600160a01b031633145b6102b057600080fd5b600090815260046020526040812055565b6102ca336102ec565b6003819055600280546001810190915560009182526004602052604090912055565b60006001600160a01b038216331461030357600080fd5b50600054604080516bffffffffffffffffffffffff19606085811b821660208085019190915294901b166034820152815160288183030181526048909101909152805191012091905056fea2646970667358221220746d6d0c40f9bf7860189712bd528e585b4255d901157cabe3ab4d8d4cfa0e1b64736f6c63430007010033";

    public static final String FUNC_COUNTER = "counter";

    public static final String FUNC_DELETEDOCUMENT = "deleteDocument";

    public static final String FUNC_ENTITY = "entity";

    public static final String FUNC_NEWDOCUMENT = "newDocument";

    public static final String FUNC_NEWID = "newID";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_VERIFY = "verify";

    @Deprecated
    protected Verifier(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Verifier(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Verifier(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Verifier(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<BigInteger> counter() {
        final Function function = new Function(FUNC_COUNTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> deleteDocument(BigInteger _identifier) {
        final Function function = new Function(
                FUNC_DELETEDOCUMENT, 
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

    public RemoteFunctionCall<TransactionReceipt> newDocument() {
        final Function function = new Function(
                FUNC_NEWDOCUMENT, 
                Arrays.<Type>asList(), 
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

    public RemoteFunctionCall<BigInteger> verify(BigInteger _identifier) {
        final Function function = new Function(FUNC_VERIFY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_identifier)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static Verifier load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Verifier(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Verifier load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Verifier(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Verifier load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Verifier(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Verifier load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Verifier(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Verifier> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _entity) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_entity)));
        return deployRemoteCall(Verifier.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Verifier> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _entity) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_entity)));
        return deployRemoteCall(Verifier.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Verifier> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _entity) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_entity)));
        return deployRemoteCall(Verifier.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Verifier> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _entity) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_entity)));
        return deployRemoteCall(Verifier.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
