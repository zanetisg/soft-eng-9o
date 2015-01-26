package app.contollers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import sys.Controller;
import app.models.CommentModel;
import app.models.UserModel;
import app.views.CommentEditView;
import app.views.EmployeeEditView;

public class CommentController extends Controller {

	/**
	 * Comment model.
	 */
	private CommentModel commentModel;

	/**
	 * Comment edit view.
	 */
	private CommentEditView commentEditView;

	/**
	 * Employee id.
	 */
	private String employeeId;

	/**
	 * Comment Controller Constructor
	 *
	 * @param userModel
	 * @param employeeEditView
	 */
	public CommentController(UserModel userModel, EmployeeEditView employeeEditView) {
		super(userModel, employeeEditView);
	}

	/**
	 * Edit comments
	 *
	 * @param employeeId
	 * @param commentId
	 */
	public void comment(String employeeId, String commentId) {
		this.employeeId = employeeId;

		parentView.dispose();

		commentModel = new CommentModel(parentModel.getConnection());

		commentEditView = new CommentEditView((EmployeeEditView) parentView);
		commentEditView.setVisible(true);

		if (commentId != null && !commentId.isEmpty()) {
			commentModel.loadData(commentId);
			commentEditView.commentTextArea.setText(commentModel.getDataValue("comment"));
			if (!((UserModel) parentModel).isForeMan()) {
				commentEditView.deleteButton.setEnabled(false);
			}
		} else {
			commentModel.setDataValue("user_id", parentModel.getDataValue("id"));
			commentModel.setDataValue("employee_id", employeeId);
			commentEditView.deleteButton.setEnabled(false);
		}

		// Validate and post a comment and return to parent view
		commentEditView.postButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String comment = commentEditView.commentTextArea.getText();
				if (comment.isEmpty()) {
					commentEditView.showError("You can not post an empty comment.");
				} else {
					commentModel.setDataValue("timestamp", String.valueOf(System.currentTimeMillis()));
					commentModel.setDataValue("comment", comment);
					commentModel.save();
					closeCommentEditView();
				}
			}
		});

		// Delete a comment and return to parent view
		commentEditView.deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				commentModel.delete();
				closeCommentEditView();
			}
		});

		// Return to parent view
		commentEditView.returnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				closeCommentEditView();
			}
		});

		// Return to parent View
		commentEditView.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeCommentEditView();
			}
		});
	}

	/**
	 * Close this view and open parent view
	 */
	private void closeCommentEditView() {
		commentEditView.dispose();
		((EmployeeController) parentController).edit(employeeId);
	}
}
