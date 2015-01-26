package app.views;

import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import sys.View;

/**
 * Comment Edit View
 *
 * @author thodoris
 */
@SuppressWarnings("serial")
public class CommentEditView extends View {

	/**
	 * Comment text area.
	 */
	public JTextArea commentTextArea;

	/**
	 * Post or Update comment button.
	 */
	public JButton postButton;

	/**
	 * Delete comment button.
	 */
	public JButton deleteButton;

	/**
	 * Return to parent view button.
	 */
	public JButton returnButton;

	/**
	 * Comment edit view.
	 *
	 * @param employeeEditView
	 */
	public CommentEditView(EmployeeEditView employeeEditView) {
		super(employeeEditView);
		StringBuilder employeeFullnameStr = new StringBuilder();
		employeeFullnameStr.append(employeeEditView.textComponents.get("firstname").getText().toUpperCase());
		employeeFullnameStr.append(" ");
		employeeFullnameStr.append(employeeEditView.textComponents.get("lastname").getText().toUpperCase());

		setTitle(employeeEditView.getTitle() + ": Comments about " + employeeFullnameStr);
	}

	@Override
	protected void build() {
		mainConstraints.insets.set(5, 5, 5, 5);

		Font font = new Font(Font.MONOSPACED, Font.PLAIN, 18);

		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		commentTextArea = new JTextArea(10, 80);
		commentTextArea.setFont(font);
		commentTextArea.setLineWrap(true);
		commentTextArea.setWrapStyleWord(true);
		mainConstraints.gridx = 0;
		mainConstraints.gridy = 0;
		mainConstraints.gridwidth = 3;
		JScrollPane commentScrollPane = new JScrollPane();
		commentScrollPane.setViewportView(commentTextArea);
		mainPanel.add(commentScrollPane, mainConstraints);

		postButton = new JButton("Post");
		postButton.setFont(font);
		mainConstraints.gridx = 0;
		mainConstraints.gridy = 1;
		mainConstraints.gridwidth = 3;
		mainConstraints.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(postButton, mainConstraints);

		deleteButton = new JButton("Delete");
		deleteButton.setFont(font);
		mainConstraints.gridx = 1;
		mainConstraints.gridy = 2;
		mainConstraints.gridwidth = 1;
		mainConstraints.weightx = 0.5;
		mainConstraints.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(deleteButton, mainConstraints);

		returnButton = new JButton("Return");
		returnButton.setFont(font);
		mainConstraints.gridx = 2;
		mainConstraints.gridy = 2;
		mainConstraints.gridwidth = 1;
		mainConstraints.weightx = 0.5;
		mainConstraints.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(returnButton, mainConstraints);

		add(mainPanel);
		pack();
		setResizable(false);
		centerView();
	}
}
