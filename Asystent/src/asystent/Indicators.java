package asystent;

import java.util.Arrays;

public class  Indicators
{
	//**********************************************************************************************************************
	static  void countADX(DayData dataArray [])                                          //wynik 1..datesNumber-1
	{
		double n=dataArray.length;
		double a=2/(n+1);
		dataArray[0].emaP=dataArray[0].pDM;
		dataArray[0].emaM=dataArray[0].mDM;
		dataArray[0].tr=dataArray[0].high-dataArray[0].low;
		for(int i=1; i<dataArray.length; i++)
		{
			double upMove=dataArray[i].high-dataArray[i-1].high;                          //upMove
			double downMove=dataArray[i-1].low-dataArray[i].low;                         //downMove

			if(upMove>downMove && upMove>0)                                                    //+DM
				dataArray[i].pDM=upMove;
			else
				dataArray[i].pDM=0;      
			if(upMove<downMove && downMove>0)                                              //-DM
				dataArray[i].mDM=downMove;
			else
				dataArray[i].mDM=0;       

			dataArray[i].emaP=a*dataArray[i].pDM+(1-a)*dataArray[i-1].emaP;         //EMA dla +DM
			dataArray[i].emaM=a*dataArray[i].mDM+(1-a)*dataArray[i-1].emaM;       //EMA dla -DM
			dataArray[i].tr=Math.max(  dataArray[i].high-dataArray[i].low,  Math.abs(dataArray[i].high-dataArray[i-1].close));    //average true range
			dataArray[i].tr=Math.max(dataArray[i].tr, Math.abs(dataArray[i].low-dataArray[i-1].close));
			dataArray[i].pDI=100*dataArray[i].emaP/dataArray[i].tr;                             //+DI
			dataArray[i].mDI=100*dataArray[i].emaM/dataArray[i].tr;                            //-DI
			dataArray[i].absoluteValue=Math.abs((dataArray[i].pDI-dataArray[i].mDI)/(dataArray[i].pDI+dataArray[i].mDI));
			double emaAbsolute=a*dataArray[i].absoluteValue+(1-a)*dataArray[i-1].absoluteValue;  
			dataArray[i].adx=100*emaAbsolute;                                                               //ADX
		}
	}
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//**********************************************************************************************************************
	static void countCCI(DayData dataArray [], int n)   // n- number of datum points
	{                                                                                    //wynik n-1..datesNumber-1
		double [] days=new double[dataArray.length];
		for(int g=0; g<dataArray.length; g++)
			days[g]=dataArray[g].close;
		Arrays.sort(days);
		double mediana=days[dataArray.length/2];

		double datesNumber=dataArray.length;
		for(int i=0; i<datesNumber; i++)
		{  dataArray [i].typicalPrice=(dataArray [i].high+dataArray [i].low+dataArray [i].close)/3;  System.out.println(dataArray[i].typicalPrice);}
		for(int j=n-1; j<datesNumber; j++)
		{
			double suma=0;
			for(int a=j-n+1; a<=j; a++)
				suma+=dataArray[a].typicalPrice;
			dataArray[j].sma=suma/n;
			//  dataArray[j].cci=(dataArray[j].typicalPrice-dataArray[j].sma)/(0.0015*(Math.abs(dataArray[j].typicalPrice-mediana)));           
		}       
		System.out.println(dataArray[1].cci);  System.out.println(dataArray[1].typicalPrice);
		System.out.println(dataArray[1].sma);  System.out.println(Math.abs(dataArray[1].close));  
		System.out.println(Math.abs(dataArray[1].typicalPrice-mediana));
	}

	//**********************************************************************************************************************
	static void countMomentum(DayData dataArray [], int n)   // n- number of datum points
	{                                                                                                 //wynik n..datesNumber-1
		double datesNumber=dataArray.length;
		for(int j=n; j<datesNumber; j++)
			dataArray[j].momentum=dataArray[j].close-dataArray[j-n].close;           
	}

	//**********************************************************************************************************************
	static void countRateOfChange(DayData dataArray [], int n)   // n- number of datum points
	{                                                                                                       //wynik: n..datesNumber-1
		double datesNumber=dataArray.length;
		for(int j=n; j<datesNumber; j++)
			dataArray[j].roc=(dataArray[j].close-dataArray[j-n].close)/dataArray[j-n].close;       
	}

	//**********************************************************************************************************************
	static void countAverageTrueRange(DayData dataArray [])      //wynik: 1..datesNumber-1
	{ 
		int datesNumber=dataArray.length;//dataArray.length;
		double a=2/(datesNumber+1);
		for(int i=1; i<datesNumber; i++)
		{
			dataArray[i].tr=Math.max(  dataArray[i].high-dataArray[i].low,  Math.abs(dataArray[i].high-dataArray[i-1].close));    // true range
			dataArray[i].tr=Math.max(dataArray[i].tr, Math.abs(dataArray[i].low-dataArray[i-1].close));                                       // true range
		}
		dataArray[1].atr=dataArray[1].tr;
		for(int i=2; i<datesNumber; i++)
			dataArray[i].atr=a*dataArray[i].tr+(1-a)*dataArray[i-1].atr;
	}

	//**********************************************************************************************************************
	static void countSimpleMovingAverage(DayData dataArray [], int n)   // n- number of datum points
	{                                                                                                                    //wynik: n-1..datesNumber-1
		int datesNumber=dataArray.length;
		for(int j=n-1; j<datesNumber; j++)
		{
			double suma=0;
			for(int a=j-n+1; a<=j; a++)
				suma+=dataArray[a].close;
			dataArray[j].sma=suma/n;    
		} 
	}
}
