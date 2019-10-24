package com.kgcorner.topspin.service;

public interface OAuthConfigProvider {
	
	/***
	 * returns App key if the provider is using OAuth
	 * @return
	 */
	String getAppKey();
	
	/***
	 * returns Secret key if the provider is using OAuth
	 * @return
	 */
	String getSecretKey();
	
	/***
	 * returns Access token exchange Url if the provider is using OAuth
	 * This can be used when auth code is received from client in
	 * order to fetch Access token 
	 * @return
	 */
	public String getAccessTokenExchangeUrl(String redirectUri, String authCode);
	
	/***
	 * returns URL to validate access token
	 * this url can be used if access token is received from client
	 * and server needs to validate it 
	 * @return
	 */
	String getAccessTokenValidationUrl(String accessToken);
	
	/***
	 * return url for fetching userInfo
	 * @return
	 */
	String getUserInfoUrl(String permissions, String accessToken);
}
