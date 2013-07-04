package br.unirio.yser.dblp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.unirio.yser.model.Pesquisador;
import br.unirio.yser.model.Publicacao;

public class GeradorListaVeiculos
{
	public void executa(List<Pesquisador> pesquisadores)
	{
		ListaVeiculosDBLP veiculos = new ListaVeiculosDBLP();
		
		for (Pesquisador pesquisador : pesquisadores)
			for (Publicacao publicacao : pesquisador.pegaPublicacoes())
				veiculos.adicionaVeiculo(pesquisador, publicacao.getVeiculo(), publicacao.getPaginas());
		
		Collections.sort(veiculos, new ComparadorVeiculosDBLP());
		System.out.print("VEICULO\tREFS\tPESQU");
		
		for (int i = 0; i <= VeiculoDBLP.MAXPAGINAS; i++)
			System.out.print("\t<=" + i + "pg");
		
		System.out.println("\t>20pg");
		
		for (VeiculoDBLP veiculo : veiculos)
		{
			System.out.print(veiculo.getNome() + "\t" + veiculo.getContadorTotal() + "\t" + veiculo.getContadorPesquisadores());
			
			for (int i = 0; i <= VeiculoDBLP.MAXPAGINAS; i++)
				System.out.print("\t" + veiculo.getContadorPaginas(i));
			
			System.out.println("\t" + veiculo.getContadorGigantes());
		}
	}
}

class VeiculoDBLP
{
	public static final int MAXPAGINAS = 20;
	
	private String nome;
	private List<Pesquisador> pesquisadores;
	private int[] contadorPaginas;
	private int contadorGigantes;	
	private int contadorTotal;
	
	public VeiculoDBLP(String nome)
	{
		this.nome = nome;
		this.pesquisadores = new ArrayList<Pesquisador>();
		this.contadorPaginas = new int[MAXPAGINAS+1];
		this.contadorGigantes = 0;
		this.contadorTotal = 0;
	}
	
	public String getNome()
	{
		return nome;
	}
	
	public VeiculoDBLP ping(Pesquisador pesquisador, int paginas)
	{
		if (!pesquisadores.contains(pesquisador))
			pesquisadores.add(pesquisador);

		for (int i = 0; i < contadorPaginas.length; i++)
			if (paginas <= i)
				contadorPaginas[i]++;
		
		if (paginas >= contadorPaginas.length)
			contadorGigantes++;
		
		contadorTotal++;
		return this;
	}
	
	public int getContadorPesquisadores()
	{
		return pesquisadores.size();
	}

	public int getContadorPaginas(int paginas)
	{
		return contadorPaginas[paginas];
	}
	
	public int getContadorGigantes()
	{
		return contadorGigantes;
	}
	
	public int getContadorTotal()
	{
		return contadorTotal;
	}
}

class ListaVeiculosDBLP extends ArrayList<VeiculoDBLP>
{
	private static final long serialVersionUID = -2936736775287192507L;

	public void adicionaVeiculo(Pesquisador pesquisador, String nomeVeiculo, int paginas)
	{
		for (VeiculoDBLP veiculo : this)
		{
			if (veiculo.getNome().compareToIgnoreCase(nomeVeiculo) == 0)
			{
				veiculo.ping(pesquisador, paginas);
				return;
			}
		}
		
		add(new VeiculoDBLP(nomeVeiculo).
				ping(pesquisador, paginas));
	}
}

class ComparadorVeiculosDBLP implements Comparator<VeiculoDBLP>
{
	@Override
	public int compare(VeiculoDBLP veiculo1, VeiculoDBLP veiculo2)
	{
		return veiculo1.getContadorTotal() - veiculo2.getContadorTotal();
	}
}