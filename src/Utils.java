/**
 * Created with IntelliJ IDEA.
 * User: ddanilov
 * Date: 1/6/13
 * Time: 12:08 PM
 * To change this template use File | Settings | File Templates.
 */

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.*;

public class Utils {

	public static long getUserId(String userAddress) {
		userAddress = userAddress.trim();
		// http://photographers.com.ua/pictures/user/23389/
		Pattern pattern = Pattern.compile("https?://photographers.com.ua/pictures/user/(\\d+)/");
		Matcher matcher = pattern.matcher(userAddress);
		if (matcher.find()) {
			return Long.parseLong(matcher.group(1));
		}
		throw new RuntimeException("Cannot parse address: " + userAddress);
	}

	public static Date parseDate(String strDate) throws Exception {

		strDate = strDate.trim();

		//"29 December '12, 12:09
		if ( strDate.matches("^\\d+\\s+\\w+\\s'\\d{2},\\s\\d+:\\d+") ) {
			SimpleDateFormat dataFormat = new SimpleDateFormat("d MMMM ''yy, HH:mm");
			Date date = dataFormat.parse(strDate);
			return date;
		}
		//1 января, 19:54
		else if ( strDate.matches("^\\d+\\s+\\w+,\\s\\d+:\\d+") ) {
			SimpleDateFormat dataFormat = new SimpleDateFormat("d MMMM, HH:mm");
			Calendar cal = Calendar.getInstance();
			cal.setTime( dataFormat.parse(strDate) );
			cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
			return cal.getTime();
		}
		//Yesterday, 18:55
		else if( strDate.matches("Yesterday,\\s+\\d+:\\d+") ) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(parseTime(strDate));
			cal.add(Calendar.DATE, -1);
			return cal.getTime();
		}
		//Today, 15:33
		else if( strDate.matches("Today,\\s+\\d+:\\d+") ) {
			return parseTime(strDate);
		}
		//20 minutes, ago
		else if(strDate.matches("^\\d+\\s+minutes.*")) {
			String[] tokens = strDate.split(" ");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, -Integer.parseInt(tokens[0]));
			cal.set(Calendar.MILLISECOND, 0);
			return cal.getTime();
		}

		throw  new RuntimeException("Cannot parse date: " + strDate);
	}

	private static Date parseTime(String strDateTime) throws Exception {
		Pattern pattern = Pattern.compile("(\\d+)");
		Matcher matcher = pattern.matcher(strDateTime);
		Calendar cal = Calendar.getInstance();
		if (matcher.find())  {
			cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt( matcher.group(1) ) );
			if (matcher.find()) {
				cal.set(Calendar.MINUTE, Integer.parseInt( matcher.group(1) ) );
				cal.set(Calendar.MILLISECOND, 0);
				return cal.getTime();
			}
		}
		throw  new RuntimeException("Cannot parse date: " + strDateTime);
	}
}
