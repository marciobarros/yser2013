package br.unirio.yser;

import java.io.IOException;

import br.unirio.yser.dblp.CarregadorPublicacoes;
import br.unirio.yser.dblp.GeradorPublicacaoConjunta;
import br.unirio.yser.model.ListaPesquisadores;

public class ProgramaPrincipal
{
	private static String DIRETORIO_RAIZ = "\\Users\\Marcio Barros\\Documents\\My Dropbox\\Academia\\Projetos\\2013\\YSER 2013\\Citacoes\\";

	public static void main(String[] args) throws IOException
	{
		ListaPesquisadores pesquisadores = new CarregadorPesquisadores().carregaPesquisadores(DIRETORIO_RAIZ + "Indicacoes.csv");
		//new ProcessadorScholar(DIRETORIO_RAIZ).executa(pesquisadores);

		new CarregadorPublicacoes(DIRETORIO_RAIZ).executa(pesquisadores);
		//new GeradorListaVeiculos().executa(pesquisadores.pegaPesquisadoresPublicacoesIndicacoes(2));
		new GeradorPublicacaoConjunta().executa(pesquisadores.pegaPesquisadoresPublicacoesIndicacoes(2));

		System.out.println(pesquisadores.size() + " pesquisadores");
		System.out.println(pesquisadores.pegaPesquisadoresIndicados().size() + " pesquisadores com indicacoes");
		System.out.println(pesquisadores.pegaPesquisadoresIndicacoesMinimas(2).size() + " pesquisadores com pelo menos 2 indicacoes");

		System.out.println(pesquisadores.pegaPesquisadoresPublicacoes().size() + " pesquisadores com publicações");
		System.out.println(pesquisadores.pegaPesquisadoresPublicacoesIndicacoes(2).size() + " pesquisadores com publicações e pelo menos 2 indicacoes");
	}
}