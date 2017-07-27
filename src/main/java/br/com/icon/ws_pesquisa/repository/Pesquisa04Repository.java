package br.com.icon.ws_pesquisa.repository;

import br.com.icon.ws_pesquisa.model.CE_Pesquisa02;
import br.com.icon.ws_pesquisa.model.CE_Pesquisa03;
import br.com.icon.ws_pesquisa.model.CE_Pesquisa04;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Henrique on 02/07/2016.
 */
public class Pesquisa04Repository {

    private EntityManager manager;

    public Pesquisa04Repository(){}

    @Inject
    public Pesquisa04Repository(EntityManager manager){
        this.manager = manager;
    }

    public List<CE_Pesquisa04> obterItensFormulario(Integer idpesquisa01, String data) throws ParseException {
        String jpql = "select p04.idpesquisa04, p04.idpesquisa01, p04.descricao descricaopergunta, p04.idpesquisa04pai, p04.idpesquisa02, p04.tamanhoresposta " +
                        " , p04.qtdecimais, p04.vldefault, p04.qtrespostas, to_char(p04.dtinicio, 'DD/MM/YYYY HH24:MI:SS') dtinicio " +
                        " , to_char(p04.dtfim, 'DD/MM/YYYY HH24:MI:SS') dtfim, p04.ordempergunta, p04.obrigatoria " +
                        " , p02.tipodado, p03.idpesquisa03, p03.descricao descricaoresposta, p03.retornopesquisa, p03.ordem " +
                        " , p04.idpesquisa02outros, p02o.tipodado tipodadooutros, p03.campotipooutros, p03.idpesquisa02 idpesquisa02valor, p04.qtminrespostas, p04.qtminobrigatoria " +
                        " from tb_pesquisa04 p04 " +
                        " left join tb_pesquisa02 p02 on p02.idpesquisa02 = p04.idpesquisa02 " +
                        " left join tb_pesquisa02 p02o on p02o.idpesquisa02 = p04.idpesquisa02outros " +
                        " left join tb_pesquisa03 p03 on p02.idpesquisa02 = p03.idpesquisa02 or p02o.idpesquisa02 = p03.idpesquisa02 " +
                        " where p04.idpesquisa01 = " + idpesquisa01 +
                        " and to_timestamp('" + data + "', 'DD/MM/YYYY HH24:MI:SS') >= p04.dtinicio " +
                        " and to_timestamp('" + data + "', 'DD/MM/YYYY HH24:MI:SS') <= p04.dtfim " +
                        " and p04.disponivel = 1";

        Query query  = manager.createNativeQuery(jpql);

        List<CE_Pesquisa04> itensFormulario = new ArrayList<CE_Pesquisa04>();

        List<Object[]> list = query.getResultList();

        for(Object[] obj : list){
            CE_Pesquisa04 itemFormulario = new CE_Pesquisa04();
            itemFormulario.setIdpesquisa04(Integer.parseInt(obj[0] != null ? obj[0].toString() : null));
            itemFormulario.setIdpesquisa01(Integer.parseInt(obj[1] != null ? obj[1].toString() : null));
            itemFormulario.setDescricao(obj[2].toString());
            itemFormulario.setIdpesquisa04pai(obj[3] != null ? Integer.parseInt(obj[3].toString()) : null);
            itemFormulario.setTamanhoresposta(obj[5] != null ? Integer.parseInt(obj[5].toString()) : null);
            itemFormulario.setQtdecimais(obj[6] != null ? Integer.parseInt(obj[6].toString()) : null);
            itemFormulario.setVldefault(obj[7] != null ? obj[7].toString() : null);
            itemFormulario.setQtrespostas(obj[8] != null ? Integer.parseInt(obj[8].toString()) : null);
            itemFormulario.setDtinicio(obj[9] != null ? new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(obj[9].toString()) : null);
            itemFormulario.setDtfim(obj[10] != null ? new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(obj[10].toString()) : null);
            itemFormulario.setOrdempergunta(obj[11] != null ? Integer.parseInt(obj[11].toString()) : null);
            itemFormulario.setObrigatoria(obj[12] != null ? Integer.parseInt(obj[12].toString()) : null);
            itemFormulario.setQtminrespostas(obj[22] != null ? Integer.parseInt(obj[22].toString()) : null);
            itemFormulario.setQtminobrigatoria(obj[23] != null ? Integer.parseInt(obj[23].toString()) : null);

            CE_Pesquisa03 pesquisa03 = new CE_Pesquisa03();
            pesquisa03.setIdpesquisa03(obj[14] != null ? Integer.parseInt(obj[14].toString()) : null);
            pesquisa03.setDescricao(obj[15] != null ? obj[15].toString() : null);
            pesquisa03.setRetornopesquisa(obj[16] != null ? obj[16].toString() : null);
            pesquisa03.setOrdem(obj[17] != null ? Integer.parseInt(obj[17].toString()) : null);
            pesquisa03.setCampotipooutros(obj[20] != null ? Integer.parseInt(obj[20].toString()) : null);
            CE_Pesquisa02 pesquisa02valor = new CE_Pesquisa02();
            pesquisa02valor.setIdpesquisa02(obj[21] != null ? Integer.parseInt(obj[21].toString()) : null);
            pesquisa03.setPesquisa02(pesquisa02valor);
            
            itemFormulario.setPesquisa03(pesquisa03);
            
            CE_Pesquisa02 pesquisa02 = new CE_Pesquisa02();
            pesquisa02.setIdpesquisa02(obj[4] != null ? Integer.parseInt(obj[4].toString()) : null);
            pesquisa02.setTipodado(obj[13] != null ? obj[13].toString() : null);
            
            itemFormulario.setPesquisa02(pesquisa02);
            
            if (obj[18] != null){
	            CE_Pesquisa02 pesquisa02outos = new CE_Pesquisa02();
	            pesquisa02outos.setIdpesquisa02(obj[18] != null ? Integer.parseInt(obj[18].toString()) : null);
	            pesquisa02outos.setTipodado(obj[19] != null ? obj[19].toString() : null);
	
	            itemFormulario.setPesquisa02outros(pesquisa02outos);
            }

            itensFormulario.add(itemFormulario);
        }

        return itensFormulario;
    }

