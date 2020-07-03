package 控制类;
import 课程表.Course;

import java.sql.Connection;

import 控制类.Tool;
public class Import_table {
	private String url;//Excel文件路径
	private Course course[];
	private Connection conn;//数据库连接
		
	//读取课程表Excel文件
	public void importCourseFile(String url) {
		
		//fileTocourse(this.course);
		
	}
	//从文件转换到Course类
	public void fileTocourse(Course course[]) {
		
	}
	//从Course类导入数据库
	public void courseToDB(Course course[]) {
		conn=Tool.connectDB();
	}
}
