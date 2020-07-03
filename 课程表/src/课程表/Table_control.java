package 课程表;
import 课程表.Table_view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.table.TableColumn;

import 课程表.Course;
import 课程表.Course_Table;
public class Table_control {
	private Table_view view;
	private Course course[];
	private Course_Table course_table;
	
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
        JMenu menu4 = new JMenu("提醒");
        JMenu menu5 = new JMenu("关于");
        // 一级菜单添加到菜单栏
        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(menu3);
        menuBar.add(menu4);
        menuBar.add(menu5);
        //一级菜单的子菜单
        JMenuItem menuItem1_1 = new JMenuItem("新建课表");
        JMenuItem menuItem1_2 = new JMenuItem("手动导入");
        JMenuItem menuItem1_3 = new JMenuItem("自动导入");
        JMenuItem menuItem1_4 = new JMenuItem("删除课表");
        menuItem1_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("新建课表");
            }
        });
        menuItem1_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("手动导入");
            }
        });
        menuItem1_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("自动导入");
            }
        });
        menuItem1_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("删除课表");
            }
        });
        // 子菜单添加到一级菜单
        menu1.add(menuItem1_1);
        menu1.add(menuItem1_2);
        menu1.add(menuItem1_3);
        menu1.add(menuItem1_4);
        JMenuItem menuItem2_1 = new JMenuItem("按周查询");
        JMenuItem menuItem2_2 = new JMenuItem("按学期查询");
        menuItem2_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("按周查询");
            }
        });
        menuItem2_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("按学期查询");
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
                System.out.println("添加课程");
            }
        });
        menuItem3_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("修改课程");
            }
        });
        menuItem3_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("删除课程");
            }
        });
        menu3.add(menuItem3_1);
        menu3.add(menuItem3_2);
        menu3.add(menuItem3_3);
        return menuBar;
	}
	public JScrollPane initTable() {	
		// 表头（列名）
        String[] columnNames = {"序号", "姓名", "语文", "数学", "英语", "总分"};
        // 表格所有行数据
        Object[][] rowData = {
                {1, "张三", 80, 80, 80, 240},
                {2, "John", 70, 80, 90, 240},
                {3, "Sue", 70, 70, 70, 210},
                {4, "Jane", 80, 70, 60, 210},
                {5, "Joe_05", 80, 70, 60, 210},
                {6, "Joe_06", 80, 70, 60, 210},
                {7, "Joe_07", 80, 70, 60, 210},
                {8, "Joe_08", 80, 70, 60, 210},
                {9, "Joe_09", 80, 70, 60, 210},
                {10, "Joe_10", 80, 70, 60, 210},
                {11, "Joe_11", 80, 70, 60, 210},
                {12, "Joe_12", 80, 70, 60, 210},
                {13, "Joe_13", 80, 70, 60, 210},
                {14, "Joe_14", 80, 70, 60, 210},
                {15, "Joe_15", 80, 70, 60, 210},
                {16, "Joe_16", 80, 70, 60, 210},
                {17, "Joe_17", 80, 70, 60, 210},
                {18, "Joe_18", 80, 70, 60, 210},
                {19, "Joe_19", 80, 70, 60, 210},
                {20, "Joe_20", 80, 70, 60, 210}
        };
        JTable table=new JTable(rowData, columnNames);
     // 创建单元格渲染器
        MyTableCellRenderer renderer = new MyTableCellRenderer();
        renderer.setColor_cell();
     // 遍历表格的每一列，分别给每一列设置单元格渲染器
        for (int i = 0; i < columnNames.length; i++) {
            // 根据 列名 获取 表格列
            TableColumn tableColumn = table.getColumn(columnNames[i]);
            // 设置 表格列 的 单元格渲染器
            tableColumn.setCellRenderer(renderer);
        }
        table.setRowHeight(50);
        //table.setShowVerticalLines(false);
        //table.setShowHorizontalLines(false);
        table.setShowGrid(false);
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
	public String[][] courseTotable(Course course[],Course_Table course_table) {
		int row=2+course_table.getLesson_max()*3;
		int column=1+7;
		String[][] table=new String[row][column];//2+14*3,1+7
				
		table[0][0]=String.valueOf(course_table.getWeek_now());
		table[0][1]="周一";
		table[0][2]="周二";
		table[0][3]="周三";
		table[0][4]="周四";
		table[0][5]="周五";
		table[0][6]="周六";
		table[0][7]="周日";
		table[1][0]="日期";
		Calendar date=course_table.getDate_start();
		//算出当前周的日期
		date.add(Calendar.DATE, (course_table.getWeek_now()-1)*7);	
		for(int j=1;j<column;j++) {
			table[1][j]=String.valueOf(date.get(Calendar.MONTH))+"."+String.valueOf(date.get(Calendar.DATE));
			date.add(Calendar.DATE, 1);
		}
		
		for(int i=2;i<row;i++) {
			for(int j=0;j<column;j++) {
				if(j==0) {
					
				}
				else {
					
				}
			}
		}
		return table;
	}
	//更新课程表面板
	public  void update_table() {
		
	}
	//手动导入
	public  void import_table() {
		
	}
	//自动导入
	public  void auto_import() {
			
	}
	//删除课表
	public  void delete_table() {
			
	}
	//按周查询课表
	public  void search_week(int week) {
		
	}
	//按学期查询课表
	public  void search_semester(int semester) {
			
	}
	//添加课程
	public  void add_course() {
				
	}
	//编辑课程
	public  void edit_course() {
				
	}
	//删除课程
	public  void delete_course() {
				
	}
	public static void main(String[] args) {
		Table_control c=new Table_control();
		c.createMain();
	}
}
