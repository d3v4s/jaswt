package it.jaswt.core.listener.selection;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;

public class DisablerControlSelctionAdapter extends SelectionAdapter {
	private Control[] controlList;

	public DisablerControlSelctionAdapter(Control... control) {
		super();
		this.controlList = control;
	}

	@Override
	public void widgetSelected(SelectionEvent se) {
		Button chckBttn = (Button) se.widget;
		for (Control control : controlList)
			control.setEnabled(chckBttn.getSelection());
			
	}
}
