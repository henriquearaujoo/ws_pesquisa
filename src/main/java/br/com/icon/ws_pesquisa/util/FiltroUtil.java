package br.com.icon.ws_pesquisa.util;

import org.json.JSONObject;

public class FiltroUtil {

	public static Filtro getFiltroDescricao(String dados){
		JSONObject object = new JSONObject(dados);
		Filtro filtro = new Filtro();

		filtro.setDescricao(object.has("descricao") && object.getString("descricao") != null ? object.getString("descricao") : null);

		return filtro;
	}

	public static Filtro getFiltroId(String dados){
		JSONObject object = new JSONObject(dados);
		Filtro filtro = new Filtro();

		filtro.setId(object.has("id") && object.getLong("id") != 0 ? object.getLong("id") : null);

		return filtro;
	}

}
