package com.pyramix.security.config;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "rsa")
@ConstructorBinding
// ref: https://www.baeldung.com/configuration-properties-in-spring-boot
public class RsaKeyProperties {

	private final RSAPublicKey publicKey;
	
	private final RSAPrivateKey privateKey;

	public RsaKeyProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}

	public RSAPublicKey getPublicKey() {
		return this.publicKey;
	}

	public RSAPrivateKey getPrivateKey() {
		return this.privateKey;
	}	
	
}
