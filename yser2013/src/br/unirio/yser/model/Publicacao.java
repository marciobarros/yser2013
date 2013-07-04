package br.unirio.yser.model;

import java.util.ArrayList;
import java.util.List;

public class Publicacao
{
	private List<String> autores;
	private String titulo;
	private String veiculo;
	private String volume;
	private String numero;
	private int paginas;
	private int ano;
	private String publicador;
	
	public Publicacao(String titulo, String veiculo, String volume, String numero, int paginas, int ano, String publicador)
	{
		this.autores = new ArrayList<String>();
		this.titulo = titulo;
		this.veiculo = veiculo;
		this.volume = volume;
		this.numero = numero;
		this.paginas = paginas;
		this.ano = ano;
		this.publicador = publicador;
	}
	
	public String getTitulo()
	{
		return titulo;
	}
	
	public String getVeiculo()
	{
		return veiculo;
	}
	
	public String getVolume()
	{
		return volume;
	}
	
	public String getNumero()
	{
		return numero;
	}

	public int getPaginas()
	{
		return paginas;
	}

	public int getAno()
	{
		return ano;
	}

	public String getPublicador()
	{
		return publicador;
	}
	
	public void adicionaAutor(String autor)
	{
		this.autores.add(autor);
	}

	public boolean contemAutor(String acronimo)
	{
		for (String autor : autores)
			if (autor.compareToIgnoreCase(acronimo) == 0)
				return true;
		
		return false;
	}
	
	public Iterable<String> getAutores()
	{
		return autores;
	}
}