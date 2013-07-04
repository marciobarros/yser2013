package br.unirio.yser.utils;

import java.util.ArrayList;
import java.util.List;

public class StringUtils
{
	public static List<String> tokenize(String s, char separador)
	{
		List<String> tokens = new ArrayList<String>();
		boolean dentroChaves = false;
		String token = "";
		
		for (int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			
			if (c == '"')
			{
				dentroChaves = !dentroChaves;
			}
			else if (c == separador && !dentroChaves)
			{
				tokens.add(token.trim());
				token = "";
			}
			else
			{
				token += c;
			}
		}
		
		tokens.add(token.trim());
		return tokens;
	}
}