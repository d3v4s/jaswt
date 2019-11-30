package jaswt.listener.selection;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;

import jaswt.utils.Jaswt;
import jutilas.utils.Jutilas;

/**
 * Class that extends SelectionAdapter for launch a System File Explorer
 * @author Andrea Serra
 *
 */
public class OpenFileFromOSSelectionAdapter implements SelectionListener {
	/* PRIVATE */
	private Shell shellParent;

	/* PROTECTED */
	protected String path;

	/* ################################################################################# */
	/* START CONSTRUCT */
	/* ################################################################################# */

	/**
	 * constructor that sets Shell parent and path
	 * @param shellParent parent
	 * @param path to be open on file explorer
	 */
	public OpenFileFromOSSelectionAdapter(Shell shellParent, String path) {
		this.shellParent = shellParent;
		this.path = path;
	}

//	/**
//	 * constructor that sets Shell parent
//	 * @param shellParent parent
//	 */
//	public OpenFileFromOSSelectionAdapter(Shell shellParent) {
//		this.shellParent = shellParent;
//	}

	/* ################################################################################# */
	/* END CONSTRUCT */
	/* ################################################################################# */

//	/* ################################################################################# */
//	/* START GET SET */
//	/* ################################################################################# */
//
//	public String getPath() {
//		return path;
//	}
//	public void setPath(String path) {
//		this.path = path;
//	}
//
//	/* ################################################################################# */
//	/* END GET SET */
//	/* ################################################################################# */

	@Override
	public void widgetSelected(SelectionEvent se) {
		openFile();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		openFile();
	}

	private void openFile() {
		try {
			Jutilas.getInstance().openFileFromOS(path);
		} catch (Exception e) {
			Jaswt.getInstance().launchMBError(shellParent, e);
		}
	}
}
