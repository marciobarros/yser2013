package br.unirio.yser.dblp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.unirio.yser.model.Pesquisador;
import br.unirio.yser.model.Publicacao;

public class GeradorMineracaoSequencial
{
	public void executa(List<Pesquisador> pesquisadores)
	{
		for (Pesquisador pesquisador : pesquisadores)
		{
			ArrayList<Publicacao> publicacoes = new ArrayList<Publicacao>();

			for (Publicacao publicacao : pesquisador.pegaPublicacoes())
				publicacoes.add(publicacao);
			
			Collections.sort(publicacoes, new ComparadorPublicacoes());
			
			if (publicacoes.size() > 0)
			{
				int ultimoAno = publicacoes.get(publicacoes.size()-1).getAno();
				
				for (int i = publicacoes.size()-1; i >= 0; i--)
				{
					Publicacao publicacao = publicacoes.get(i);
					
					if (publicacao.getAno() < ultimoAno)
					{
						System.out.print("-1\t");
						ultimoAno = publicacao.getAno();
					}
						
					String nome = publicacao.getVeiculo().replace(" ", "_").replace(".", "").replace(",", "").replace("(", "").replace(")", "");
					System.out.print(nome + "\t");
				}
			
				System.out.println("-2");
			}
		}
	}
}

class ComparadorPublicacoes implements Comparator<Publicacao>
{
	@Override
	public int compare(Publicacao p1, Publicacao p2)
	{
		return p1.getAno() - p2.getAno();
	}	
}