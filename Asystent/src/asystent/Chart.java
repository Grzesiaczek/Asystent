package asystent;

import javax.swing.JFrame;
import org.jfree.chart.*;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYSeries;
import java.awt.Color;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.axis.PeriodAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.DateAxis;
import java.awt.BasicStroke;
import org.jfree.util.Rotation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.PaintScale;
import org.jfree.chart.renderer.GrayPaintScale;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Week;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Graphics2D;
import javax.swing.JFrame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Chart extends JFrame
{
	ImageIcon picture;

	public Chart(String applicationTitle, String chartTitle, DayData dataArray[], ArrayList<String> zestawWskaznikow, GregorianCalendar cal, String okres) 
	{
		super(applicationTitle);
		Indicators indicator=new Indicators();
		//obliczenie wskaznika     (uzupelnienie struktury DayData)

		//zbior wykresow 
		final CombinedDomainXYPlot plott = new CombinedDomainXYPlot(new DateAxis("Date"));
		plott.setGap(10.0);
		plott.setFixedRangeAxisSpace(new AxisSpace());    
		//dane do wspolnego wykresu plot  
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		TimeSeries pop2;
		XYPlot plot;
		
		TimeSeriesCollection dataset3 = new TimeSeriesCollection();
		TimeSeries pop3;
		//************************************************************************************************
		
		if (okres.equals("dziennie")) {
		
		Day startDay = new Day(cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.MONTH),cal.get(Calendar.YEAR));
			
		//plot=drawPrices(dataArray, 0, startDay);
		//plott.add(plot, 1);
		//pop2=drawPrices(dataArray, 0, startDay);
		//dataset.addSeries(pop2);
		pop3=drawPrices(dataArray, 0, startDay);
		dataset3.addSeries(pop3);

		if (zestawWskaznikow.contains("CCI")) {
		indicator.countCCI(dataArray, 2);        
		//plot=drawPrices(dataArray, 1, startDay);
		//plott.add(plot, 1);    
		pop2=drawPrices(dataArray, 1, startDay);
		dataset.addSeries(pop2);
		}

		if (zestawWskaznikow.contains("ADX")) {
		indicator.countADX(dataArray);        
		//plot=drawPrices(dataArray, 2, startDay);
		//plott.add(plot, 1);
		pop2=drawPrices(dataArray, 2, startDay);
		dataset.addSeries(pop2);
		}

		if (zestawWskaznikow.contains("Momentum")) {
		indicator.countMomentum(dataArray, 2);
		pop2=drawPrices(dataArray, 3, startDay);
		dataset.addSeries(pop2);
		}

		if (zestawWskaznikow.contains("ROC")) {
		indicator.countRateOfChange(dataArray, 2);
		pop2=drawPrices(dataArray, 4, startDay);
		dataset.addSeries(pop2);
		}

		if (zestawWskaznikow.contains("ATR")) {
		indicator.countAverageTrueRange(dataArray);
		pop2=drawPrices(dataArray, 5, startDay);
		dataset.addSeries(pop2);
		}

		if (zestawWskaznikow.contains("SMA")) {
		indicator.countSimpleMovingAverage(dataArray, 3);
		//plot=drawPrices(dataArray, 6, startDay);
		//plott.add(plot, 1);   
		pop2=drawPrices(dataArray, 6, startDay);
		dataset.addSeries(pop2);
		}
		
		} else if (okres.equals("tygodniowo")) {
			
			Week startDay = new Week(cal.get(Calendar.WEEK_OF_YEAR),cal.get(Calendar.YEAR));
			
			//plot=drawPrices(dataArray, 0, startDay);
			//plott.add(plot, 1);
			//pop2=drawPrices2(dataArray, 0, startDay);
			//dataset.addSeries(pop2);
			pop3=drawPrices2(dataArray, 0, startDay);
			dataset3.addSeries(pop3);

			if (zestawWskaznikow.contains("CCI")) {
			indicator.countCCI(dataArray, 2);        
			//plot=drawPrices(dataArray, 1, startDay);
			//plott.add(plot, 1);    
			pop2=drawPrices2(dataArray, 1, startDay);
			dataset.addSeries(pop2);
			}

			if (zestawWskaznikow.contains("ADX")) {
			indicator.countADX(dataArray);        
			//plot=drawPrices(dataArray, 2, startDay);
			//plott.add(plot, 1);
			pop2=drawPrices2(dataArray, 2, startDay);
			dataset.addSeries(pop2);
			}

			if (zestawWskaznikow.contains("Momentum")) {
			indicator.countMomentum(dataArray, 2);
			pop2=drawPrices2(dataArray, 3, startDay);
			dataset.addSeries(pop2);
			}

			if (zestawWskaznikow.contains("ROC")) {
			indicator.countRateOfChange(dataArray, 2);
			pop2=drawPrices2(dataArray, 4, startDay);
			dataset.addSeries(pop2);
			}

			if (zestawWskaznikow.contains("ATR")) {
			indicator.countAverageTrueRange(dataArray);
			pop2=drawPrices2(dataArray, 5, startDay);
			dataset.addSeries(pop2);
			}

			if (zestawWskaznikow.contains("SMA")) {
			indicator.countSimpleMovingAverage(dataArray, 3);
			//plot=drawPrices(dataArray, 6, startDay);
			//plott.add(plot, 1);   
			pop2=drawPrices2(dataArray, 6, startDay);
			dataset.addSeries(pop2);
			}
			
		} else {
			
			Month startDay = new Month(cal.get(Calendar.MONTH),cal.get(Calendar.YEAR));
			
			//plot=drawPrices(dataArray, 0, startDay);
			//plott.add(plot, 1);
			//pop2=drawPrices3(dataArray, 0, startDay);
			//dataset.addSeries(pop2);
			pop3=drawPrices3(dataArray, 0, startDay);
			dataset3.addSeries(pop3);

			if (zestawWskaznikow.contains("CCI")) {
			indicator.countCCI(dataArray, 2);        
			//plot=drawPrices(dataArray, 1, startDay);
			//plott.add(plot, 1);    
			pop2=drawPrices3(dataArray, 1, startDay);
			dataset.addSeries(pop2);
			}

			if (zestawWskaznikow.contains("ADX")) {
			indicator.countADX(dataArray);        
			//plot=drawPrices(dataArray, 2, startDay);
			//plott.add(plot, 1);
			pop2=drawPrices3(dataArray, 2, startDay);
			dataset.addSeries(pop2);
			}

			if (zestawWskaznikow.contains("Momentum")) {
			indicator.countMomentum(dataArray, 2);
			pop2=drawPrices3(dataArray, 3, startDay);
			dataset.addSeries(pop2);
			}

			if (zestawWskaznikow.contains("ROC")) {
			indicator.countRateOfChange(dataArray, 2);
			pop2=drawPrices3(dataArray, 4, startDay);
			dataset.addSeries(pop2);
			}

			if (zestawWskaznikow.contains("ATR")) {
			indicator.countAverageTrueRange(dataArray);
			pop2=drawPrices3(dataArray, 5, startDay);
			dataset.addSeries(pop2);
			}

			if (zestawWskaznikow.contains("SMA")) {
			indicator.countSimpleMovingAverage(dataArray, 3);
			//plot=drawPrices(dataArray, 6, startDay);
			//plott.add(plot, 1);   
			pop2=drawPrices3(dataArray, 6, startDay);
			dataset.addSeries(pop2);
			}
			
		}

		NumberAxis rangeAxis=new NumberAxis("value");;  
		XYItemRenderer renderer = new StandardXYItemRenderer();
		XYPlot pl = new XYPlot(dataset, null, rangeAxis, renderer);
		DateAxis axis = (DateAxis) pl.getDomainAxis();
		DateAxis domainAxis = new DateAxis("XXX L");
		rangeAxis.setAutoRangeIncludesZero(false);
		PaintScale scale = new GrayPaintScale(0, 10);
		XYLineAndShapeRenderer rendererr = new XYLineAndShapeRenderer();  
		
		
		NumberAxis rangeAxis2=new NumberAxis("value");;  
		XYItemRenderer renderer2 = new StandardXYItemRenderer();
		XYPlot pl2 = new XYPlot(dataset3, null, rangeAxis2, renderer2);
		DateAxis axis2 = (DateAxis) pl2.getDomainAxis();
		DateAxis domainAxis2 = new DateAxis("XXX L");
		rangeAxis.setAutoRangeIncludesZero(false);
		PaintScale scale2 = new GrayPaintScale(0, 10);
		XYLineAndShapeRenderer rendererr2 = new XYLineAndShapeRenderer();  
		
		plott.add(pl2,1);
		plott.add(pl,2);
		
		plott.getDomainAxis().setAxisLineVisible(false);
		plott.setOrientation(PlotOrientation.VERTICAL);

		JFreeChart chart = new JFreeChart("Indicators",
				JFreeChart.DEFAULT_TITLE_FONT, plott, true);
		ChartUtilities.applyCurrentTheme(chart);
		chart.setBackgroundPaint((new Color(0xAABB99)));

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
		// add it to our application
		setContentPane(chartPanel);

		BufferedImage image = chart.createBufferedImage(500,300);      //describes an Image with an accessible buffer of image data
		picture= new ImageIcon(image);
	}

	public ImageIcon getPicture() {
		return picture;
	}

	//**********************************************************************************************************************
	private  TimeSeries drawPrices(DayData dataArray [], int indicator, Day startDay)
	{      
		TimeSeries pop2; 
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		NumberAxis rangeAxis2=new NumberAxis("");
		switch(indicator)
		{
		case 0:  
			pop2=new TimeSeries("Price", Day.class);
			for (int i=0; i<dataArray.length; i++) {
				pop2.add(startDay, dataArray[i].close);
				startDay = (Day) startDay.next();
			}
			rangeAxis2=new NumberAxis("price");
			break;

		case 1:  
			pop2=new TimeSeries("Commodity Channel Index ", Day.class);
			for (int i=1; i<dataArray.length; i++) {
				pop2.add(startDay, dataArray[i].cci);
				startDay = (Day) startDay.next();
			}
			rangeAxis2=new NumberAxis("cci");
			break;

		case 2:  
			pop2=new TimeSeries("Average directional index ", Day.class);
			for (int i=1; i<dataArray.length; i++) {
				pop2.add(startDay, dataArray[i].adx);
				startDay = (Day) startDay.next();
			}
			rangeAxis2=new NumberAxis("adx");
			break;   

		case 3:  
			pop2=new TimeSeries("Momentum ", Day.class);
			for (int i=1; i<dataArray.length; i++) {
				pop2.add(startDay, dataArray[i].momentum);
				startDay = (Day) startDay.next();
			}
			rangeAxis2=new NumberAxis("momentum");
			break;   

		case 4:  
			pop2=new TimeSeries("Rate Of Change ", Day.class);
			for (int i=1; i<dataArray.length; i++) {
				pop2.add(startDay, dataArray[i].roc);
				startDay = (Day) startDay.next();
			}
			rangeAxis2=new NumberAxis("roc");
			break;   

		case 5:  
			pop2=new TimeSeries("Average True Range ", Day.class);
			for (int i=1; i<dataArray.length; i++) {
				pop2.add(startDay, dataArray[i].atr);
				startDay = (Day) startDay.next();
			}
			rangeAxis2=new NumberAxis("atr");
			break;   

		case 6:  
			pop2=new TimeSeries("Simple Moving Average ", Day.class);
			for (int i=1; i<dataArray.length; i++) {
				pop2.add(startDay, dataArray[i].sma);
				startDay = (Day) startDay.next();
			}
			rangeAxis2=new NumberAxis("sma");
			break;   

		default:
			pop2=new TimeSeries("", Day.class);   
			break;
		}
		
		return pop2;
	}

	//**********************************************************************************************************************
	
	private  TimeSeries drawPrices2(DayData dataArray [], int indicator, Week startDay)
	{      
		TimeSeries pop2; 
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		NumberAxis rangeAxis2=new NumberAxis("");
		switch(indicator)
		{
		case 0:  
			pop2=new TimeSeries("Price", Week.class);
			for (int i=0; i<dataArray.length; i++) {
				pop2.add(startDay, dataArray[i].close);
				startDay = (Week) startDay.next();
			}
			rangeAxis2=new NumberAxis("price");
			break;

		case 1:  
			pop2=new TimeSeries("Commodity Channel Index ", Week.class);
			for (int i=1; i<dataArray.length; i++) {
				pop2.add(startDay, dataArray[i].cci);
				startDay = (Week) startDay.next();
			}
			rangeAxis2=new NumberAxis("cci");
			break;

		case 2:  
			pop2=new TimeSeries("Average directional index ", Week.class);
			for (int i=1; i<dataArray.length; i++) {
				pop2.add(startDay, dataArray[i].adx);
				startDay = (Week) startDay.next();
			}
			rangeAxis2=new NumberAxis("adx");
			break;   

		case 3:  
			pop2=new TimeSeries("Momentum ", Week.class);
			for (int i=1; i<dataArray.length; i++) {
				pop2.add(startDay, dataArray[i].momentum);
				startDay = (Week) startDay.next();
			}
			rangeAxis2=new NumberAxis("momentum");
			break;   

		case 4:  
			pop2=new TimeSeries("Rate Of Change ", Week.class);
			for (int i=1; i<dataArray.length; i++) {
				pop2.add(startDay, dataArray[i].roc);
				startDay = (Week) startDay.next();
			}
			rangeAxis2=new NumberAxis("roc");
			break;   

		case 5:  
			pop2=new TimeSeries("Average True Range ", Week.class);
			for (int i=1; i<dataArray.length; i++) {
				pop2.add(startDay, dataArray[i].atr);
				startDay = (Week) startDay.next();
			}
			rangeAxis2=new NumberAxis("atr");
			break;   

		case 6:  
			pop2=new TimeSeries("Simple Moving Average ", Week.class);
			for (int i=1; i<dataArray.length; i++) {
				pop2.add(startDay, dataArray[i].sma);
				startDay = (Week) startDay.next();
			}
			rangeAxis2=new NumberAxis("sma");
			break; 

		default:
			pop2=new TimeSeries("", Week.class);   
			break;
		}

		return pop2;
	}

	//**********************************************************************************************************************
	private  TimeSeries drawPrices3(DayData dataArray [], int indicator, Month startDay)
	{      
		TimeSeries pop2; 
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		NumberAxis rangeAxis2=new NumberAxis("");
		switch(indicator)
		{
		case 0:  
			pop2=new TimeSeries("Price", Month.class);
			for (int i=0; i<dataArray.length; i++) {
				pop2.add(startDay, dataArray[i].close);
				startDay = (Month) startDay.next();
			}
			rangeAxis2=new NumberAxis("price");
			break;

		case 1:  
			pop2=new TimeSeries("Commodity Channel Index ", Month.class);
			for (int i=1; i<dataArray.length; i++) {
				pop2.add(startDay, dataArray[i].cci);
				startDay = (Month) startDay.next();
			}
			rangeAxis2=new NumberAxis("cci");
			break;

		case 2:  
			pop2=new TimeSeries("Average directional index ", Month.class);
			for (int i=1; i<dataArray.length; i++) {
				pop2.add(startDay, dataArray[i].adx);
				startDay = (Month) startDay.next();
			}
			rangeAxis2=new NumberAxis("adx");
			break;   

		case 3:  
			pop2=new TimeSeries("Momentum ", Month.class);
			for (int i=1; i<dataArray.length; i++) {
				pop2.add(startDay, dataArray[i].momentum);
				startDay = (Month) startDay.next();
			}
			rangeAxis2=new NumberAxis("momentum");
			break;   

		case 4:  
			pop2=new TimeSeries("Rate Of Change ", Month.class);
			for (int i=1; i<dataArray.length; i++) {
				pop2.add(startDay, dataArray[i].roc);
				startDay = (Month) startDay.next();
			}
			rangeAxis2=new NumberAxis("roc");
			break;   

		case 5:  
			pop2=new TimeSeries("Average True Range ", Month.class);
			for (int i=1; i<dataArray.length; i++) {
				pop2.add(startDay, dataArray[i].atr);
				startDay = (Month) startDay.next();
			}
			rangeAxis2=new NumberAxis("atr");
			break;  

		case 6:  
			pop2=new TimeSeries("Simple Moving Average ", Month.class);
			for (int i=1; i<dataArray.length; i++) {
				pop2.add(startDay, dataArray[i].sma);
				startDay = (Month) startDay.next();
			}
			rangeAxis2=new NumberAxis("sma");
			break;   


		default:
			pop2=new TimeSeries("", Month.class);   
			break;
		}
		
		return pop2;
	}
}







