package br.unirio.yser.gscholar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;

import br.unirio.yser.model.ListaPesquisadores;
import br.unirio.yser.model.Pesquisador;
import br.unirio.yser.model.Publicacao;
import br.unirio.yser.utils.DistanciaLevenstein;

public class ProcessadorPesquisadores
{
	private static String[] DE_PARA_PESQUISADORES = 
	{
		"KASTNER, C.", "KAESTNER, C.",
		"ZHOU, Y.", "ZOU, Y.",
		"BEGEL, A.B.", "BEGEL, A.",
		"DE SOUZA, C.R.", "SOUZA, C.",
		"DE SOUZA, C.", "SOUZA, C.",
		"DE SOUZA, C.R.B.", "SOUZA, C.",
		"D’AMBROS, M.", "D'AMBROS, M.",
		"HASSAN, A.E.", "HASSAN, A.",
		"HASSAN, AE", "HASSAN, A.",
		"HOLMES, R.T.", "HOLMES, R.",
		"KÄSTNER, C.", "KAESTNER, C.",
		"KIM, SB", "KIM, S.",
		"KO, A.J.", "KO, A.",
		"MURPHY, G.C.", "MURPHY, G.",
		"MURPHY, GC", "MURPHY, G.",
		"ROY, C.K.", "ROY, C.",
		"TANTER, E.", "TANTER, É.",
		"TANTER, É.", "TANTER, É.",
		"WAGSTROM, P.A.", "WAGSTROM, P.",
		"WAGSTROM, PA", "WAGSTROM, P.",
		"ZOU, Y.J.", "ZOU, Y.",
		"ÂDAMS, B.", "ADAMS, B.",
		"KÄSTNER, C.", "KAESTNER, C.",
		"ROBILLARD, M.", "P. ROBILLARD, M.",
		"TANTERX, E.", "TANTER, É.",
		"DI PENTA, G.A.M.", "DI PENTA, M.",
		"GUEHENEUC, Y.", "GUEHENEUC, Y.",
		"PASAREANU, C.S.", "PASAREANU, C.",
		"ROBILLARD, M.P.", "ROBILLARD, M.",
		"ZIMMERMANN, T.M.J.", "ZIMMERMANN, T."
	};

	public void salvaPesquisadores(String nomeArquivo, ListaPesquisadores pesquisadores) throws IOException
	{
		ArrayList<String> autores = new ArrayList<String>();
		
		for (Pesquisador pesquisador : pesquisadores)
			for (Publicacao publicacao : pesquisador.pegaPublicacoes())
				for (String autor : publicacao.getAutores())
					if (autores.indexOf(autor.toUpperCase()) == -1)
						autores.add(autor.toUpperCase());
		
		Collections.sort(autores);

	    Writer out = new OutputStreamWriter(new FileOutputStream(new File(nomeArquivo)), "UTF8");
	    DistanciaLevenstein dl = new DistanciaLevenstein(2);

	    for (String autor : autores)
	    {
	    	int minDistancia = 6;
	    	Pesquisador proximo = null;
	    	
	    	for (Pesquisador pesquisador : pesquisadores)
	    	{
	    		int distancia = dl.calcula(autor, pesquisador.pegaAcronimo().toUpperCase());
	    		
	    		if (distancia < minDistancia)
	    		{
	    			minDistancia = distancia;
	    			proximo = pesquisador;
	    		}
	    	}
	    	
	    	if (proximo != null)
	    		out.write(autor + "\t" + proximo.pegaAcronimo().toUpperCase() + "\t" + minDistancia + "\n");
	    }
	    
		out.close();
	}

	public String convertePesquisador(String autorOriginal)
	{
		autorOriginal = autorOriginal.trim();
		
		for (int i = 0; i < DE_PARA_PESQUISADORES.length; i += 2)
		{
			String nome = DE_PARA_PESQUISADORES[i];
			
			if (autorOriginal.compareToIgnoreCase(nome) == 0)
				return DE_PARA_PESQUISADORES[i+1];
		}
		
		return autorOriginal;
	}
}