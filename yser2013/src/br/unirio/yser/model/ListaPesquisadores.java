package br.unirio.yser.model;

import java.util.ArrayList;
import java.util.List;

public class ListaPesquisadores extends ArrayList<Pesquisador>
{
	private static final long serialVersionUID = 5584602159681120856L;

	public Pesquisador pegaPesquisadorNome(String nome)
	{
		for (Pesquisador p : this)
			if (p.getNome().compareToIgnoreCase(nome) == 0)
				return p;
		
		return null;
	}
	
	public Pesquisador pegaPesquisadorAcronimo(String acronimo)
	{
		for (Pesquisador pesquisador : this)
			if (pesquisador.pegaAcronimo().compareToIgnoreCase(acronimo) == 0)
				return pesquisador;
		
		return null;
	}
	
	public Pesquisador adicionaPesquisador(String nome)
	{
		Pesquisador pesquisador = new Pesquisador(nome);
		add(pesquisador);
		return pesquisador;
	}

	public List<Pesquisador> pegaPesquisadoresIndicacoesMinimas(int minimoIndicacoes)
	{
		List<Pesquisador> resultado = new ArrayList<Pesquisador>();
		
		for (Pesquisador p : this)
			if (p.contaIndicantes() >= minimoIndicacoes)
				resultado.add(p);
		
		return resultado;
	}

	public List<Pesquisador> pegaPesquisadoresIndicados()
	{
		return pegaPesquisadoresIndicacoesMinimas(1);
	}

	public List<Pesquisador> pegaPesquisadoresPublicacoes()
	{
		List<Pesquisador> resultado = new ArrayList<Pesquisador>();
		
		for (Pesquisador p : this)
			if (p.pegaNumeroPublicacoes() > 0)
				resultado.add(p);
		
		return resultado;
	}

	public List<Pesquisador> pegaPesquisadoresPublicacoesIndicacoes(int minimoIndicacoes)
	{
		List<Pesquisador> resultado = new ArrayList<Pesquisador>();
		
		for (Pesquisador p : this)
			if (p.pegaNumeroPublicacoes() > 0 && p.contaIndicantes() >= minimoIndicacoes)
				resultado.add(p);
		
		return resultado;
	}
}