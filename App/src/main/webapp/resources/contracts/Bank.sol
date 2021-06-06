// SPDX-License-Identifier: GPL-3.0
pragma solidity >=0.4.16 <0.9.0;

contract Bank{

    // The owner of the contract.
    address public owner;
    // Customer number that opens an online account.
    uint public counter;
    
    constructor() {
        owner = msg.sender;
        counter = 0;
    }
    
    // This is the credential sent to user
    struct Customer{
        uint number;
    }
    
    // Given a identifier return the Customer associated.
    uint identifier;
    mapping(uint => Customer) registers;
    
    // A customer number is assigned and incremented for future calls.
    function newCustomer() public{ 
        identifier = newID(msg.sender);
        registers[identifier].number = counter++;
    }
    
    // Revoke your credential
    function deleteCustomer(uint _identifier) public{
        // Just can deleted her/his identifier who called newCredential method
        require(_identifier == newID(msg.sender));
        delete registers[_identifier];
    }
    
    // Verify credentials given.
    function verify(uint _identifier)  public view returns(uint _number){
        require(_identifier == newID(msg.sender) || owner == msg.sender);
        _number = registers[_identifier].number;
    }
    
    // Create a new identifier.
    function newID(address _address) public view returns(uint) {
        require(_address == msg.sender);
        return uint(keccak256(abi.encodePacked(_address, owner)));

    }

}