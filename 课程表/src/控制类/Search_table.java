package 控制类;
import 课程表.Course;
import 课程表.Table_control;
import 课程表.Course_Table;
import java.sql.Connection;

import 控制类.Tool;
public class Search_table {
	private Course course[];
	private Course_Table course_table;
	private String table_name;
	private Connection conn;//数据库连接
	
	//按周查询
	public void searchByweek(int week) {
		Course[]temp_course=Tool.readCourse(this.table_name);
		int length=0;		
		for(int i=0;i<temp_course.length;i++) {
			if(week>=temp_course[i].getBegin_week() && week<=temp_course[i].getEnd_week())
				length++;					
		}
		this.course=new Course[length];
		int count=0;
		//将一个学期的数组缩小为一周的数组
		for(int i=0;i<temp_course.length;i++) {
			if(week>=temp_course[i].getBegin_week() && week<=temp_course[i].getEnd_week()) {
				this.course[count]=temp_course[i];
				count++;
			}
		}
		for(int i=0;i<length;i++) {
			System.out.println(this.course[i].getCourse_name());
		}
	}
	//按学期查询
	public void searchBysemester(String semester) {
		
	}
	public Course[] getCourse() {
		return course;
	}
	public void setCourse(Course course[]) {
		this.course = course;
	}
	public Connection getConn() {
		return conn;
	}
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	public Search_table(Course_Table course_table) {
		this.course_table=course_table;
		this.table_name="_"+this.course_table.getId()+"_"+this.course_table.getSemester();
	}
}
