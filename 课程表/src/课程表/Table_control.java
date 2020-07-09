package 课程表;
import 课程表.Table_view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumn;

import 课程表.Course;
import 课程表.Course_Table;
import 控制类.Import_table;
import 控制类.Login;
import 控制类.Search_table;
import 控制类.Tool;
import 控制类.Delete_table;
import 控制类.Download_table;
import 控制类.Edit_table;
public class Table_control {
	private Table_view view;
	private Course course[];//当前周的课程
	private Course_Table course_table;
	private String[][] table;
	public JFrame initFrame() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//操作系统的界面风格
		Table_view.InitGlobalFont(new Font("黑体", Font.BOLD, 18));  //统一设置字体
		JFrame frame=new JFrame("阳阳课程表");
		frame.setSize(view.width, view.height);
		frame.setLocationRelativeTo(null);		
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		return frame;
	}
	public JMenuBar initMenuBar() {
		JMenuBar menuBar=new JMenuBar();		
        JMenu menu1 = new JMenu("文件");
        JMenu menu2 = new JMenu("查询");
        JMenu menu3 = new JMenu("课程");
        JMenu menu5 = new JMenu("关于");
        // 一级菜单添加到菜单栏
        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(menu3);
        menuBar.add(menu5);
        //一级菜单的子菜单
        JMenuItem menuItem1_2 = new JMenuItem("手动导入");
        JMenuItem menuItem1_3 = new JMenuItem("自动导入");
        JMenuItem menuItem1_4 = new JMenuItem("删除课表");
        menuItem1_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                import_table();
            }
        });
        menuItem1_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                auto_import();
            }
        });
        menuItem1_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete_table();
            }
        });
        // 子菜单添加到一级菜单
        menu1.add(menuItem1_2);
        menu1.add(menuItem1_3);
        menu1.add(menuItem1_4);
        JMenuItem menuItem2_1 = new JMenuItem("按周查询");
        JMenuItem menuItem2_2 = new JMenuItem("按学期查询");
        menuItem2_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	search_week();
            }
        });
        menuItem2_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search_semester();
            }
        });
        menu2.add(menuItem2_1);
        menu2.add(menuItem2_2);
        JMenuItem menuItem3_1 = new JMenuItem("添加课程");
        JMenuItem menuItem3_2 = new JMenuItem("修改课程");
        JMenuItem menuItem3_3 = new JMenuItem("删除课程");
        menuItem3_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add_course();
            }
        });
        menuItem3_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                edit_course();
            }
        });
        menuItem3_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete_course();
            }
        });
        menu3.add(menuItem3_1);
        menu3.add(menuItem3_2);
        menu3.add(menuItem3_3);
        
        JMenuItem menuItem5_1 = new JMenuItem("关于我们");
        menuItem5_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	JOptionPane.showMessageDialog(view.frame,"该软件由'阳阳课程表'团队制作",
                        "关于我们",
                        JOptionPane.PLAIN_MESSAGE
                );
            }
        });
        menu5.add(menuItem5_1);
        return menuBar;
	}
	public JScrollPane initTable() {
		int row=1+this.course_table.getLesson_max()*3;
		int column=1+7;
		String[] columnNames=new String[column];
		String[][]rowData=new String[row][column];
		
        Search_table s=new Search_table(this.course_table);
		s.searchByweek(this.course_table.getWeek_now());
		this.course=s.getCourse();
        this.table=this.courseTotable(this.course);
        for(int j=0;j<column;j++) {
        	columnNames[j]=this.table[0][j];
        }
        for(int i=1;i<row+1;i++) {
        	for(int j=0;j<column;j++) {
        		rowData[i-1][j]=this.table[i][j];
        	}
        }
        
        JTable table=new JTable(rowData, columnNames);
     // 创建单元格渲染器
        MyTableCellRenderer renderer = new MyTableCellRenderer();
        renderer.setColor_cell(this.course);
     // 遍历表格的每一列，分别给每一列设置单元格渲染器
        for (int i = 0; i < columnNames.length; i++) {
            // 根据 列名 获取 表格列
            TableColumn tableColumn = table.getColumn(columnNames[i]);
            // 设置 表格列 的 单元格渲染器
            tableColumn.setCellRenderer(renderer);
        }
        table.setRowHeight(35);
        //table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);
        //table.setShowGrid(false);
        table.setPreferredScrollableViewportSize(new Dimension(800, 900));
        JScrollPane scrollpane = new JScrollPane(table);
        return scrollpane;
	}
	public void createMain() {
		view=new Table_view();
		view.frame=initFrame();
		view.menuBar=initMenuBar();
		view.scrollpane=initTable();
		view.frame.setJMenuBar(view.menuBar);				
		view.frame.setContentPane(view.scrollpane);
		view.frame.setVisible(true);
	}
	//课程数组转table二维数组
	public String[][] courseTotable(Course course[]) {
		int row=2+course_table.getLesson_max()*3;
		int column=1+7;
		String[][] table=new String[row][column];//2+14*3,1+7
				
		table[0][0]="第"+String.valueOf(course_table.getWeek_now())+"周";
		table[0][1]="周一";
		table[0][2]="周二";
		table[0][3]="周三";
		table[0][4]="周四";
		table[0][5]="周五";
		table[0][6]="周六";
		table[0][7]="周日";
		table[1][0]="日期";
		Calendar date=Calendar.getInstance();
		String date_start[]=course_table.getDate_start().split("_");
		date.set(Calendar.YEAR, Integer.parseInt(date_start[0]));//年
		date.set(Calendar.MONTH, Integer.parseInt(date_start[1])-1);//月（月份0代表1月）
		date.set(Calendar.DATE, Integer.parseInt(date_start[2]));//日
		//算出当前周的日期
		date.add(Calendar.DATE, (course_table.getWeek_now()-1)*7);	
		for(int j=1;j<column;j++) {//第二行日期
			table[1][j]=String.valueOf(date.get(Calendar.MONTH)+1)+"."+String.valueOf(date.get(Calendar.DATE));
			date.add(Calendar.DATE, 1);
		}
		//第一列节数和上课时间
		for(int i=2;i<row;i++) {
			int num=(i+1)/3;
			if((i+1)%3==0) table[i][0]=String.valueOf(num);
			if((i+1)%3==1) table[i][0]=course_table.getLesson_begintime()[num-1][0];
			if((i+1)%3==2) table[i][0]=course_table.getLesson_begintime()[num-1][1];
		}
		//写入当前周的课程		
		for(int j=1;j<column;j++) {
			for(int k=0;k<course.length;k++) {//遍历当周课程
				if(course[k].getDay()==j) {
					int line=course[k].getBegin_lesson()*3-1;//行数和节数的关系
					table[line][j]=course[k].getCourse_name();
					table[line+1][j]=course[k].getCourse_location();
					table[line+2][j]=course[k].getCourse_teacher();
				}
			}	
		}
		return table;
	}
	//更新课程表面板
	public  void update_table() {
		view.scrollpane=initTable();
		view.frame.setContentPane(view.scrollpane);
		view.frame.setVisible(true);
	}
	//手动导入
	public  void import_table() {
		 // 创建一个模态对话框
        JDialog dialog = new JDialog(view.frame, "导入课表", true);
        dialog.setSize(320, 210);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(view.frame);
        
        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel1.add(new JLabel("学号："));
        JTextField textField1 = new JTextField(15);
        panel1.add(textField1);
        
        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel2.add(new JLabel("学期："));
        JTextField textField2 = new JTextField("2019-2020",15);
        panel2.add(textField2);
        String[] listData = new String[]{"1", "2"};
        JComboBox<String> comboBox = new JComboBox<String>(listData);
        panel2.add(comboBox);
        
        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel3.add(new JLabel("文件："));
        JTextField textField3 = new JTextField(15);
        textField3.setEditable(false);
        panel3.add(textField3);
        JButton btn1=new JButton("浏览");
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	JFileChooser fileChooser = new JFileChooser();
            	fileChooser.setFileFilter(new FileNameExtensionFilter("xls(*.xls, *.xlsx)", "xls", "xlsx"));
            	int result = fileChooser.showOpenDialog(dialog);
            	if (result == JFileChooser.APPROVE_OPTION) {
            		File file = fileChooser.getSelectedFile();
            		textField3.setText(file.getAbsolutePath());
            	}
            }
        });        
        panel3.add(btn1);
        
        JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btn2=new JButton("导入");
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID=textField1.getText();
                String num=comboBox.getSelectedItem().toString();//学期数                
                String url=textField3.getText();
                String date_start;
                System.out.println(ID);
                System.out.println(url);
               
                //分开年份
                String []s=textField2.getText().split("-");
                if(num.equals("1")) {
                	date_start=s[0]+"_9_1";
                }
                else {
                	date_start=s[1]+"_3_1";
                }
                String semester=s[0]+"_"+s[1]+"_"+num;
                System.out.println(date_start);
                Course_Table temp_course_table=new Course_Table();
                temp_course_table.setDate_start(date_start);
                temp_course_table.setId(ID);
                temp_course_table.setSemester(semester);
                //开始导入
                Import_table import_table=new Import_table(temp_course_table);
                import_table.importCourseFile(url); 
                course_table=temp_course_table;
                //更新面板
                update_table();
                dialog.dispose();
            }
        });
        panel4.add(btn2);
        Box vBox = Box.createVerticalBox();
        vBox.add(panel1);
        vBox.add(panel2);
        vBox.add(panel3);
        vBox.add(panel4);
        dialog.setContentPane(vBox);
        dialog.setVisible(true);
	}
	//自动导入
	public  void auto_import() {
		JDialog dialog = new JDialog(view.frame, "自动导入", true);
        dialog.setSize(360, 220);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(view.frame);
        
        JPanel panel1 = new JPanel();
        String[] listData1 = {"2017","2018","2019"};
        String[] listData2 = {"2018","2019","2020"};
        String[] listData3 = {"1","2"};
        JComboBox<String> comboBox1 = new JComboBox<String>(listData1);
        JComboBox<String> comboBox2 = new JComboBox<String>(listData2);
        JComboBox<String> comboBox3 = new JComboBox<String>(listData3);
        
        panel1.add(new JLabel("学年:"));
        panel1.add(comboBox1);
        panel1.add(new JLabel("-"));
        panel1.add(comboBox2);
        panel1.add(new JLabel("学期:"));
        panel1.add(comboBox3);
        
        JPanel panel2 = new JPanel();
        panel2.add(new JLabel("学号:"));
        JTextField textField1 = new JTextField(15);
        panel2.add(textField1);       
        JPanel panel3 = new JPanel();
        panel3.add(new JLabel("密码:"));
        JTextField textField2 = new JTextField(15);        
        panel3.add(textField2);
        
        JPanel panel4 = new JPanel();
        JButton btn1=new JButton("导入");
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Course_Table temp_course_table=new Course_Table();
            	String ID=textField1.getText();
            	String password=textField2.getText();
                String semester=comboBox1.getSelectedItem().toString()+"_"+comboBox2.getSelectedItem().toString()+"_"+comboBox3.getSelectedItem().toString();
                String date_start;
                if(comboBox3.getSelectedItem().toString().equals("1")) {
                	date_start=comboBox1.getSelectedItem().toString()+"_9_1";
                }
                else {
                	date_start=comboBox2.getSelectedItem().toString()+"_3_1";
                }
                temp_course_table.setId(ID);
                temp_course_table.setSemester(semester);
                temp_course_table.setDate_start(date_start);
                
                Course_Table temp[]=Tool.readCourse_Table();
                boolean find=false;//是否找到
                for(int i=0;i<temp.length;i++) {
            		if(temp[i].getId().equals(ID) && temp[i].getSemester().equals(semester)) {            			
            			
            			JOptionPane.showMessageDialog(dialog,"课表已存在！","警告",JOptionPane.WARNING_MESSAGE);            			
            			find=true;
            			break;
            		}
            	}
                if(!find){
                	Login l=new Login(ID,password);
	                Download_table down=new Download_table(ID,semester,l.login());
	                String url=down.download();
	                Import_table it=new Import_table(temp_course_table);
	                it.importCourseFile(url);
	                JOptionPane.showMessageDialog(dialog,"导入课表成功！","警告",JOptionPane.WARNING_MESSAGE);
	                dialog.dispose();
                }                
            }
        });
        panel4.add(btn1);
        
        Box vBox = Box.createVerticalBox();
        vBox.add(panel2);
        vBox.add(panel3);
        vBox.add(panel1);
        vBox.add(panel4);
        dialog.setContentPane(vBox);
        dialog.setVisible(true);
	}
	//删除课表
	public  void delete_table() {
		JDialog dialog = new JDialog(view.frame, "删除课表", true);
        dialog.setSize(380, 160);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(view.frame);
        
        JPanel panel3 = new JPanel();
        panel3.add(new JLabel("学号:"));
        JTextField textField1 = new JTextField(15);
        panel3.add(textField1);
        
        JPanel panel1 = new JPanel();
        String[] listData1 = {"2017","2018","2019"};
        String[] listData2 = {"2018","2019","2020"};
        String[] listData3 = {"1","2"};
        JComboBox<String> comboBox1 = new JComboBox<String>(listData1);
        JComboBox<String> comboBox2 = new JComboBox<String>(listData2);
        JComboBox<String> comboBox3 = new JComboBox<String>(listData3);
        
        panel1.add(new JLabel("学年:"));
        panel1.add(comboBox1);
        panel1.add(new JLabel("-"));
        panel1.add(comboBox2);
        panel1.add(new JLabel("学期:"));
        panel1.add(comboBox3);
        JPanel panel2 = new JPanel();
        JButton btn1=new JButton("删除");
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Course_Table temp_course_table[]=Tool.readCourse_Table();
            	String ID=textField1.getText();
                String semester=comboBox1.getSelectedItem().toString()+"_"+comboBox2.getSelectedItem().toString()+"_"+comboBox3.getSelectedItem().toString();
                boolean find=false;//是否找到
                for(int i=0;i<temp_course_table.length;i++) {
            		if(temp_course_table[i].getId().equals(ID) && temp_course_table[i].getSemester().equals(semester)) {
            			Delete_table dt=new Delete_table(temp_course_table[i]);
            			dt.delete_DBtable();
            			
            			JOptionPane.showMessageDialog(dialog,"删除课表成功！","警告",JOptionPane.WARNING_MESSAGE);
            			dialog.dispose();
            			find=true;
            			break;
            		}
            	}
                if(!find)
                	JOptionPane.showMessageDialog(dialog,"没有这个课表！","警告",JOptionPane.WARNING_MESSAGE);
                
            }
        });
        panel2.add(btn1);
        
        Box vBox = Box.createVerticalBox();
        vBox.add(panel3);
        vBox.add(panel1);
        vBox.add(panel2);
        dialog.setContentPane(vBox);
        dialog.setVisible(true);
	}
	//按周查询课表
	public  void search_week() {
		JDialog dialog = new JDialog(view.frame, "按周查询课表", true);
        dialog.setSize(200, 80);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(view.frame);
        JPanel panel1 = new JPanel();
        String[] listData = new String[course_table.getWeeks()];//course_table.getWeeks()=16
        for(int i=1;i<=course_table.getWeeks();i++) {
        	listData[i-1]=String.valueOf(i);
        }
        JComboBox<String> comboBox = new JComboBox<String>(listData);
        JButton btn1=new JButton("查询");
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//Search_table s=new Search_table();
        		//s.searchByweek(Integer.parseInt(comboBox.getSelectedItem().toString()));
        		//course=s.getCourse();
        		int week=Integer.parseInt(comboBox.getSelectedItem().toString());
        		course_table.setWeek_now(week);
        		update_table();
            }
        });
        panel1.add(comboBox);
        panel1.add(btn1);
        dialog.setContentPane(panel1);
        dialog.setVisible(true);
	}
	//按学期查询课表
	public  void search_semester() {
		JDialog dialog = new JDialog(view.frame, "按学期查询", true);
        dialog.setSize(380, 120);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(view.frame);
        
        JPanel panel1 = new JPanel();
        String[] listData1 = {"2017","2018","2019"};
        String[] listData2 = {"2018","2019","2020"};
        String[] listData3 = {"1","2"};
        JComboBox<String> comboBox1 = new JComboBox<String>(listData1);
        JComboBox<String> comboBox2 = new JComboBox<String>(listData2);
        JComboBox<String> comboBox3 = new JComboBox<String>(listData3);
        
        panel1.add(new JLabel("学年:"));
        panel1.add(comboBox1);
        panel1.add(new JLabel("-"));
        panel1.add(comboBox2);
        panel1.add(new JLabel("学期:"));
        panel1.add(comboBox3);
        JPanel panel2 = new JPanel();
        JButton btn1=new JButton("查询");
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Course_Table temp_course_table=new Course_Table();
            	String ID=course_table.getId();
                String semester=comboBox1.getSelectedItem().toString()+"_"+comboBox2.getSelectedItem().toString()+"_"+comboBox3.getSelectedItem().toString();
                String date_start;
                if(comboBox3.getSelectedItem().toString().equals("1")) {
                	date_start=comboBox1.getSelectedItem().toString()+"_9_1";
                }
                else {
                	date_start=comboBox2.getSelectedItem().toString()+"_3_1";
                }
                temp_course_table.setId(ID);
                temp_course_table.setSemester(semester);
                temp_course_table.setDate_start(date_start);
                
                Course_Table temp[]=Tool.readCourse_Table();
                boolean find=false;//是否找到
                for(int i=0;i<temp.length;i++) {
            		if(temp[i].getId().equals(ID) && temp[i].getSemester().equals(semester)) {            			
            			course_table=temp[i];
            			/*
            			Search_table s=new Search_table(course_table);
            			s.searchBysemester(semester);
            			course=s.getCourse(); 
            			*/           			
            			update_table();            			
            			dialog.dispose();
            			find=true;
            			break;
            		}
            	}
                if(!find) {
                	JOptionPane.showMessageDialog(dialog,"没有这个学期！","警告",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        panel2.add(btn1);
        Box vBox = Box.createVerticalBox();
        vBox.add(panel1);
        vBox.add(panel2);
        dialog.setContentPane(vBox);
        dialog.setVisible(true);
	}
	//添加课程
	public  void add_course() {
		JDialog dialog = new JDialog(view.frame, "添加课程", true);
        dialog.setSize(300, 320);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(view.frame);
        
        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel1.add(new JLabel("课程名："));
        JTextField textField1 = new JTextField(17);
        panel1.add(textField1);	
        
        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel2.add(new JLabel("上课位置："));
        JTextField textField2 = new JTextField(15);
        panel2.add(textField2);
        
        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel3.add(new JLabel("上课老师："));
        JTextField textField3 = new JTextField(15);
        panel3.add(textField3);
        
        JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] listData1 = new String[course_table.getWeeks()];
        for(int i=1;i<=course_table.getWeeks();i++) {
        	listData1[i-1]=String.valueOf(i);
        }
        JComboBox<String> comboBox1 = new JComboBox<String>(listData1);
        JComboBox<String> comboBox2 = new JComboBox<String>(listData1);
        panel4.add(new JLabel("周数："));
        panel4.add(comboBox1);
        panel4.add(new JLabel("-"));
        panel4.add(comboBox2);
        
        JPanel panel5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] listData2 = new String[7];
        for(int i=1;i<=7;i++) {
        	listData2[i-1]=String.valueOf(i);
        }
        JComboBox<String> comboBox3 = new JComboBox<String>(listData2);
        panel5.add(new JLabel("星期："));
        panel5.add(comboBox3);
        
        JPanel panel6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] listData3 = new String[course_table.getLesson_max()];
        for(int i=1;i<=course_table.getLesson_max();i++) {
        	listData3[i-1]=String.valueOf(i);
        }
        JComboBox<String> comboBox4 = new JComboBox<String>(listData3);
        JComboBox<String> comboBox5 = new JComboBox<String>(listData3);
        panel6.add(new JLabel("节数："));
        panel6.add(comboBox4);
        panel6.add(new JLabel("-"));
        panel6.add(comboBox5);
        JPanel panel7 = new JPanel();
        JButton btn1=new JButton("导入");
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Course temp_course=new Course();
                temp_course.setCourse_name(textField1.getText());
                temp_course.setCourse_location(textField2.getText());
                temp_course.setCourse_teacher(textField3.getText());
                temp_course.setBegin_week(Integer.parseInt(comboBox1.getSelectedItem().toString()));
                temp_course.setEnd_week(Integer.parseInt(comboBox2.getSelectedItem().toString()));
                temp_course.setDay(Integer.parseInt(comboBox3.getSelectedItem().toString()));
                temp_course.setBegin_lesson(Integer.parseInt(comboBox4.getSelectedItem().toString()));
                temp_course.setEnd_lesson(Integer.parseInt(comboBox5.getSelectedItem().toString()));
                Edit_table et=new Edit_table(course,course_table);
                et.add_course(temp_course);
            }
        });
        panel7.add(btn1);
        Box vBox = Box.createVerticalBox();
        vBox.add(panel1);
        vBox.add(panel2);
        vBox.add(panel3);
        vBox.add(panel4);
        vBox.add(panel5);
        vBox.add(panel6);
        vBox.add(panel7);
        dialog.setContentPane(vBox);
        dialog.setVisible(true);
	}
	//编辑课程
	public void edit_course() {
		JDialog dialog = new JDialog(view.frame, "编辑课程", true);
        dialog.setSize(400, 120);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(view.frame);
        
        JPanel panel1 = new JPanel();
        String[] listData1 = new String[course_table.getWeeks()];
        for(int i=1;i<=course_table.getWeeks();i++) {
        	listData1[i-1]=String.valueOf(i);
        }
        JComboBox<String> comboBox1 = new JComboBox<String>(listData1);
                        
        String[] listData2 = new String[7];
        for(int i=1;i<=7;i++) {
        	listData2[i-1]=String.valueOf(i);
        }
        JComboBox<String> comboBox2 = new JComboBox<String>(listData2);
        String[] listData3 = new String[course_table.getLesson_max()];
        for(int i=1;i<=course_table.getLesson_max();i++) {
        	listData3[i-1]=String.valueOf(i);
        }
        JComboBox<String> comboBox3 = new JComboBox<String>(listData3);
        panel1.add(new JLabel("第几周："));
        panel1.add(comboBox1);
        panel1.add(new JLabel("星期："));
        panel1.add(comboBox2);
        panel1.add(new JLabel("起始节："));
        panel1.add(comboBox3);
        
        JPanel panel2 = new JPanel();
        JButton btn1=new JButton("查找");
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int week=Integer.parseInt(comboBox1.getSelectedItem().toString());
                int day=Integer.parseInt(comboBox2.getSelectedItem().toString());
                int Begin_lesson=Integer.parseInt(comboBox3.getSelectedItem().toString());
                boolean find=false;//是否找到
                for(int i=0;i<course.length;i++) {                	
                	if(week>=course[i].getBegin_week() && week<=course[i].getEnd_week() && day==course[i].getDay() && Begin_lesson==course[i].getBegin_lesson()) {
                		course_view(course[i]);
                		find=true;
                		break;
                	}
                }
                if(!find)
                	JOptionPane.showMessageDialog(dialog,"没有这个课程！","警告",JOptionPane.WARNING_MESSAGE);
            }
        });
        panel2.add(btn1);
        
        Box vBox = Box.createVerticalBox();
        vBox.add(panel1);
        vBox.add(panel2);
        dialog.setContentPane(vBox);
        dialog.setVisible(true);
	}
	//删除课程
	public void delete_course() {
		JDialog dialog = new JDialog(view.frame, "删除课程", true);
        dialog.setSize(400, 120);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(view.frame);
        
        JPanel panel1 = new JPanel();
        String[] listData1 = new String[course_table.getWeeks()];
        for(int i=1;i<=course_table.getWeeks();i++) {
        	listData1[i-1]=String.valueOf(i);
        }
        JComboBox<String> comboBox1 = new JComboBox<String>(listData1);
                        
        String[] listData2 = new String[7];
        for(int i=1;i<=7;i++) {
        	listData2[i-1]=String.valueOf(i);
        }
        JComboBox<String> comboBox2 = new JComboBox<String>(listData2);
        String[] listData3 = new String[course_table.getLesson_max()];
        for(int i=1;i<=course_table.getLesson_max();i++) {
        	listData3[i-1]=String.valueOf(i);
        }
        JComboBox<String> comboBox3 = new JComboBox<String>(listData3);
        panel1.add(new JLabel("第几周："));
        panel1.add(comboBox1);
        panel1.add(new JLabel("星期："));
        panel1.add(comboBox2);
        panel1.add(new JLabel("起始节："));
        panel1.add(comboBox3);
        
        JPanel panel2 = new JPanel();
        JButton btn1=new JButton("查找");
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int week=Integer.parseInt(comboBox1.getSelectedItem().toString());
                int day=Integer.parseInt(comboBox2.getSelectedItem().toString());
                int Begin_lesson=Integer.parseInt(comboBox3.getSelectedItem().toString());
                boolean find=false;//是否找到
                for(int i=0;i<course.length;i++) {                	
                	if(week>=course[i].getBegin_week() && week<=course[i].getEnd_week() && day==course[i].getDay() && Begin_lesson==course[i].getBegin_lesson()) {
                		
                		String name="是否删除课程： "+course[i].getCourse_name();
                		int result = JOptionPane.showConfirmDialog(dialog,name,"提示",
                                JOptionPane.YES_NO_OPTION);
                		if(result==JOptionPane.YES_OPTION) {
                			Edit_table et=new Edit_table(course,course_table);
                			et.delete_course(course[i]);
                		}
                		find=true;
                		break;
                	}
                }
                if(!find)
                	JOptionPane.showMessageDialog(dialog,"没有这个课程！","警告",JOptionPane.WARNING_MESSAGE);
            }
        });
        panel2.add(btn1);
        
        Box vBox = Box.createVerticalBox();
        vBox.add(panel1);
        vBox.add(panel2);
        dialog.setContentPane(vBox);
        dialog.setVisible(true);		
	}
	
	public void course_view(Course course_old) {
		JDialog dialog = new JDialog(view.frame, "修改课程", true);
        dialog.setSize(300, 320);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(view.frame);
        
        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel1.add(new JLabel("课程名："));
        JTextField textField1 = new JTextField(17);
        textField1.setText(course_old.getCourse_name());
        panel1.add(textField1);	
        
        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel2.add(new JLabel("上课位置："));
        JTextField textField2 = new JTextField(15);
        textField2.setText(course_old.getCourse_location());
        panel2.add(textField2);
        
        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel3.add(new JLabel("上课老师："));
        JTextField textField3 = new JTextField(15);
        textField3.setText(course_old.getCourse_teacher());
        panel3.add(textField3);
        
        JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] listData1 = new String[course_table.getWeeks()];
        for(int i=1;i<=course_table.getWeeks();i++) {
        	listData1[i-1]=String.valueOf(i);
        }
        JComboBox<String> comboBox1 = new JComboBox<String>(listData1);
        JComboBox<String> comboBox2 = new JComboBox<String>(listData1);
        comboBox1.setSelectedIndex(course_old.getBegin_week()-1);
        comboBox2.setSelectedIndex(course_old.getEnd_week()-1);
        panel4.add(new JLabel("周数："));
        panel4.add(comboBox1);
        panel4.add(new JLabel("-"));
        panel4.add(comboBox2);
        
        JPanel panel5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] listData2 = new String[7];
        for(int i=1;i<=7;i++) {
        	listData2[i-1]=String.valueOf(i);
        }
        JComboBox<String> comboBox3 = new JComboBox<String>(listData2);
        comboBox3.setSelectedIndex(course_old.getDay()-1);
        panel5.add(new JLabel("星期："));
        panel5.add(comboBox3);
        
        JPanel panel6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] listData3 = new String[course_table.getLesson_max()];
        for(int i=1;i<=course_table.getLesson_max();i++) {
        	listData3[i-1]=String.valueOf(i);
        }
        JComboBox<String> comboBox4 = new JComboBox<String>(listData3);
        JComboBox<String> comboBox5 = new JComboBox<String>(listData3);
        comboBox4.setSelectedIndex(course_old.getBegin_lesson()-1);
        comboBox5.setSelectedIndex(course_old.getEnd_lesson()-1);
        panel6.add(new JLabel("节数："));
        panel6.add(comboBox4);
        panel6.add(new JLabel("-"));
        panel6.add(comboBox5);
        JPanel panel7 = new JPanel();
        JButton btn1=new JButton("修改");
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Course temp_course=new Course();
                temp_course.setCourse_name(textField1.getText());
                temp_course.setCourse_location(textField2.getText());
                temp_course.setCourse_teacher(textField3.getText());
                temp_course.setBegin_week(Integer.parseInt(comboBox1.getSelectedItem().toString()));
                temp_course.setEnd_week(Integer.parseInt(comboBox2.getSelectedItem().toString()));
                temp_course.setDay(Integer.parseInt(comboBox3.getSelectedItem().toString()));
                temp_course.setBegin_lesson(Integer.parseInt(comboBox4.getSelectedItem().toString()));
                temp_course.setEnd_lesson(Integer.parseInt(comboBox5.getSelectedItem().toString()));
                Edit_table et=new Edit_table(course,course_table);
                et.edit_course(course_old,temp_course);
            }
        });
        panel7.add(btn1);
        Box vBox = Box.createVerticalBox();
        vBox.add(panel1);
        vBox.add(panel2);
        vBox.add(panel3);
        vBox.add(panel4);
        vBox.add(panel5);
        vBox.add(panel6);
        vBox.add(panel7);
        dialog.setContentPane(vBox);
        dialog.setVisible(true);
	}
	public Table_control() {		
		//导入course_table
		this.course_table=Tool.readCourse_Table()[0];
		/*
		this.course_table.setDate_start("2020_3_1");
		this.course_table.setId("20170060217");
		this.course_table.setSemester("2019_2020_2");
		*/
	}
	public static void main(String[] args) {
		Table_control c=new Table_control();
		c.createMain();
	}
}
