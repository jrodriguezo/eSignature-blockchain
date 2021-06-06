// SPDX-License-Identifier: GPL-3.0
pragma solidity >=0.4.16 <0.9.0;

contract Issuer{

    // The owner of the contract.
    address public owner;
    // Institution or company dedicated to the issuance of credentials (e.g police department).
    string public entity;
    
    constructor(string memory _entity) {
        owner = msg.sender;
        entity = _entity;
    }
    
    
    // The user with a credential can authorize others to verify the credential.
    struct Authorized{
        address user;
    }
    
    // This is the credential sent to user.
    struct Credential{
        Authorized[] users;
        string name;
        string surname;
        string gender;
    }
    
    // Given a identifier return the Credential associated.
    uint identifier;
    mapping(uint => Credential) registers;
    mapping(uint => mapping(address => Authorized)) authRegisters;
    
    // Create a new credential with an empty array of authorized users.
    function newCredential(string memory _name, string memory _surname, string memory _gender) public{ 
        require(bytes(_name).length > 0);
        require(bytes(_surname).length > 0);
        identifier = newID(msg.sender);
        registers[identifier].name = _name;
        registers[identifier].surname = _surname;
        registers[identifier].gender = _gender;
     
    }
    
    // Authorize third parties to use your credential.
    function authorize(address _user) public {
        require(bytes(abi.encodePacked(_user)).length > 0);
        identifier = newID(msg.sender);
        authRegisters[identifier][_user] = Authorized(_user);
    }
    
    // Revoke your credential.
    function deleteCredential(uint _identifier) public{
        // Just can deleted her/his identifier who called newCredential method.
        require(_identifier == newID(msg.sender));
        delete registers[_identifier];
    }
    
    // Verify credentials given.
    function verify(uint _identifier)  public view returns(string memory _name, string memory _surname,  string memory _gender){
        if(_identifier == newID(msg.sender) || owner == msg.sender || authRegisters[identifier][msg.sender].user != address(0)){
            _name = registers[_identifier].name;
            _surname = registers[_identifier].surname;
            _gender = registers[_identifier].gender;
        }
        
    }
    
    // Create a new identifier.
    function newID(address _address) public view returns(uint) {
        require(_address == msg.sender);
        return uint(keccak256(abi.encodePacked(_address, owner)));

    }
    
}