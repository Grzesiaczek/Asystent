package asystent;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.zip.GZIPInputStream;

public class Database
{
	static DataInputStream reader;
	static String today;
	
	static short commodityCount;
	static short currencyCount;
	static short indexCount;
	
	static String user = "";
	static String sessionID = "";
	static Random random;
	
	public static void main(String[] args) 
	{
		try 
		{
			random = new Random(System.currentTimeMillis());
			today = convertDate(new GregorianCalendar());
			load();
			//update();
//			for (int i=0; i < Commodity.get(4).count; i++)
//				System.out.println(Commodity.get(4).getClose(i));
			
			//System.out.println(Commodity.get(4).end.get(Calendar.DAY_OF_YEAR));
			
			System.out.println(Commodity.get("Gold").getMonthAverageClose(11, 2012));
			System.out.println(Commodity.get("Gas").getMonthAverageClose(11, 2012));
			System.out.println(Commodity.get("Copper").getMonthAverageClose(11, 2012));
			System.out.println(Currency.get("pln").getMonthAverageClose(10, 2012));	
			System.out.println(Commodity.get("Yen").getMonthAverageClose(11, 2012));				
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void load()
	{
		long time = System.currentTimeMillis();
		
		Commodity.init();
		Currency.init();
		Index.init();
		
		try
		{
			reader = new DataInputStream(new FileInputStream("data/config.cfg"));
			reader.skip(6);
			commodityCount = reader.readShort();
			currencyCount = reader.readShort();
			indexCount = reader.readShort();
			
			reader = new DataInputStream(new FileInputStream("data/config.dbf"));
			reader.skip(16);
			
			for(int i = 0; i < commodityCount; i++)
				Commodity.add(new Commodity(reader));
			
			for(int i = 0; i < currencyCount; i++)
				Currency.add(new Currency(reader));
			
			for(int i = 0; i < indexCount; i++)
				Index.add(new Index(reader));
			
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		time = System.currentTimeMillis() - time;
		System.out.println("£adowanie trwa³o: " + time + " ms");
	}
	
	static String[] load(String symbol, String start, String end)
	{
		URL url;
		HttpURLConnection conn;
		
		try
	    {
	        url = new URL("http://stooq.com/q/d/?s=" + symbol + "&d1=" + start + "&d2=" + end + "&i=d");
	        conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Host", "stooq.com");
	        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:16.0) Gecko/20100101 Firefox/16.0");
	        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	        conn.setRequestProperty("Accept-Language", "pl,en-us;q=0.7,en;q=0.3");
	        conn.setRequestProperty("Accept-Encoding", "gzip");
	        conn.setRequestProperty("DNT", "1");
	        conn.setRequestProperty("Connection", "keep-alive");
	        conn.setRequestProperty("Cookie", "cookie_uu=" + today.substring(2) + "000; cookie_user=" + user + "; uid=pl105936826; PHPSESSID=" + sessionID);
	        
	        InputStream in = null;
	        byte[] data = new byte[8000];

	 	    in = new GZIPInputStream(conn.getInputStream());
	 	    in.read(data);
	 	    
	 	    Thread.sleep(400 + random.nextInt()%200);	    

	    	url = new URL("http://stooq.com/q/d/l/?s=" + symbol + "&d1=" + start + "&d2=" + end + "&i=d");
	        conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Host", "stooq.com");
	        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:16.0) Gecko/20100101 Firefox/16.0");
	        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	        conn.setRequestProperty("Accept-Language", "pl,en-us;q=0.7,en;q=0.3");
	        conn.setRequestProperty("Accept-Encoding", "gzip");
	        conn.setRequestProperty("DNT", "1");
	        conn.setRequestProperty("Connection", "keep-alive");
	        conn.setRequestProperty("Referer", "http://stooq.com/q/d/?s=%5Edax&c=0&d1=" + start + "&d2=" + end);
	        conn.setRequestProperty("Cookie", "cookie_uu=" + today.substring(2) + "000; cookie_user=" + user + "; uid=pl11546928; PHPSESSID=" + sessionID);
	        
	        data = new byte[8000];

	 	    in = new GZIPInputStream(conn.getInputStream());
	 	    in.read(data);

	 	    return new String(data).split("\n");
	    }	
	    catch (Exception e) 
	    {
	    	e.printStackTrace();
	    }
	    
	    String[] error = null;
	    
	    return error;
	}
	
	static void update() throws IOException
	{
		URL url;
		HttpURLConnection conn;
		user = "%3F0001dllg000001500d1300e3%7C" + Commodity.get(0).symbol;
		sessionID = "";
		
		for(int i = 1; i < commodityCount; i++)
			user += "+" + Commodity.get(i).symbol;
		
		for(int i = 0; i < currencyCount; i++)
			user += "+" + Currency.get(i).symbol;
		
		for(int i = 0; i < indexCount; i++)
			user += "+" + Index.get(i).symbol;
		
		try
	    {        
	        url = new URL("http://stooq.com");
	        conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Host", "stooq.com");
	        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:16.0) Gecko/20100101 Firefox/16.0");
	        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	        conn.setRequestProperty("Accept-Language", "pl,en-us;q=0.7,en;q=0.3");
	        conn.setRequestProperty("Accept-Encoding", "gzip");
	        conn.setRequestProperty("DNT", "1");
	        conn.setRequestProperty("Connection", "keep-alive");
	        conn.setRequestProperty("Cookie", "cookie_uu=" + today.substring(2) + "000; cookie_user=" + user + "; uid=pl105936826");
	        
	        InputStream in = null;
	        byte[] data = new byte[4000];

	 	    in = new GZIPInputStream(conn.getInputStream());
	 	    in.read(data);
	 	    
	 	    sessionID = conn.getHeaderField(3).substring(10, 42);
	 	    
	 	    Thread.sleep(400 + random.nextInt()%200);	  
	    }	
	    catch (Exception e) 
	    {
	    	e.printStackTrace();
	    }	   
	    
	    DataOutputStream  writer = new DataOutputStream(new FileOutputStream("data/config.dbf"));
		writer.writeBytes("[dbf01020304end]");
		
		for(int i = 0; i < commodityCount; i++)
		{
			Commodity.get(i).update();
			Commodity.get(i).save(writer);
		}
		
		for(int i = 0; i < currencyCount; i++)
		{
			Currency.get(i).update();
			Currency.get(i).save(writer);
		}
		
		for(int i = 0; i < indexCount; i++)
		{
			Index.get(i).update();
			Index.get(i).save(writer);
		}
	   
		writer.close();	
	}
	
