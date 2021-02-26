package com.ebveneto.models;


import com.google.gson.annotations.SerializedName;


public class BodyGetCenter {

	@SerializedName("api_key")
	private String apiKey;

	public BodyGetCenter(String apiKey) {
		this.apiKey = apiKey;
	}

	public void setApiKey(String apiKey){
		this.apiKey = apiKey;
	}

	public String getApiKey(){
		return apiKey;
	}

	@Override
 	public String toString(){
		return 
			"BodyGetCenter{" + 
			"api_key = '" + apiKey + '\'' + 
			"}";
		}
}