package br.com.icon.ws_pesquisa.repository;

import br.com.icon.ws_pesquisa.model.CE_Pesquisa01;
import br.com.icon.ws_pesquisa.model.CE_Pesquisa06;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Henrique on 28/06/2016.
 */
public class Pesquisa01Repository {

    private EntityManager manager;

    public Pesquisa01Repository(){}

    @Inject
    public Pesquisa01Repository(EntityManager manager){
        this.manager = manager;
    }

    public List<CE_Pesquisa06> obterPesquisas(String data) throws ParseException {
        String jpql = "select p06.idpesquisa06, p06.idcliente, p06.qtamostra, to_char(p06.dtiniciopesquisa, 'DD/MM/YYYY HH24:MI:SS') dtiniciopesquisa " +
                        ", to_char(p06.dtfimpesquisa, 'DD/MM/YYYY HH24:MI:SS') dtfimpesquisa, p06.qtamostraporpesquisador, p01.idpesquisa01, p01.nomepesquisa, to_char(p01.dtinicio " +
                        ", 'DD/MM/YYYY HH24:MI:SS') dtinicio, to_char(p01.dtfim, 'DD/MM/YYYY HH24:MI:SS') dtfim, p06.nome nomeonda " +
                        "from tb_pesquisa06 p06 " +
                        "join tb_pesquisa01 p01 on p06.idpesquisa01 = p01.idpesquisa01 " +
                        "where to_timestamp('" + data + "', 'DD/MM/YYYY HH24:MI:SS') >= p06.dtiniciopesquisa " +
                        "      and to_timestamp('" + data + "', 'DD/MM/YYYY HH24:MI:SS') <= p06.dtfimpesquisa";
        Query query  = manager.createNativeQuery(jpql);

        List<CE_Pesquisa06> pesquisas = new ArrayList<CE_Pesquisa06>();

        List<Object[]> list = query.getResultList();

        for(Object[] obj : list){
            CE_Pesquisa06 pesquisa06 = new CE_Pesquisa06();
            pesquisa06.setIdpesquisa06(obj[0] != null ? Integer.parseInt(obj[0].toString()) : null);
            pesquisa06.setIdcliente(obj[1] != null ? Integer.parseInt(obj[1].toString()) : null);
            pesquisa06.setQtamostra(obj[2] != null ? Integer.parseInt(obj[2].toString()) : null);
            pesquisa06.setDtiniciopesquisa(obj[3] != null ? new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(obj[3].toString()) : null);
            pesquisa06.setDtfimpesquisa(obj[4] != null ? new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(obj[4].toString()) : null);
            pesquisa06.setQtamostraporpesquisador(obj[5] != null ? Integer.parseInt(obj[5].toString()) : null);
            pesquisa06.setNome(obj[10] != null ? obj[10].toString() : null);
            
            CE_Pesquisa01 pesquisa01 = new CE_Pesquisa01();
            pesquisa01.setIdpesquisa01(obj[6] != null ? Integer.parseInt(obj[6].toString()) : null);
            pesquisa01.setNomepesquisa(obj[7] != null ? obj[7].toString() : null);
            pesquisa01.setDtinicio(obj[8] != null ? new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(obj[8].toString()) : null);
            pesquisa01.setDtfim(obj[9] != null ? new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(obj[9].toString()) : null);

            pesquisa06.setPesquisa01(pesquisa01);

            pesquisas.add(pesquisa06);
        }

        return pesquisas;
    }

    public List<CE_Pesquisa06> obterPesquisasPorPeriodo(String dataInicio, String dataFim, Integer idcliente) throws ParseException {
        String jpql = "select p06.idpesquisa06, p06.idcliente, p06.qtamostra, to_char(p06.dtiniciopesquisa, 'DD/MM/YYYY HH24:MI:SS') dtiniciopesquisa \n" +
                    ", to_char(p06.dtfimpesquisa, 'DD/MM/YYYY HH24:MI:SS') dtfimpesquisa, p06.qtamostraporpesquisador, p01.idpesquisa01, p01.nomepesquisa, to_char(p01.dtinicio " +
                    ", 'DD/MM/YYYY HH24:MI:SS') dtinicio, to_char(p01.dtfim, 'DD/MM/YYYY HH24:MI:SS') dtfim, p06.nome nomeonda " +
                    "from tb_pesquisa06 p06 " +
                    "join tb_pesquisa01 p01 on p06.idpesquisa01 = p01.idpesquisa01 " +
                    "where p06.idcliente = " + idcliente + " and to_timestamp(to_char(p06.dtiniciopesquisa, 'MM/YYYY'), 'MM/YYYY') >= to_timestamp('"+ dataInicio +"', 'MM/YYYY') " +
                    "      and to_timestamp(to_char(p06.dtfimpesquisa, 'MM/YYYY'), 'MM/YYYY') <= to_timestamp('" + dataFim +"', 'MM/YYYY')";
        Query query  = manager.createNativeQuery(jpql);

        List<CE_Pesquisa06> pesquisas = new ArrayList<CE_Pesquisa06>();

        List<Object[]> list = query.getResultList();

        for(Object[] obj : list){
            CE_Pesquisa06 pesquisa06 = new CE_Pesquisa06();
            pesquisa06.setIdpesquisa06(obj[0] != null ? Integer.parseInt(obj[0].toString()) : null);
            pesquisa06.setIdcliente(obj[1] != null ? Integer.parseInt(obj[1].toString()) : null);
            pesquisa06.setQtamostra(obj[2] != null ? Integer.parseInt(obj[2].toString()) : null);
            pesquisa06.setDtiniciopesquisa(obj[3] != null ? new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(obj[3].toString()) : null);
            pesquisa06.setDtfimpesquisa(obj[4] != null ? new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(obj[4].toString()) : null);
            pesquisa06.setQtamostraporpesquisador(obj[5] != null ? Integer.parseInt(obj[5].toString()) : null);
            pesquisa06.setNome(obj[10] != null ? obj[10].toString() : null);

            CE_Pesquisa01 pesquisa01 = new CE_Pesquisa01();
            pesquisa01.setIdpesquisa01(obj[6] != null ? Integer.parseInt(obj[6].toString()) : null);
            pesquisa01.setNomepesquisa(obj[7] != null ? obj[7].toString() : null);
            pesquisa01.setDtinicio(obj[8] != null ? new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(obj[8].toString()) : null);
            pesquisa01.setDtfim(obj[9] != null ? new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(obj[9].toString()) : null);

            pesquisa06.setPesquisa01(pesquisa01);

            pesquisas.add(pesquisa06);
        }

        return pesquisas;
    }
}
