package jaswt.listener.selection;

import java.util.List;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;

/**
 * Class that extends SelectionAdapter for disable a Control list
 * @author Andrea Serra
 *
 */
public class DisablerControlsSelctionAdapter implements SelectionListener {
	protected List<Control> controlList;

	/* CONSTRUCTOR */
	/**
	 * constructor that set the ArrayList of Controls to be closed
	 * @param controls list to be closed
	 */
	public DisablerControlsSelctionAdapter(List<Control> controls) {
		super();
		this.controlList = controls;
	}

	@Override
	public void widgetSelected(SelectionEvent se) {
		disableControls(se.widget);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent se) {
		disableControls(se.widget);
	}

	private void disableControls(Widget widget) {
		try {
			Button chckBttn = (Button) widget;
			for (Control control : controlList) control.setEnabled(chckBttn.getSelection());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
