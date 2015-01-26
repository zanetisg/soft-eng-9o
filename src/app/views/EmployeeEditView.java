package app.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import sys.Model;
import sys.View;
import app.models.UserModel;

@SuppressWarnings("serial")
public class EmployeeEditView extends View {

	public JScrollPane scrollPane;

	public HashMap<String, JTextComponent> textComponents;

	public JButton saveButton, deleteButton, returnButton, commentButton;

	public ArrayList<JButton> commentDeleteButtons;

	public EmployeeEditView(View employeeSearchView) {
		super(employeeSearchView);
		setTitle(parentView.getTitle() + " - Employee Editor");
	}

	@Override
	protected void build() {
		mainConstraints.insets.set(5, 5, 5, 5);

		textComponents = new HashMap<>();

		String[] fields = { "firstname", "lastname", "fathername", "mothername", "birthdate", "email", "phone",
				"mobile", "country", "city", "area", "zip", "address", "salary" };

		Font font = new Font(Font.MONOSPACED, Font.PLAIN, 18);

		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		int columns = 2;
		int counter = 0;

		for (String field : fields) {
			int x = (counter % columns) * columns;
			int y = (counter / columns) * 2;
			counter++;

			// Label
			JLabel label = new JLabel(field.toUpperCase());
			label.setFont(font);
			mainConstraints.gridx = x;
			mainConstraints.gridy = y;
			mainConstraints.anchor = GridBagConstraints.LINE_END;

			mainPanel.add(label, mainConstraints);

			// Input
			JTextComponent textComp;
			mainConstraints.gridx++;
			mainConstraints.gridy = y;
			mainConstraints.anchor = GridBagConstraints.LINE_START;
			if (field.equals("birthdate")) {
				JDatePickerImpl birthdateJDP = new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel()));
				birthdateJDP.setFont(font);
				mainPanel.add(birthdateJDP, mainConstraints);
				textComponents.put(field, birthdateJDP.getJFormattedTextField());
				continue;
			}
			textComp = new JTextField(25);
			textComp.setFont(font);
			mainPanel.add(textComp, mainConstraints);
			textComponents.put(field, textComp);
		}

		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));

		saveButton = new JButton("Save");
		saveButton.setFont(font);
		buttonsPanel.add(saveButton);

		deleteButton = new JButton("Delete");
		deleteButton.setFont(font);
		buttonsPanel.add(deleteButton);

		returnButton = new JButton("Return to search");
		returnButton.setFont(font);
		buttonsPanel.add(returnButton);

		mainConstraints.gridx = 0;
		mainConstraints.gridy++;
		mainConstraints.gridwidth = columns * 2;
		mainConstraints.anchor = GridBagConstraints.LAST_LINE_END;
		mainPanel.add(buttonsPanel, mainConstraints);

		JLabel commentJL = new JLabel("Comments");
		commentJL.setFont(font);
		mainConstraints.gridx = 0;
		mainConstraints.gridy++;
		mainConstraints.gridwidth = columns * 2;
		mainConstraints.anchor = GridBagConstraints.CENTER;
		mainConstraints.fill = GridBagConstraints.NONE;
		mainPanel.add(commentJL, mainConstraints);

		scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(800, 340));

		mainConstraints.gridx = 0;
		mainConstraints.gridy++;
		mainConstraints.gridwidth = columns * 2;
		mainConstraints.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(scrollPane, mainConstraints);

		commentButton = new JButton("Comment");
		commentButton.setFont(font);
		mainConstraints.gridx = 0;
		mainConstraints.gridy++;
		mainConstraints.gridwidth = columns * 2;
		mainConstraints.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(commentButton, mainConstraints);

		add(mainPanel);
		pack();
		setResizable(false);
		centerView();
	}

	/**
	 * Show comments
	 *
	 * @param comments
	 */
	public void populateCommentList(ArrayList<Model> comments) {
		commentDeleteButtons = new ArrayList<>();

		Font commentInfoFont = new Font(Font.MONOSPACED, Font.BOLD | Font.ITALIC, 12);
		Font commentFont = new Font(Font.MONOSPACED, Font.PLAIN, 18);

		JPanel commentListPanel = new JPanel(new GridBagLayout());
		commentListPanel.setBackground(Color.darkGray);

		int counter = 0;
		for (Model comment : comments) {
			Date date = new Date(Long.parseLong(comment.getDataValue("timestamp")));
			SimpleDateFormat sdf = new SimpleDateFormat("'On' E dd/MM/yyyy 'at' hh:mm:ss");
			UserModel user = new UserModel(comment.getConnection());
			user.loadData(comment.getDataValue("user_id"));

			JPanel commentRowPanel = new JPanel(new GridBagLayout());
			commentRowPanel.setBackground(Color.WHITE);

			GridBagConstraints listRowConstraints = new GridBagConstraints();
			listRowConstraints.insets.set(5, 5, 5, 5);

			JLabel commentDatetimeLabel = new JLabel(sdf.format(date));
			commentDatetimeLabel.setFont(commentInfoFont);
			listRowConstraints.gridx = 0;
			listRowConstraints.gridy = 0;
			listRowConstraints.gridwidth = 1;
			listRowConstraints.gridheight = 1;
			listRowConstraints.weightx = 0.0;
			listRowConstraints.weighty = 0.0;
			listRowConstraints.fill = GridBagConstraints.NONE;
			listRowConstraints.anchor = GridBagConstraints.CENTER;
			commentRowPanel.add(commentDatetimeLabel, listRowConstraints);

			JTextArea commentTextArea = new JTextArea(comment.getDataValue("comment"));
			commentTextArea.setFont(commentFont);
			commentTextArea.setLineWrap(true);
			commentTextArea.setWrapStyleWord(true);
			listRowConstraints.gridx = 0;
			listRowConstraints.gridy = 1;
			listRowConstraints.gridwidth = 1;
			listRowConstraints.gridheight = 1;
			listRowConstraints.weightx = 1.0;
			listRowConstraints.weighty = 0.0;
			listRowConstraints.fill = GridBagConstraints.HORIZONTAL;
			listRowConstraints.anchor = GridBagConstraints.LINE_START;
			commentRowPanel.add(commentTextArea, listRowConstraints);

			JLabel commentByLabel = new JLabel("By " + user.getDataValue("username"));
			commentByLabel.setFont(commentInfoFont);
			listRowConstraints.gridx = 0;
			listRowConstraints.gridy = 2;
			listRowConstraints.gridwidth = 1;
			listRowConstraints.gridheight = 1;
			listRowConstraints.weightx = 0.0;
			listRowConstraints.weighty = 0.0;
			listRowConstraints.fill = GridBagConstraints.NONE;
			listRowConstraints.anchor = GridBagConstraints.LINE_END;
			commentRowPanel.add(commentByLabel, listRowConstraints);

			JButton commentEditButton = new JButton("Edit");
			commentEditButton.setFont(commentFont);
			commentEditButton.setActionCommand(comment.getDataValue("id"));
			commentDeleteButtons.add(commentEditButton);
			listRowConstraints.gridx = 1;
			listRowConstraints.gridy = 0;
			listRowConstraints.gridwidth = 1;
			listRowConstraints.gridheight = 3;
			listRowConstraints.weightx = 0.0;
			listRowConstraints.weighty = 0.0;
			listRowConstraints.fill = GridBagConstraints.VERTICAL;
			listRowConstraints.anchor = GridBagConstraints.CENTER;
			commentRowPanel.add(commentEditButton, listRowConstraints);

			listRowConstraints.gridx = 0;
			listRowConstraints.gridy = counter;
			listRowConstraints.gridwidth = 1;
			listRowConstraints.gridheight = 1;
			listRowConstraints.weightx = 1.0;
			listRowConstraints.weighty = 0.0;
			listRowConstraints.fill = GridBagConstraints.HORIZONTAL;
			listRowConstraints.anchor = GridBagConstraints.ABOVE_BASELINE;
			listRowConstraints.insets.set(1, 1, 1, 1);
			commentListPanel.add(commentRowPanel, listRowConstraints);

			counter++;
		}

		scrollPane.setViewportView(commentListPanel);
	}
}
