package jaswt.core;

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

import jogger.JoggerError;
import jutilas.core.JutilasSys;

/**
 * Class with utils for the Java library SWT
 * 
 * @author Andrea Serra
 *
 */
public class Jaswt {
	private static Jaswt utilsViewAS;

	/* CONSTRUCTOR */
	private Jaswt() {
	}

	/* SINGLETON */
	public static Jaswt getInstance() {
		return utilsViewAS = (utilsViewAS == null) ? new Jaswt() : utilsViewAS;
	}

	/* metodo che lancia message box */
	/**
	 * method to launch a message box
	 * 
	 * @param parent shell
	 * @param style of message box
	 * @param title of message box
	 * @param message to be view
	 * @return response to message
	 */
	public int launchMB(Shell parent, int style, String title, String message) {
		MessageBox mb = new MessageBox(parent, style);
		mb.setText(title);
		mb.setMessage("\n" + message);
		return mb.open();
	}

	/* metodo che centra la finestra */
	/**
	 * method to center the window of application
	 * 
	 * @param shell to be centered
	 */
	public void centerWindow(Shell shell) {
		Dimension dmnsn = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dmnsn.getWidth() - shell.getSize().x) / 2);
		int y = (int) ((dmnsn.getHeight() - shell.getSize().y) / 2);
		shell.setLocation(x, y);
	}

	/* metodo che lancia messaggio di errore */
	/**
	 * method to launch a message box error
	 * 
	 * @param parent    shell
	 * @param exception to be view
	 */
	public void launchMBError(Shell parent, Exception exception) {
		launchMB(parent, SWT.OK, "FAIL!!!", "Error!!! Messagge: " + exception.getMessage());
	}

	/* metodo che lancia messaggio di errore e scrive log */
	/**
	 * method that launch message box error and write a log
	 * 
	 * @param parent      shell
	 * @param exception   to be view and logged
	 * @param joggerError to write a log
	 */
	public void launchMBError(Shell parent, Exception exception, JoggerError joggerError) {
		launchMBError(parent, exception);
		try {
			joggerError.writeLog(exception);
		} catch (Exception e) {
			launchMB(parent, SWT.OK, "FAIL!!!",
					"Error!!! Error while writing the log file.\nError message: " + e.getMessage());
		}
	}

	/* metodo che lancia finestra per selezionare una cartella */
	/**
	 * method that launch window to select a folder
	 * 
	 * @param shell     parent
	 * @param pathStart to open in the window
	 * @return String of directory path
	 */
	public String launchDirectoryDialog(Shell shell, String pathStart) {
		DirectoryDialog dirDialog = new DirectoryDialog(shell, SWT.NONE);
		pathStart = !(pathStart == null || pathStart.trim().isEmpty()) ? pathStart
				: JutilasSys.getInstance().getPathUsrHome();
		dirDialog.setFilterPath(pathStart);
		return dirDialog.open();
	}

	/* metodo che lancia finestra per selezionare una file */
	/**
	 * method that launch window to select file
	 * 
	 * @param shell     parent
	 * @param pathStart to open in the window
	 * @return String of file path
	 */
	public String launchFileDialog(Shell shell, String pathStart) {
		FileDialog fileDialog = new FileDialog(shell, SWT.NONE);
		pathStart = !(pathStart == null || pathStart.trim().isEmpty()) ? pathStart
				: JutilasSys.getInstance().getPathUsrHome();
		fileDialog.setFilterPath(pathStart);
		return fileDialog.open();
	}

	/* metodo che stampa label su shell in verticale */
	/**
	 * method that create labels vertically on the composite
	 * 
	 * @param namesLabel      list
	 * @param x               coordinate on the shell
	 * @param y               coordinate on the shell
	 * @param width           of label
	 * @param height          of label
	 * @param space           between two labels
	 * @param compositeParent parent
	 * @param style           label
	 */
	public void printLabelVertical(String[] namesLabel, int x, int y, int width, int height, int space,
			Composite compositeParent, int style) {
		CLabel lbl;
		int hs = height + space;
		int heightFont = height - 6;
		/* ciclo per label */
		for (int i = 0, length = namesLabel.length; i < length; i++) {
			lbl = new CLabel(compositeParent, style);
			lbl.getFont().getFontData()[0].setHeight(heightFont);
			lbl.setBounds(x, y + hs * i, width, height);
			lbl.setText(namesLabel[i]);
		}
	}

	/* metodo che stampa text su shell in verticale e gli inserisce sulla mappa */
	/**
	 * method that create text inputs vertically on the composite and insert them in
	 * the map
	 * 
	 * @param x               coordinate on the composite
	 * @param y               coordinate on the shell
	 * @param width           of text input
	 * @param height          of text input
	 * @param space           between two text inputs
	 * @param compositeParent parent
	 * @param keyMapList      list of key to be used on map
	 * @param mapSaveText     map where save the text inputs
	 * @param disableTextList list of text inputs indexes to be disabled
	 */
	public void printTextVertical(int x, int y, int[] width, int height, int space, Composite compositeParent,
			String[] keyMapList, HashMap<String, Text> mapSaveText, int[] disableTextList) {
		Text txt;
		int hs = height + space;
		int heightFont = height - 6;
		/* ciclo per aree di testo */
		for (int i = 0, length = keyMapList.length; i < length; i++) {
			txt = new Text(compositeParent, SWT.NONE);
			txt.getFont().getFontData()[0].setHeight(heightFont);
			txt.setBounds(x, y + hs * i, width[i], height);
			if (i == 0)
				txt.forceFocus();
			for (int j : disableTextList)
				if (i == j)
					txt.setEnabled(false);
			mapSaveText.put(keyMapList[i], txt);
		}
	}

	/**
	 * method that create button horizontally on the composite
	 * 
	 * @param namesList          of buttons
	 * @param x                  coordinate on the composite
	 * @param y                  coordinate on the composite
	 * @param width              of button
	 * @param height             of button
	 * @param space              between two buttons
	 * @param compositeParent    parent
	 * @param selectListenerList list of SelectionListener for buttons
	 */
	public void printButtonHorizontal(String[] namesList, int x, int y, int width, int height, int space,
			Composite compositeParent, SelectionListener[] selectListenerList) {
		Button bttn;
		int ws = width + space;
		/* ciclo per button */
		for (int i = 0, length = namesList.length; i < length; i++) {
			bttn = new Button(compositeParent, SWT.PUSH);
			bttn.setBounds(x + ws * i, y, width, height);
			if (selectListenerList[i] != null)
				bttn.addSelectionListener(selectListenerList[i]);
			if (namesList[i] != null)
				bttn.setText(namesList[i]);
			bttn.setCursor(new Cursor(compositeParent.getDisplay(), SWT.CURSOR_HAND));
		}
	}
}
