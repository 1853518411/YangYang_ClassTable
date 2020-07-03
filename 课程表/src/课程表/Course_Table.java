package 课程表;

import java.util.Calendar;

public class Course_Table {
	private String id;//学号，如20170060217
	private String semester;//学期,如2019-2020-1
	private int weeks=16;//周数
	private Calendar date_start;//开学日期
	private int week_now;//当前周数
	private int day_now;//当前星期几
	private int lesson_now;//当前第几节	
	private String lesson_begintime[][]= {
			{"8:00","8:40"},{"8:45","9:25"},{"9:40","10:20"},{"10:25","11:05"},
			{"11:10","11:50"},{"13:30","14:10"},{"14:15","14:55"},{"15:10","15:50"},
			{"15:55","16:35"},{"16:40","17:20"},{"18:30","19:10"},{"19:15","19:55"},
			{"20:05","20:45"},{"20:50","21:30"}
	};//每节课上课和下课时间
	
	private int lesson_max=14;//课表最大节数
	private int lesson_time=40;//一节课上课持续时间
		
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public int getWeeks() {
		return weeks;
	}
	public void setWeeks(int weeks) {
		this.weeks = weeks;
	}
	public Calendar getDate_start() {
		return date_start;
	}
	public void setDate_start(Calendar date_start) {
		this.date_start = date_start;
	}
	public int getWeek_now() {
		return week_now;
	}
	public void setWeek_now(int week_now) {
		this.week_now = week_now;
	}
	public int getLesson_max() {
		return lesson_max;
	}
	public void setLesson_max(int lesson_max) {
		this.lesson_max = lesson_max;
	}
	public int getLesson_time() {
		return lesson_time;
	}
	public void setLesson_time(int lesson_time) {
		this.lesson_time = lesson_time;
	}
	public int getDay_now() {
		return day_now;
	}
	public void setDay_now(int day_now) {
		this.day_now = day_now;
	}
	public int getLesson_now() {
		return lesson_now;
	}
	public void setLesson_now(int lesson_now) {
		this.lesson_now = lesson_now;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String[][] getLesson_begintime() {
		return lesson_begintime;
	}
	public void setLesson_begintime(String lesson_begintime[][]) {
		this.lesson_begintime = lesson_begintime;
	}
	
	
}
