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
public class OpenerFileFromOSSelectionAdapter implements SelectionListener {
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
	public OpenerFileFromOSSelectionAdapter(Shell shellParent, String path) {
		this.shellParent = shellParent;
		this.path = path;
	}

	/* ################################################################################# */
	/* END CONSTRUCT */
	/* ################################################################################# */

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
