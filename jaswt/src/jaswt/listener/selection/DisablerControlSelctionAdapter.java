package jaswt.listener.selection;

import java.util.ArrayList;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;

/**
 * Class that extends SelectionAdapter for disable a Control list
 * @author Andrea Serra
 *
 */
public class DisablerControlSelctionAdapter extends SelectionAdapter {
	private ArrayList<Control> controlList;

	/* CONSTRUCTOR */
	/**
	 * constructor that set the ArrayList of Controls to be closed
	 * @param controls list to be closed 
	 */
	public DisablerControlSelctionAdapter(ArrayList<Control> controls) {
		super();
		this.controlList = controls;
	}

	/* GET */
	public ArrayList<Control> getControlList() {
		return controlList;
	}

	@Override
	public void widgetSelected(SelectionEvent se) {
		Button chckBttn = (Button) se.widget;
		for (Control control : controlList) control.setEnabled(chckBttn.getSelection());
	}
}
