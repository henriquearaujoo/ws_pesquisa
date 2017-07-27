package br.com.icon.ws_pesquisa.repository;

import br.com.icon.ws_pesquisa.model.CE_Pesquisa04;
import br.com.icon.ws_pesquisa.model.CE_Pesquisa07;
import br.com.icon.ws_pesquisa.model.Filtro;

import javax.management.Query;
import javax.persistence.EntityManager;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Henrique on 18/07/2016.
 */
public class Pesquisa07Repository {

    private EntityManager manager;

    public Pesquisa07Repository(EntityManager manager){
        this.manager = manager;
    }

    public boolean respostaExiste(CE_Pesquisa07 pesquisa07)
    {
        String sql = "select idpesquisa07 from tb_pesquisa07 where idpesquisa06 = " + pesquisa07.getPesquisa06().getIdpesquisa06() + " and idpesquisador = " + pesquisa07.getPesquisa08().getIdpesquisador() + " and chavepesquisa = '" + pesquisa07.getChavepesquisa() + "' and idpesquisa04 = " + pesquisa07.getPesquisa04().getIdpesquisa04() + " and idpesquisa03 = " + pesquisa07.getIdpesquisa03();

        return manager.createNativeQuery(sql).getResultList().size() > 0;
    }

    public void salvarResposta(CE_Pesquisa07 pesquisa07){

    	try {
    		String sql = "";
            
            sql = "INSERT INTO tb_pesquisa07 (idpesquisa06, idpesquisa04, chavepesquisa, vlresposta, txresposta, idcliente, idpesquisador, vlrespostaoutros, txrespostaoutros, idpesquisa03) " +
                    " VALUES (:p_idpesquisa06, :p_idpesquisa04, :p_chavepesquisa, :p_vlresposta, :p_txresposta, :p_idcliente, :p_idpesquisador, :p_vlrespostaoutros, :p_txrespostaoutros, :p_idpesquisa03)";

            javax.persistence.Query query = manager.createNativeQuery(sql);
            query.setParameter("p_idpesquisa06", pesquisa07.getPesquisa06().getIdpesquisa06());
            query.setParameter("p_idpesquisa04", pesquisa07.getPesquisa04().getIdpesquisa04());
            query.setParameter("p_chavepesquisa", pesquisa07.getChavepesquisa());
            query.setParameter("p_vlresposta", pesquisa07.getVlresposta() != null ? pesquisa07.getVlresposta() : null);
            query.setParameter("p_txresposta", pesquisa07.getTxresposta() != null ? pesquisa07.getTxresposta() : null);
            query.setParameter("p_idcliente", pesquisa07.getCliente().getIdcliente());
            query.setParameter("p_idpesquisador", pesquisa07.getPesquisa08().getIdpesquisador());
            query.setParameter("p_vlrespostaoutros", pesquisa07.getVlrespostaoutros() != null ? pesquisa07.getVlrespostaoutros() : null);
            query.setParameter("p_txrespostaoutros", pesquisa07.getTxrespostaoutros() != null ? pesquisa07.getTxrespostaoutros() : null);
            query.setParameter("p_idpesquisa03", pesquisa07.getIdpesquisa03());
            
            query.executeUpdate();
		
		} catch (Exception e) {
			throw e;
		}
    	
    }
    
    public void insereLog(Integer idpesquisador, String erro){
    	String sql = "insert into tb_log (datahora, idpesquisador, log) values ('" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "', :p_idpesquisador, :p_log)";
    	
    	javax.persistence.Query query = manager.createNativeQuery(sql);
    	query.setParameter("p_idpesquisador", idpesquisador);
    	query.setParameter("p_log", erro);
    	
    	query.executeUpdate();
    }

    public List<CE_Pesquisa07> obterRespostasPorOnda(Integer idpesquisa06){
        String sql = "select chavepesquisa, idpesquisa04, vlresposta, txresposta, vlrespostaoutros, txrespostaoutros from tb_pesquisa07 where idpesquisa06 = " + idpesquisa06;
        javax.persistence.Query query  = manager.createNativeQuery(sql);

        List<CE_Pesquisa07> respostas = new ArrayList<CE_Pesquisa07>();

        List<Object[]> list = query.getResultList();

        for(Object[] obj : list){
            CE_Pesquisa07 resposta = new CE_Pesquisa07();
            resposta.setChavepesquisa(obj[0] != null ? obj[0].toString() : null);

            CE_Pesquisa04 pesquisa04 = new CE_Pesquisa04();
            pesquisa04.setIdpesquisa04(obj[1] != null ? Integer.parseInt(obj[1].toString()) : null);
            resposta.setPesquisa04(pesquisa04);
            resposta.setVlresposta(obj[2] != null ? new BigDecimal(obj[2].toString()) : null);
            resposta.setTxresposta(obj[3] != null ? obj[3].toString() : null);
            resposta.setVlrespostaoutros(obj[4] != null ? new BigDecimal(obj[4].toString()) : null);
            resposta.setTxrespostaoutros(obj[5] != null ? obj[5].toString() : null);

            respostas.add(resposta);
        }

        return respostas;
    }

