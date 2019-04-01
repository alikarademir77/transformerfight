package com.aequilibrium.transformerscoring.model.rest;

public enum TransformerType {
	Autobot('A'), Decepticon('D');
	
	private char code;
	TransformerType(char code){
		this.code = code;
	}
	
	public char getCode() {
		return this.code;
	}
}
