package utils;

public class SimpleClock {
	private long lastRecord = 0;
	private long time = 0;
	public SimpleClock() {
	}
	
	public void start() {
		lastRecord = System.currentTimeMillis();
	}
	
	public void end() {
		time = lastRecord;
		lastRecord = System.currentTimeMillis();
		time = lastRecord - time;
	}
	
	public void displayTime() {
		System.out.println(" --Time: " + showTime());
	}
	
	public String showTime() {
		long secs = time/1000;
		long msec = time-secs*1000;
		if (secs <= 0) {
			return (msec + " ms");
		}
		return (secs + "." + msec + " s");
	}
	
}
