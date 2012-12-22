package org.apache.lucene.demo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.Gson;
class Google1
{
public static void main(String[] args) throws Exception {
int i,j,var = 1;



String out1="", out2="", s;

String searchterms[]={"DNA","Apple","Epigenetics","Hollywood","Maya","Microsoft","Precision","Tuscany","99 balloons","Computer Programming","Financial meltdown","Justin Timberlake","Least Squares","Mars robots","Page six","Roman Empire","Solar energy","Statistical Significance","Steve Jobs","The Maya","Triple Cross","US Constitution","Eye of Horus","Madam I’m Adam","Mean Average Precision","Physics Nobel Prizes","Read the manual","Spanish Civil War","Do geese see god","Much ado about nothing"};
//String searchterms[]={"DNA","Apple","Epigenetics","Hollywood","Maya","Microsoft","Precision","Tuscany","99 balloons","Computer Programming","Financial meltdown","Justin Timberlake","Least Squares","Mars robots","Page six","Roman Empire","Solar energy","Statistical Significance","Steve Jobs","The Maya","Triple Cross","US Constitution","Eye of Horus","Madam I’m Adam","Mean Average Precision","Physics Nobel Prizes","Read the manual","Spanish Civil War","Do geese see god","Much ado about nothing"};

for(int outer=0; outer<5; outer++)
{

for(i=0; i<10; i++)
{
	
	String start = "&start=" + var; 
	String google = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0" + start + "&q=";
	String search = "site:en.wikipedia.org "+searchterms[outer];
	String charset = "UTF-8";
	URL url = new URL(google + URLEncoder.encode(search, charset));
	Reader reader = new InputStreamReader(url.openStream(), charset);
	GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);
//Thread.sleep(5000);
System.out.println("---------" + i + "over-------------------");

// Show title and URL of 1st result.
//System.out.println("Results size:" + results.getResponseData().getResults().size());
//def jsonArray = JSON.parse("https://graph.facebook.com/me".toURL().getText())
for(j=1; j<4; j++)
{
	//System.out.println("inside for loop");
	//try{
out1 = results.getResponseData().getResults().get(j).getTitle();
//out1="";
out2 = results.getResponseData().getResults().get(j).getUrl();
	//}
	//catch(Exception e)
	//{
		
	//}
s = out1 + "\n" + out2;
//System.out.println(s);
//System.out.println(out2);
if(out2.contains("Category"))
{
	i--;
	continue;
}
System.out.println(out2);
//System.out.println(out1);
FileWriter fstream = new FileWriter("D:\\new_google_search\\"+searchterms[outer]+".txt",true);
BufferedWriter out = new BufferedWriter(fstream);
out.write("\n"+out2);
out.close();
}
var+=3;
//Thread.sleep(1000);
}

}


}

}