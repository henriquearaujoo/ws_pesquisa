package br.com.icon.ws_pesquisa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Henrique on 29/06/2016.
 */

@Entity
@Table(name="tb_pesquisa01")
public class CE_Pesquisa01 {

    @Id
    @Column
    private Integer idpesquisa01;

    @Column
    private String nomepesquisa;

    @Column
    private Date dtinicio;

    @Column
    private Date dtfim;

    public Integer getIdpesquisa01() {
        return idpesquisa01;
    }

    public void setIdpesquisa01(Integer idtpesquisa01) {
        this.idpesquisa01 = idtpesquisa01;
    }

    public String getNomepesquisa() {
        return nomepesquisa;
    }

    public void setNomepesquisa(String nomepesquisa) {
        this.nomepesquisa = nomepesquisa;
    }

    public Date getDtinicio() {
        return dtinicio;
    }

    public void setDtinicio(Date dtinicio) {
        this.dtinicio = dtinicio;
    }

    public Date getDtfim() {
        return dtfim;
    }

    public void setDtfim(Date dtfim) {
        this.dtfim = dtfim;
    }
}
