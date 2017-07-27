package br.com.icon.ws_pesquisa.repository;

import br.com.icon.ws_pesquisa.model.CE_Cliente01;
import br.com.icon.ws_pesquisa.model.CE_Pesquisa08;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Created by Henrique on 20/07/2016.
 */
public class Cliente01Repository {

    private EntityManager manager;

    public Cliente01Repository(){}

    public Cliente01Repository(EntityManager manager){
        this.manager = manager;
    }
}
