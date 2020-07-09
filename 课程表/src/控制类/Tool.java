package 控制类;
import java.sql.*;
import 课程表.Course_Table;
import 课程表.Course;
public class Tool {
	//连接数据库
	public static Connection connectDB() {
		Connection conn=null;
		String sql = "jdbc:sqlite:..\\数据\\test1.db";
		String driverClass="org.sqlite.JDBC";
		try {
			Class.forName(driverClass);
			conn = DriverManager.getConnection(sql);
			System.out.println("Link to database successfully!");
		}
		catch(java.lang.ClassNotFoundException | SQLException e) {
			System.err.println( e.getMessage());
		}
		return conn;
	}
	//新建数据库表
	public static void createDBtable(String table_name)
	{
		try {
			Connection conn=Tool.connectDB();
			Statement stat = conn.createStatement();
			String command="create table if not exists "+table_name+"(course_name varchar(30), course_location varchar(50), course_teacher varchar(10), begin_week int, end_week int, day int, begin_lesson int, end_lesson int);";
			stat.executeUpdate(command);// 创建一个表，八列
			System.out.println("create table success!");
         // 关闭Statement
            stat.close();
            conn.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	//删除数据库表
	public static void deleteDBtable(String table_name)
	{
		try {
			Connection conn=Tool.connectDB();
			Statement stat = conn.createStatement();
			String command="drop table "+table_name;
			stat.executeUpdate(command);// 创建一个表，八列
			System.out.println("drop table success!");
         // 关闭Statement
            stat.close();
            conn.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	//删除课程表配置信息记录
	public static void delete_course_table(Course_Table course_table) {
		try {
			Connection conn=Tool.connectDB();
			Statement stat = conn.createStatement();
			String sql = "delete from course_table where id=? and semester=?";//数据库操作语句（插入）
			PreparedStatement pst = conn.prepareStatement(sql);//用来执行SQL语句查询，对sql语句进行预编译处理
			pst.setString(1, course_table.getId());
			pst.setString(2, course_table.getSemester());
			pst.executeUpdate();
			System.out.println("delete from table successfully!");
			pst.close();
			stat.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	//从Course_Table类导入数据库
	public static void course_tableToDB(Course_Table course_table) {
		Connection conn=Tool.connectDB();
		try {
			Statement stat = conn.createStatement();
			String sql = "insert into course_table values(?,?,?,?,?,?,?,?)";//数据库操作语句（插入）
			PreparedStatement pst = conn.prepareStatement(sql);//用来执行SQL语句查询，对sql语句进行预编译处理
			pst.setString(1, course_table.getId());
			pst.setString(2, course_table.getSemester());
			pst.setString(3, course_table.getDate_start());
			pst.setInt(4, course_table.getWeeks());
			pst.setInt(5, course_table.getWeek_now());
			pst.setInt(6, course_table.getDay_now());
			pst.setInt(7, course_table.getLesson_now());
			pst.setInt(8, course_table.getLesson_max());
			pst.executeUpdate();
			System.out.println("Insert to table successfully!");
			pst.close();
			stat.close();
			//conn.commit();
			conn.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	//从数据库读取课程表配置文件
	public static Course_Table[] readCourse_Table() {
		Course_Table course_Table[]=new Course_Table[1];
		Connection conn=Tool.connectDB();
		
		try {
		 	Statement stat = conn.createStatement();
			String sql = "select COUNT(*) length from  course_table";
			ResultSet result = stat.executeQuery(sql);
			result.next();
			int length=result.getInt("length");
			course_Table=new Course_Table[length];
			sql = "select * from  course_table";
			result = stat.executeQuery(sql);
			int i=0;
			while (result.next())
			{
				course_Table[i]=new Course_Table();
				course_Table[i].setId(result.getString("id"));
				course_Table[i].setSemester(result.getString("semester"));
				course_Table[i].setDate_start(result.getString("date_start"));
				course_Table[i].setWeeks(result.getInt("weeks"));
				course_Table[i].setWeek_now(result.getInt("week_now"));
				course_Table[i].setDay_now(result.getInt("day_now"));
				course_Table[i].setLesson_now(result.getInt("lesson_now"));
				course_Table[i].setLesson_max(result.getInt("lesson_max"));
				i++;
			}
			result.close();
			stat.close();
			conn.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return course_Table;
	}
	//从数据库读取课程表文件
	public static Course[] readCourse(String table_name) {
		Course[] temp_course=new Course[1];//防止编译器报错
		Connection conn=Tool.connectDB();
		 int i=0;
		 try {
			 	Statement stat = conn.createStatement();
				String sql = "select COUNT(*) length from  "+table_name;
				ResultSet result = stat.executeQuery(sql);
				result.next();
				int length=result.getInt("length");
				temp_course=new Course[length];
				
				sql = "select * from  "+table_name;
				result = stat.executeQuery(sql);
				while (result.next())
				{
					temp_course[i] = new Course();
					temp_course[i].setCourse_name(result.getString("course_name"));
					temp_course[i].setCourse_location(result.getString("course_location"));
					temp_course[i].setCourse_teacher(result.getString("course_teacher"));
					temp_course[i].setBegin_week(result.getInt("begin_week"));
					temp_course[i].setEnd_week(result.getInt("end_week"));
					temp_course[i].setDay(result.getInt("day"));
					temp_course[i].setBegin_lesson(result.getInt("begin_lesson"));
					temp_course[i].setEnd_lesson(result.getInt("end_lesson"));
					
					i++;
				}
				result.close();
				stat.close();
				conn.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		 return temp_course;
	}
	public static String ClearBracket(String context) {
	      // 修改原来的逻辑，防止右括号出现在左括号前面的位置
	      int head = context.indexOf('('); // 标记第一个使用左括号的位置
	      if (head == -1) {
	          return context; // 如果context中不存在括号，什么也不做，直接跑到函数底端返回初值str
	      } else {
	          int next = head + 1; // 从head+1起检查每个字符
	          int count = 1; // 记录括号情况
	          do {
	              if (context.charAt(next) == '(') {
	                  count++;
	              } else if (context.charAt(next) == ')') {
	                  count--;
	              }
	              next++; // 更新即将读取的下一个字符的位置
	              if (count == 0) { // 已经找到匹配的括号
	                  String temp = context.substring(head, next);
	                  context = context.replace(temp, ""); // 用空内容替换，复制给context
	                  head = context.indexOf('('); // 找寻下一个左括号
	                  next = head + 1; // 标记下一个左括号后的字符位置
	                  count = 1; // count的值还原成1
	              }
	          } while (head != -1); // 如果在该段落中找不到左括号了，就终止循环
	      }
	      return context; // 返回更新后的context
	  }
}
