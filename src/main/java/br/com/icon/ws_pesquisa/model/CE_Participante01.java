package br.com.icon.ws_pesquisa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_participante01")
public class CE_Participante01 {

	@Id
    @Column
    private Integer idparticipante01;
	
	@Column
    private Integer idcliente01;
    
	@Column
    private String nome;
    
	@Column
    private String email;
    
	@Column
    private String telefone;
    
	@Column
    private String infoadicional;
	
	@Column
	private String empresa;
    
	@Column
    private Integer qtqrcode;

	public Integer getIdparticipante01() {
		return idparticipante01;
	}

	public void setIdparticipante01(Integer idparticipante01) {
		this.idparticipante01 = idparticipante01;
	}

	public Integer getIdcliente01() {
		return idcliente01;
	}

	public void setIdcliente01(Integer idcliente01) {
		this.idcliente01 = idcliente01;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getInfoadicional() {
		return infoadicional;
	}

	public void setInfoadicional(String infoadicional) {
		this.infoadicional = infoadicional;
	}

	public Integer getQtqrcode() {
		return qtqrcode;
	}

	public void setQtqrcode(Integer qtqrcode) {
		this.qtqrcode = qtqrcode;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	
}
