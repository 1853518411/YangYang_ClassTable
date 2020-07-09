package 控制类;
import 课程表.Course;
import 课程表.Course_Table;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class Edit_table {
	private Course course[];
	private Course_Table course_table;//这里是为了获取表名
	private Connection conn;//数据库连接
	private String table_name;
	
	//添加课程
	public void add_course(Course course) {
		if(course_conflict(course)) {
			
			try {
				Statement stat = conn.createStatement();
				String sql = "insert into "+this.table_name+" values(?,?,?,?,?,?,?,?)";//数据库操作语句（插入）
				PreparedStatement pst = conn.prepareStatement(sql);//用来执行SQL语句查询，对sql语句进行预编译处理
				pst.setString(1, course.getCourse_name());
				pst.setString(2, course.getCourse_location());
				pst.setString(3, course.getCourse_teacher());
				pst.setInt(4, course.getBegin_week());
				pst.setInt(5, course.getEnd_week());
				pst.setInt(6, course.getDay());
				pst.setInt(7, course.getBegin_lesson());
				pst.setInt(8, course.getEnd_lesson());
				pst.executeUpdate();
				System.out.println("Insert to table successfully!");
				pst.close();
				stat.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		else {
			return;
		}
	}
	//编辑课程
	public void edit_course(Course course_old,Course course_new) {
		if(course_conflict(course_new)) {
			try {
				Statement stat = conn.createStatement();
				String sql = "delete from "+this.table_name+" where begin_week=? and day=? and begin_lesson=?";//数据库操作语句（插入）
				PreparedStatement pst = conn.prepareStatement(sql);//用来执行SQL语句查询，对sql语句进行预编译处理
				pst.setInt(1, course_old.getBegin_week());
				pst.setInt(2, course_old.getDay());
				pst.setInt(3, course_old.getBegin_lesson());
				pst.executeUpdate();
				
				sql= "insert into "+this.table_name+" values(?,?,?,?,?,?,?,?)";//数据库操作语句（插入）;
				pst = conn.prepareStatement(sql);//用来执行SQL语句查询，对sql语句进行预编译处理
				pst.setString(1, course_new.getCourse_name());
				pst.setString(2, course_new.getCourse_location());
				pst.setString(3, course_new.getCourse_teacher());
				pst.setInt(4, course_new.getBegin_week());
				pst.setInt(5, course_new.getEnd_week());
				pst.setInt(6, course_new.getDay());
				pst.setInt(7, course_new.getBegin_lesson());
				pst.setInt(8, course_new.getEnd_lesson());
				pst.executeUpdate();
				
				System.out.println("Edit table successfully!");
				pst.close();
				stat.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		else {
			return;
		}		
	}
	//删除课程
	public void delete_course(Course course) {
		try {
			Statement stat = conn.createStatement();
			String sql = "delete from "+this.table_name+" where begin_week=? and day=? and begin_lesson=?";//数据库操作语句（插入）
			PreparedStatement pst = conn.prepareStatement(sql);//用来执行SQL语句查询，对sql语句进行预编译处理
			pst.setInt(1, course.getBegin_week());
			pst.setInt(2, course.getDay());
			pst.setInt(3, course.getBegin_lesson());
			pst.executeUpdate();
			System.out.println("delete from table successfully!");
			pst.close();
			stat.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	//检测课程是否冲突
	public boolean course_conflict(Course course) {
		boolean state=true;
		List<String> list_of_course_lesson = makeList(course.getBegin_lesson(),course.getEnd_lesson());	
		List<String> list_of_course_week = makeList(course.getBegin_week(),course.getEnd_week());
		List<String> list_of_week = null;
		List<String> list_of_lesson = null; //每周这天课程的始末节列表
		try {
			Statement stat = conn.createStatement();
			String sql = "select * from "+this.table_name+" where day=?";
			PreparedStatement pst = conn.prepareStatement(sql);//用来执行SQL语句查询，对sql语句进行预编译处理
			pst.setInt(1, course.getDay());
			ResultSet result=pst.executeQuery();
			while(result.next()) 
			{
				list_of_week = makeList(result.getInt("begin_week"),result.getInt("end_week"));
				list_of_lesson = makeList(result.getInt("begin_lesson"),result.getInt("end_lesson"));
				list_of_week.retainAll(list_of_course_week);
				list_of_lesson.retainAll(list_of_course_lesson);
				
				if(list_of_week.isEmpty()||list_of_course_lesson.isEmpty())
				{
					//"不包含"
				}
				else
				{
					state=false;
					break;
				}
			}
			pst.close();
			stat.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}	
		return state;
	}
	//传入起始节和结束节，生成列表，可用于判断课程是否冲突
	public List<String> makeList(int start,int end)
	{
		int times = 0;
		int tmp = start;
		List<String> tmp_list = new ArrayList<String>();
		for(times=0;times<end-start+1;times++)
		{
			tmp_list.add(String.valueOf(tmp));
			tmp++;
		}
		return tmp_list;
	}
	
	public Edit_table(Course course[],Course_Table course_table) {
		  this.course=course;
		  this.course_table=course_table;
		  this.table_name="_"+this.course_table.getId()+"_"+this.course_table.getSemester();
		  conn=Tool.connectDB();
	}
	
	
}