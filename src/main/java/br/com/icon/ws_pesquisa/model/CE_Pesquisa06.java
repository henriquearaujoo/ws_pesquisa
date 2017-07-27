package br.com.icon.ws_pesquisa.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Henrique on 07/07/2016.
 */
@Entity
@Table(name="tb_pesquisa06")
public class CE_Pesquisa06 {

    @Id
    @Column
    private Integer idpesquisa06;

    @Column
    private Integer idcliente;

    @Column
    private Integer qtamostra;

    @Column
    private Date dtiniciopesquisa;

    @Column
    private Date dtfimpesquisa;

    @Column
    private Integer qtamostraporpesquisador;
    
    @Column
    private String nome;

    @Transient
    private CE_Pesquisa01 pesquisa01;

    public Integer getQtamostraporpesquisador() {
        return qtamostraporpesquisador;
    }

    public void setQtamostraporpesquisador(Integer qtamostraporpesquisador) {
        this.qtamostraporpesquisador = qtamostraporpesquisador;
    }

    public Integer getIdpesquisa06() {
        return idpesquisa06;
    }

    public void setIdpesquisa06(Integer idtpesquisa06) {
        this.idpesquisa06 = idtpesquisa06;
    }

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public Integer getQtamostra() {
        return qtamostra;
    }

    public void setQtamostra(Integer qtamostra) {
        this.qtamostra = qtamostra;
    }

    public Date getDtiniciopesquisa() {
        return dtiniciopesquisa;
    }

    public void setDtiniciopesquisa(Date dtiniciopesquisa) {
        this.dtiniciopesquisa = dtiniciopesquisa;
    }

    public Date getDtfimpesquisa() {
        return dtfimpesquisa;
    }

    public void setDtfimpesquisa(Date dtfimpesquisa) {
        this.dtfimpesquisa = dtfimpesquisa;
    }

    public CE_Pesquisa01 getPesquisa01() {
        return pesquisa01;
    }

    public void setPesquisa01(CE_Pesquisa01 pesquisa01) {
        this.pesquisa01 = pesquisa01;
    }

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
    
}
