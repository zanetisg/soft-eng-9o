package app.contollers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import sys.Controller;
import app.models.UserModel;
import app.views.LoginView;

/**
 * User controller class.
 *
 * @author thodoris
 */
public class UserContoller extends Controller {

	/**
	 * User model.
	 */
	private final UserModel userModel;

	/**
	 * User Controller constructor.
	 *
	 * @param conn
	 *            JDBC Connection.
	 */
	public UserContoller(Connection conn) {
		super(null, null);
		// Initiate user model.
		userModel = new UserModel(conn);
	}

	/**
	 * User login method.
	 */
	public void login() {
		LoginView loginView = new LoginView();
		loginView.setVisible(true);

		// Validate and login user
		loginView.loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = loginView.usernameField.getText();
				String password = String.valueOf(loginView.passwordFIeld.getPassword());

				// Validation.
				if (username.isEmpty() || password.isEmpty()) { // Check
																// required
																// fields.
					loginView.showError("All fields are required.");
				} else if (userModel.exists("username='" + username + "'") == null) { // Check
																						// if
																						// username
																						// exists.
					loginView.showError("Username does not exist.");
				} else if (!userModel.login(username, password)) { // Check
																	// password
																	// and
																	// login.
					loginView.showError("Password is wrong.");
				} else { // Login success.
					loginView.dispose();
					EmployeeController employeeController = new EmployeeController(userModel, loginView);
					employeeController.search();
				}
			}
		});

		// Terminate application
		loginView.cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loginView.dispose();
				System.exit(0);
			}
		});
	}
}
