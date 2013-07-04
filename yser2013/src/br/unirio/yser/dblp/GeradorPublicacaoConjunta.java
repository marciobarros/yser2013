package br.unirio.yser.dblp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.unirio.yser.model.Pesquisador;
import br.unirio.yser.model.Publicacao;

public class GeradorPublicacaoConjunta
{
	private static String[] CONVERSOES =
	{
		"Ahmed E. Hassan", 					"Ahmed Hassan",
		"Alessandro F. Garcia",				"Alessandro Garcia",
		"Andrew J. Ko",						"Andrew Ko",
		"Chanchal K. Roy",					"Chanchal Roy",
		"Christian K‰stner",				"Christian Kaestner",
		"Cleidson R. B. de Souza",			"Cleidson de Souza",
		"Emerson R. Murphy-Hill",			"Emerson Murphy-Hill",
		"James A. Jones",					"James Jones",
		"Jane Cleland-Huang",				"Jane Huang",
		"Travis D. Breaux",					"Travis Breaux",
		"Alexander B. Romanovsky",			"Alexander Romanovsky",
		"Christina von Flach G. Chavez",	"Christina Chavez",
		"Jaelson Brelaz de Castro",			"Jaelson Castro",
		"Andrew Jensen Ko",					"Andrew Ko",
		"Andy Ko",							"Andrew Ko",
		"Laurie A. Williams",				"Laurie Williams",
		"Harald C. Gall",					"Harald Gall",
		"Daniela E. Damian",				"Daniela Damian",
		"Cathrin Weiﬂ",						"Cathrin Weiss",
		"S. C. Cheung",						"Shing-Chi Cheung",
		"Martin P. Robillard",				"Martin Robillard"
	};
	
	public void executa(List<Pesquisador> pesquisadores)
	{
		for (Pesquisador pesquisador : pesquisadores)
		{
			List<String> coAutores = new ArrayList<String>();
			
			for (Publicacao publicacao : pesquisador.pegaPublicacoes())
				for (String coAutor : publicacao.getAutores())
					coAutores.add(coAutor);
			
			Collections.sort(coAutores);

			String ultimoCoAutor = coAutores.get(0);
			int contador = 1;
			
			for (int i = 1; i < coAutores.size(); i++)
			{
				String coAutor = converteNome(coAutores.get(i));
				
				if (ultimoCoAutor.compareToIgnoreCase(coAutor) == 0)
				{
					contador++;
				}
				else
				{
					if (ultimoCoAutor.compareToIgnoreCase(pesquisador.getNome()) != 0)
						System.out.println(pesquisador.getNome() + "\t" + ultimoCoAutor + "\t" + contador);
					
					ultimoCoAutor = coAutor;
					contador = 1;
				}
			}
			
			if (ultimoCoAutor.compareToIgnoreCase(pesquisador.getNome()) != 0)
				System.out.println(pesquisador.getNome() + "\t" + ultimoCoAutor + "\t" + contador);
		}
	}
	
	private String converteNome(String nome)
	{
		for (int i = 0; i < CONVERSOES.length; i += 2)
			if (nome.compareToIgnoreCase(CONVERSOES[i]) == 0)
				return CONVERSOES[i+1];
		
		return nome;
	}
}