    public List<CE_Pesquisa04> obterItensFormularioAnalise(Integer idpesquisa01) throws ParseException {
        String jpql = "select p04.idpesquisa04, p04.idpesquisa01, p04.descricao descricaopergunta, p04.idpesquisa04pai, p04.idpesquisa02, p04.tamanhoresposta " +
                ", p04.qtdecimais, p04.vldefault, p04.qtrespostas, to_char(p04.dtinicio, 'DD/MM/YYYY HH24:MI:SS') dtinicio " +
                ", to_char(p04.dtfim, 'DD/MM/YYYY HH24:MI:SS') dtfim, p04.ordempergunta, p04.obrigatoria " +
                ", p02.tipodado, p03.idpesquisa03, p03.descricao descricaoresposta, p03.retornopesquisa, p03.ordem " +
                "from tb_pesquisa04 p04 " +
                "left join tb_pesquisa02 p02 on p02.idpesquisa02 = p04.idpesquisa02 " +
                "left join tb_pesquisa03 p03 on p02.idpesquisa02 = p03.idpesquisa02 " +
                "where p04.idpesquisa01 = " + idpesquisa01 +
                " and p04.disponivel = 1" +
                " order by p04.ordempergunta, p03.ordem" ;

        Query query  = manager.createNativeQuery(jpql);

        List<CE_Pesquisa04> itensFormulario = new ArrayList<CE_Pesquisa04>();

        List<Object[]> list = query.getResultList();

        for(Object[] obj : list){
            CE_Pesquisa04 itemFormulario = new CE_Pesquisa04();
            itemFormulario.setIdpesquisa04(Integer.parseInt(obj[0] != null ? obj[0].toString() : null));
            itemFormulario.setIdpesquisa01(Integer.parseInt(obj[1] != null ? obj[1].toString() : null));
            itemFormulario.setDescricao(obj[2].toString());
            itemFormulario.setIdpesquisa04pai(obj[3] != null ? Integer.parseInt(obj[3].toString()) : null);
            itemFormulario.setTamanhoresposta(obj[5] != null ? Integer.parseInt(obj[5].toString()) : null);
            itemFormulario.setQtdecimais(obj[6] != null ? Integer.parseInt(obj[6].toString()) : null);
            itemFormulario.setVldefault(obj[7] != null ? obj[7].toString() : null);
            itemFormulario.setQtrespostas(obj[8] != null ? Integer.parseInt(obj[8].toString()) : null);
            itemFormulario.setDtinicio(obj[9] != null ? new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(obj[9].toString()) : null);
            itemFormulario.setDtfim(obj[10] != null ? new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(obj[10].toString()) : null);
            itemFormulario.setOrdempergunta(obj[11] != null ? Integer.parseInt(obj[11].toString()) : null);
            itemFormulario.setObrigatoria(obj[12] != null ? Integer.parseInt(obj[12].toString()) : null);

            CE_Pesquisa02 pesquisa02 = new CE_Pesquisa02();
            pesquisa02.setIdpesquisa02(obj[4] != null ? Integer.parseInt(obj[4].toString()) : null);
            pesquisa02.setTipodado(obj[13] != null ? obj[13].toString() : null);

            itemFormulario.setPesquisa02(pesquisa02);

            CE_Pesquisa03 pesquisa03 = new CE_Pesquisa03();
            pesquisa03.setIdpesquisa03(obj[14] != null ? Integer.parseInt(obj[14].toString()) : null);
            pesquisa03.setDescricao(obj[15] != null ? obj[15].toString() : null);
            pesquisa03.setRetornopesquisa(obj[16] != null ? obj[16].toString() : null);
            pesquisa03.setOrdem(obj[17] != null ? Integer.parseInt(obj[17].toString()) : null);

            itemFormulario.setPesquisa03(pesquisa03);

            itensFormulario.add(itemFormulario);
        }

        return itensFormulario;
    }
}
