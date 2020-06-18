package 课程表;

public class Course {
	private String course_name;
	private String course_location;
	private String course_teacher;
	private int begin_week;//起始周
	private int end_week;//结束周
	private int day;//星期几
	private int begin_lesson;//起始节
	private int end_lesson;//结束节
	
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public String getCourse_location() {
		return course_location;
	}
	public void setCourse_location(String course_location) {
		this.course_location = course_location;
	}
	public String getCourse_teacher() {
		return course_teacher;
	}
	public void setCourse_teacher(String course_teacher) {
		this.course_teacher = course_teacher;
	}
	public int getBegin_week() {
		return begin_week;
	}
	public void setBegin_week(int begin_week) {
		this.begin_week = begin_week;
	}
	public int getEnd_week() {
		return end_week;
	}
	public void setEnd_week(int end_week) {
		this.end_week = end_week;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getBegin_lesson() {
		return begin_lesson;
	}
	public void setBegin_lesson(int begin_lesson) {
		this.begin_lesson = begin_lesson;
	}
	public int getEnd_lesson() {
		return end_lesson;
	}
	public void setEnd_lesson(int end_lesson) {
		this.end_lesson = end_lesson;
	}
	
}
