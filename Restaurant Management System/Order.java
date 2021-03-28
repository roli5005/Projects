package BusinessLayer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Order {
	private static final AtomicInteger count = new AtomicInteger(0);
	private LocalDate Date;
	private LocalTime Time;
	private int orderId;
	private int Table;
	Random TableNr = new Random();
	
	public Order() {
		setDate();
		orderId = count.incrementAndGet();
		Table = TableNr.nextInt(30);
	}
	@Override
	public int hashCode() {
		return orderId;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (orderId != other.orderId)
			return false;
		return true;
	}
	public LocalDate getDate() {
		return Date;
	}
	public LocalTime getTime() {
		return Time;
	}
	public void setDate() {
		Date = LocalDate.now();
		Time = LocalTime.now();
	}
	public int getTable() {
		return Table;
	}
	public static AtomicInteger getCount() {
		return count;
	}
	

}
