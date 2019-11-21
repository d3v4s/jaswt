package jaswt.listener.selection;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import jaswt.core.Jaswt;

/**
 * Class that extends SelectionAdapter for launch a window to select a folder and set it in Text
 * @author Andrea Serra
 *
 */
public class LauncherSelectPathSelectionAdapter extends SelectionAdapter {
	private Shell shell;
	private Text text;

	/* CONSTRUCT */
	/**
	 * construct that set the shell parent and Text
	 * @param shell parent
	 * @param text  where write a path
	 */
	public LauncherSelectPathSelectionAdapter(Shell shell, Text text) {
		super();
		this.shell = shell;
		this.text = text;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		String path = Jaswt.getInstance().launchDirectoryDialog(shell, text.getText());
		if (!(path == null || path.isEmpty())) text.setText(path);
	}
}
