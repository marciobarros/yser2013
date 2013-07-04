package br.unirio.yser.model;

import java.util.ArrayList;
import java.util.List;

public class Pesquisador
{
	private String nome;
	private List<Pesquisador> indicantes;
	private List<Publicacao> publicacoes;
	
	public Pesquisador(String nome)
	{
		this.nome = nome;
		this.indicantes = new ArrayList<Pesquisador>();
		this.publicacoes = new ArrayList<Publicacao>();
	}
	
	public String getNome()
	{
		return nome;
	}

	public String pegaAcronimo()
	{
		int posicao = nome.indexOf(' ');
		
		if (posicao == -1)
			return "";
		
		return nome.substring(posicao + 1) + ", " + nome.charAt(0) + ".";
	}
	
	public void adicionaIndicante(Pesquisador pesquisador)
	{
		this.indicantes.add(pesquisador);
	}

	public int contaIndicantes()
	{
		return indicantes.size();
	}

	public Iterable<Pesquisador> pegaIndicantes()
	{
		return indicantes;
	}
	
	public void adicionaPublicacao(Publicacao publicacao)
	{
		this.publicacoes.add(publicacao);
	}

	public int pegaNumeroPublicacoes()
	{
		return publicacoes.size();
	}

	public Iterable<Publicacao> pegaPublicacoes()
	{
		return publicacoes;
	}
}