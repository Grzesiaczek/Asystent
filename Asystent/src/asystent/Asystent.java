/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asystent;

import java.util.ArrayList;
import java.util.Calendar;

import org.jfree.data.time.Day;

/**
 *
 * @author asia
 */
public class Asystent
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
    	
                      //DANE
//                     DayData dataArray[]=new DayData[6];
//                     DayData DD=new DayData(1.2652, 1.2652, 1.2652, 1.2652);
//                     dataArray[0]=DD;
//                     DD=new DayData(1.2705, 1.2705, 1.2705, 1.2705);
//                      dataArray[1]=DD;
//                      DD=new DayData(1.2652, 1.2652, 1.2652, 1.2652);
//                      dataArray[2]=DD;
//                     DD=new DayData(1.2688, 1.2688, 1.2688, 1.2688);
//                      dataArray[3]=DD;
//                     DD=new DayData(1.2726, 1.2726, 1.2726, 1.2726);
//                      dataArray[4]=DD;
//                       DD=new DayData(1.2717, 1.2717, 1.2717, 1.2717);
//                      dataArray[5]=DD;

    	
    	ArrayList<String> zestawWskaznikow = new ArrayList<String>();
    	zestawWskaznikow.add("Momentum");
    	zestawWskaznikow.add("CCI");
    	zestawWskaznikow.add("ADX");
    	zestawWskaznikow.add("ROC");
    	zestawWskaznikow.add("ATR");
    	
    	Database.load();
    	Commodity gold = Commodity.get(3);
    	int count = gold.count;
    	DayData dataArray[] = new DayData[count];
    	
    	Day day = new Day(gold.archive.get(0).day.get(Calendar.DAY_OF_MONTH), gold.archive.get(0).day.get(Calendar.MONTH), gold.archive.get(0).day.get(Calendar.YEAR));
    	
    	for(int i = 0; i < count; i++)
    	{
    		dataArray[i] = new DayData(gold.getOpen(i), gold.getLow(i), gold.getHigh(i), gold.getClose(i));
    	}
                      
        Chart demo = new Chart("Wskazniki biznesowe- wykresy", "Wskazniki", dataArray, zestawWskaznikow, gold.archive.get(0).day, "dziennie");
        demo.pack();
        demo.setVisible(true);
        demo.setDefaultCloseOperation(demo.EXIT_ON_CLOSE);
    }
}