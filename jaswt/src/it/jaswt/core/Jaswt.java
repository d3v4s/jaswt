package it.jaswt.core;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import it.jogger.core.JoggerError;
import it.jogger.exception.FileLogException;
import it.jogger.exception.LockLogException;
import it.jutilas.core.JutilasSys;

public class Jaswt {
	private static Jaswt utilsViewAS;
	
	private Jaswt() {
	}
	
	/* singleton */
	public static Jaswt getInstance() {
		utilsViewAS = (utilsViewAS == null) ? new Jaswt() : utilsViewAS;
		return utilsViewAS;
	}
	
	/* metodo che lancia message box */
	public int lunchMB(Shell parent, int style, String title, String message) {
		MessageBox mb = new MessageBox(parent, style);
		mb.setText(title);
		mb.setMessage("\n" + message);
		return mb.open();
	}

	/* metodo che centra la finestra */
	public void centerWindow(Shell shell) {
		Dimension dmnsn = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dmnsn.getWidth() - shell.getSize().x) / 2);
		int y = (int) ((dmnsn.getHeight() - shell.getSize().y) / 2);
		shell.setLocation(x, y);
	}
	
	/* metodo che lancia messaggio di errore */
	public void lunchMBError(Shell parent, Exception exception, String nameLog) {
		lunchMB(parent, SWT.OK, "FAIL!!!", "Errore!!!Messaggio: " + exception.getMessage());
		try {
			JoggerError.writeLog(exception, nameLog);
		} catch (FileLogException | LockLogException e) {
			lunchMB(parent, SWT.OK, "FAIL!!!", "Errore!!! Errore durante la scrittura del file log.\n"
					+ "Controllare la cartella dei log su " + JoggerError.getLogDirPath() + ".\n"
					+ "Messaggio errore: " + e.getMessage());
		}
	}
	
	/* metodo che lancia finestra per selezionare una cartella */
	public String lunchDirectoryDialog(Shell shell, String pathStart) {
		DirectoryDialog dirDialog = new DirectoryDialog(shell, SWT.NONE);
		pathStart = !(pathStart == null || pathStart.trim().isEmpty()) ? pathStart : JutilasSys.getInstance().getUsrHomePath();
		dirDialog.setFilterPath(pathStart);
		return dirDialog.open();
	}

	/* metodo che lancia finestra per selezionare una file */
	public String lunchFileDialog(Shell shell, String pathStart) {
		FileDialog fileDialog = new FileDialog(shell, SWT.NONE);
		pathStart = !(pathStart == null || pathStart.trim().isEmpty()) ? pathStart : JutilasSys.getInstance().getUsrHomePath();
		fileDialog.setFilterPath(pathStart);
		return fileDialog.open();
	}

	/* metodo che stampa label su shell in verticale */
	public void printLabelVertical(String[] namesLabel, int x, int y, int width, int height, int space, Composite compositeParent, int style) {
		/* ciclo per label */
		CLabel lbl;
		for (int i = 0; i < namesLabel.length; i++) {
			lbl = new CLabel(compositeParent, style);
			lbl.getFont().getFontData()[0].setHeight(height);
			lbl.setBounds(x, y + (height + space) * i, width, height + 6);
			lbl.setText(namesLabel[i]);
		}
	}

	/* metodo che stampa text su shell in verticale e gli inserisce sulla mappa */
	public void printTextVertical(int x, int y, int[] width, int height, int space, Composite compositeParent, String[] keyMapList, HashMap<String, Text> mapSaveText, int[] disableTextList) {
		/* ciclo per areee di testo */
		Text txt;
		for (int i = 0; i < keyMapList.length; i++) {
			txt = new Text(compositeParent, SWT.NONE);
			txt.getFont().getFontData()[0].setHeight(height);
			txt.setBounds(x, y + (height + space) * i, width[i], height + 6);
			if (i == 0) txt.forceFocus();
			for (int j : disableTextList)
				if (i == j) txt.setEnabled(false);
			mapSaveText.put(keyMapList[i], txt);
		}
	}
	
	public void printButtonHorizontal(String[] namesList, int x, int y, int width, int height, int space, Composite compositeParent, SelectionListener[] selectListenerList) {
		Button bttn;
		for (int i = 0; i < namesList.length; i++) {
			bttn = new Button(compositeParent, SWT.PUSH);
			bttn.setBounds(x + (width + space) * i, y, width, height);
			if (selectListenerList[i] != null)
				bttn.addSelectionListener(selectListenerList[i]);
			if (namesList[i] != null)
				bttn.setText(namesList[i]);
			bttn.setCursor(new Cursor(compositeParent.getDisplay(), SWT.CURSOR_HAND));
		}
	}
}
