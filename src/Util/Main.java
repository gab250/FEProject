package Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Map;
import java.util.Vector;


public class Main 
{
	public static void main(String[] args)
	{
		//Load Nasdaq Symbols
		Vector<String> symbols = YahooAPI.getNasdaqSymbols();
		symbols.addAll(YahooAPI.getNYSESymbols());
		
		//Prepare File
		File file = new File("results_" + Calendar.getInstance().getTime().getDate() + "_" + Calendar.getInstance().getTime().getYear() + ".txt");

	    try
	    {

			file.createNewFile();
						
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			//Get Avgs.
			for(int i=0; i<symbols.size(); ++i)
			{
				Map<String,Map<String,Float>> data = YahooAPI.getHistoricalData(symbols.get(i), "2013-07-29", "2013-08-30");
				System.out.println("Got Data");
				
				if(data != null)
				{
					float avgDailyVolatilityPerc = 0;
					float avgDailyVolatilityAbs = 0;
					float avgDailyVol = 0;
							
					for(String date : data.keySet())
					{
						avgDailyVolatilityPerc += data.get(date).get("High")/data.get(date).get("Low") - 1;
						avgDailyVolatilityAbs += data.get(date).get("High") - data.get(date).get("Low");
						avgDailyVol +=  data.get(date).get("Volume");
					}
					
					avgDailyVolatilityPerc /= data.keySet().size();
					avgDailyVolatilityAbs /= data.keySet().size();
					avgDailyVol /= data.keySet().size();
					
					if(avgDailyVolatilityPerc*100  > 5 && avgDailyVolatilityPerc*100 <= 8 && (avgDailyVol>= Math.pow(10, 6)) && data.get("2013-08-28").get("Close") > 15)
					{
						System.out.println("HIT!");
						
						//bw.flush();
						
						bw.write("");bw.newLine();
						bw.write(symbols.get(i));bw.newLine();
						bw.write(avgDailyVolatilityPerc*100 + "%");bw.newLine();
						bw.write(avgDailyVolatilityAbs + "$");bw.newLine();
						bw.write(Float.toString(avgDailyVol));bw.newLine();
						bw.write("");bw.newLine();
						
				    }
				}
				
				System.out.println(i);
		   }
			
		   bw.close();
	 }
     catch (IOException e) 
	 {
		e.printStackTrace();
	 }
		
	}
	

	
}