	static String convertDate(GregorianCalendar date)
	{
		String day = Integer.toString(date.get(Calendar.YEAR));
		int number = date.get(Calendar.MONTH) + 1;
		if (number < 10) day += "0";
		day += number;
		number = date.get(Calendar.DAY_OF_MONTH);
		if (number < 10) day += "0";
		day += number;
		return day;
	}
}

class Commodity extends Value
{
	private static ArrayList<Commodity> data;
	
	Commodity(DataInputStream reader) throws IOException
	{
		super(reader);
		type = "Commodity";
	}
	
	static void init()
	{
		data = new ArrayList<Commodity>();
	}
	
	static void add(Commodity commodity)
	{
		data.add(commodity);
	}
	
	static Commodity get(int index)
	{
		return data.get(index);
	}
	
	static Commodity get(String name) throws DatabaseException
	{
		for(int i = 0; i< data.size(); i++)
			if(data.get(i).name.matches(name))
				return data.get(i);
		
		throw new DatabaseException("Wrong name");
	}
}

class Currency extends Value
{
	private static ArrayList<Currency> data;
	
	Currency(DataInputStream reader) throws IOException
	{
		super(reader);
		type = "Currency";
	}
	
	static void init()
	{
		data = new ArrayList<Currency>();
	}
	
	static void add(Currency currency)
	{
		data.add(currency);
	}
	
	static Currency get(int index)
	{
		return data.get(index);
	}
	
