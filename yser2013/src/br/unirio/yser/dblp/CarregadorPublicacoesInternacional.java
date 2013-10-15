package br.unirio.yser.dblp;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.unirio.yser.model.ListaPesquisadores;
import br.unirio.yser.model.Pesquisador;
import br.unirio.yser.model.Publicacao;

public class CarregadorPublicacoesInternacional
{
	private String[] CONVERSAO_VEICULOS =
	{
			"Autom. Softw. Eng.", 							"ASE",
			"Business Process Management",					"BPM",
			"Concurrency - Practice and Experience",		"Concurrency and Computation: Practice and Experience",
			"Empirical Software Engineering Issues",		"Empirical Software Engineering",
			"ESEC / SIGSOFT FSE",							"ESEC/SIGSOFT FSE",
			"ESEC",											"ESEC/SIGSOFT FSE",
			"ICLP (Technical Communications)",				"ICLP",
			"T. Aspect-Oriented Software Development VI",	"T. Aspect-Oriented Software Development"
	};
	
	private String diretorioRaiz;
	
	public CarregadorPublicacoesInternacional(String diretorio)
	{
		this.diretorioRaiz = diretorio;
	}
	
	public void executa(ListaPesquisadores pesquisadores)
	{
		for (Pesquisador pesquisador : pesquisadores)
			carregaPublicacoes(pesquisador);
	}

	private void carregaPublicacoes(Pesquisador pesquisador)
	{
		try
		{
			File xmlFile = new File(diretorioRaiz + "DBLP\\" + pesquisador.getNome() + ".xml");
	
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
	 
			NodeList dblpList = doc.getElementsByTagName("dblp");
	 
			for (int i = 0; i < dblpList.getLength(); i++) 
			{ 
			   Element dblpNode = (Element) dblpList.item(i);
			   Element conferenceNode = getSingleElement(dblpNode, "inproceedings");
			   
			   if (conferenceNode != null)
			   {
				   carregaPublicacaoConferencia(pesquisador, conferenceNode);
				   continue;
			   }

			   Element journalNode = getSingleElement(dblpNode, "article");

			   if (journalNode != null)
			   {
				   carregaPublicacaoRevista(pesquisador, journalNode);
				   continue;
			   }
			   
			   Element proceedingsNode = getSingleElement(dblpNode, "proceedings");

			   if (proceedingsNode != null)
			   {
				   // IGNORA organização de conferências
				   continue;
			   }
			   
			   Element collectionNode = getSingleElement(dblpNode, "incollection");

			   if (collectionNode != null)
			   {
				   // IGNORA organização de livro
				   continue;
			   }
			   
			   Element bookNode = getSingleElement(dblpNode, "book");

			   if (bookNode != null)
			   {
				   // IGNORA escrita de livro
				   continue;
			   }
			   
			   Element phdThesisNode = getSingleElement(dblpNode, "phdthesis");

			   if (phdThesisNode != null)
			   {
				   // IGNORA teses de Doutorado
				   continue;
			   }
			   
			   System.out.println("Pelo menos uma publicação de " + pesquisador.getNome() + " tem um formato desconhecido");
			}

		} catch(IOException ioe)
		{
			System.out.println("Erro de I/O ao carregar a lista de publicações de " + pesquisador.getNome());

		} catch (ParserConfigurationException pce)
		{
			System.out.println("Erro de processamento de configuração ao carregar a lista de publicações de " + pesquisador.getNome());

		} catch (SAXException e)
		{
			System.out.println("Erro de formato de arquivo ao carregar a lista de publicações de " + pesquisador.getNome());
		}
	}

	private boolean carregaPublicacaoConferencia(Pesquisador pesquisador, Element raizPublicacao)
	{
		String title = getSingleElementValue(raizPublicacao, "title");
		 
		if (title == null)
		{
			System.out.println("Existe uma publicação de " + pesquisador.getNome() + " sem título");
			return false;
		}
		
		String year = getSingleElementValue(raizPublicacao, "year");
		 
		if (year == null)
		{
			System.out.println("Existe uma publicação de " + pesquisador.getNome() + " sem ano definido: " + title);
			return false;
		}
		
		String pages = getSingleElementValue(raizPublicacao, "pages", "0");
		int iPages = processaNumeroPaginas(pages);
		
		String venue = getSingleElementValue(raizPublicacao, "booktitle");

		if (venue == null)
		{
			System.out.println("Existe uma publicação de " + pesquisador.getNome() + " sem veículo definido: " + title);
			return false;
		}

		venue = processaVeiculo(venue);
		int iYear = Integer.parseInt(year);
		
		Publicacao publicacao = new Publicacao(title, venue, "", "", iPages, iYear, "");
		NodeList authorList = raizPublicacao.getElementsByTagName("author");
		 
		for (int i = 0; i < authorList.getLength(); i++) 
		{ 
		   Element node = (Element) authorList.item(i);
		   String autor = getTagValue(node);
		   publicacao.adicionaAutor(autor);
		}
		
		pesquisador.adicionaPublicacao(publicacao);
		return true;
	}

