package jaswt.listener.selection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;

import jaswt.core.Jaswt;
import jutilas.core.Jutilas;

/**
 * Class that extends SelectionAdapter for lunch a System File Explorer
 * @author Andrea Serra
 *
 */
public class LuncherFileExplorerSelectionAdapter extends SelectionAdapter {
	private Shell shellParent;
	private String path;

	/* CONSTRUCTOR */
	/**
	 * constructor that sets Shell parent and path 
	 * @param shellParent
	 * @param path to be open on file explorer
	 */
	public LuncherFileExplorerSelectionAdapter(Shell shellParent, String path) {
		this.shellParent = shellParent;
		this.path = path;
	}

	@Override
	public void widgetSelected(SelectionEvent se) {
		try {
			Jutilas.getInstance().openFileExplorer(path);
		} catch (Exception e) {
//			throw new RuntimeException("Errore durante l'apertura del file explorer!!!");
			Jaswt.getInstance().lunchMB(shellParent, SWT.OK, "FAIL!!!", "Error!!! Messagge: " + e.getMessage());
		}
	}
}
