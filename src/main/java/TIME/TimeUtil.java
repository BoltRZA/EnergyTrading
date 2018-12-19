package TIME;
public class TimeUtil {
	public static long hD = 60000;
	public static int hourDuration = 30000;

	public static void main(String[] args) {
		
	}
	
	public static int CurrentHour() {
		long ctm = System.currentTimeMillis();
		int days = (int) (ctm/hD/24);
		int hour = (int) (ctm/hD - days*24);
		return (hour-1);
		//return 10;
	}
	public static long getSecMillTillNextHour() {
		long ctm = System.currentTimeMillis();
		return hD-ctm%(hD);
		//return 20000;
	}

}