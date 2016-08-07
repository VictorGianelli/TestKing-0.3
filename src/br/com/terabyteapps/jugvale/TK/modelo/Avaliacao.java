package br.com.terabyteapps.jugvale.TK.modelo;

import java.io.Serializable;

import android.util.Log;

public class Avaliacao implements Serializable {
	
	
	private Long id;
	
	private int nroOpc;
	private int res;
	private String questao;
	private String op;
	private String correto;
	
	public void setTeste(Long id, int nroOpc, int res, String questao, String op,String correto) {
		
		this.id = id;
		this.nroOpc = nroOpc;
		this.res = res;
		this.questao = questao;
		this.op = op;
		this.correto = correto;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getNroOpc() {
		return nroOpc;
	}
	public void setNroOpc(int nroOpc) {
		this.nroOpc = nroOpc;
	}
	public int getRes() {
		return res;
	}
	public void setRes(int res) {
		this.res = res;
	}
	public String getQuestao() {
		return questao;
	}
	public void setQuestao(String questao) {
		this.questao = questao;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getCorreto() {
		return correto;
	}
	public void setCorreto(String correto) {
		this.correto = correto;
	}
}
