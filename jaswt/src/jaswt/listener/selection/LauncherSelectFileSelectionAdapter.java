package jaswt.listener.selection;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import jaswt.utils.Jaswt;

/**
 * Class that extends SelectionAdapter for launch a window to select a file and set it in Text
 * @author Andrea Serra
 *
 */
public class LauncherSelectFileSelectionAdapter implements SelectionListener{
	private Shell shell;
	private Text text;

	/* CONSTRUCT */
	/**
	 * construct that set the shell parent and Text
	 * @param shell parent
	 * @param text  where write a path
	 */
	public LauncherSelectFileSelectionAdapter(Shell shell, Text text) {
		super();
		this.shell = shell;
		this.text = text;
	}

	@Override
	public void widgetSelected(SelectionEvent se) {
		launchFileDialog();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent se) {
		launchFileDialog();
	}

	private void launchFileDialog() {
		String path = Jaswt.getInstance().launchFileDialog(shell, text.getText());
		if (!(path == null || path.isEmpty())) text.setText(path);
	}
}
