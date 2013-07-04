package br.unirio.yser.gscholar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import br.unirio.yser.model.ListaPesquisadores;
import br.unirio.yser.model.Pesquisador;
import br.unirio.yser.model.Publicacao;
import br.unirio.yser.utils.FileUtils;
import br.unirio.yser.utils.StringUtils;

public class ProcessadorScholar
{
	private String diretorioRaiz;
	
	public ProcessadorScholar(String diretorio)
	{
		this.diretorioRaiz = diretorio;
	}
	
	private int safeParseInt(String s)
	{
		try
		{
			return Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			return 0;
		}
	}

	private String processaVeiculo(String veiculo)
	{
		return veiculo;
	}
	
	private void carregaPublicacoesPesquisador(Pesquisador pesquisador)
	{
		List<String> publicacoes = FileUtils.loadTextFile(diretorioRaiz + "GScholar\\" + pesquisador.getNome() + ".csv");
		
		for (int i = 1; i < publicacoes.size(); i++)
		{
			List<String> tokens = StringUtils.tokenize(publicacoes.get(i), ',');

			if (tokens.size() == 8)
			{
				String nomesAutores = tokens.get(0);
				String titulo = tokens.get(1);
				String veiculo = tokens.get(2);
				String volume = tokens.get(3);
				String numero = tokens.get(4);
				int paginas = safeParseInt(tokens.get(5));
				int ano = safeParseInt(tokens.get(6));
				String publicador = tokens.get(7);
				
				veiculo = processaVeiculo(veiculo);
				
				if (veiculo != null)
				{
					Publicacao publicacao = new Publicacao(titulo, veiculo, volume, numero, paginas, ano, publicador);
					
					List<String> autores = StringUtils.tokenize(nomesAutores, ';');
					
					for (String autor : autores)
						if (autor.length() > 0)
							publicacao.adicionaAutor(autor);
					
					if (publicacao.contemAutor(pesquisador.pegaAcronimo()))
						pesquisador.adicionaPublicacao(publicacao);
					else
						System.out.println("A pesquisa #" + i + " de '" + pesquisador.getNome() + "' não referencia o pesquisador");
				}
			}			
		}
	}

	private void carregaPublicacoes(ListaPesquisadores pesquisadores)
	{
		for (Pesquisador pesquisador : pesquisadores)
			carregaPublicacoesPesquisador(pesquisador);
	}
	
	private void geraPublicacoesVeiculo(String nomeArquivo, ListaPesquisadores pesquisadores) throws IOException
	{
		ProcessadorVeiculos pv = new ProcessadorVeiculos();
		ProcessadorPesquisadores pa = new ProcessadorPesquisadores();
	    Writer out = new OutputStreamWriter(new FileOutputStream(new File(nomeArquivo)), "UTF8");
		
		for (Pesquisador pesquisador : pesquisadores)
		{
			for (Publicacao publicacao : pesquisador.pegaPublicacoes())
			{
				String veiculoOriginal = publicacao.getVeiculo();				
				String veiculoConvertido = pv.converteVeiculo(veiculoOriginal);
				
				if (veiculoConvertido.length() > 0)
				{
					out.write(pesquisador.getNome() + "\t" + veiculoConvertido);
					
					for (String autor : publicacao.getAutores())
					{
						String acronimoConvertido = pa.convertePesquisador(autor);						
						Pesquisador coautor = pesquisadores.pegaPesquisadorAcronimo(acronimoConvertido);
						
						if (coautor != null && coautor != pesquisador)
							out.write("\t" + coautor.getNome());
					}

					out.write("\n");
				}
			}
		}
		
		out.close();
	}
	
	public void executa(ListaPesquisadores pesquisadores) throws IOException
	{
		carregaPublicacoes(pesquisadores);

		ProcessadorVeiculos pv = new ProcessadorVeiculos();
		pv.verificaConsistenciaVeiculos(pesquisadores);
		pv.salvaVeiculos(diretorioRaiz + "\\veiculos.txt", pesquisadores);
		
		ProcessadorPesquisadores pa = new ProcessadorPesquisadores();
		pa.salvaPesquisadores(diretorioRaiz + "\\pesquisadores.txt", pesquisadores);
		geraPublicacoesVeiculo(diretorioRaiz + "\\publicacoes conjuntas.txt", pesquisadores);
	}
}