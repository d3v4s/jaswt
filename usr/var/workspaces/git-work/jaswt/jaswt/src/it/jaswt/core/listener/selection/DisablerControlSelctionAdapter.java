package it.jaswt.core.listener.selection;

import java.util.ArrayList;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;

public class DisablerControlSelctionAdapter extends SelectionAdapter {
	private ArrayList<Control> controlList;

	public DisablerControlSelctionAdapter(ArrayList<Control> control) {
		super();
		this.controlList = control;
	}
	
	public ArrayList<Control> getControlList() {
		return controlList;
	}

	@Override
	public void widgetSelected(SelectionEvent se) {
		Button chckBttn = (Button) se.widget;
		for (Control control : controlList)
			control.setEnabled(chckBttn.getSelection());
	}
}
