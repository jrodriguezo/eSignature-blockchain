package es.upm.dit.service;

import java.util.List;

import eu.europa.esig.dss.enumerations.DigestAlgorithm;
import eu.europa.esig.dss.model.SignatureValue;
import eu.europa.esig.dss.model.ToBeSigned;
import eu.europa.esig.dss.token.DSSPrivateKeyEntry;
import eu.europa.esig.dss.token.MSCAPISignatureToken;
import eu.europa.esig.dss.utils.Utils;

public class SignWithMSCAPI {
	
	public void sign(){
		
		try (MSCAPISignatureToken token = new MSCAPISignatureToken()) {

			List<DSSPrivateKeyEntry> keys = token.getKeys();
			for (DSSPrivateKeyEntry entry : keys) {
				System.out.println(entry.getCertificate().getCertificate());
			}

			ToBeSigned toBeSigned = new ToBeSigned("Hello world".getBytes());
			SignatureValue signatureValue = token.sign(toBeSigned, DigestAlgorithm.SHA256, keys.get(0));

			System.out.println("Signature value : " + Utils.toBase64(signatureValue.getValue()));
		}
	}

}

