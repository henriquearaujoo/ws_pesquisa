package br.com.icon.ws_pesquisa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Henrique on 20/07/2016.
 */
@Entity
@Table(name="tb_cliente01")
public class CE_Cliente01 {

    @Id
    @Column
    private Integer idcliente;

    @Column
    private String razaosocial;

    @Column
    private String fantasia;

    @Column
    private Integer cep;

    @Column
    private Integer cnpjcpf;

    @Column
    private String endereco;

    @Column
    private String bairro;

    @Column
    private String cidade;

    @Column
    private String telefone01;

    @Column
    private String telefone02;

    @Column
    private String telefone03;

    @Column
    private String contato01;

    @Column
    private String contato02;

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public String getRazaosocial() {
        return razaosocial;
    }

    public void setRazaosocial(String razaosocial) {
        this.razaosocial = razaosocial;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public Integer getCnpjcpf() {
        return cnpjcpf;
    }

    public void setCnpjcpf(Integer cnpjcpf) {
        this.cnpjcpf = cnpjcpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
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

    public String getTelefone03() {
        return telefone03;
    }

    public void setTelefone03(String telefone03) {
        this.telefone03 = telefone03;
    }

    public String getContato01() {
        return contato01;
    }

    public void setContato01(String contato01) {
        this.contato01 = contato01;
    }

    public String getContato02() {
        return contato02;
    }

    public void setContato02(String contato02) {
        this.contato02 = contato02;
    }
}
