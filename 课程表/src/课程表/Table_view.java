package 课程表;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Enumeration;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
public class Table_view {
	public int width=1400;
	public int height=900;
	public JFrame frame;
	public Box hBox1;
	public JPanel panel,week,day,course,course_num;
	public JMenuBar menuBar;
	public JTable table;
	public JScrollPane scrollpane;
	public JTable getTable() {
		return table;
	}
	public void initTable() {	
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
        this.table=new JTable(rowData, columnNames);
     // 创建单元格渲染器
        MyTableCellRenderer renderer = new MyTableCellRenderer();
        renderer.setColor_cell();
     // 遍历表格的每一列，分别给每一列设置单元格渲染器
        for (int i = 0; i < columnNames.length; i++) {
            // 根据 列名 获取 表格列
            TableColumn tableColumn = this.table.getColumn(columnNames[i]);
            // 设置 表格列 的 单元格渲染器
            tableColumn.setCellRenderer(renderer);
        }
        this.table.setRowHeight(50);
        //this.table.setShowVerticalLines(false);
        //this.table.setShowHorizontalLines(false);
        this.table.setShowGrid(false);
        this.table.setPreferredScrollableViewportSize(new Dimension(800, 900));
        scrollpane = new JScrollPane(this.table);
	}
	
	public JFrame getFrame() {
		return frame;
	}
	public void initFrame() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//操作系统的界面风格
		InitGlobalFont(new Font("黑体", Font.BOLD, 18));  //统一设置字体
		this.frame=new JFrame("阳阳课程表");
		this.frame.setSize(width, height);
		this.frame.setLocationRelativeTo(null);		
		this.frame.setResizable(false);
		this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	public JPanel getPanel() {
		return panel;
	}
	public void setPanel(JPanel panel) {
		this.panel = panel;
		GridBagLayout gridBag = new GridBagLayout();    // 布局管理器
        GridBagConstraints c = null;                    // 约束
        this.panel = new JPanel(gridBag);
        week=new JPanel();
        course_num=new JPanel();
        course=new JPanel();
        
	}
	
	public JPanel getCourse() {
		return course;
	}
	public void setCourse(JPanel course) {
		this.course = course;
	}
	public JMenuBar getMenuBar() {
		return menuBar;
	}
	public void initMenuBar() {
		this.menuBar=new JMenuBar();		
        JMenu menu1 = new JMenu("文件");
        JMenu menu2 = new JMenu("查询");
        JMenu menu3 = new JMenu("课程");
        JMenu menu4 = new JMenu("提醒");
        JMenu menu5 = new JMenu("关于");
        // 一级菜单添加到菜单栏
        this.menuBar.add(menu1);
        this.menuBar.add(menu2);
        this.menuBar.add(menu3);
        this.menuBar.add(menu4);
        this.menuBar.add(menu5);
        //一级菜单的子菜单
        JMenuItem menuItem1_1 = new JMenuItem("新建课表");
        JMenuItem menuItem1_2 = new JMenuItem("手动导入");
        JMenuItem menuItem1_3 = new JMenuItem("自动导入");
        JMenuItem menuItem1_4 = new JMenuItem("删除课表");
        
        // 子菜单添加到一级菜单
        menu1.add(menuItem1_1);
        menu1.add(menuItem1_2);
        menu1.add(menuItem1_3);
        menu1.add(menuItem1_4);
        JMenuItem menuItem2_1 = new JMenuItem("按周查询");
        JMenuItem menuItem2_2 = new JMenuItem("按学期查询");
        
        menu2.add(menuItem2_1);
        menu2.add(menuItem2_2);
        JMenuItem menuItem3_1 = new JMenuItem("添加课程");
        JMenuItem menuItem3_2 = new JMenuItem("修改课程");
        JMenuItem menuItem3_3 = new JMenuItem("删除课程");
        
        menu3.add(menuItem3_1);
        menu3.add(menuItem3_2);
        menu3.add(menuItem3_3);
        
	}
	
	public Box gethBox1() {
		return hBox1;
	}
	public void sethBox1(Box hBox1) {
		this.hBox1 = hBox1;
		GridLayout layout = new GridLayout(15, 1);
		this.hBox1 = Box.createHorizontalBox();
		this.course_num=new JPanel(layout);
		String week[]= {"周一","周二","周三","周四","周五","周六","周日"};
		//第1列
		course_num.add(new JLabel("7月"));		
		for(int i=1;i<=14;i++) {
			course_num.add(new JLabel(String.valueOf(i)));
		}
		this.hBox1.add(course_num);
		//后面7列
		for(int i=0;i<7;i++) {
			this.course=new JPanel(layout);
			this.course.add(new JLabel(week[i]));
			this.hBox1.add(this.course);
		}
	}
	public void show() {
		frame.setJMenuBar(menuBar);				
		frame.setContentPane(scrollpane);
		frame.setVisible(true);
	}

	/**
	 * 统一设置字体，父界面设置之后，所有由父界面进入的子界面都不需要再次设置字体
	 */
	public static void InitGlobalFont(Font font) {
		FontUIResource fontRes = new FontUIResource(font);
		for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				UIManager.put(key, fontRes);
			}
		}
	}
	//构造方法
	public Table_view() {
		
	}
}
class MyTableCellRenderer extends DefaultTableCellRenderer {
	public int color_cell[][];//需染色的单元格
	public void setColor_cell() {
		
	}
    /**
     * 返回默认的表单元格渲染器，此方法在父类中已实现，直接调用父类方法返回，在返回前做相关参数的设置即可
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // 偶数行背景设置为白色,奇数行背景设置为灰色
        if (column == 1 && (row%4==1||row%4==2)) {
            setBackground(Color.LIGHT_GRAY);
        } else {
            setBackground(Color.WHITE);
        }
        //文字居中对齐
        setHorizontalAlignment(SwingConstants.CENTER);
        
        // PS: 多个单元格使用同一渲染器时，需要自定义的属性，必须每次都设置，否则将自动沿用上一次的设置。
        /*
	         * 单元格渲染器为表格单元格提供具体的显示，实现了单元格渲染器的 DefaultTableCellRenderer 继承自
	         * 一个标准的组件类 JLabel，因此 JLabel 中相应的 API 在该渲染器实现类中都可以使用。
	         *
	         * super.getTableCellRendererComponent(...) 返回的实际上是当前对象（this），即 JLabel 实例，
	         * 也就是以 JLabel 的形式显示单元格。
	         *
	         * 如果需要自定义单元格的显示形式（比如显示成按钮、复选框、内嵌表格等），可以在此自己创建一个标准组件
	         * 实例返回。
         */
        // 调用父类的该方法完成渲染器的其他设置
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}