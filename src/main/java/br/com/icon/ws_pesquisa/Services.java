package br.com.icon.ws_pesquisa;

import br.com.icon.ws_pesquisa.model.*;
import br.com.icon.ws_pesquisa.repository.GenericRepository;
import br.com.icon.ws_pesquisa.repository.Participante01Repository;
import br.com.icon.ws_pesquisa.repository.Pesquisa01Repository;
import br.com.icon.ws_pesquisa.repository.Pesquisa04Repository;
import br.com.icon.ws_pesquisa.repository.Pesquisa07Repository;
import br.com.icon.ws_pesquisa.repository.Pesquisa08Repository;
import br.com.icon.ws_pesquisa.util.JpaUtil;
import org.hibernate.mapping.UnionSubclass;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by henrique on 28/06/2016.
 */
@Path("/services")
public class Services {

    @GET
    @Path("/testews")
    @Produces(MediaType.APPLICATION_JSON)
    public Response testews()
    {
        JSONObject jRetorno = new JSONObject();
        jRetorno.put("retorno", "OK");

        return Response.status(Response.Status.OK).entity(jRetorno.toString()).build();
    }

    @GET
    @Path("/testewsdb")
    @Produces(MediaType.APPLICATION_JSON)
    public Response testewsdb()
    {
        JSONObject jRetorno = new JSONObject();
        jRetorno.put("retorno", "OK");

        EntityManager manager = null;

        try {

            manager = JpaUtil.getEntityManager();
            manager.getTransaction().begin();
            Pesquisa08Repository repository = new Pesquisa08Repository(manager);
            Pesquisa07Repository repository7 = new Pesquisa07Repository(manager);
            repository7.insereLog(100, "Teste WS DB");
            manager.getTransaction().commit();
            CE_Pesquisa08 pesquisador = repository.getPesquisador();
            if (pesquisador != null) {
                JSONObject jPesquisador = new JSONObject();
                jPesquisador.put("nome", pesquisador.getNome());

                jRetorno.put("pesquisador", jPesquisador);
            }
        }catch (Exception e)
        {
        	manager.getTransaction().rollback();
            e.printStackTrace();
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }finally {
            manager.close();
        }

        return Response.status(Response.Status.OK).entity(jRetorno.toString()).build();
    }
    
