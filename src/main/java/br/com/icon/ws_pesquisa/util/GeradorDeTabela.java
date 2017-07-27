package br.com.icon.ws_pesquisa.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GeradorDeTabela {

	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ws_app");
	    factory.close();
	}
	
}