	static Currency get(String name) throws DatabaseException
	{
		for(int i = 0; i< data.size(); i++)
			if(data.get(i).symbol.substring(3).matches(name.toLowerCase()))
				return data.get(i);
		
		throw new DatabaseException("Wrong name");
	}
	
	void update() throws IOException
	{		
		GregorianCalendar date = new GregorianCalendar();
		if(date.get(Calendar.YEAR) < end.get(Calendar.YEAR)) return;
		if(date.get(Calendar.DAY_OF_YEAR) <= end.get(Calendar.DAY_OF_YEAR)) return;
		end.set(Calendar.DAY_OF_YEAR, end.get(Calendar.DAY_OF_YEAR) + 1);
		
		DataOutputStream out = new DataOutputStream(new FileOutputStream("data/" + name + ".dbf", true));
		String[] data = Database.load(symbol, Database.convertDate(end), Database.today);
		
		for(int i = 1; i < data.length - 1; i++)
			try
			{
				writeLine(out, data[i], 10000);
				count++;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
		end = date;
		out.close();
		return;
	}
}

class Index extends Value
{
	private static ArrayList<Index> data;
	
	Index(DataInputStream reader) throws IOException
	{
		super(reader);
		type = "Index";
	}
	
	static void init()
	{
		data = new ArrayList<Index>();
	}
	
	static void add(Index index)
	{
		data.add(index);
	}
	
	static Index get(int index)
	{
		return data.get(index);
	}
	
	static Index get(String name) throws DatabaseException
	{
		for(int i = 0; i< data.size(); i++)
			if(data.get(i).name.matches(name))
				return data.get(i);
		
		throw new DatabaseException("Wrong name");
	}
}

class EndOfDay
{
	GregorianCalendar day;
	int open;
	int high;
	int low;
	int close;
	long volume;
	
	EndOfDay(GregorianCalendar day, int open, int high, int low, int close, long volume)
	{
		this.day = day;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
	}
	
	EndOfDay(DataInputStream reader) throws IOException
	{
		day = new GregorianCalendar(reader.readShort(), reader.readByte() - 1, reader.readByte());
		open = reader.readInt();
		high = reader.readInt();
		low = reader.readInt();
		close = reader.readInt();
		volume = reader.readLong();
	}
}

abstract class Value
{
	String name;
	String symbol;
	String type;
	GregorianCalendar start; 
	GregorianCalendar end;
	int offset;
	int count;
	int value;
	ArrayList<EndOfDay> archive;
	
	/*
	Index(int offset) throws FileNotFoundException
	{
		DataInputStream reader;
		reader = new DataInputStream(new FileInputStream("data/config.dbf"));
		this.name = name;
		
	}*/
	
	Value(DataInputStream reader) throws IOException
	{
		name = reader.readUTF();
		symbol = reader.readUTF();
		start = new GregorianCalendar(reader.readShort(), reader.readByte() - 1, reader.readByte());
		end = new GregorianCalendar(reader.readShort(), reader.readByte() - 1, reader.readByte());
		count = reader.readInt();
		value = reader.readInt();
		
		archive = new ArrayList<EndOfDay>();
		DataInputStream ireader = new DataInputStream(new FileInputStream("data/" + name + ".dbf"));
		ireader.skip(16);
		
		for(int i = 0; i < count; i++)
			archive.add(new EndOfDay(ireader));
		
		ireader.close();
	}
	
	protected long average()
	{
		long total = 0;
		
		for (int i = 0; i < archive.size(); i++)
			total += archive.get(i).close;
		
		return total/archive.size();
	}
	
	protected long average(int start, int end)
	{
		long total = 0;
		
		for (int i = 0; i < archive.size(); i++)
			total += archive.get(i).close;
		
		return total/archive.size();
	}
	
	protected long average(GregorianCalendar start, GregorianCalendar end)
	{
		return 0;
	}
	
	int max()
	{
		int value = 0;
		int next;
		
		for (int i = 0; i < archive.size(); i++)
		{
			next = archive.get(i).high;
			if (value < next)
				value = next;
		}
		
		return value;
	}
	
