package com.api.utils;

import java.util.Map;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.LogicalResponse;

public class VaultDBConfig {

	public static VaultConfig vaultConfig;
	public static Vault vault;

	static {
		try {
			vaultConfig = new VaultConfig().address(System.getenv("VAULT_SERVER")).token(System.getenv("VAULT_TOKEN")).build();

		} catch (VaultException e) {
			e.printStackTrace();
		}

		vault = new Vault(vaultConfig);
	}

	private VaultDBConfig() {
		// private constructor for a utility class
	}

	public static String getSecret(String key) {

		LogicalResponse response = null;
		try {
			response = vault.logical().read("secret/phoenix/qa/databsse");

			//negative scenario: exception occured
		} catch (VaultException e) {
			e.printStackTrace();
			return null; //if anything goes wrong return null
		}

		//positive scenario: that we got response from vault
		Map<String, String> dataMap = response.getData();

		String secretValue = dataMap.get(key);

		return secretValue;

	}

}
