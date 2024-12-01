package jframe1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import java.awt.Checkbox;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.JTextField;

public class TestJFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestJFrame frame = new TestJFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestJFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 896, 449);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		table = new JTable();
		table.setBackground(new Color(0, 255, 64));
		contentPane.add(table);
		
		textField = new JTextField();
		contentPane.add(textField);
		textField.setColumns(10);
		
		JTextPane txtpnNhpSdt = new JTextPane();
		txtpnNhpSdt.setText("nhập sdt");
		contentPane.add(txtpnNhpSdt);
		
		Checkbox checkbox = new Checkbox("New check box");
		contentPane.add(checkbox);
	}

}
