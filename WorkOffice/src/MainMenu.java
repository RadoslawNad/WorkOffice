import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.JMenuBar;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.LayoutStyle.ComponentPlacement;

public class MainMenu {

	private JFrame frmWorkOffice;
	private JTable table;
	private WorkOfficeDAO dao;
	private DefaultTableModel model;
	private ArrayList<LinkModel> linkList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
					window.frmWorkOffice.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWorkOffice = new JFrame();
		frmWorkOffice.setTitle("WORK OFFICE");
		frmWorkOffice.setBounds(100, 100, 900, 600);
		frmWorkOffice.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		dao = new WorkOfficeDAO();
		dao.ifLinkTablesExist();
		dao.ifEmpTablesExist();
		dao.ifTablesExist();

		JPanel panel_table = new JPanel();

		JPanel panel_worker = new JPanel();
		panel_worker.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		JPanel panel_family = new JPanel();
		panel_family.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		JButton btnGetWorker = new JButton("Link");
		btnGetWorker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LinkFrame link = new LinkFrame();
				link.setVisible(true);
			}
		});

		JButton btnDeleteLink = new JButton("Delete");
		btnDeleteLink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int pane = JOptionPane.showConfirmDialog(null, "Do you want to delete the items?", "DELETE",
						JOptionPane.YES_NO_OPTION);
				if (pane == JOptionPane.YES_OPTION) {
					deleteData();
				}
			}
		});
		GroupLayout groupLayout = new GroupLayout(frmWorkOffice.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(btnGetWorker)
						.addPreferredGap(ComponentPlacement.RELATED, 706, Short.MAX_VALUE).addComponent(btnDeleteLink)
						.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup().addGap(10)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(panel_table, GroupLayout.DEFAULT_SIZE, 858, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(panel_worker, GroupLayout.PREFERRED_SIZE, 424,
												GroupLayout.PREFERRED_SIZE)
										.addGap(10).addComponent(panel_family, GroupLayout.PREFERRED_SIZE, 424,
												GroupLayout.PREFERRED_SIZE)))
						.addGap(16)));
		groupLayout
				.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addGap(17)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(panel_family, 0, 0, Short.MAX_VALUE)
										.addComponent(panel_worker, GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE))
								.addGap(31)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnGetWorker)
										.addComponent(btnDeleteLink))
								.addGap(18).addComponent(panel_table, GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
								.addContainerGap()));
		panel_table.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 858, 218);
		panel_table.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Name", "Surname", "", "ID", "Name", "Surname", "", "From_Date", "To_Date" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, false, false,
					false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(45);
		table.getColumnModel().getColumn(0).setMinWidth(40);
		table.getColumnModel().getColumn(0).setMaxWidth(60);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(30);
		table.getColumnModel().getColumn(3).setMaxWidth(40);
		table.getColumnModel().getColumn(4).setPreferredWidth(45);
		table.getColumnModel().getColumn(4).setMinWidth(40);
		table.getColumnModel().getColumn(4).setMaxWidth(60);
		table.getColumnModel().getColumn(7).setResizable(false);
		table.getColumnModel().getColumn(7).setPreferredWidth(30);
		table.getColumnModel().getColumn(7).setMaxWidth(40);
		scrollPane.setViewportView(table);

		linkList = dao.getLinkList();
		populateLinkTable();

		JLabel lblWorker = new JLabel("Worker");
		lblWorker.setFont(new Font("Tahoma", Font.PLAIN, 17));

		JButton btnNewEmployee = new JButton("New worker");
		btnNewEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				NewEmployeeFrame eFrame = new NewEmployeeFrame();
				eFrame.setVisible(true);
			}
		});

		JButton btnEmployeeList = new JButton("Workers list");
		btnEmployeeList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EmployeeList employeeList = new EmployeeList();
				employeeList.setVisible(true);

			}
		});
		GroupLayout gl_panel_worker = new GroupLayout(panel_worker);
		gl_panel_worker.setHorizontalGroup(gl_panel_worker.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_worker.createSequentialGroup()
						.addGroup(gl_panel_worker.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_worker.createSequentialGroup().addGap(173).addComponent(lblWorker))
								.addGroup(gl_panel_worker.createSequentialGroup().addContainerGap()
										.addComponent(btnEmployeeList)))
						.addContainerGap(172, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_panel_worker.createSequentialGroup()
						.addContainerGap(234, Short.MAX_VALUE).addComponent(btnNewEmployee).addGap(61)));
		gl_panel_worker.setVerticalGroup(gl_panel_worker.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_worker.createSequentialGroup().addContainerGap().addComponent(lblWorker).addGap(35)
						.addComponent(btnNewEmployee).addGap(26).addComponent(btnEmployeeList)
						.addContainerGap(56, Short.MAX_VALUE)));
		panel_worker.setLayout(gl_panel_worker);

		JLabel lblFamily = new JLabel("Family");
		lblFamily.setFont(new Font("Tahoma", Font.PLAIN, 17));

		JButton btnNFamily = new JButton("New Family");
		btnNFamily.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewFamilyFrame nff = new NewFamilyFrame();
				nff.setVisible(true);

			}
		});

		JButton btnListaRodzin = new JButton("Families list");
		btnListaRodzin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FamiliesList flist = new FamiliesList();
				flist.setVisible(true);
			}
		});
		GroupLayout gl_panel_family = new GroupLayout(panel_family);
		gl_panel_family.setHorizontalGroup(gl_panel_family.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_family.createSequentialGroup().addGap(179)
						.addComponent(lblFamily, GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE).addGap(176))
				.addGroup(gl_panel_family.createSequentialGroup().addContainerGap().addComponent(btnListaRodzin)
						.addContainerGap(311, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_panel_family.createSequentialGroup()
						.addContainerGap(232, Short.MAX_VALUE).addComponent(btnNFamily).addGap(77)));
		gl_panel_family.setVerticalGroup(gl_panel_family.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_family.createSequentialGroup().addContainerGap()
						.addComponent(lblFamily, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGap(37).addComponent(btnNFamily).addGap(21).addComponent(btnListaRodzin).addGap(59)));
		panel_family.setLayout(gl_panel_family);
		frmWorkOffice.getContentPane().setLayout(groupLayout);

		JMenuBar menuBar = new JMenuBar();
		frmWorkOffice.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);

	}
	
// ====================================================================================

	// Method populate Family Table
	public void populateLinkTable() {
		model = (DefaultTableModel) table.getModel();
		Object[] tablerow = new Object[10];

		for (int i = 0; i < linkList.size(); i++) {

			tablerow[0] = linkList.get(i).getEid();
			tablerow[1] = linkList.get(i).getEname();
			tablerow[2] = linkList.get(i).getEsurname();
			tablerow[3] = " ";
			tablerow[4] = linkList.get(i).getFid();
			tablerow[5] = linkList.get(i).getFName();
			tablerow[6] = linkList.get(i).getFsurname();
			tablerow[7] = " ";
			tablerow[8] = linkList.get(i).getFromDate();
			tablerow[9] = linkList.get(i).getToDate();

			model.addRow(tablerow);
		}
		System.out.println("Table  Link populated");
	}

	// Delete selected row
	public void deleteData() {
		dao = new WorkOfficeDAO();
		int delID = linkList.get(table.convertRowIndexToModel(table.getSelectedRow())).getId();
		dao.deleteLinkedData(delID);
		refreshTable();
	}

	// Refresh
	public void refreshTable() {
		dao = new WorkOfficeDAO();
		linkList = dao.getLinkList();
		model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		populateLinkTable();
	}
}
