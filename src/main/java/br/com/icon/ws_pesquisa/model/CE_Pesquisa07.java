package br.com.icon.ws_pesquisa.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Henrique on 18/07/2016.
 */
@Entity
@Table(name="tb_pesquisa07")
public class CE_Pesquisa07 {

    @Id
    @Column
    private Integer idpesquisa07;

    @Column
    private BigDecimal vlresposta;

    @Column
    private String txresposta;
    
    @Column
    private BigDecimal vlrespostaoutros;

    @Column
    private String txrespostaoutros;

    @Column
    private String chavepesquisa;

    @ManyToOne
    @JoinColumn(name = "idpesquisa06")
    private CE_Pesquisa06 pesquisa06;

    @ManyToOne
    @JoinColumn(name = "idpesquisa04")
    private CE_Pesquisa04 pesquisa04;

    @ManyToOne
    @JoinColumn(name = "idcliente")
    private CE_Cliente01 cliente;

    @ManyToOne
    @JoinColumn(name = "idpesquisador")
    private CE_Pesquisa08 pesquisa08;
    
    @Column
    private Integer idpesquisa03;

    public CE_Cliente01 getCliente() {
        return cliente;
    }

    public void setCliente(CE_Cliente01 cliente) {
        this.cliente = cliente;
    }

    public CE_Pesquisa08 getPesquisa08() {
        return pesquisa08;
    }

    public void setPesquisa08(CE_Pesquisa08 pesquisa08) {
        this.pesquisa08 = pesquisa08;
    }

    public Integer getIdpesquisa07() {
        return idpesquisa07;
    }

    public void setIdpesquisa07(Integer idpesquisa07) {
        this.idpesquisa07 = idpesquisa07;
    }

    public CE_Pesquisa06 getPesquisa06() {
        return pesquisa06;
    }

    public void setPesquisa06(CE_Pesquisa06 pesquisa06) {
        this.pesquisa06 = pesquisa06;
    }

    public CE_Pesquisa04 getPesquisa04() {
        return pesquisa04;
    }

    public void setPesquisa04(CE_Pesquisa04 pesquisa04) {
        this.pesquisa04 = pesquisa04;
    }

    public BigDecimal getVlresposta() {
        return vlresposta;
    }

    public void setVlresposta(BigDecimal vlresposta) {
        this.vlresposta = vlresposta;
    }

    public String getTxresposta() {
        return txresposta;
    }

    public void setTxresposta(String txresposta) {
        this.txresposta = txresposta;
    }

    public String getChavepesquisa() {
        return chavepesquisa;
    }

    public void setChavepesquisa(String chavepesquisa) {
        this.chavepesquisa = chavepesquisa;
    }

	public BigDecimal getVlrespostaoutros() {
		return vlrespostaoutros;
	}

	public void setVlrespostaoutros(BigDecimal vlrespostaoutros) {
		this.vlrespostaoutros = vlrespostaoutros;
	}

	public String getTxrespostaoutros() {
		return txrespostaoutros;
	}

	public void setTxrespostaoutros(String txrespostaoutros) {
		this.txrespostaoutros = txrespostaoutros;
	}

	public Integer getIdpesquisa03() {
		return idpesquisa03;
	}

	public void setIdpesquisa03(Integer idpesquisa03) {
		this.idpesquisa03 = idpesquisa03;
	}
    
}
