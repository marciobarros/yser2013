package br.unirio.yser;

import java.io.IOException;

import br.unirio.yser.dblp.CarregadorPublicacoes;
import br.unirio.yser.dblp.GeradorPublicacaoConjunta;
import br.unirio.yser.model.ListaPesquisadores;

@SuppressWarnings("unused")
public class ProgramaPrincipal
{
	private static String DIRETORIO_BASE_INTERNACIONAL = "\\Users\\Marcio Barros\\Dropbox\\Academia\\Projetos\\2013\\YSER 2013\\Citacoes\\";

	private static String DIRETORIO_BASE_NACIONAL = "\\Users\\Marcio Barros\\Dropbox\\Academia\\Projetos\\2013\\YSER-BR 2013\\";

	public static void main(String[] args) throws IOException
	{
		String diretorioBase = DIRETORIO_BASE_INTERNACIONAL;
		//String diretorioBase = DIRETORIO_BASE_NACIONAL;
		
		ListaPesquisadores pesquisadores = new CarregadorPesquisadores().carregaPesquisadores(diretorioBase + "Indicacoes.csv");
		//new ProcessadorScholar(DIRETORIO_RAIZ).executa(pesquisadores);

		new CarregadorPublicacoes(diretorioBase).executa(pesquisadores);
		//new GeradorListaVeiculos().executa(pesquisadores.pegaPesquisadoresPublicacoesIndicacoes(2));
		//new GeradorPublicacaoConjunta().executa(pesquisadores.pegaPesquisadoresPublicacoesIndicacoes(2));

		System.out.println(pesquisadores.size() + " pesquisadores");
		System.out.println(pesquisadores.pegaPesquisadoresIndicados().size() + " pesquisadores com indicacoes");
		System.out.println(pesquisadores.pegaPesquisadoresIndicacoesMinimas(2).size() + " pesquisadores com pelo menos 2 indicacoes");

		System.out.println(pesquisadores.pegaPesquisadoresPublicacoes().size() + " pesquisadores com publica��es");
		System.out.println(pesquisadores.pegaPesquisadoresPublicacoesIndicacoes(2).size() + " pesquisadores com publica��es e pelo menos 2 indicacoes");
	}
}