    public List<Object[]> obterRespostasAgrupadasPorOnda(Integer idpesquisa06, Integer idcliente, List<Filtro> filtros){
        String sql = " with sql_x as  " +
                        "       (select idpesquisa04, pergunta, txresposta, vlresposta, qt " +
                "      , round( qt / sum(qt) over (partition by idpesquisa04 ) * 100,1 ) as perc " +
                "      , RANK() OVER(partition by idpesquisa04 order by  qt desc, txresposta) rnk " +
                "   from (select pesq07.idpesquisa04, pesq04.descricao pergunta " +
                "       , case when txresposta is null then trim(to_char(vlresposta,'999')) else txresposta end txresposta " +
                "       , vlresposta " +
                "       , count(*) qt " +
                "    from tb_pesquisa07 pesq07 " +
                "       , tb_pesquisa04 pesq04" +
                " where pesq07.idpesquisa06 = " + idpesquisa06 +
                " and pesq04.idpesquisa04 = pesq07.idpesquisa04 " +
        		" and pesq07.idcliente = " + idcliente;

        if (filtros != null && filtros.size() > 0){
            sql += "   and pesq07.chavepesquisa in (select chavepesquisa from ( ";
        }

        for (int i = 0; i < filtros.size() ; i++) {
            if (i > 0)
                sql += " intersect ";

            sql += " select distinct chavepesquisa from tb_pesquisa07 p07 " +
                    "      where p07.idpesquisa04 = " + filtros.get(i).getIdpesquisa04() + " and p07.vlresposta ";

            if (filtros.get(i).getTemOpcoes()) {
                for (int j = 0; j < filtros.get(i).getValores().size(); j++) {
                    if (j == 0)
                        sql += " IN ( ";

                    if (j != filtros.get(i).getValores().size() - 1)
                        sql += filtros.get(i).getValores().get(j).toString() + ", ";
                    else
                        sql += filtros.get(i).getValores().get(j).toString() + " ) ";
                }
            }
            else{
                for (int j = 0; j < filtros.get(i).getValores().size(); j++) {
                    if (j == 0)
                        sql += " BETWEEN " + filtros.get(i).getValores().get(j).toString();
                    else
                        sql += " AND " + filtros.get(i).getValores().get(j).toString();
                }
            }

        }

        if (filtros != null && filtros.size() > 0){
            sql += " ) a )";
        }

        sql += " group by pesq07.idpesquisa04, pesq04.descricao, case when txresposta is null then trim(to_char(vlresposta,'999')) else txresposta end, pesq07.vlresposta " +
                "   order by pesq07.idpesquisa04, qt desc, txresposta  ) b " +
                " ) " +
                "select a.* " +
                "     , (select sum(perc) from sql_x b where b.idpesquisa04 = a.idpesquisa04 and b.rnk <= a.rnk) ttx " +
                "  from sql_x a";

        javax.persistence.Query query  = manager.createNativeQuery(sql);

        List<Object[]> list = query.getResultList();

        return list;
    }