	private boolean carregaPublicacaoRevista(Pesquisador pesquisador, Element raizPublicacao)
	{
		String title = getSingleElementValue(raizPublicacao, "title");
		 
		if (title == null)
		{
			System.out.println("Existe uma publicação de " + pesquisador.getNome() + " sem título");
			return false;
		}
		
		String year = getSingleElementValue(raizPublicacao, "year");
		 
		if (year == null)
		{
			System.out.println("Existe uma publicação de " + pesquisador.getNome() + " sem ano definido: " + title);
			return false;
		}
		
		String pages = getSingleElementValue(raizPublicacao, "pages", "0");
		int iPages = processaNumeroPaginas(pages);

		String booktitle = getSingleElementValue(raizPublicacao, "booktitle");
		
		if (booktitle != null)			// IGNORA artigos em livros
			return true;
		
		String venue = getSingleElementValue(raizPublicacao, "journal");

		if (venue == null)
		{
			System.out.println("Existe uma publicação de " + pesquisador.getNome() + " sem veículo definido: " + title);
			return false;
		}

		String volume = getSingleElementValue(raizPublicacao, "volume");

		if (volume == null)
		{
			System.out.println("Existe uma publicação de " + pesquisador.getNome() + " sem volume definido: " + title);
			return false;
		}

		String number = getSingleElementValue(raizPublicacao, "number", "");

		venue = processaVeiculo(venue);
		int iYear = Integer.parseInt(year);
		
		Publicacao publicacao = new Publicacao( title, venue, volume, number, iPages, iYear, "");		
		NodeList authorList = raizPublicacao.getElementsByTagName("author");
		 
		for (int i = 0; i < authorList.getLength(); i++) 
		{ 
		   Element node = (Element) authorList.item(i);
		   String autor = getTagValue(node);
		   publicacao.adicionaAutor(autor);
		}
		
		pesquisador.adicionaPublicacao(publicacao);
		return true;
	}

	private String processaVeiculo(String veiculo)
	{
		int posicaoAbreParenteses = veiculo.indexOf('(');
		
		if (posicaoAbreParenteses != -1)
		{
			int posicaoFechaParenteses = posicaoAbreParenteses + 1;
			
			while (posicaoFechaParenteses < veiculo.length() && veiculo.charAt(posicaoFechaParenteses) >= '0' && veiculo.charAt(posicaoFechaParenteses) <= '9')
				posicaoFechaParenteses++;
			
			if (posicaoFechaParenteses < veiculo.length() && veiculo.charAt(posicaoFechaParenteses) == ')')
				veiculo = veiculo.substring(0, posicaoAbreParenteses).trim();
		}
		
		for (int i = 0; i < CONVERSAO_VEICULOS.length; i += 2)
		{
			if (veiculo.compareToIgnoreCase(CONVERSAO_VEICULOS[i]) == 0)
			{
				veiculo = CONVERSAO_VEICULOS[i+1];
				break;
			}
		}
		
		return veiculo;
	}

	private int processaNumeroPaginas(String pages)
	{
		if (pages.length() == 0)
			return 0;
		
		int posicaoVirgula = pages.indexOf(',');
		
		if (posicaoVirgula != -1)
			return processaNumeroPaginas(pages.substring(0, posicaoVirgula).trim()) + processaNumeroPaginas(pages.substring(posicaoVirgula+1).trim());
		
		int posicaoTraco = pages.indexOf('-');
		
		if (posicaoTraco == pages.length()-1)
			return 0;
		
		if (posicaoTraco != -1)
		{
			int paginaInicial = Integer.parseInt(pages.substring(0, posicaoTraco));
			int paginaFinal = Integer.parseInt(pages.substring(posicaoTraco+1));
			
			if (paginaFinal <= paginaInicial)
				return 0;
			
			return paginaFinal - paginaInicial + 1;
		}

		// confirma que é um número
		Integer.parseInt(pages);
		return 1;
	}
	
	private Element getSingleElement(Element root, String name)
	{
		NodeList nodeList = root.getElementsByTagName(name);
		 
		if (nodeList.getLength() > 0)
			return (Element) nodeList.item(0);
		
		return null;
	}
	
	private String getTagValue(Element eElement) 
	{
		NodeList nodeList = eElement.getChildNodes();
		
		if (nodeList.getLength() > 0)
			return nodeList.item(0).getNodeValue().toString();
		
		return "";
	}
	
	private String getSingleElementValue(Element root, String name)
	{
		Element element = getSingleElement(root, name);
		
		if (element != null)
			return getTagValue(element);
		
		return null;
	}
	
	private String getSingleElementValue(Element root, String name, String _def)
	{
		Element element = getSingleElement(root, name);
		
		if (element != null)
			return getTagValue(element);
		
		return _def;
	}
}