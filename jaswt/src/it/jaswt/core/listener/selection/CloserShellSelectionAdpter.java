package it.jaswt.core.listener.selection;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;

public class CloserShellSelectionAdpter extends SelectionAdapter {
	private Shell shell;

	public CloserShellSelectionAdpter(Shell shell) {
		super();
		this.shell = shell;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		shell.dispose();
	}

}