    @GET
    @Path("/corrigicampos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response corrigirCampos()
    {
        JSONObject jRetorno = new JSONObject();
        jRetorno.put("retorno", "OK");

        EntityManager manager = null;

        try {

            manager = JpaUtil.getEntityManager();
            Pesquisa07Repository repository7 = new Pesquisa07Repository(manager);
            repository7.alterarPesquisas();
        }catch (Exception e)
        {
        	manager.getTransaction().rollback();
            e.printStackTrace();
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }finally {
            manager.close();
        }

        return Response.status(Response.Status.OK).entity(jRetorno.toString()).build();
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(String data) {

        JSONObject jRetorno = new JSONObject();
        EntityManager manager = null;

        try{

            manager = JpaUtil.getEntityManager();

            Pesquisa08Repository repository = new Pesquisa08Repository(manager);
            JSONObject parametros = new JSONObject(data);
            CE_Pesquisa08 pesquisador = repository.autenticarPesquisador(parametros.getInt("idpesquisador"), parametros.getString("senha"), parametros.getString("imei"));

            if (pesquisador != null){
                JSONObject jPesquisador = new JSONObject();
                jPesquisador.put("nome", pesquisador.getNome());
                jPesquisador.put("idcliente", pesquisador.getCliente().getIdcliente());
                jPesquisador.put("razaosocial", pesquisador.getCliente().getRazaosocial());

                jRetorno.put("pesquisador", jPesquisador);
            }
            else
                return Response.status(Response.Status.FORBIDDEN).entity("Id ou senha inválidos.").type(MediaType.TEXT_PLAIN).build();

        }catch (Exception e)
        {
            e.printStackTrace();
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }finally {
            manager.close();
        }

        return Response.status(Response.Status.OK).entity(jRetorno.toString()).build();
    }

    @POST
    @Path("/obterPesquisas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterPesquisas(String data)
    {
        JSONObject jRetorno = new JSONObject();
        JSONArray jLista = new JSONArray();
        EntityManager manager = null;

        try {

            manager = JpaUtil.getEntityManager();

            Pesquisa01Repository repository = new Pesquisa01Repository(manager);
            List<CE_Pesquisa06> pesquisas = repository.obterPesquisas(new JSONObject(data).getString("data"));

            for(CE_Pesquisa06 pesquisa : pesquisas){
                JSONObject jPesquisa06 = new JSONObject();
                jPesquisa06.put("idpesquisa06", pesquisa.getIdpesquisa06());
                jPesquisa06.put("idcliente", pesquisa.getIdcliente());
                jPesquisa06.put("qtamostra", pesquisa.getQtamostra());
                jPesquisa06.put("dtiniciopesquisa", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(pesquisa.getDtiniciopesquisa()));
                jPesquisa06.put("dtfimpesquisa", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(pesquisa.getDtfimpesquisa()));
                jPesquisa06.put("qtamostraporpesquisador", pesquisa.getQtamostraporpesquisador());
                jPesquisa06.put("nome", pesquisa.getNome());

                JSONObject jPesquisa01 = new JSONObject();
                jPesquisa01.put("idpesquisa01", pesquisa.getPesquisa01().getIdpesquisa01());
                jPesquisa01.put("dtinicio", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(pesquisa.getPesquisa01().getDtinicio()));
                jPesquisa01.put("dtfim", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(pesquisa.getPesquisa01().getDtfim()));
                jPesquisa01.put("nomepesquisa", pesquisa.getPesquisa01().getNomepesquisa());

                jPesquisa06.put("pesquisa01", jPesquisa01);

                jLista.put(jPesquisa06);
            }

            jRetorno.put("pesquisas", jLista);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }
        finally {
            manager.close();
        }

        return Response.status(Response.Status.OK).entity(jRetorno.toString()).build();
    }

    @POST
    @Path("/obterPesquisasPorPeriodo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterPesquisasPorPeriodo(String data)
    {
        JSONObject jRetorno = new JSONObject();
        JSONArray jLista = new JSONArray();
        EntityManager manager = null;

        try {

            manager = JpaUtil.getEntityManager();

            Pesquisa01Repository repository = new Pesquisa01Repository(manager);
            JSONObject obj = new JSONObject(data);
            List<CE_Pesquisa06> pesquisas = repository.obterPesquisasPorPeriodo(obj.getString("dataInicio"), obj.getString("dataFim"), obj.getInt("idcliente"));

            for(CE_Pesquisa06 pesquisa : pesquisas){
                JSONObject jPesquisa06 = new JSONObject();
                jPesquisa06.put("idpesquisa06", pesquisa.getIdpesquisa06());
                jPesquisa06.put("idcliente", pesquisa.getIdcliente());
                jPesquisa06.put("qtamostra", pesquisa.getQtamostra());
                jPesquisa06.put("dtiniciopesquisa", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(pesquisa.getDtiniciopesquisa()));
                jPesquisa06.put("dtfimpesquisa", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(pesquisa.getDtfimpesquisa()));
                jPesquisa06.put("qtamostraporpesquisador", pesquisa.getQtamostraporpesquisador());
                jPesquisa06.put("nome", pesquisa.getNome());

                JSONObject jPesquisa01 = new JSONObject();
                jPesquisa01.put("idpesquisa01", pesquisa.getPesquisa01().getIdpesquisa01());
                jPesquisa01.put("dtinicio", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(pesquisa.getPesquisa01().getDtinicio()));
                jPesquisa01.put("dtfim", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(pesquisa.getPesquisa01().getDtfim()));
                jPesquisa01.put("nomepesquisa", pesquisa.getPesquisa01().getNomepesquisa());

                jPesquisa06.put("pesquisa01", jPesquisa01);

                jLista.put(jPesquisa06);
            }

            jRetorno.put("pesquisas", jLista);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }
        finally {
            manager.close();
        }

        return Response.status(Response.Status.OK).entity(jRetorno.toString()).build();
    }

    @POST
    @Path("/obterItensFormulario")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterItensFormulario(String data)
    {
        JSONObject jRetorno = new JSONObject();
        JSONArray jLista = new JSONArray();
        EntityManager manager = null;

        try {
            manager = JpaUtil.getEntityManager();

            Pesquisa04Repository repository = new Pesquisa04Repository(manager);
            JSONObject parametros = new JSONObject(data);
            Integer idpesquisa01 = parametros.getInt("idpesquisa01");
            String datap = parametros.getString("data");
            List<CE_Pesquisa04> itensFormulario = repository.obterItensFormulario(idpesquisa01, datap);

            for (CE_Pesquisa04 itemFormulario : itensFormulario){
                JSONObject jItemFormulario = new JSONObject();
                jItemFormulario.put("idpesquisa04", itemFormulario.getIdpesquisa04());
                jItemFormulario.put("idpesquisa01", itemFormulario.getIdpesquisa01());
                jItemFormulario.put("descricao", itemFormulario.getDescricao());
                jItemFormulario.put("idpesquisa04pai", itemFormulario.getIdpesquisa04pai());
                jItemFormulario.put("tamanhoresposta", itemFormulario.getTamanhoresposta());
                jItemFormulario.put("qtdecimais", itemFormulario.getQtdecimais());
                jItemFormulario.put("vldefault", itemFormulario.getVldefault());
                jItemFormulario.put("qtrespostas", itemFormulario.getQtrespostas());
                jItemFormulario.put("qtminrespostas", itemFormulario.getQtminrespostas());
                jItemFormulario.put("qtminobrigatoria", itemFormulario.getQtminobrigatoria());
                if (itemFormulario.getDtinicio() != null)
                    jItemFormulario.put("dtinicio", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(itemFormulario.getDtinicio()));
                if (itemFormulario.getDtfim() != null)
                    jItemFormulario.put("dtfim", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(itemFormulario.getDtfim()));
                jItemFormulario.put("ordempergunta", itemFormulario.getOrdempergunta());
                jItemFormulario.put("obrigatoria", itemFormulario.getObrigatoria());

                JSONObject jPesquisa02 = new JSONObject();
                jPesquisa02.put("idpesquisa02", itemFormulario.getPesquisa02().getIdpesquisa02());
                jPesquisa02.put("tipodado", itemFormulario.getPesquisa02().getTipodado());

                jItemFormulario.put("pesquisa02", jPesquisa02);
                
                if (itemFormulario.getPesquisa02outros() != null){
	                JSONObject jPesquisa02outros = new JSONObject();
	                jPesquisa02outros.put("idpesquisa02", itemFormulario.getPesquisa02outros().getIdpesquisa02());
	                jPesquisa02outros.put("tipodado", itemFormulario.getPesquisa02outros().getTipodado());
	
	                jItemFormulario.put("pesquisa02outros", jPesquisa02outros);
                }

                JSONObject jPesquisa03 = new JSONObject();
                jPesquisa03.put("idpesquisa03", itemFormulario.getPesquisa03().getIdpesquisa03());
                jPesquisa03.put("descricao", itemFormulario.getPesquisa03().getDescricao());
                jPesquisa03.put("retornopesquisa", itemFormulario.getPesquisa03().getRetornopesquisa());
                jPesquisa03.put("ordem", itemFormulario.getPesquisa03().getOrdem());
                jPesquisa03.put("campotipooutros", itemFormulario.getPesquisa03().getCampotipooutros());
                JSONObject jPesquisa02valor = new JSONObject();
                jPesquisa02valor.put("idpesquisa02", itemFormulario.getPesquisa03().getPesquisa02().getIdpesquisa02());
                
                jPesquisa03.put("pesquisa02", jPesquisa02valor);

                jItemFormulario.put("pesquisa03", jPesquisa03);

                jLista.put(jItemFormulario);
            }

            jRetorno.put("itensFormulario", jLista);
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }finally {
            manager.close();
        }

        return Response.status(Response.Status.OK).entity(jRetorno.toString()).build();
    }

    @POST
    @Path("/obterItensFormularioAnalise")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterItensFormularioAnalise(String data)
    {
        JSONObject jRetorno = new JSONObject();
        JSONArray jLista = new JSONArray();
        EntityManager manager = null;

        try {
            manager = JpaUtil.getEntityManager();

            Pesquisa04Repository repository = new Pesquisa04Repository(manager);
            JSONObject parametros = new JSONObject(data);
            Integer idpesquisa01 = parametros.getInt("idpesquisa01");

            List<CE_Pesquisa04> itensFormulario = repository.obterItensFormularioAnalise(idpesquisa01);

            for (CE_Pesquisa04 itemFormulario : itensFormulario){
                JSONObject jItemFormulario = new JSONObject();
                jItemFormulario.put("idpesquisa04", itemFormulario.getIdpesquisa04());
                jItemFormulario.put("idpesquisa01", itemFormulario.getIdpesquisa01());
                jItemFormulario.put("descricao", itemFormulario.getDescricao());
                jItemFormulario.put("idpesquisa04pai", itemFormulario.getIdpesquisa04pai());
                jItemFormulario.put("tamanhoresposta", itemFormulario.getTamanhoresposta());
                jItemFormulario.put("qtdecimais", itemFormulario.getQtdecimais());
                jItemFormulario.put("vldefault", itemFormulario.getVldefault());
                jItemFormulario.put("qtrespostas", itemFormulario.getQtrespostas());
                if (itemFormulario.getDtinicio() != null)
                    jItemFormulario.put("dtinicio", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(itemFormulario.getDtinicio()));
                if (itemFormulario.getDtfim() != null)
                    jItemFormulario.put("dtfim", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(itemFormulario.getDtfim()));
                jItemFormulario.put("ordempergunta", itemFormulario.getOrdempergunta());
                jItemFormulario.put("obrigatoria", itemFormulario.getObrigatoria());

                JSONObject jPesquisa02 = new JSONObject();
                jPesquisa02.put("idpesquisa02", itemFormulario.getPesquisa02().getIdpesquisa02());
                jPesquisa02.put("tipodado", itemFormulario.getPesquisa02().getTipodado());


                jItemFormulario.put("pesquisa02", jPesquisa02);

                JSONObject jPesquisa03 = new JSONObject();
                jPesquisa03.put("idpesquisa03", itemFormulario.getPesquisa03().getIdpesquisa03());
                jPesquisa03.put("descricao", itemFormulario.getPesquisa03().getDescricao());
                jPesquisa03.put("retornopesquisa", itemFormulario.getPesquisa03().getRetornopesquisa());
                jPesquisa03.put("ordem", itemFormulario.getPesquisa03().getOrdem());

                jItemFormulario.put("pesquisa03", jPesquisa03);

                jLista.put(jItemFormulario);
            }

            jRetorno.put("itensFormulario", jLista);
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }finally {
            manager.close();
        }

        return Response.status(Response.Status.OK).entity(jRetorno.toString()).build();
    }

    @POST
    @Path("/enviarRespostas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response enviarRespostas(String data) {

        EntityManager manager = null;

        try {

            manager = JpaUtil.getEntityManager();
            
            Pesquisa07Repository repository = new Pesquisa07Repository(manager);

            JSONObject parametros = new JSONObject(data);
            JSONArray jRespostas = parametros.getJSONArray("respostas");
            
            if (jRespostas.length() > 0){
            	try {
            		manager.getTransaction().begin();
                	repository.insereLog(jRespostas.getJSONObject(0).getInt("idpesquisador"), "Iniciou envio das respostas");
                	manager.getTransaction().commit();
				} catch (Exception e) {
					manager.getTransaction().rollback();
				}
            }

            manager.getTransaction().begin();
            
            for (int i = 0; i < jRespostas.length(); i++) {
            	
            	try {
            		
            		CE_Pesquisa07 resposta = new CE_Pesquisa07();

                    CE_Pesquisa04 pesquisa04 = new CE_Pesquisa04();
                    pesquisa04.setIdpesquisa04(jRespostas.getJSONObject(i).getInt("idpesquisa04"));

                    CE_Pesquisa06 pesquisa06 = new CE_Pesquisa06();
                    pesquisa06.setIdpesquisa06(jRespostas.getJSONObject(i).getInt("idpesquisa06"));

                    CE_Cliente01 cliente01 = new CE_Cliente01();
                    cliente01.setIdcliente(jRespostas.getJSONObject(i).getInt("idcliente"));

                    CE_Pesquisa08 pesquisa08 = new CE_Pesquisa08();
                    pesquisa08.setIdpesquisador(jRespostas.getJSONObject(i).getInt("idpesquisador"));
                    
                    resposta.setChavepesquisa(jRespostas.getJSONObject(i).getString("chavepesquisa"));
                    resposta.setPesquisa04(pesquisa04);
                    resposta.setPesquisa06(pesquisa06);
                    resposta.setPesquisa08(pesquisa08);
                    resposta.setCliente(cliente01);
                    resposta.setIdpesquisa03(jRespostas.getJSONObject(i).getInt("idpesquisa03"));

                    if (jRespostas.getJSONObject(i).has("txresposta") && jRespostas.getJSONObject(i).get("txresposta") != null)
                        resposta.setTxresposta(jRespostas.getJSONObject(i).getString("txresposta"));

                    if (jRespostas.getJSONObject(i).has("vlresposta") && jRespostas.getJSONObject(i).get("vlresposta") != null)
                        resposta.setVlresposta(new BigDecimal(jRespostas.getJSONObject(i).getDouble("vlresposta")));
                    
                    if (jRespostas.getJSONObject(i).has("txrespostaoutros") && jRespostas.getJSONObject(i).get("txrespostaoutros") != null)
                        resposta.setTxrespostaoutros(jRespostas.getJSONObject(i).getString("txrespostaoutros"));

                    if (jRespostas.getJSONObject(i).has("vlrespostaoutros") && jRespostas.getJSONObject(i).get("vlrespostaoutros") != null)
                        resposta.setVlrespostaoutros(new BigDecimal(jRespostas.getJSONObject(i).getDouble("vlrespostaoutros")));

                    if (!repository.respostaExiste(resposta))    
                    	repository.salvarResposta(resposta);
                    
				} catch (Exception e) {
					manager.getTransaction().rollback();
					try {
						manager.getTransaction().begin();
						StringWriter sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						repository.insereLog(jRespostas.getJSONObject(i).getInt("idpesquisador"), sw.toString());
						manager.getTransaction().commit();
						throw e;
					} catch (Exception e2) {
						manager.getTransaction().rollback();
					}
					
				}
                
            }

            manager.getTransaction().commit();
        }catch (Exception e)
        {
            e.printStackTrace();
            manager.getTransaction().rollback();
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }
        finally {
            manager.close();
        }

        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/obterRespostasPorOnda")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterRespostasPorOnda(String data) {

        EntityManager manager = null;
        JSONObject jRetorno = new JSONObject();
        JSONArray jLista = new JSONArray();

        try{
            manager = JpaUtil.getEntityManager();
            Pesquisa07Repository repository = new Pesquisa07Repository(manager);
            JSONObject obj = new JSONObject(data);
            List<CE_Pesquisa07> respostas = repository.obterRespostasPorOnda(obj.getInt("idpesquisa06"));

            for (CE_Pesquisa07 resposta : respostas){
                JSONObject jResposta = new JSONObject();
                jResposta.put("chavepesquisa", resposta.getChavepesquisa());
                jResposta.put("txresposta", resposta.getTxresposta());
                jResposta.put("vlresposta", resposta.getVlresposta());
                jResposta.put("idpesquisa04", resposta.getPesquisa04().getIdpesquisa04());

                jLista.put(jResposta);
            }

            jRetorno.put("respostas", jLista);
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }finally {
            manager.close();
        }

        return Response.status(Response.Status.OK).entity(jRetorno.toString()).build();
    }

    @POST
    @Path("/obterRespostasAgrupadasPorOnda")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterRespostasAgrupadasPorOnda(String data) {

        EntityManager manager = null;
        JSONObject jRetorno = new JSONObject();
        JSONArray jLista = new JSONArray();

        try{
            manager = JpaUtil.getEntityManager();
            Pesquisa07Repository repository = new Pesquisa07Repository(manager);
            JSONObject parametros = new JSONObject(data);

            JSONArray jFiltros = parametros.getJSONArray("filtros");

            List<Filtro> filtros = new ArrayList<Filtro>();

            for (int i = 0; i < jFiltros.length(); i++) {
                Filtro filtro = new Filtro();
                filtro.setIdpesquisa04(jFiltros.getJSONObject(i).getInt("idpesquisa04"));

                filtro.setValores(new ArrayList<BigDecimal>());

                if (jFiltros.getJSONObject(i).getBoolean("temopcoes"))
                    filtro.setTemOpcoes(true);
                else
                    filtro.setTemOpcoes(false);

                JSONArray jValor = jFiltros.getJSONObject(i).getJSONArray("valores");

                for (int j = 0; j < jValor.length(); j++) {
                    filtro.getValores().add(new BigDecimal(jValor.getJSONObject(j).getDouble("vlresposta")));
                }

                filtros.add(filtro);
            }

            List<Object[]> respostas = repository.obterRespostasAgrupadasPorOnda(parametros.getInt("idpesquisa06"), parametros.getInt("idcliente"), filtros);

            for(Object[] obj : respostas){
                JSONObject jResposta = new JSONObject();
                jResposta.put("idpesquisa04", Integer.parseInt(obj[0].toString()));
                jResposta.put("txresposta", obj[2] != null ? obj[2].toString() : null);
                jResposta.put("vlresposta", obj[3] != null ? new BigDecimal(obj[3].toString()) : null);
                jResposta.put("quantidade", Integer.parseInt(obj[4].toString()));
                jResposta.put("percentual", Double.parseDouble(obj[5].toString()));
                jResposta.put("sequencial", Integer.parseInt(obj[6].toString()));
                jResposta.put("totalpercentual", Double.parseDouble(obj[7].toString()));

                jLista.put(jResposta);
            }

            jRetorno.put("respostas", jLista);
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }finally {
            manager.close();
        }

        return Response.status(Response.Status.OK).entity(jRetorno.toString()).build();
    }

    @POST
    @Path("/obterRespostasAgrupadasEntreOndas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterRespostasAgrupadasEntreOndas(String data) {

        EntityManager manager = null;
        JSONObject jRetorno = new JSONObject();
        JSONArray jLista = new JSONArray();

        try{
            manager = JpaUtil.getEntityManager();
            Pesquisa07Repository repository = new Pesquisa07Repository(manager);
            JSONObject parametros = new JSONObject(data);

            JSONArray jFiltros = parametros.getJSONArray("filtros");

            List<Filtro> filtros = new ArrayList<Filtro>();

            for (int i = 0; i < jFiltros.length(); i++) {
                Filtro filtro = new Filtro();
                filtro.setIdpesquisa04(jFiltros.getJSONObject(i).getInt("idpesquisa04"));

                filtro.setValores(new ArrayList<BigDecimal>());

                if (jFiltros.getJSONObject(i).getBoolean("temopcoes"))
                    filtro.setTemOpcoes(true);
                else
                    filtro.setTemOpcoes(false);

                JSONArray jValor = jFiltros.getJSONObject(i).getJSONArray("valores");

                for (int j = 0; j < jValor.length(); j++) {
                    filtro.getValores().add(new BigDecimal(jValor.getJSONObject(j).getDouble("vlresposta")));
                }

                filtros.add(filtro);
            }

            List<Object[]> respostas = repository.obterRespostasAgrupadasEntreOndas(parametros.getString("idsPesquisas06"), filtros);

            for(Object[] obj : respostas){
                JSONObject jResposta = new JSONObject();
                jResposta.put("totalpercentual", Double.parseDouble(obj[0].toString()));
                jResposta.put("idpesquisa04", Integer.parseInt(obj[1].toString()));
                jResposta.put("vlresposta", new BigDecimal(obj[3].toString()));
                jResposta.put("txresposta", obj[4] != null ? obj[4].toString() : null);
                jResposta.put("quantidade", Integer.parseInt(obj[5].toString()));
                jResposta.put("percentual", Double.parseDouble(obj[6].toString()));
                jResposta.put("sequencial", Integer.parseInt(obj[7].toString()));
                jResposta.put("quantidade1", Integer.parseInt(obj[8].toString()));
                jResposta.put("percentual1", Double.parseDouble(obj[9].toString()));
                jResposta.put("quantidade2", Integer.parseInt(obj[10].toString()));
                jResposta.put("percentual2", Double.parseDouble(obj[11].toString()));
                jResposta.put("quantidade3", Integer.parseInt(obj[12].toString()));
                jResposta.put("percentual3", Double.parseDouble(obj[13].toString()));
                jResposta.put("quantidade4", Integer.parseInt(obj[14].toString()));
                jResposta.put("percentual4", Double.parseDouble(obj[15].toString()));
                jResposta.put("quantidade5", Integer.parseInt(obj[16].toString()));
                jResposta.put("percentual5", Double.parseDouble(obj[17].toString()));
                jResposta.put("quantidade6", Integer.parseInt(obj[18].toString()));
                jResposta.put("percentual6", Double.parseDouble(obj[19].toString()));
                jLista.put(jResposta);
            }

            jRetorno.put("respostas", jLista);
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }finally {
            manager.close();
        }

        return Response.status(Response.Status.OK).entity(jRetorno.toString()).build();
    }
    
    private String obterJSON(List<Object[]> list){
    	
    	JSONArray jList = new JSONArray();
    	
    	for (Object[] obj : list) {
    		JSONObject jRetorno = new JSONObject();
    		for (int i = 0; i < obj.length; i++) {
        		jRetorno.put(i + "", obj[i]);
			}
    		
    		jList.put(jRetorno);
		}
    	
    	return jList.toString();
    }
    
    @POST
    @Path("/salvarParticipante")
    @Produces(MediaType.APPLICATION_JSON)
    public Response salvarParticipante(String data) {
    	
    	JSONObject jRetorno = new JSONObject();
        EntityManager manager = null;
        
    	try {
    		
    		manager = JpaUtil.getEntityManager();
    		manager.getTransaction().begin();
    		Participante01Repository repo = new Participante01Repository(manager);
    		
    		JSONObject jParticipante = new JSONObject(data);
    		
    		CE_Participante01 participante = new CE_Participante01();
    		participante.setIdparticipante01(jParticipante.has("idparticipante01") ? jParticipante.getInt("idparticipante01") : null);
    		participante.setIdcliente01(jParticipante.has("idcliente01") ? jParticipante.getInt("idcliente01") : null);
    		participante.setNome(jParticipante.has("nome") ? jParticipante.getString("nome") : null);
    		participante.setEmail(jParticipante.has("email") ? jParticipante.getString("email") : null);
    		participante.setTelefone(jParticipante.has("telefone") ? jParticipante.getString("telefone") : null);
    		participante.setInfoadicional(jParticipante.has("infoadicional") ? jParticipante.getString("infoadicional") : null);
    		participante.setQtqrcode(jParticipante.has("qtqrcode") ? jParticipante.getInt("qtqrcode") : null);
    		participante.setEmpresa(jParticipante.has("empresa") ? jParticipante.getString("empresa") : null);
    		
    		int idparticipante = repo.salvarParticipante(participante);
    		
    		jRetorno.put("idparticipante01", idparticipante);
    		
    		manager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			manager.getTransaction().rollback();
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}
    	
    	return Response.status(Response.Status.OK).entity(jRetorno.toString()).build();
    }
    
    @GET
    @Path("/obterParticipantes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterParticipantes()
    {
        JSONObject jRetorno = new JSONObject();
        JSONArray jLista = new JSONArray();
        EntityManager manager = null;

        try {

            manager = JpaUtil.getEntityManager();

            Participante01Repository repository = new Participante01Repository(manager);
            List<CE_Participante01> participantes = repository.obterParticipantes();

            for(CE_Participante01 participante : participantes){
                JSONObject jParticipante = new JSONObject();
                jParticipante.put("idparticipante01", participante.getIdparticipante01());
                jParticipante.put("idcliente01", participante.getIdcliente01());
                jParticipante.put("nome", participante.getNome());
                jParticipante.put("email", participante.getEmail());
                jParticipante.put("telefone", participante.getTelefone());
                jParticipante.put("infoadicional", participante.getInfoadicional());
                jParticipante.put("qtqrcode", participante.getQtqrcode());
                jParticipante.put("empresa", participante.getEmpresa());

                jLista.put(jParticipante);
            }

            jRetorno.put("participantes", jLista);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }
        finally {
            manager.close();
        }

        return Response.status(Response.Status.OK).entity(jRetorno.toString()).build();
    }
    
    @POST
    @Path("/executarSQL")
    @Produces(MediaType.APPLICATION_JSON)
    public Response executarSQL(String data) {
    
    	JSONObject jRetorno = new JSONObject();
        EntityManager manager = null;
        
        try{
        	manager = JpaUtil.getEntityManager();
        	manager.getTransaction().begin();
        	GenericRepository repo = new GenericRepository(manager);
        	
        	JSONObject jSQL = new JSONObject(data);
        	int tipo = jSQL.getInt("tipo");
        	
        	if (tipo == 1){
	        	List<Object[]> list = repo.executarSelect(jSQL.getString("sql"));
	        	
	        	String json = obterJSON(list);
	        	jRetorno.put("retorno", json);
        	}else{
        		int id = 0;
        		String sql = jSQL.getString("sql");
        		if (sql.toLowerCase().contains("returning"))
        			id = repo.executarUpdateRetorno(sql);
        		else
        			id = repo.executarUpdate(sql);
        		
        		jRetorno.put("retorno", id);
        	}
        	
        	manager.getTransaction().commit();
        }catch (Exception e) {
        	e.printStackTrace();
        	manager.getTransaction().rollback();
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}
        
        return Response.status(Response.Status.OK).entity(jRetorno.toString()).build();
    }
}
