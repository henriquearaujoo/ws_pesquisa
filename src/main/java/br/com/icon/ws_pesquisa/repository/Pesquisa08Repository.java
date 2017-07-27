package br.com.icon.ws_pesquisa.repository;

import br.com.icon.ws_pesquisa.model.CE_Pesquisa08;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.constraints.AssertTrue;
import java.util.List;

/**
 * Created by Henrique on 20/07/2016.
 */
public class Pesquisa08Repository {

    private EntityManager manager;

    public Pesquisa08Repository(){

    }

    public Pesquisa08Repository(EntityManager manager){
        this.manager = manager;
    }

    public CE_Pesquisa08 getPesquisador(){

        String hql = "SELECT new CE_Pesquisa08(p.nome, p.cliente.idcliente, p.cliente.razaosocial) FROM CE_Pesquisa08 p ";

        Query query  = manager.createQuery(hql);

        List<CE_Pesquisa08> list = query.getResultList();

        if (list.size() > 0){
            return  list.get(0);
        }
        else
            return null;
    }

    public CE_Pesquisa08 autenticarPesquisador(Integer idpesquisador, String senha, String imei){

        String hql = "SELECT new CE_Pesquisa08(p.nome, p.cliente.idcliente, p.cliente.razaosocial) FROM CE_Pesquisa08 p WHERE p.idpesquisador = " + idpesquisador + " AND p.senha = '" + senha + "' AND p.imei = '" + imei + "'";

        Query query  = manager.createQuery(hql);

        List<CE_Pesquisa08> list = query.getResultList();

        if (list.size() > 0){
            return  list.get(0);
        }
        else
            return null;
    }
}
