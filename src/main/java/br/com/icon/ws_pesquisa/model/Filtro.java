package br.com.icon.ws_pesquisa.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Henrique on 05/08/2016.
 */
public class Filtro {

    private Integer idpesquisa04;

    private Boolean temOpcoes;

    private List<BigDecimal> valores;

    public Integer getIdpesquisa04() {
        return idpesquisa04;
    }

    public void setIdpesquisa04(Integer idpesquisa04) {
        this.idpesquisa04 = idpesquisa04;
    }

    public List<BigDecimal> getValores() {
        return valores;
    }

    public void setValores(List<BigDecimal> valores) {
        this.valores = valores;
    }

    public Boolean getTemOpcoes() {
        return temOpcoes;
    }

    public void setTemOpcoes(Boolean temOpcoes) {
        this.temOpcoes = temOpcoes;
    }
}
