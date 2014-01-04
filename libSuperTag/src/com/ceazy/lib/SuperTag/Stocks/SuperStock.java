package com.ceazy.lib.SuperTag.Stocks;

import android.os.Parcel;
import android.os.Parcelable;

public class SuperStock implements Parcelable {
	
	String symbol, stockExchange;
	long daysHigh, daysLow, yearsHigh, yearsLow, price, change, volume;
	
	public SuperStock(String symbol, long price) {
		setSymbol(symbol);
		setPrice(price);
	}
	
	protected SuperStock(Parcel in) {
		setSymbol(in.readString());
		setPrice(in.readLong());
		setChange(in.readLong());
		setDaysHigh(in.readLong());
		setDaysLow(in.readLong());
		setYearsHigh(in.readLong());
		setYearsLow(in.readLong());
		setVolume(in.readLong());
		setStockExchange(in.readString());
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public void setPrice(long price) {
		this.price = price;
	}
	
	public void setChange(long change) {
		this.change = change;
	}
	
	public void setDaysHigh(long daysHigh) {
		this.daysHigh = daysHigh;
	}
	
	public void setDaysLow(long daysLow) {
		this.daysLow = daysLow;
	}
	
	public void setYearsHigh(long yearsHigh) {
		this.yearsHigh = yearsHigh;
	}
	
	public void setYearsLow(long yearsLow) {
		this.yearsLow = yearsLow;
	}
	
	public void setVolume(long volume) {
		this.volume = volume;
	}
	
	public void setStockExchange(String stockExchange) {
		this.stockExchange = stockExchange;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public long getChange() {
		return change;
	}
	
	public long getPrice() {
		return price;
	}
	
	public long getDaysHigh() {
		return daysHigh;
	}
	
	public long getDaysLow() {
		return daysLow;
	}
	
	public long getYearsHigh() {
		return yearsHigh;
	}
	
	public long getYearsLow() {
		return yearsLow;
	}
	
	public long getVolume() {
		return volume;
	}
	
	public String getStockExchange() {
		return stockExchange;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(getSymbol());
		out.writeLong(getPrice());
		out.writeLong(getChange());
		out.writeLong(getDaysHigh());
		out.writeLong(getDaysLow());
		out.writeLong(getYearsHigh());
		out.writeLong(getYearsLow());
		out.writeLong(getVolume());
		out.writeString(getStockExchange());
	}
	
	Creator<SuperStock> CREATOR = new Creator<SuperStock>() {

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
