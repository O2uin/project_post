package temp;
import java.util.*;
import java.text.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TimeCheck {
	
	public void nowTime() {//현재시간 출력
		Date now=new Date();
		
		SimpleDateFormat sdf=new SimpleDateFormat("| yyyy.MM.dd. a hh시 mm분 |");
		String strNow=sdf.format(now);
		System.out.println(strNow);
	}
	
	public String timeFormat(LocalDateTime time) {//모양정리
		DateTimeFormatter sdf = DateTimeFormatter.ofPattern("a hh시 mm분");
		String strTime=time.format(sdf);
		return strTime;
	}
	
	public String expireFormat(LocalDateTime time) {//유통기한 출력을 위한 용도
		DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy년 M월 d일 hh시 mm분");
		String expire=time.format(sdf);
		return expire;
	}
	
	public LocalDateTime readTime() {//현시간 return
		return LocalDateTime.now();
	}
	
	public long timeCompute(LocalDateTime start, LocalDateTime end) {
		//start랑 end 시간 비교(분)
		long minute=start.until(end, ChronoUnit.MINUTES);
		return minute;
	}
	
	public LocalDateTime timeSet(int year, int month, int day, int hour, int minute) {//유통기한 return
		//SimpleDateFormat fm=new SimpleDateFormat("yyyy.mm.dd.");
		LocalDateTime time= LocalDateTime.of(year,month,day,hour,minute,0);
		return time;
	}
	
	/*public LocalDateTime expireSet(LocalDateTime expire,int hours,int minutes) {//원본 시간에 시간 더하기
		Duration toAdd = Duration.ofHours(hours).plusMinutes(minutes);
		LocalDateTime result=expire.plus(toAdd);
		return result;
	}*/
}
