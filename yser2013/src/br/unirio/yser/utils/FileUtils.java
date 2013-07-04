package br.unirio.yser.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtils
{
	public static List<String> loadTextFile(String nomeArquivo)
	{
		List<String> conteudo = new ArrayList<String>();
		File file = new File(nomeArquivo);

		if(!file.exists())
			return conteudo;
			
		try
		{
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
			String linha = null;

			while ((linha = input.readLine()) != null)
				if (linha.length() > 0)
					conteudo.add(linha);
		
			input.close();

		} catch (IOException ex)
		{
			ex.printStackTrace();
		}

		return conteudo;
	}
}