    public List<Object[]> obterRespostasAgrupadasEntreOndas(String idsPesquisa06, List<Filtro> filtros){
        String sql =
                "with sql_x as  \n" +
                "\n" +
                " (select idpesquisa04, pergunta, vlresposta, txresposta \n" +
                "       , qt  , round( qt   / sum(qt)   over (partition by idpesquisa04 ) * 100,0 ) as perc\n" +
                "        , RANK() OVER(partition by idpesquisa04 order by  qt desc, txresposta) rnk \n" +
                "        , qt01, round( qt01 / sum(qt01) over (partition by idpesquisa04 ) * 100,0 ) as perc01 \n" +
                "       , qt02, case when qt02 = 0 then 0 else round( qt02 / sum(qt02) over (partition by idpesquisa04 ) * 100,0 ) end as perc02 \n" +
                "       , qt03, case when qt03 = 0 then 0 else round( qt03 / sum(qt03) over (partition by idpesquisa04 ) * 100,0 ) end as perc03 \n" +
                "       , qt04, case when qt04 = 0 then 0 else round( qt04 / sum(qt04) over (partition by idpesquisa04 ) * 100,0 ) end as perc04 \n" +
                "       , qt05, case when qt05 = 0 then 0 else round( qt05 / sum(qt05) over (partition by idpesquisa04 ) * 100,0 ) end as perc05 \n" +
                "       , qt06, case when qt06 = 0 then 0 else round( qt06 / sum(qt06) over (partition by idpesquisa04 ) * 100,0 ) end as perc06 \n" +
                "    from (   \n" +
                "          select pesq07.idpesquisa04 \n" +
                "               , pesq04.descricao pergunta, pesq07.vlresposta, pesq07.txresposta \n" +
                "               , count(*) qt \n" +
                "        , sum(case when pesq06.seq = 1 then 1 else 0 end) qt01 \n" +
                "        , sum(case when pesq06.seq = 2 then 1 else 0 end) qt02 \n" +
                "        , sum(case when pesq06.seq = 3 then 1 else 0 end) qt03 \n" +
                "        , sum(case when pesq06.seq = 4 then 1 else 0 end) qt04 \n" +
                "        , sum(case when pesq06.seq = 5 then 1 else 0 end) qt05 \n" +
                "        , sum(case when pesq06.seq = 6 then 1 else 0 end) qt06 \n" +
                "     from tb_pesquisa07 pesq07 \n" +
                "        , (select idpesquisa06,  to_char(dtiniciopesquisa, 'DD-MM-YYYY') || ' a ' ||  to_char(dtfimpesquisa, 'DD-MM-YYYY') onda \n" +
                "          , row_number() OVER (ORDER BY idpesquisa06) AS seq\n" +
                "      from tb_pesquisa06\n" +
                "     where idpesquisa06 in " + idsPesquisa06 + ") pesq06 \n" +
                "        , tb_pesquisa04 pesq04 \n" +
                "    where pesq07.idpesquisa06 in " + idsPesquisa06 + " \n" +
                "      and pesq06.idpesquisa06  = pesq07.idpesquisa06 \n" +
                "      and pesq04.idpesquisa04  = pesq07.idpesquisa04 ";

        if (filtros != null && filtros.size() > 0){
            sql += "   and pesq07.chavepesquisa in (select chavepesquisa from ( ";
        }

        for (int i = 0; i < filtros.size() ; i++) {
            if (i > 0)
                sql += " intersect ";

            sql += " select distinct chavepesquisa from tb_pesquisa07 p07 " +
                    "      where p07.idpesquisa04 = " + filtros.get(i).getIdpesquisa04() + " and p07.vlresposta ";

            if (filtros.get(i).getTemOpcoes()) {
                for (int j = 0; j < filtros.get(i).getValores().size(); j++) {
                    if (j == 0)
                        sql += " IN ( ";

                    if (j != filtros.get(i).getValores().size() - 1)
                        sql += filtros.get(i).getValores().get(j).toString() + ", ";
                    else
                        sql += filtros.get(i).getValores().get(j).toString() + " ) ";
                }
            }
            else{
                for (int j = 0; j < filtros.get(i).getValores().size(); j++) {
                    if (j == 0)
                        sql += " BETWEEN " + filtros.get(i).getValores().get(j).toString();
                    else
                        sql += " AND " + filtros.get(i).getValores().get(j).toString();
                }
            }

        }

        if (filtros != null && filtros.size() > 0){
            sql += " ) a )";
        }

        sql += "  group by pesq07.idpesquisa04, pesq04.descricao, pesq07.vlresposta, pesq07.txresposta \n" +
                "    order by pesq07.idpesquisa04, qt desc, pesq07.vlresposta, pesq07.txresposta ) b \n" +
                "        ) \n " +
                " select (select sum(perc) from sql_x b where b.idpesquisa04 = a.idpesquisa04 and b.rnk <= a.rnk) ttPerc \n" +
                "     , a.* \n" +
                "  from sql_x a";

        javax.persistence.Query query  = manager.createNativeQuery(sql);

        List<Object[]> list = query.getResultList();

        return list;
    }
    
    public void alterarPesquisas(){
    	String sql = "select idpesquisa07, chavepesquisa, txresposta, txrespostaoutros from tb_pesquisa07 where idpesquisa06 = 44";
    	
        javax.persistence.Query query  = manager.createNativeQuery(sql);

        List<Object[]> list = query.getResultList();
        
        try {
        	manager.getTransaction().begin();
            for (Object[] objects : list) {
    			String chavepesquisa = objects[1].toString();
    			String txreposta = objects[2] != null ? objects[2].toString() : null;
    			String txrespostaoutros = objects[3] != null ? objects[3].toString() : null;
    			
    			chavepesquisa = chavepesquisa.replace("\'", "");
    			if (txreposta != null)
    				txreposta = txreposta.replace("\'", "");
    			if (txrespostaoutros != null)
    				txrespostaoutros = txrespostaoutros.replace("\'", "");
    			
    			try {
    				
    				if (txreposta != null && txrespostaoutros != null)
    					sql = "update tb_pesquisa07 set chavepesquisa = '" + chavepesquisa+ "', txresposta = '" + txreposta + "', txrespostaoutros = '" + txrespostaoutros + "' where idpesquisa07 = " + objects[0];
    				else if (txreposta != null && txrespostaoutros == null)
    					sql = "update tb_pesquisa07 set chavepesquisa = '" + chavepesquisa+ "', txresposta = '" + txreposta + "' where idpesquisa07 = " + objects[0];
    				else
    					sql = "update tb_pesquisa07 set chavepesquisa = '" + chavepesquisa+ "', txrespostaoutros = '" + txrespostaoutros + "' where idpesquisa07 = " + objects[0];
    				query  = manager.createNativeQuery(sql);
    				query.executeUpdate();
    				
    			} catch (Exception e) {
    				// TODO: handle exception
    				throw e;
    			}
    			
    		}
            manager.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			manager.getTransaction().rollback();
			e.printStackTrace();
		}
        
    }

}
