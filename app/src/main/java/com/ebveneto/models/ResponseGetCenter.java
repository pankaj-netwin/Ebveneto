package com.ebveneto.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseGetCenter {

	@SerializedName("centre")
	private List<CentreItem> centre;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public void setCentre(List<CentreItem> centre){
		this.centre = centre;
	}

	public List<CentreItem> getCentre(){
		return centre;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ResponseGetCenter{" + 
			"centre = '" + centre + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}