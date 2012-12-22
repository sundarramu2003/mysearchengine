package org.apache.lucene.demo;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import info.bliki.wiki.filter.PlainTextConverter;
import info.bliki.wiki.model.WikiModel;


public class XMLParser {
	XMLParser(String xmlfile, String output) {
 
	  SAXBuilder builder = new SAXBuilder();
	  builder.setValidation(false);
	  builder.setIgnoringElementContentWhitespace(true);
	  //File xmlFile = new File("D:\\The_Simpsons.xml");
	  //File xmlFile = new File(args[0]);
	  File xmlFile = new File(xmlfile);
	  try {
 
		Document document = (Document) builder.build(xmlFile);
			/*System.out.println("before building");
		URL url = new URL("http://en.wikipedia.org/wiki/Sachin_Tendulkar");
		  Document document = (Document) builder.build(url);  
		  System.out.println("after building");
		*/
		  /*
		  System.out.println("before building");
		  URL url = new URL("http://en.wikipedia.org/wiki/Sachin_Tendulkar");
		  SAXBuilder parser = new SAXBuilder();
		  Document doc = null;
		  doc = parser.build(url);
		  System.out.println("after building");
		  */
		Element rootNode = document.getRootElement();
		System.out.println("rootNode"+rootNode);
		Namespace ns=Namespace.getNamespace("http://www.mediawiki.org/xml/export-0.5/");
		
		List list = rootNode.getChildren("page", ns);
		
		Element pagenode=null;
		
		for (int i=0;i<list.size();i++)
		{
		pagenode=(Element) list.get(i);
		String WikiText = pagenode.getChildText("title",ns);
		 
	
 		//System.out.println("WikiText"+WikiText);
        WikiModel wikiModel = new WikiModel("http://www.mywiki.com/wiki/${image}", "http://www.mywiki.com/wiki/${title}");
        String wikititle = wikiModel.render(new PlainTextConverter(), WikiText);

        wikititle=wikititle.replaceAll(":", "");
        wikititle=wikititle.replaceAll("/", "");        
        
		Element revisionnode=pagenode.getChild("revision",ns);
		WikiText = revisionnode.getChildText("text",ns);
		//System.out.println("WikiText"+WikiText);
        String plainStr = wikiModel.render(new PlainTextConverter(), WikiText);

        
        FileWriter fstream = new FileWriter(output+"\\"+wikititle);
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(plainStr);
        out.close();

        
		}
		
		
	  } catch (IOException io) {
		System.out.println(io.getMessage());
	  } catch (JDOMException jdomex) {
		System.out.println(jdomex.getMessage());
	  }
	}
}