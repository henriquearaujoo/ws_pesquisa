package br.com.icon.ws_pesquisa.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Henrique on 02/07/2016.
 */
@Entity
@Table(name="tb_pesquisa04")
public class CE_Pesquisa04 {

    @Id
    @Column
    private Integer idpesquisa04;

    @Column
    private Integer idpesquisa01;

    @ManyToOne
    @JoinColumn(name = "idpesquisa02")
    private CE_Pesquisa02 pesquisa02;
    
    @ManyToOne
    @JoinColumn(name = "idpesquisa02outros")
    private CE_Pesquisa02 pesquisa02outros;

    @Column
    private Integer numeropesquisa;

    @Column
    private String descricao;

    @Column
    private Integer idpesquisa04pai;

    @Column
    private Integer numeroperguntapai;

    @Column
    private Integer tamanhoresposta;

    @Column
    private Integer qtdecimais;

    @Column
    private String vldefault;

    @Column
    private Integer qtrespostas;

    @Column
    private Date dtinicio;

    @Column
    private Date dtfim;

    @Column
    private Integer ordempergunta;

    @Column
    private Integer obrigatoria;
    
    @Column
    private Integer qtminrespostas;
    
    @Column
    private Integer qtminobrigatoria;

    @Transient
    private CE_Pesquisa03 pesquisa03;

    public Integer getObrigatoria() {
        return obrigatoria;
    }

    public void setObrigatoria(Integer obrigatoria) {
        this.obrigatoria = obrigatoria;
    }

    public CE_Pesquisa03 getPesquisa03() {
        return pesquisa03;
    }

    public void setPesquisa03(CE_Pesquisa03 pesquisa03) {
        this.pesquisa03 = pesquisa03;
    }

    public CE_Pesquisa02 getPesquisa02() {
        return pesquisa02;
    }

    public void setPesquisa02(CE_Pesquisa02 pesquisa02) {
        this.pesquisa02 = pesquisa02;
    }

    public Integer getIdpesquisa04() {
        return idpesquisa04;
    }

    public void setIdpesquisa04(Integer idtpesquisa04) {
        this.idpesquisa04 = idtpesquisa04;
    }

    public Integer getIdpesquisa01() {
        return idpesquisa01;
    }

    public void setIdpesquisa01(Integer idtpesquisa01) {
        this.idpesquisa01 = idtpesquisa01;
    }

    public Integer getNumeropesquisa() {
        return numeropesquisa;
    }

    public void setNumeropesquisa(Integer numeropesquisa) {
        this.numeropesquisa = numeropesquisa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getIdpesquisa04pai() {
        return idpesquisa04pai;
    }

    public void setIdpesquisa04pai(Integer idpesquisa04pai) {
        this.idpesquisa04pai = idpesquisa04pai;
    }

    public Integer getNumeroperguntapai() {
        return numeroperguntapai;
    }

    public void setNumeroperguntapai(Integer numeroperguntapai) {
        this.numeroperguntapai = numeroperguntapai;
    }

    public Integer getTamanhoresposta() {
        return tamanhoresposta;
    }

    public void setTamanhoresposta(Integer tamanhoresposta) {
        this.tamanhoresposta = tamanhoresposta;
    }

    public Integer getQtdecimais() {
        return qtdecimais;
    }

    public void setQtdecimais(Integer qtdecimais) {
        this.qtdecimais = qtdecimais;
    }

    public String getVldefault() {
        return vldefault;
    }

    public void setVldefault(String vldefault) {
        this.vldefault = vldefault;
    }

    public Integer getQtrespostas() {
        return qtrespostas;
    }

    public void setQtrespostas(Integer qtrespostas) {
        this.qtrespostas = qtrespostas;
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

    public Integer getOrdempergunta() {
        return ordempergunta;
    }

    public void setOrdempergunta(Integer ordempergunta) {
        this.ordempergunta = ordempergunta;
    }

	public CE_Pesquisa02 getPesquisa02outros() {
		return pesquisa02outros;
	}

	public void setPesquisa02outros(CE_Pesquisa02 pesquisa02outros) {
		this.pesquisa02outros = pesquisa02outros;
	}

	public Integer getQtminrespostas() {
		return qtminrespostas;
	}

	public void setQtminrespostas(Integer qtminrespostas) {
		this.qtminrespostas = qtminrespostas;
	}

	public Integer getQtminobrigatoria() {
		return qtminobrigatoria;
	}

	public void setQtminobrigatoria(Integer qtminobrigatoria) {
		this.qtminobrigatoria = qtminobrigatoria;
	}
    
}
