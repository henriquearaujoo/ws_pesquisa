package br.com.icon.ws_pesquisa.model;

import java.math.BigDecimal;

/**
 * Created by Henrique on 05/08/2016.
 */
public class Valor {

    private Integer idpesquisa03;

    private BigDecimal valor;

    public Integer getIdpesquisa03() {
        return idpesquisa03;
    }

    public void setIdpesquisa03(Integer idpesquisa03) {
        this.idpesquisa03 = idpesquisa03;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
