// SPDX-License-Identifier: GPL-3.0
pragma solidity >=0.4.16 <0.9.0;

import "@openzeppelin/contracts/utils/math/SafeMath.sol";

contract Verifier{

    using SafeMath for uint;

    // The owner of the contract.
    address public owner;
    // Institution or company dedicated to the verification of credentials (although they are also issued, for example, by the Social Security General Treasury Administration).
    string public entity;
    // SS base number.
    uint public counter;
    
    constructor(string memory _entity) {
        owner = msg.sender;
        entity = _entity;
        counter = 123456000000;
    }
    
    // This is the credential sent to user.
    struct Document{
        uint ss;
    }
    
    // Given a identifier return the Document associated.
    uint identifier;
    mapping(uint => Document) registers;
    
    // A social security number is assigned and incremented for future calls.
    function newDocument() public{ 
        identifier = newID(msg.sender);
        registers[identifier].ss = counter.add(1);
    }
    
    // Revoke your credential.
    function deleteDocument(uint _identifier) public{
        // Just can deleted her/his identifier who called newCredential method.
        require(_identifier == newID(msg.sender));
        delete registers[_identifier];
    }

    // Verify credentials given.
    function verify(uint _identifier)  public view returns(uint _ss){
        require(_identifier == newID(msg.sender) || owner == msg.sender);
        _ss = registers[_identifier].ss;
    }
    
    // Create a new identifier.
    function newID(address _address) public view returns(uint) {
        require(_address == msg.sender);
        return uint(keccak256(abi.encodePacked(_address, owner)));

    }
    
}