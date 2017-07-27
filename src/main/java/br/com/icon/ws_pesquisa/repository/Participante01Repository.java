package br.com.icon.ws_pesquisa.repository;

import br.com.icon.ws_pesquisa.model.CE_Cliente01;
import br.com.icon.ws_pesquisa.model.CE_Participante01;
import br.com.icon.ws_pesquisa.model.CE_Pesquisa08;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Henrique on 20/07/2016.
 */
public class Participante01Repository {

    private EntityManager manager;

    public Participante01Repository(){}

    public Participante01Repository(EntityManager manager){
        this.manager = manager;
    }
    
    public int salvarParticipante(CE_Participante01 participante){
    	if (participante.getIdparticipante01() != null)
    		return alterarParticipante(participante);
    	else
    		return inserirParticipante(participante);
    }
    
    private int inserirParticipante(CE_Participante01 participante){
    	String sql = "insert into tb_participante01 (nome, email, telefone, infoadicional, qtqrcode, empresa) values (:p_nome, :p_email, :p_telefone, :p_infoadicional, :p_qtqrcode, :p_empresa)";
    	
    	Query query = manager.createNativeQuery(sql);
    	query.setParameter("p_nome", participante.getNome());
    	query.setParameter("p_email", participante.getEmail());
    	query.setParameter("p_telefone", participante.getTelefone());
    	query.setParameter("p_infoadicional", participante.getInfoadicional());
    	query.setParameter("p_qtqrcode", participante.getQtqrcode());
    	query.setParameter("p_empresa", participante.getEmpresa());
    	
    	return query.executeUpdate();
    }
    
    private int alterarParticipante(CE_Participante01 participante){
    	String sql = "update tb_participante01 set nome = :p_nome, email = :p_email, telefone = :p_telefone, infoadicional = :p_infoadicional, qtqrcode = :p_qtqrcode, empresa = :p_empresa where idparticipante01 = :p_idparticipante01";
    	
    	Query query = manager.createNativeQuery(sql);
    	query.setParameter("p_nome", participante.getNome());
    	query.setParameter("p_email", participante.getEmail());
    	query.setParameter("p_telefone", participante.getTelefone());
    	query.setParameter("p_infoadicional", participante.getInfoadicional());
    	query.setParameter("p_qtqrcode", participante.getQtqrcode());
    	query.setParameter("p_idparticipante01", participante.getIdparticipante01());
    	query.setParameter("p_empresa", participante.getEmpresa());
    	
    	return query.executeUpdate();
    }
    
    public List<CE_Participante01> obterParticipantes(){
    	
    	String sql = "from CE_Participante01";
    	
    	Query query = manager.createQuery(sql);
    	
    	return query.getResultList();
    }
    
}
