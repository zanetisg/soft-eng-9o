package app.contollers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;

import sys.Controller;
import sys.Model;
import sys.log.Log;
import app.models.CommentModel;
import app.models.EmployeeModel;
import app.models.UserModel;
import app.views.EmployeeEditView;
import app.views.EmployeeSearchView;
import app.views.LoginView;
import java.util.ArrayList;

public class EmployeeController extends Controller {

	/**
	 * Employee model for editing employees.
	 */
	private EmployeeModel employeeModel;

	/**
	 * Comment model for editing comments about the employees.
	 */
	private CommentModel commentModel;

	/**
	 * Employee search view.
	 */
	private EmployeeSearchView employeeSearchView;

	/**
	 * Employee edit view.
	 */
	private EmployeeEditView employeeEditView;

	/**
	 * Comments controller object.
	 */
	private CommentController commentController;

	/**
	 * Employee table fields to view.
	 */
	private final String[] employeeTableFields;

	/**
	 * Employee Constructor.
	 *
	 * @param userModel
	 * @param loginView
	 */
	public EmployeeController(UserModel userModel, LoginView loginView) {
		super(userModel, loginView);
		employeeTableFields = new String[] { "id", "salary", "lastname", "firstname", "fathername", "email", "phone",
				"mobile", "birthdate" };
	}

	/**
	 * Search employees
	 */
	public void search() {
		// Create comment model.
		employeeModel = new EmployeeModel(parentModel.getConnection());

		// Create employee search view object.
		employeeSearchView = new EmployeeSearchView(parentView);
		employeeSearchView.setVisible(true);

		// Initiate employee table
		updateEmpolyeeTable(null);

		// Initiate editView for adding employee
		employeeSearchView.addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				employeeSearchView.dispose();
				edit(null);
			}
		});

		// Find employees by search query
		employeeSearchView.findButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String searchQuery = employeeSearchView.searchTextField.getText();
				if (searchQuery.isEmpty()) {
					employeeSearchView.showError("Search field is empty.");
				} else {
					updateEmpolyeeTable(searchQuery);
				}
			}
		});

		// Clear search field
		employeeSearchView.clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				employeeSearchView.searchTextField.setText("");
			}
		});

		// Terminate application
		employeeSearchView.closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				employeeSearchView.dispose();
				System.exit(0);
			}
		});

		// Edit employee by clicking a row on the table
		employeeSearchView.employeeTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 1) {
					int row = employeeSearchView.employeeTable.getSelectedRow();
					String employeeId = employeeSearchView.employeeTable.getValueAt(row, 0).toString();
					Log.msg("Employee id: " + employeeId);
					employeeSearchView.dispose();
					edit(employeeId);
				}
			}
		});
	}

	/**
	 * Edit employee data
	 *
	 * @param employeeId
	 */
	public void edit(String employeeId) {
		// Create employee model.
		employeeModel = new EmployeeModel(parentModel.getConnection());

		commentModel = new CommentModel(parentModel.getConnection());

		// Create employee edit view.
		employeeEditView = new EmployeeEditView(employeeSearchView);
		employeeEditView.setVisible(true);

		if (employeeId != null && !employeeId.isEmpty()) {
			// Load empoyee data to edit.
			employeeModel.loadData(employeeId);

			// Populate fields with the selected employee data
			for (String key : employeeEditView.textComponents.keySet()) {
				String value = employeeModel.getDataValue(key);
				employeeEditView.textComponents.get(key).setText(value);
			}

			updateCommentList(employeeId);
		} else {
			// Only if employee is selected data can be deleted and comments
			// posted
			employeeEditView.deleteButton.setEnabled(false);
			employeeEditView.commentButton.setEnabled(false);
		}

		// Validate and save employee data
		employeeEditView.saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean valid = true;

				for (String key : employeeEditView.textComponents.keySet()) {
					String value = employeeEditView.textComponents.get(key).getText().trim();
					// Validate
					if (value.isEmpty()) {
						valid = false;
						employeeEditView.showError("All fields are required.");
						break;
					}
					employeeModel.setDataValue(key, value);
					Log.msg("Value added -> " + key + " = " + employeeModel.getDataValue(key));
				}

				if (valid) {
					employeeModel.save();
					closeEmployeeEditView();
				}
			}
		});

		// Delete employee if employee id is set
		if (employeeId != null) {
			employeeEditView.deleteButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					employeeModel.delete();
					commentModel.deleteByEmployeeId(employeeId);
					closeEmployeeEditView();
				}
			});
		}

		// Return to search by clicking the return button
		employeeEditView.returnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				closeEmployeeEditView();
			}
		});

		// Return to search by clicking the close button of the window corner
		employeeEditView.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeEmployeeEditView();
			}
		});

		if (employeeId != null) {
			commentController = new CommentController((UserModel) parentModel, employeeEditView);
			commentController.setParentController(this);

			// Initiate comment edit view for adding a comment
			employeeEditView.commentButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					commentController.comment(employeeId, null);
				}
			});

			// Initiate comment edit view for updating a comment
			for (JButton btn : employeeEditView.commentDeleteButtons) {
				// Load seleceted comment model.
				commentModel.loadData(btn.getActionCommand());

				// Cast parent model to user model.
				UserModel userModel = (UserModel) parentModel;

				// User can edit on his comments. Only the foreman can edit all
				// comments
				if (!userModel.getDataValue("id").equals(commentModel.getDataValue("user_id"))
						&& !userModel.isForeMan()) {
					btn.setEnabled(false);
				}

				// Update the selected comment.
				btn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						commentController.comment(employeeId, e.getActionCommand());
					}
				});
			}
		}
	}

	/**
	 * Update employee table
	 *
	 * @param searchQuery
	 */
	private void updateEmpolyeeTable(String searchQuery) {
		if (searchQuery == null) {
			searchQuery = "";
		}
		ArrayList<Model> employeeList = employeeModel.search(searchQuery, employeeTableFields);
		employeeSearchView.populateEmployeeTable(employeeList, employeeTableFields);
	}

	/**
	 * Close employee edit view and return to search
	 */
	private void closeEmployeeEditView() {
		employeeEditView.dispose();
		search();
	}

	/**
	 * Update comment list by id
	 *
	 * @param employeeId
	 */
	private void updateCommentList(String employeeId) {
		String where = "`employee_id` = '" + employeeId + "' ORDER BY `timestamp` DESC";
		ArrayList<Model> commentList = commentModel.getDataList(where);
		employeeEditView.populateCommentList(commentList);
	}
}
