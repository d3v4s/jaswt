package jaswt.listener.selection;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import jaswt.core.Jaswt;

/**
 * Class that extends SelectionAdapter for lunch a window to select a folder and set it in Text
 * @author Andrea Serra
 *
 */
public class LuncherSelectPathSelectionAdapter extends SelectionAdapter {
	private Shell shell;
	private Text text;

	/* CONSTRUCT */
	/**
	 * construct that set the shell parent and Text
	 * @param shell parent
	 * @param text where write a path
	 */
	public LuncherSelectPathSelectionAdapter(Shell shell, Text text) {
		super();
		this.shell = shell;
		this.text = text;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		String path = Jaswt.getInstance().lunchDirectoryDialog(shell, text.getText());
		if (!(path == null || path.isEmpty())) text.setText(path);
	}
}
