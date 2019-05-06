package it.jaswt.core.listener.selection;

import java.io.IOException;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;

import it.jaswt.core.Jaswt;
import it.jutilas.core.Jutilas;

public class LuncherFileExplorerSelectionAdapter extends SelectionAdapter {
	private Shell shellParent;
	private String path;
	private String nameProgramm;

	public LuncherFileExplorerSelectionAdapter(Shell shellParent, String path, String nameProgramm) {
		this.shellParent = shellParent;
		this.path = path;
		this.nameProgramm = nameProgramm;
	}

	@Override
	public void widgetSelected(SelectionEvent se) {
		try {
			Jutilas.getInstance().openFileExplore(path);
		} catch (IOException e) {
			Jaswt.getInstance().lunchMBError(shellParent, e, nameProgramm);
		}
	}
}
