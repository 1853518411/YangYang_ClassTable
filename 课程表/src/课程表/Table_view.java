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
	public int width=1300;
	public int height=900;
	public JFrame frame;
	public JMenuBar menuBar;
	public JTable table;
	public JScrollPane scrollpane;
	
	public JPanel panel,week,day,course,course_num;	
	
	
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
	
	public JMenuBar getMenuBar() {
		return menuBar;
	}
	public void initMenuBar() {
		this.menuBar=new JMenuBar();		  
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
	public void setColor_cell(Course course[]) {
		int row=2+14*3;
		int column=1+7;
		color_cell=new int[row][column];
		
		for(int k=0;k<course.length;k++) {//遍历当周课程
			int begin=course[k].getBegin_lesson();
			int end=course[k].getEnd_lesson();
			for(int i=begin;i<=end;i++) {
				int line=i*3-2;//行数和节数的关系
				int j=course[k].getDay();
				color_cell[line][j]=(k%4)+1;
				color_cell[line+1][j]=(k%4)+1;
				color_cell[line+2][j]=(k%4)+1;
			}
		}	
		
	}
    /**
     * 返回默认的表单元格渲染器，此方法在父类中已实现，直接调用父类方法返回，在返回前做相关参数的设置即可
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // 偶数行背景设置为白色,奇数行背景设置为灰色
        if (color_cell[row][column]==1) {
            setBackground(Color.LIGHT_GRAY);
        }
        else if (color_cell[row][column]==2) {
            setBackground(Color.YELLOW);
        }
        else if (color_cell[row][column]==3) {
            setBackground(Color.GREEN);
        }
        else if (color_cell[row][column]==4) {
            setBackground(Color.ORANGE);
        }
        else {
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