	int min()
	{
		int value = Integer.MAX_VALUE;
		int next;
		
		for (int i = 0; i < archive.size(); i++)
		{
			next = archive.get(i).low;
			if (value > next)
				value = next;
		}
		
		return value;
	}
	
	int getClose(int index)
	{
		return archive.get(index).close;
	}
	
	int getClose(GregorianCalendar date) throws DatabaseException
	{
		return archive.get(searchIndex(date)).close;
	}
	
	int getClose(int day, int month, int year) throws DatabaseException
	{
		return archive.get(searchIndex(new GregorianCalendar(year, month - 1, day))).open;
	}
	
	int getWeekAverageClose(int day, int month, int year) throws DatabaseException
	{
		return getWeekAverageClose(new GregorianCalendar(year, month - 1, day));
	}
	
	int getWeekAverageClose(GregorianCalendar date) throws DatabaseException
	{
		int index = searchIndex(new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH)));
		int total = 0;
		int days = 0;
		
		if(date.get(Calendar.WEEK_OF_YEAR) == end.get(Calendar.MONTH))
		{
			while(index < count)
			{
				total += archive.get(index++).close;
				days++;
			}
		}
		
		else while(archive.get(index).day.get(Calendar.WEEK_OF_YEAR) == date.get(Calendar.WEEK_OF_YEAR))
		{
			total += archive.get(index++).close;
			days++;
		}
		
