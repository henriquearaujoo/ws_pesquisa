package br.com.icon.ws_pesquisa.util;

public class Filtro {

	private Long id;

	private String nome;

	private String descricao;
	
	private String codigo;
	
	private String camara;
	
	private String curral;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCamara() {
		return camara;
	}

	public void setCamara(String camara) {
		this.camara = camara;
	}

	public String getCurral() {
		return curral;
	}

	public void setCurral(String curral) {
		this.curral = curral;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
