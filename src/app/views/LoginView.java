package app.views;

import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import sys.View;
import app.Main;

/**
 * Login View.
 *
 * @author thodoris
 */
@SuppressWarnings("serial")
public class LoginView extends View {

	/**
	 * Username field.
	 */
	public JTextField usernameField;

	/**
	 * Password field.
	 */
	public JPasswordField passwordFIeld;

	/**
	 * Login button.
	 */
	public JButton loginButton;

	/**
	 * Cancel button.
	 */
	public JButton cancelButton;

	/**
	 * Login View Constructor.
	 */
	public LoginView() {
		super(null);
		setTitle(Main.APP_NAME + ": User login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	protected void build() {
		mainConstraints.insets.set(5, 5, 5, 5);

		Font font = new Font(Font.MONOSPACED, Font.PLAIN, 20);

		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel usernameL = new JLabel("Username");
		usernameL.setFont(font);
		mainConstraints.gridx = 0;
		mainConstraints.gridy = 0;
		mainConstraints.gridwidth = 2;
		mainPanel.add(usernameL, mainConstraints);

		usernameField = new JTextField(20);
		usernameField.setFont(font);
		mainConstraints.gridx = 0;
		mainConstraints.gridy = 1;
		mainConstraints.gridwidth = 2;
		mainPanel.add(usernameField, mainConstraints);

		JLabel passwordL = new JLabel("Password");
		passwordL.setFont(font);
		mainConstraints.gridx = 0;
		mainConstraints.gridy = 2;
		mainConstraints.gridwidth = 2;
		mainPanel.add(passwordL, mainConstraints);

		passwordFIeld = new JPasswordField(20);
		passwordFIeld.setFont(font);
		mainConstraints.gridx = 0;
		mainConstraints.gridy = 3;
		mainConstraints.gridwidth = 2;
		mainPanel.add(passwordFIeld, mainConstraints);

		loginButton = new JButton("Login");
		loginButton.setFont(font);
		mainConstraints.gridx = 0;
		mainConstraints.gridy = 4;
		mainConstraints.gridwidth = 2;
		mainConstraints.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(loginButton, mainConstraints);

		cancelButton = new JButton("Cancel");
		cancelButton.setFont(font);
		mainConstraints.gridx = 0;
		mainConstraints.gridy = 5;
		mainConstraints.gridwidth = 2;
		mainConstraints.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(cancelButton, mainConstraints);

		add(mainPanel);
		pack();
		setResizable(false);
		centerView();
	}
}