		return total/days;
	}
	
	int getMonthAverageClose(int day, int month, int year) throws DatabaseException
	{
		return getMonthAverageClose(new GregorianCalendar(year, month - 1, 1));
	}
	
	int getMonthAverageClose(int month, int year) throws DatabaseException
	{
		return getMonthAverageClose(new GregorianCalendar(year, month - 1, 1));
	}
	
	int getMonthAverageClose(GregorianCalendar date) throws DatabaseException
	{
		int index = searchIndex(new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH), 1));
		int total = 0;
		int days = 0;
		
		if(date.get(Calendar.MONTH) == end.get(Calendar.MONTH))
		{
			while(index < count)
			{
				total += archive.get(index++).close;
				days++;
			}
		}
		
		else while(archive.get(index).day.get(Calendar.MONTH) == date.get(Calendar.MONTH))
		{
			total += archive.get(index++).close;
			days++;
		}
		
		return total/days;
	}
	
	int getHigh(int index)
	{
		return archive.get(index).high;
	}
	
	int getHigh(GregorianCalendar date) throws DatabaseException
	{
		return archive.get(searchIndex(date)).high;
	}
	
	int getHigh(int day, int month, int year) throws DatabaseException
	{
		return archive.get(searchIndex(new GregorianCalendar(year, month - 1, day))).high;
	}
	
	int getWeekAverageHigh(int day, int month, int year) throws DatabaseException
	{
		return getWeekAverageHigh(new GregorianCalendar(year, month - 1, day));
	}
	
	int getWeekAverageHigh(GregorianCalendar date) throws DatabaseException
	{
		int index = searchIndex(new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH)));
		int total = 0;
		int days = 0;
		
		if(date.get(Calendar.WEEK_OF_YEAR) == end.get(Calendar.MONTH))
		{
			while(index < count)
			{
				total += archive.get(index++).high;
				days++;
			}
		}
		
		else while(archive.get(index).day.get(Calendar.WEEK_OF_YEAR) == date.get(Calendar.WEEK_OF_YEAR))
		{
			total += archive.get(index++).high;
			days++;
		}
		
		return total/days;
	}
	
	int getMonthAverageHigh(int day, int month, int year) throws DatabaseException
	{
		return getMonthAverageHigh(new GregorianCalendar(year, month - 1, 1));
	}
	
	int getMonthAverageHigh(int month, int year) throws DatabaseException
	{
		return getMonthAverageHigh(new GregorianCalendar(year, month - 1, 1));
	}
	
	int getMonthAverageHigh(GregorianCalendar date) throws DatabaseException
	{
		int index = searchIndex(new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH), 1));
		int total = 0;
		int days = 0;
		
		if(date.get(Calendar.MONTH) == end.get(Calendar.MONTH))
		{
			while(index < count)
			{
				total += archive.get(index++).high;
				days++;
			}
		}
		
		else while(archive.get(index).day.get(Calendar.MONTH) == date.get(Calendar.MONTH))
		{
			total += archive.get(index++).high;
			days++;
		}
		
		return total/days;
	}
	
	int getLow(int index)
	{
		return archive.get(index).low;
	}
	
	int getLow(GregorianCalendar date) throws DatabaseException
	{
		return archive.get(searchIndex(date)).low;
	}
	
	int getLow(int day, int month, int year) throws DatabaseException
	{
		return archive.get(searchIndex(new GregorianCalendar(year, month - 1, day))).low;
	}
	
	int getWeekAverageLow(int day, int month, int year) throws DatabaseException
	{
		return getWeekAverageLow(new GregorianCalendar(year, month - 1, day));
	}
	
	int getWeekAverageLow(GregorianCalendar date) throws DatabaseException
	{
		int index = searchIndex(new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH)));
		int total = 0;
		int days = 0;
		
		if(date.get(Calendar.WEEK_OF_YEAR) == end.get(Calendar.MONTH))
		{
			while(index < count)
			{
				total += archive.get(index++).low;
				days++;
			}
		}
		
		else while(archive.get(index).day.get(Calendar.WEEK_OF_YEAR) == date.get(Calendar.WEEK_OF_YEAR))
		{
			total += archive.get(index++).low;
			days++;
		}
		
		return total/days;
	}
	
	int getMonthAverageLow(int day, int month, int year) throws DatabaseException
	{
		return getMonthAverageLow(new GregorianCalendar(year, month - 1, 1));
	}
	
	int getMonthAverageLow(int month, int year) throws DatabaseException
	{
		return getMonthAverageLow(new GregorianCalendar(year, month - 1, 1));
	}
	
	int getMonthAverageLow(GregorianCalendar date) throws DatabaseException
	{
		int index = searchIndex(new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH), 1));
		int total = 0;
		int days = 0;
		
		if(date.get(Calendar.MONTH) == end.get(Calendar.MONTH))
		{
			while(index < count)
			{
				total += archive.get(index++).low;
				days++;
			}
		}
		
		else while(archive.get(index).day.get(Calendar.MONTH) == date.get(Calendar.MONTH))
		{
			total += archive.get(index++).low;
			days++;
		}
		
		return total/days;
	}
	
	int getOpen(int index)
	{
		return archive.get(index).open;
	}
	
	int getOpen(GregorianCalendar date) throws DatabaseException
	{
		return archive.get(searchIndex(date)).open;
	}
	
	int getOpen(int day, int month, int year) throws DatabaseException
	{
		return archive.get(searchIndex(new GregorianCalendar(year, month - 1, day))).open;
	}
	
	int getWeekAverageOpen(int day, int month, int year) throws DatabaseException
	{
		return getWeekAverageOpen(new GregorianCalendar(year, month - 1, day));
	}
	
	int getWeekAverageOpen(GregorianCalendar date) throws DatabaseException
	{
		int index = searchIndex(new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH)));
		int total = 0;
		int days = 0;
		
		if(date.get(Calendar.WEEK_OF_YEAR) == end.get(Calendar.MONTH))
		{
			while(index < count)
			{
				total += archive.get(index++).open;
				days++;
			}
		}
		
		else while(archive.get(index).day.get(Calendar.WEEK_OF_YEAR) == date.get(Calendar.WEEK_OF_YEAR))
		{
			total += archive.get(index++).open;
			days++;
		}
		
		return total/days;
	}
	
	int getMonthAverageOpen(int month, int year) throws DatabaseException
	{
		return getMonthAverageOpen(new GregorianCalendar(year, month - 1, 1));
	}
	
	int getMonthAverageOpen(int day, int month, int year) throws DatabaseException
	{
		return getMonthAverageOpen(new GregorianCalendar(year, month - 1, 1));
	}
	
	int getMonthAverageOpen(GregorianCalendar date) throws DatabaseException
	{
		int index = searchIndex(new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH), 1));
		int total = 0;
		int days = 0;
		
		if(date.get(Calendar.MONTH) == end.get(Calendar.MONTH))
		{
			while(index < count)
			{
				total += archive.get(index++).open;
				days++;
			}
		}
		
		else while(archive.get(index).day.get(Calendar.MONTH) == date.get(Calendar.MONTH))
		{
			total += archive.get(index++).open;
			days++;
		}
		
		return total/days;
	}
	
	protected EndOfDay search(GregorianCalendar date) throws DatabaseException
	{
		return archive.get(searchIndex(date));
	}
	
	protected int searchIndex(GregorianCalendar date) throws DatabaseException
	{
		if(date.compareTo(end)>0) throw new DatabaseException("Wrong date");
		
		int first = start.get(Calendar.YEAR) * 365 + start.get(Calendar.DAY_OF_YEAR);
		int last = end.get(Calendar.YEAR) * 365 + end.get(Calendar.DAY_OF_YEAR);
		int now = date.get(Calendar.YEAR) * 365 + date.get(Calendar.DAY_OF_YEAR);
		
		int index = count*(now-first)/(last-first) - 4;
		
		while(archive.get(index).day.compareTo(date) < 0)
			index++;
		
		return index;
	}
	
	void update() throws IOException
	{		
		GregorianCalendar date = new GregorianCalendar();
		if(date.get(Calendar.YEAR) < end.get(Calendar.YEAR)) return;
		if(date.get(Calendar.DAY_OF_YEAR) <= end.get(Calendar.DAY_OF_YEAR)) return;
		end.set(Calendar.DAY_OF_YEAR, end.get(Calendar.DAY_OF_YEAR) + 1);
		
		DataOutputStream out = new DataOutputStream(new FileOutputStream("data/" + name + ".dbf", true));
		String[] data = Database.load(symbol, Database.convertDate(end), Database.today);
		
		for(int i = 1; i < data.length - 1; i++)
			try
			{
				writeLine(out, data[i], 100);
				count++;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
		end = date;
		out.close();
		return;
	}
	
	void writeLine(DataOutputStream out, String line, int divisor) throws IOException
	{
		System.out.println(line);
		
		String[] tab = line.split(",");
		String[] date = tab[0].split("-");
		int ndate = 0;
		int value = 0;
		
		int d = Integer.parseInt(date[2]);
		int m = Integer.parseInt(date[1]);
		int y = Integer.parseInt(date[0]);
		
		ndate = (y << 8);
		ndate += m;
		ndate <<= 8;
		ndate += d;
		
		value = (int)(Double.parseDouble(tab[4])*divisor);
		
		out.writeInt(ndate);
		out.writeInt((int)(Double.parseDouble(tab[1])*divisor));
		out.writeInt((int)(Double.parseDouble(tab[2])*divisor));
		out.writeInt((int)(Double.parseDouble(tab[3])*divisor));
		out.writeInt(value);
		
		if(tab.length > 6)
			out.writeLong(Long.parseLong(tab[5]));
		else
			out.writeLong(-1);
	}
	
	void save(DataOutputStream writer) throws IOException
	{
		writer.writeUTF(name);
		writer.writeUTF(symbol);
		
		writer.writeShort(start.get(Calendar.YEAR));
		writer.writeByte(start.get(Calendar.MONTH) + 1);
		writer.writeByte(start.get(Calendar.DAY_OF_MONTH));
		
		writer.writeShort(end.get(Calendar.YEAR));
		writer.writeByte(end.get(Calendar.MONTH) + 1);
		writer.writeByte(end.get(Calendar.DAY_OF_MONTH));
		
		writer.writeInt(count);
		writer.writeInt(value);
	}
}

class DatabaseException extends Exception
{

	public DatabaseException(String string) {
		super(string);
	}
	
}