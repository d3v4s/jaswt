package it.jaswt.core.listener.selection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;

import it.jaswt.core.Jaswt;
import it.jutilas.core.Jutilas;

public class LuncherFileExplorerSelectionAdapter extends SelectionAdapter {
	private Shell shellParent;
	private String path;

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
			Jaswt.getInstance().lunchMB(shellParent, SWT.OK, "FAIL!!!", "Errore!!!Messaggio: " + e.getMessage());
		}
	}
}
