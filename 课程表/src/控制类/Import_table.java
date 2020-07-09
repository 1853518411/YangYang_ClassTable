package 控制类;
import 课程表.Course;
import 课程表.Course_Table;
import java.sql.*;
import java.io.File;
import java.io.IOException;

import jxl.*;
import jxl.read.biff.BiffException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import 控制类.Tool;
public class Import_table {
	private String url;//Excel文件路径
	private Course course[];
	private Course_Table course_table;
	private String table_name;
	private Connection conn;//数据库连接
	
	public String getNumeric(String str) {
	    String regEx="[^0-9]";  
	    Pattern p = Pattern.compile(regEx);  
	    Matcher m = p.matcher(str);  
	    return m.replaceAll("\n").trim();
	}
	//读取课程表Excel文件
	public void importCourseFile(String url) {
		Course[] temp_cos=new Course[50];
		int j,k,p,i,q,z=0;
		Sheet sheet;
		Workbook book;
		Cell cell;
		String str;
		try {
			book=Workbook.getWorkbook(new File(url));
			sheet=book.getSheet(0);
			for(p=1;p<=5;p++)
			{
				for(i=3;i<=8;i++,z++)
				{
					cell=sheet.getCell(p,i);//星期p的课
					
					if(" ".equals(cell.getContents()))
					{
						System.out.println("这一节没课！\n");
						z--;
						continue;
					}
					temp_cos[z]=new Course();
					switch(sheet.getCell(p,2).getContents().trim())
					{
					case "星期一":
						temp_cos[z].setDay(1);
						break;
					case "星期二":
						temp_cos[z].setDay(2);
						break;
					case "星期三":
						temp_cos[z].setDay(3);
						break;
					case "星期四":
						temp_cos[z].setDay(4);
						break;
					default:
						temp_cos[z].setDay(5);
					}
					
					System.out.println("星期几："+temp_cos[z].getDay());
					j=0;
					k=0;
//					System.out.println("计数菌z,p,i："+z+" "+p+" "+i);
					for(String t:cell.getContents().split("\n"))
					{
						j=k%5;
						if("".equals(t))
						{
							
							continue;
						}
						if(j==0)
						{
							if(k==5)
							{
								z++;
//								System.out.println("计数菌z,p,i："+z+" "+p+" "+i);
								temp_cos[z]=new Course();
								switch(sheet.getCell(p,2).getContents().trim())
								{
								case "星期一":
									temp_cos[z].setDay(1);
									break;
								case "星期二":
									temp_cos[z].setDay(2);
									break;
								case "星期三":
									temp_cos[z].setDay(3);
									break;
								case "星期四":
									temp_cos[z].setDay(4);
									break;
								default:
									temp_cos[z].setDay(5);
								}
								System.out.println("星期几："+temp_cos[z].getDay());
								k=0;
							}
							temp_cos[z].setCourse_name(t);
//							System.out.println("哈哈哈哈k="+k);
							System.out.println("课程："+temp_cos[z].getCourse_name());	
						}
						else if(j==1)
						{
							t=Tool.ClearBracket(t);
							temp_cos[z].setCourse_teacher(t);
							System.out.println("教师："+temp_cos[z].getCourse_teacher());
						}
						else if(j==2)
						{//
							str=getNumeric(t);
							q=0;
							for(String tmp:str.split("\n"))
							{
								if(q==0)
								{
									temp_cos[z].setBegin_week(Integer.parseInt(tmp));
									System.out.println("起始周："+temp_cos[z].getBegin_week());
								}
								else
								{
									temp_cos[z].setEnd_week(Integer.parseInt(tmp));
									System.out.println("末周："+temp_cos[z].getEnd_week());
								}
								q++;
							}
						}
						else if(j==3)
						{
							temp_cos[z].setCourse_location(t);
							System.out.println("地点："+temp_cos[z].getCourse_location());
						}
						else
						{//
							str=getNumeric(t);
							q=0;
							for(String tmp:str.split("\n"))
							{
								if(q==0)
								{
									temp_cos[z].setBegin_lesson(Integer.parseInt(tmp));
									System.out.println("起始节："+temp_cos[z].getBegin_lesson());
								}
								else
								{
									temp_cos[z].setEnd_lesson(Integer.parseInt(tmp));
									System.out.println("末节："+temp_cos[z].getEnd_lesson()+"\n");
								}
								q++;
							}
//							System.out.println("起始节："+t+"\n");
						}
						k++;
					}
				}
			}	
			book.close();
			} catch (BiffException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			System.out.println();
			this.course=temp_cos;
			//导入数据库
			courseToDB(this.course);
			//导入数据库配置信息
			Tool.course_tableToDB(this.course_table);
	}
	//从文件转换到Course类
	public void fileTocourse(Course course[]) {
		
	}
	//从Course类导入数据库
	public void courseToDB(Course course[]) {
		Connection conn=Tool.connectDB();
		//创建表
		Tool.createDBtable(this.table_name);
		try {
			Statement stat = conn.createStatement();
			
			String sql = "select * from "+this.table_name;			
			ResultSet result = stat.executeQuery(sql);
			if(!result.next()) //若表中无数据
			{
				for(Course cs:course)
				{
					if(cs==null)
						break;
					sql="insert into "+this.table_name+" values(?,?,?,?,?,?,?,?)";//数据库操作语句（插入）
					PreparedStatement pst = conn.prepareStatement(sql);//用来执行SQL语句查询，对sql语句进行预编译处理
					pst.setString(1, cs.getCourse_name());
					pst.setString(2, cs.getCourse_location());
					pst.setString(3, cs.getCourse_teacher());
					pst.setInt(4, cs.getBegin_week());
					pst.setInt(5, cs.getEnd_week());
					pst.setInt(6, cs.getDay());
					pst.setInt(7, cs.getBegin_lesson());
					pst.setInt(8, cs.getEnd_lesson());
					pst.executeUpdate();
					pst.close();
				}
				result.close();
				stat.close();
				//conn.commit();
				conn.close();
			}
			
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public Import_table(Course_Table course_table) {
		this.course_table=course_table;
		this.table_name="_"+this.course_table.getId()+"_"+this.course_table.getSemester();
		//conn = Tool.connectDB();
	}
	/*
	public static void main(String[] args) {
		Course_Table course_table=new Course_Table();
		Import_table imp=new Import_table(course_table);
		String url="D:\\Study\\软件工程\\课程设计\\阳阳课程表\\课程表EXCEL文件\\20170060217-2019-2020-1.xls";
		imp.importCourseFile(url);

	}
	*/
}
