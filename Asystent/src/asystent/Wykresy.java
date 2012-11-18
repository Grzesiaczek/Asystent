package asystent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;

import org.jfree.data.time.Day;

public class Wykresy {

	public static ImageIcon getWykres(String dataOd, String dataDo, String okres,
			String typWykresu, String instrument, String przedmiot,
			ArrayList<String> zestawWskaznikow) throws DatabaseException {
		
		//OBLICZENIE ILOSCI DNI
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(Integer.parseInt(dataOd.substring(0,4)), Integer.parseInt(dataOd.substring(4,6)), Integer.parseInt(dataOd.substring(6,8))); 
		GregorianCalendar cal2 = new GregorianCalendar();
		cal2.set(Integer.parseInt(dataDo.substring(0,4)), Integer.parseInt(dataDo.substring(4,6)), Integer.parseInt(dataDo.substring(6,8))); 
		
		long differenceInMillis = cal2.getTimeInMillis() - cal.getTimeInMillis();
		int differenceInDays = (int) (differenceInMillis /(24*60*60*1000));
		
		DayData dataArray[] = new DayData[differenceInDays];
		
		//POBIERANIE DANYCH Z BAZY
		Database.load();
		Value val;
		if (instrument.equals("commodity")) {
			 val = Commodity.get(przedmiot);
		} else if (instrument.equals("currency")) {
			 val = Currency.get(przedmiot);
		} else {
			 val = Index.get(przedmiot);
		}
		
		Day startDay = new Day(cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.MONTH),cal.get(Calendar.YEAR));
		Day startDay2 = new Day(cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.MONTH),cal.get(Calendar.YEAR));
		
		for(int i=0; i<differenceInDays; i++) {
			dataArray[i] = new DayData(val.getOpen(cal), val.getClose(cal), val.getHigh(cal), val.getLow(cal));
			startDay = (Day) startDay.next();
			cal.set(startDay.getYear(), startDay.getMonth(), startDay.getDayOfMonth());
		}

		cal.set(Integer.parseInt(dataOd.substring(0,4)), Integer.parseInt(dataOd.substring(4,6)), Integer.parseInt(dataOd.substring(6,8)));
		
        Chart wykres = new Chart("Wskazniki biznesowe - wykresy", "Wskazniki", dataArray, zestawWskaznikow, cal, okres );
        return wykres.getPicture();
	}

}
