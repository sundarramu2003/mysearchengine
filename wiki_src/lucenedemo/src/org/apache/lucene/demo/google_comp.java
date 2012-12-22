package org.apache.lucene.demo;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class google_comp {
	public static void main(String args[])
	{
		int i, j, rel, count, pos, strt_loop = 0;;
		float P, AP, MAP_ans, DCG, IDCG, NDCG;
		float M_NDCG, T_NDCG;
		String s;
		ArrayList<String> gUrls =	new ArrayList<String>();
		ArrayList<String> lUrls =	new ArrayList<String>();
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		String searchterms[]={"DNA","Apple","Epigenetics","Hollywood","Maya","Microsoft","Precision","Tuscany","99 balloons","Computer Programming","Financial meltdown","Justin Timberlake","Least Squares","Mars robots","Page six","Roman Empire","Solar energy","Statistical Significance","Steve Jobs","The Maya","Triple Cross","US Constitution","Eye of Horus","Madam I’m Adam","Mean Average Precision","Physics Nobel Prizes","Read the manual","Spanish Civil War","Do geese see god","Much ado about nothing"};
		try
		{
			FileInputStream fstream = new FileInputStream("D:\\google_300.txt");
		    DataInputStream in = new DataInputStream(fstream);
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    String strLine;
		    while ((strLine = br.readLine()) != null)   //read till end of file.
		    {
		    	gUrls.add(strLine);
		    } 
		 }catch(Exception e)
		 {
			   System.out.println(e.getMessage());
			   e.printStackTrace();
		 }
		 
		 rel = 6;
		 //System.out.println(gUrls.size());
		  
		 for(i = 0; i < gUrls.size(); i++)
		 {
			
				if( map.put(gUrls.get(i), rel) != null)
				{
					//System.out.println(gUrls.get(i));
				}
				 
			 if(rel>1)
				 rel--;
			 if((i+1)%10 == 0)
				 rel = 6;
		 }
	/*
		 System.out.println("Map size:" + map.size());
	     System.out.println("Retrieving all keys and values from the HashMap");        
	     Iterator iterator = map.keySet().iterator();
	  
	     
	      while(iterator. hasNext()){
	    	  	String s = iterator.next().toString();
	    	  	System.out.println("Key= " + s);
	            System.out.println("Value= " + map.get(s));
	      }
	
	*/ 
			try
			{
				FileInputStream fstream = new FileInputStream("D:\\lucene_300.txt");
			    DataInputStream in = new DataInputStream(fstream);
			    BufferedReader br = new BufferedReader(new InputStreamReader(in));
			    String strLine;
			    while ((strLine = br.readLine()) != null)   //read till end of file.
			    {
			    	lUrls.add(strLine);
			    } 
			 }catch(Exception e)
			 {
				   System.out.println(e.getMessage());
				   e.printStackTrace();
			 }
	//-------------------------Start of MAP calculation---------------------------------------------
		
		AP = (float)0;
		int loop = 0;
		for(i = 1; i <= 30; i++)
		{
			count = 0; 
			P = (float)0;
			for(pos = 1; pos <=10; pos++)
			{	System.out.println("before map.contains");
				if(map.containsKey(lUrls.get(loop++)) == false)
				{
					System.out.println("in map.contains loop");
					continue;
				}
					else
				{
				P+= (float)(++count)/pos;
				//System.out.println("P=" + P + "pos=" + pos + "count=" + count);
				}
			}
			if(count>0)
			AP+= (float)(P/count);
			if(count == 0)
				System.out.println("AP for "+i+"=0");
			else
			System.out.println("AP for "+i+"="+ ((float) (P/count)));
		}
		MAP_ans = (float) AP/30;
		System.out.println("Mean average precison:" + MAP_ans + "\n");
		
	// ----------------------End of map calculation----------------------------------------------------------
	
		
			rel = 5; IDCG = 6;
			for(pos = 2; pos <= 5; pos++)
			{
				IDCG+= (float) rel * Math.log(2.0)/Math.log(pos);
				rel--;
			}
		//	System.out.println("IDCG= " + IDCG);
		
		int q = 0;
		loop = 0;
		T_NDCG = 0;
		for(i = 1; i <= 30; i++)
		{
			DCG = 0;
			for(pos = 1; pos <= 5; pos++)
			{
				strt_loop = loop;
				s = lUrls.get(loop++);
				if(map.containsKey(s) == false)
					continue;
				else if(pos == 1)
					DCG += map.get(s);
				else
				{
					DCG += (float)( map.get(s) * Math.log(2.0)/Math.log(pos) );
				}
				
			}
			NDCG = (float) DCG/IDCG;
			System.out.println("NDCG @5 for " + searchterms[q++] + " = " + NDCG);
			T_NDCG += (float) NDCG;
			loop = loop+=5;
		}
		M_NDCG = (float) T_NDCG/30;
		System.out.println("\nMean NDCG = " + M_NDCG);
	} 	// end of main
}
