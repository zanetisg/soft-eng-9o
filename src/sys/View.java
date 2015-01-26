package sys;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * View Class. Manage Application UI.
 *
 * @author thodoris
 */
@SuppressWarnings("serial")
public class View extends JFrame {

	/**
	 * The parent view.
	 */
	protected View parentView;

	/**
	 * Host Screen Size.
	 */
	protected Dimension screenSize;

	/**
	 * Main Grid Bag Constraints
	 */
	protected GridBagConstraints mainConstraints;

	/**
	 * Main Grid Bag Layout.
	 */
	protected GridBagLayout mainLayout;

	/**
	 * Main Panel.
	 */
	protected JPanel mainPanel;

	/**
	 *
	 * @param parentView
	 */
	public View(View parentView) {
		this.parentView = parentView;

		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		mainConstraints = new GridBagConstraints();
		mainLayout = new GridBagLayout();
		mainPanel = new JPanel(mainLayout);

		setLocationRelativeTo(null);

		build();
	}

	/**
	 * Build User Interface.
	 */
	protected void build() {
	}

	/**
	 * Center window on the screen.
	 */
	public void centerView() {
		int x = (int) ((screenSize.getWidth() - this.getWidth()) / 2);
		int y = (int) ((screenSize.getHeight() - this.getHeight()) / 2);
		setLocation(x, y);
	}

	/**
	 * Show error dialog.
	 *
	 * @param msg
	 */
	public void showError(String msg) {
		JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Show success dialog.
	 *
	 * @param msg
	 */
	public void showSuccess(String msg) {
		JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Show warning dialog.
	 *
	 * @param msg
	 * @return boolean Confirm result.
	 */
	public boolean showWarning(String msg) {
		int option = JOptionPane.showConfirmDialog(this, msg, "Warning", JOptionPane.OK_CANCEL_OPTION);
		return (option == JOptionPane.OK_OPTION);
	}
}
