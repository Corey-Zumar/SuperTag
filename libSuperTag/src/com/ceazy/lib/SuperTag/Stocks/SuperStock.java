package com.ceazy.lib.SuperTag.Stocks;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**<b>class SuperStock</b>
 * <p>A data class containing information about a stock obtained via the 
 * {@link com.ceazy.lib.SuperTag.SuperTag#getJSON getJSON} method within a "stocks" {@link com.ceazy.lib.SuperTag.SuperTag
 * SuperTag}</p>
 * <p>SuperStock data is obtained using the Yahoo Query Language
 * @see <a href="http://developer.yahoo.com/yql/guide/">Yahoo Query Language</a>
 */
public class SuperStock implements Parcelable {
	
	StocksQuery query;
	private String symbol, name, stockExchange;
	private double change = -1;
	private double daysLow = -1;
	private double daysHigh = -1;
	private double yearLow = -1;
	private double yearHigh = -1;
	private double currentPrice = -1;
	private double volume = -1;
	
	/**<b>{@link SuperStock} class constructor</b>*/
	public SuperStock() {

	}
	
	protected SuperStock(Parcel in) {
		this.symbol = in.readString();
		this.name = in.readString();
		this.stockExchange = in.readString();
		this.change = in.readDouble();
		this.daysLow = in.readDouble();
		this.daysHigh = in.readDouble();
		this.yearLow = in.readDouble();
		this.yearHigh = in.readDouble();
		this.currentPrice = in.readDouble();
		this.volume = in.readDouble();
	}
	
	/**<b><i>public <code>String</code> getSymbol()</i></b>
	 * <br></br>
	 * @return The stock's ticker symbol
	 * @see <a href="http://en.wikipedia.org/wiki/Ticker_symbol">Ticker Symbols</a>
	 */
	public String getSymbol() {
		if(symbol != null) {
			return symbol;
		}
		return query.getResults().getQuote().getSymbol();
	}
	
	/**<b><i>public <code>double</code> getChange()</i></b>
	 * <br></br>
	 * @return The change in the stock's value in the past trading day
	 */
	public double getChange() {
		if(change != -1) {
			return change;
		}
		return query.getResults().getQuote().getChange();
	}
	
	/**<b><i>public <code>double</code> getPercentageChange()</i></b>
	 * <br></br>
	 * @return The percentage change in the stock's value in the past trading day
	 */
	public double getPercentageChange() {
		double change = getChange();
		return ((100 * change)/(getCurrentPrice() - change));
	}
	
	/**<b><i>public <code>double</code> getDaysLow()</i></b>
	 * <br></br>
	 * @return The lowest value of the stock during the past trading day
	 */
	public double getDaysLow() {
		if(daysLow != -1) {
			return daysLow;
		}
		return query.getResults().getQuote().getDaysLow();
	}
	
	/**<b><i>public <code>double</code> getDaysHigh()</i></b>
	 * <br></br>
	 * @return The highest value of the stock during the past trading day
	 */
	public double getDaysHigh() {
		if(daysHigh != -1) {
			return daysHigh;
		}
		return query.getResults().getQuote().getDaysHigh();
	}
	
	/**<b><i>public <code>double</code> getYearLow()</i></b>
	 * <br></br>
	 * @return The lowest value of the stock in the current year
	 */
	public double getYearLow() {
		if(yearLow != -1) {
			return yearLow;
		}
		return query.getResults().getQuote().getYearLow();
	}
	
	/**<b><i>public <code>double</code> getYearHigh()</i></b>
	 * <br></br>
	 * @return The highest value of the stock in the current year
	 */
	public double getYearHigh() {
		if(yearHigh != -1) {
			return yearHigh;
		}
		return query.getResults().getQuote().getYearHigh();
	}
	
	/**<b><i>public <code>double</code> getCurrentPrice()</i></b>
	 * <br></br>
	 * @return The current value of the stock
	 */
	public double getCurrentPrice() {
		if(currentPrice != -1) {
			return currentPrice;
		}
		return query.getResults().getQuote().getCurrentPrice();
	}
	
	/**<b><i>public <code>String</code> getName()</i></b>
	 * <br></br>
	 * @return The name of the company with which the stock is associated
	 */
	public String getName() {
		if(name != null) {
			return name;
		}
		return query.getResults().getQuote().getName();
	}
	
	/**<b><i>public <code>double</code> getVolume()</i></b>
	 * <br></br>
	 * @return The volume (number of shares traded) of the stock
	 * @see <a href="http://en.wikipedia.org/wiki/Volume_(finance)">Stock Volume</a>
	 */
	public double getVolume() {
		if(volume != -1) {
			return volume;
		}
		return query.getResults().getQuote().getVolume();
	}
	
	/**<b><i>public <code>String</code> getName()</i></b>
	 * <br></br>
	 * @return The name of stock exchange on which the stock is traded
	 */
	public String getStockExchange() {
		if(stockExchange != null) {
			return stockExchange;
		}
		return query.getResults().getQuote().getStockExchange();
	}
	
	class StocksQuery {
		StockResults results;
		protected StockResults getResults() {
			return results;
		}
	}
	
	class StockResults {
		StockQuote quote;
		protected StockQuote getQuote() {
			return quote;
		}
	}
	
	class StockQuote {
		String symbol;
		@SerializedName("Change")
		double change;
		@SerializedName("DaysLow")
		double daysLow;
		@SerializedName("daysHigh")
		double daysHigh;
		@SerializedName("YearLow")
		double yearLow;
		@SerializedName("YearHigh")
		double yearHigh;
		@SerializedName("LastTradePriceOnly")
		double currentPrice;
		@SerializedName("Name")
		String name;
		@SerializedName("Volume")
		double volume;
		@SerializedName("StockExchange")
		String stockExchange;
		
		protected String getSymbol() {
			return symbol;
		}
		
		protected double getChange() {
			return change;
		}
		
		protected double getDaysLow() {
			return daysLow;
		}
		
		protected double getDaysHigh() {
			return daysHigh;
		}
		
		protected double getYearLow() {
			return yearLow;
		}
		
		protected double getYearHigh() {
			return yearHigh;
		}
		
		protected double getCurrentPrice() {
			return currentPrice;
		}
		
		protected String getName() {
			return name;
		}
		
		protected double getVolume() {
			return volume;
		}
		
		protected String getStockExchange() {
			return stockExchange;
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(getSymbol());
		out.writeString(getName());
		out.writeString(getStockExchange());
		out.writeDouble(getDaysLow());
		out.writeDouble(getDaysHigh());
		out.writeDouble(getYearLow());
		out.writeDouble(getYearHigh());
		out.writeDouble(getCurrentPrice());
		out.writeDouble(getVolume());
	}
	
	public static final Creator<SuperStock> CREATOR = new Creator<SuperStock>() {

		@Override
		public SuperStock createFromParcel(Parcel in) {
			return new SuperStock(in);
		}

		@Override
		public SuperStock[] newArray(int size) {
			return new SuperStock[size];
		}
		
	};

}
