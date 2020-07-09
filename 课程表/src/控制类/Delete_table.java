package 控制类;

import java.sql.Connection;

import 课程表.Course;
import 课程表.Course_Table;

public class Delete_table {
	private Course_Table course_table;
	private String table_name;
	
	//删除数据库表和数据库配置信息
	public void delete_DBtable() {
		Tool.deleteDBtable(this.table_name);
		Tool.delete_course_table(this.course_table);
	}
	public Delete_table(Course_Table course_table) {
		this.course_table=course_table;
		this.table_name="_"+this.course_table.getId()+"_"+this.course_table.getSemester();
	}
}
