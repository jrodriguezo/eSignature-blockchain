package es.upm.dit.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.List;

import es.upm.dit.model.ParametersSelectedToSign;
import eu.europa.esig.dss.cades.CAdESSignatureParameters;
import eu.europa.esig.dss.cades.signature.CAdESService;
import eu.europa.esig.dss.enumerations.DigestAlgorithm;
import eu.europa.esig.dss.enumerations.SignatureLevel;
import eu.europa.esig.dss.enumerations.SignaturePackaging;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.InMemoryDocument;
import eu.europa.esig.dss.model.SignatureValue;
import eu.europa.esig.dss.model.ToBeSigned;
import eu.europa.esig.dss.token.DSSPrivateKeyEntry;
import eu.europa.esig.dss.token.MSCAPISignatureToken;
import eu.europa.esig.dss.token.Pkcs12SignatureToken;
import eu.europa.esig.dss.token.SignatureTokenConnection;
import eu.europa.esig.dss.validation.CommonCertificateVerifier;

public class SignWithCAdES {
	
	public void sign(ParametersSelectedToSign selected) throws IOException {
		// Preparing parameters for the CAdES signature
		CAdESSignatureParameters parameters = new CAdESSignatureParameters();
		// We choose the level of the signature (-B, -T, -LT, -LTA).
		parameters.setSignatureLevel(SignatureLevel.valueOf(selected.getLevel()));
		// We choose the type of the signature packaging (ENVELOPED, ENVELOPING, DETACHED).
		parameters.setSignaturePackaging(SignaturePackaging.valueOf(selected.getPackaging()));
		// We set the digest algorithm to use with the signature algorithm. You must use the
		// same parameter when you invoke the method sign on the token. The default value is SHA256
		parameters.setDigestAlgorithm(DigestAlgorithm.valueOf(selected.getDigest_algorithm()));
		
		DSSPrivateKeyEntry privateKey = null;
		SignatureTokenConnection signingToken = null;
		
		if(selected.getDevice().equals("mscapi")) {
			// This is to sign with a spain ID or whatever with a device in Microsoft
			signingToken = new MSCAPISignatureToken();
			List<DSSPrivateKeyEntry> keys = signingToken.getKeys();
			privateKey = keys.get(0);
		}else {
			// This is to sign with Pkcs12
			signingToken= new Pkcs12SignatureToken(new BufferedInputStream(selected.getKey().getInputStream()), new KeyStore.PasswordProtection(selected.getPassword().toCharArray()));
			List<DSSPrivateKeyEntry> keys = signingToken.getKeys();
			for (DSSPrivateKeyEntry entry : keys) {
			    privateKey = entry;
			    break;
			}
		}
		
		// We set the signing certificate
		parameters.setSigningCertificate(privateKey.getCertificate());
		// We set the certificate chain
		parameters.setCertificateChain(privateKey.getCertificateChain());

		// Create common certificate verifier
		CommonCertificateVerifier commonCertificateVerifier = new CommonCertificateVerifier();

		// Create XAdES service for signature
		CAdESService  service = new CAdESService(commonCertificateVerifier);
		
		//It is necessary for using with XAdES_BASELINE_T
		//service.setTspSource(getOnlineTSPSource());
		//String tspServer = "http://dss.nowina.lu/pki-factory/tsa/good-tsa";
		//OnlineTSPSource onlineTSPSource = new OnlineTSPSource(tspServer);
		//onlineTSPSource.setDataLoader(new TimestampDataLoader()); // uses the specific content-type
		//service.setTspSource(onlineTSPSource);
		
		DSSDocument toSignDocument = new InMemoryDocument(new BufferedInputStream(selected.getFile().getInputStream()));
		// Get the SignedInfo XML segment that need to be signed.
		ToBeSigned dataToSign = service.getDataToSign(toSignDocument, parameters);

		// This function obtains the signature value for signed information using the
		// private key and specified algorithm
		SignatureValue signatureValue = signingToken.sign(dataToSign, parameters.getDigestAlgorithm(), privateKey);

		// We invoke the service to sign the document with the signature value obtained in
		// the previous step.
		DSSDocument signedDocument = service.signDocument(toSignDocument, parameters, signatureValue);
		
        signedDocument.save(System.getProperty("user.home")+"/Downloads"+"/signedFile");
		String nSigned = signedDocument.getName();
		signingToken.close();
	}

}
