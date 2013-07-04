package br.unirio.yser;

import java.util.List;

import br.unirio.yser.model.ListaPesquisadores;
import br.unirio.yser.model.Pesquisador;
import br.unirio.yser.utils.FileUtils;
import br.unirio.yser.utils.StringUtils;

public class CarregadorPesquisadores
{
	public ListaPesquisadores carregaPesquisadores(String nomeArquivo)
	{
		ListaPesquisadores pesquisadores = new ListaPesquisadores();
		List<String> indicacoes = FileUtils.loadTextFile(nomeArquivo);
		
		for (int i = 1; i < indicacoes.size(); i++)
		{
			List<String> tokens = StringUtils.tokenize(indicacoes.get(i), ',');
			
			if (tokens.size() == 2)
			{
				String nomeIndicante = tokens.get(0);
				String nomeIndicado = tokens.get(1);
				
				Pesquisador pesquisadorIndicante = pesquisadores.pegaPesquisadorNome(nomeIndicante);
				
				if (pesquisadorIndicante == null)
					pesquisadorIndicante = pesquisadores.adicionaPesquisador(nomeIndicante);
	
				Pesquisador pesquisadorIndicado = pesquisadores.pegaPesquisadorNome(nomeIndicado);
				
				if (pesquisadorIndicado == null)
					pesquisadorIndicado = pesquisadores.adicionaPesquisador(nomeIndicado);
				
				pesquisadorIndicado.adicionaIndicante(pesquisadorIndicante);
			}
		}
		
		return pesquisadores;
	}
}