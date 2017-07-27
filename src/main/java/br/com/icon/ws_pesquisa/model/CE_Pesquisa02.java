package br.com.icon.ws_pesquisa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Henrique on 06/07/2016.
 */
@Entity
@Table(name="tb_pesquisa02")
public class CE_Pesquisa02{
    @Id
    @Column
    private Integer idpesquisa02;
    @Column
    private String descricao;
    @Column
    private String tipodado;

    public Integer getIdpesquisa02() {
        return idpesquisa02;
    }

    public void setIdpesquisa02(Integer idpesquisa02) {
        this.idpesquisa02 = idpesquisa02;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipodado() {
        return tipodado;
    }

    public void setTipodado(String tipodado) {
        this.tipodado = tipodado;
    }
}
