package br.com.icon.ws_pesquisa.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Created by Henrique on 20/07/2016.
 */
public class GenericRepository {

    private EntityManager manager;

    public GenericRepository(){}

    public GenericRepository(EntityManager manager){
        this.manager = manager;
    }
    
    public List<Object[]> executarSelect(String sql){
    	Query query  = manager.createNativeQuery(sql);
    	
    	List<Object[]> list = query.getResultList();
    	
    	return list;
    }
    
    public int executarUpdateRetorno(String sql){
    	
    	Object obj = manager.createNativeQuery(sql).getSingleResult();
    	
    	return obj != null ? Integer.parseInt(obj.toString()) : 0;
    }
    
    public int executarUpdate(String sql){
    	
    	return manager.createNativeQuery(sql).executeUpdate();
    }
}
