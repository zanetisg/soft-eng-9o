package sys;

public class Controller {

	protected View parentView;
	protected Model parentModel;
	protected Controller parentController;

	/**
	 * 
	 * @param parentModel
	 * @param parentView
	 */
	public Controller(Model parentModel, View parentView) {
		this.parentModel = parentModel;
		this.parentView = parentView;
	}

	/**
	 * 
	 * @param parentModel
	 * @param parentView
	 * @param parentController
	 */
	public Controller(Model parentModel, View parentView, Controller parentController) {
		this.parentModel = parentModel;
		this.parentView = parentView;
		this.parentController = parentController;
	}

	/**
	 * 
	 * @param parentController
	 */
	public void setParentController(Controller parentController) {
		this.parentController = parentController;
	}
}
