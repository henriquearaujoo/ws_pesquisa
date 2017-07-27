package br.com.icon.ws_pesquisa.model;

import javax.persistence.*;

/**
 * Created by Henrique on 06/07/2016.
 */
@Entity
@Table(name="tb_pesquisa03")
public class CE_Pesquisa03 {
    @Id
    @Column
    private Integer idpesquisa03;
    @Column
    private String descricao;
    @Column
    private String retornopesquisa;
    @Column
    private Integer ordem;
    @ManyToOne
    @JoinColumn(name = "idpesquisa02")
    private CE_Pesquisa02 pesquisa02;
    @Column
    private Integer campotipooutros;

    public Integer getCampotipooutros() {
		return campotipooutros;
	}

	public void setCampotipooutros(Integer campotipooutros) {
		this.campotipooutros = campotipooutros;
	}

	public Integer getIdpesquisa03() {
        return idpesquisa03;
    }

    public void setIdpesquisa03(Integer idpesquisa03) {
        this.idpesquisa03 = idpesquisa03;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getRetornopesquisa() {
        return retornopesquisa;
    }

    public void setRetornopesquisa(String retornopesquisa) {
        this.retornopesquisa = retornopesquisa;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public CE_Pesquisa02 getPesquisa02() {
        return pesquisa02;
    }

    public void setPesquisa02(CE_Pesquisa02 pesquisa02) {
        this.pesquisa02 = pesquisa02;
    }
}
