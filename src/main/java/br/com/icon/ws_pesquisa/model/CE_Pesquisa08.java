package br.com.icon.ws_pesquisa.model;

import javax.persistence.*;

/**
 * Created by Henrique on 20/07/2016.
 */
@Entity
@Table(name="tb_pesquisa08")
public class CE_Pesquisa08 {

    @Id
    @Column
    private Integer idpesquisador;

    @Column
    private String senha;

    @Column
    private String nome;

    @Column
    private String cognome;

    @Column
    private String endereco;

    @Column
    private Integer cpf;

    @Column
    private String telefone01;

    @Column
    private String telefone02;

    @Column
    private String telefonepesquisa;

    @Column
    private String imei;

    @Column
    private String email;

    @ManyToOne
    @JoinColumn(name = "idcliente")
    private CE_Cliente01 cliente;

    public CE_Pesquisa08(){}

    public CE_Pesquisa08(String nome, Integer idcliente, String razaosocial){
        this.nome = nome;
        this.cliente = new CE_Cliente01();
        this.cliente.setIdcliente(idcliente);
        this.cliente.setRazaosocial(razaosocial);
    }

    public CE_Cliente01 getCliente() {
        return cliente;
    }

    public void setCliente(CE_Cliente01 cliente) {
        this.cliente = cliente;
    }

    public Integer getIdpesquisador() {
        return idpesquisador;
    }

    public void setIdpesquisador(Integer idpesquisador) {
        this.idpesquisador = idpesquisador;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Integer getCpf() {
        return cpf;
    }

    public void setCpf(Integer cpf) {
        this.cpf = cpf;
    }

    public String getTelefone01() {
        return telefone01;
    }

    public void setTelefone01(String telefone01) {
        this.telefone01 = telefone01;
    }

    public String getTelefone02() {
        return telefone02;
    }

    public void setTelefone02(String telefone02) {
        this.telefone02 = telefone02;
    }

    public String getTelefonepesquisa() {
        return telefonepesquisa;
    }

    public void setTelefonepesquisa(String telefonepesquisa) {
        this.telefonepesquisa = telefonepesquisa;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
