package br.unirio.yser.dblp;

import java.util.List;

import br.unirio.yser.model.Pesquisador;
import br.unirio.yser.model.Publicacao;

public class GeradorMineracaoSequencial
{
	public void executa(List<Pesquisador> pesquisadores)
	{
		for (Pesquisador pesquisador : pesquisadores)
		{
			for (Publicacao publicacao : pesquisador.pegaPublicacoes())
			{
				String nome = publicacao.getVeiculo().replace(" ", "_").replace(".", "");
				System.out.print(nome + "\t");
			}
			
			System.out.println();
		}
	}
}