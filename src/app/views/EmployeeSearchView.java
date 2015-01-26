package app.views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import sys.Model;
import sys.View;
import app.Main;

@SuppressWarnings("serial")
public class EmployeeSearchView extends View {

	private JScrollPane scrollPane;

	public JTextField searchTextField;
	public JButton findButton, clearButton, closeButton, addButton;
	public JTable employeeTable;

	public EmployeeSearchView(View parentView) {
		super(parentView);
		setTitle(Main.APP_NAME + ": Employee Search");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	protected void build() {
		mainConstraints.insets.set(5, 5, 5, 5);

		Font font = new Font(Font.MONOSPACED, Font.PLAIN, 20);

		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		addButton = new JButton("+");
		addButton.setFont(font);
		mainConstraints.gridx = 0;
		mainConstraints.gridy = 0;
		mainPanel.add(addButton, mainConstraints);

		searchTextField = new JTextField(40);
		searchTextField.setFont(font);
		mainConstraints.gridx = 1;
		mainConstraints.gridy = 0;
		mainPanel.add(searchTextField, mainConstraints);

		findButton = new JButton("Find");
		findButton.setFont(font);
		mainConstraints.gridx = 2;
		mainConstraints.gridy = 0;
		mainConstraints.weightx = 0.2;
		mainConstraints.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(findButton, mainConstraints);

		clearButton = new JButton("Clear");
		clearButton.setFont(font);
		mainConstraints.gridx = 3;
		mainConstraints.gridy = 0;
		mainConstraints.weightx = 0.2;
		mainConstraints.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(clearButton, mainConstraints);

		closeButton = new JButton("Close");
		closeButton.setFont(font);
		mainConstraints.gridx = 4;
		mainConstraints.gridy = 0;
		mainConstraints.weightx = 0.2;
		mainPanel.add(closeButton, mainConstraints);

		scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(1024, 1024 / 5 * 3));

		mainConstraints.gridx = 0;
		mainConstraints.gridy = 1;
		mainConstraints.gridwidth = 5;
		mainPanel.add(scrollPane, mainConstraints);

		add(mainPanel);
		pack();
		setResizable(false);
		centerView();
	}

	public void populateEmployeeTable(ArrayList<Model> employees, String[] fields) {
		Font font = new Font(Font.MONOSPACED, Font.PLAIN, 16);

		Vector<String> employeeColumnNames = new Vector<>();
		Vector<Vector<String>> employeeRows = new Vector<>();

		for (String field : fields) {
			employeeColumnNames.add(field.toUpperCase());
		}

		for (Model employee : employees) {
			Vector<String> row = new Vector<>();
			for (String f : fields) {
				row.add(employee.getDataValue(f));
			}
			employeeRows.add(row);
		}

		DefaultTableModel tableModel = new DefaultTableModel() {
			/**
			 * No editable cells
			 */
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// Populate table
		tableModel.setDataVector(employeeRows, employeeColumnNames);

		employeeTable = new JTable();
		employeeTable.setModel(tableModel);
		employeeTable.setFont(font);
		employeeTable.setRowHeight(24);
		employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		employeeTable.setToolTipText("Double click a row to edit.");
		employeeTable.setBorder(new EmptyBorder(10, 10, 10, 10));

		scrollPane.setViewportView(employeeTable);
	}
}
