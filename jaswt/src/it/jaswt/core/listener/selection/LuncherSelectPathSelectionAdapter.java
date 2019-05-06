package it.jaswt.core.listener.selection;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import it.jaswt.core.Jaswt;

public class LuncherSelectPathSelectionAdapter extends SelectionAdapter {
	private Shell shell;
	private Text text;

	public LuncherSelectPathSelectionAdapter(Shell shell, Text text) {
		super();
		this.shell = shell;
		this.text = text;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		String path = Jaswt.getInstance().lunchDirectoryDialog(shell, text.getText());
		if (!(path == null || path.isEmpty()))
			text.setText(path);
	}
}
