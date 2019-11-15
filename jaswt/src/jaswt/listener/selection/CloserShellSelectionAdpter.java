package jaswt.listener.selection;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;

/**
 * Class that extends SelectionAdapter for close shell
 * @author Andrea Serra
 *
 */
public class CloserShellSelectionAdpter extends SelectionAdapter {
	private Shell shell;

	/* CONSTRUCTOR */
	/**
	 * constructor that set the shell to be close
	 * @param shell to be closed
	 */
	public CloserShellSelectionAdpter(Shell shell) {
		super();
		this.shell = shell;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		shell.dispose();
	}

}
