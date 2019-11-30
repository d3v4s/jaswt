package jaswt.utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

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
import jutilas.utils.JutilasSys;

/**
 * Class with utils for the Java library SWT
 * 
 * @author Andrea Serra
 *
 */
public class Jaswt {
	/* PRIVATE */
	private static Jaswt utilsViewAS;

	/* CONSTRUCTOR */
	private Jaswt() {
	}

	/* SINGLETON */
	public static Jaswt getInstance() {
		return utilsViewAS = (utilsViewAS == null) ? new Jaswt() : utilsViewAS;
	}

	/* ############################################################################# */
	/* START MESSAGE BOX METHODS */
	/* ############################################################################# */

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
		mb.setMessage(MessageFormat.format("\n{0}", message));
		return mb.open();
	}

	/* metodo che lancia messaggio di errore */
	/**
	 * method to launch a message box error
	 * 
	 * @param parent    shell
	 * @param exception to be view
	 */
	public void launchMBError(Shell parent, Exception exception) {
		launchMB(parent, SWT.OK, "FAIL!!!", MessageFormat.format("Error!!! Messagge: {0}", exception.getMessage()));
		exception.printStackTrace();
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
			exception.printStackTrace();
			launchMB(parent, SWT.OK, "FAIL!!!", MessageFormat.format("Error!!! Error while writing the log file.\nMessage: {0}", e.getMessage()));
			e.printStackTrace();
		}
	}

	/* ############################################################################# */
	/* END MESSAGE BOX METHODS */
	/* ############################################################################# */

	/* ############################################################################# */
	/* START CREATE CONTENTS METHODS */
	/* ############################################################################# */

	// TODO implement keyMapList
	/**
	 * method that create labels horizontally on the composite
	 * 
	 * @param textsLabel      list
	 * @param x               coordinate on the shell
	 * @param y               coordinate on the shell
	 * @param width           of label
	 * @param height          of label
	 * @param space           between two labels
	 * @param compositeParent parent
	 * @param labelStyle      label
	 */
	public Map<String, CLabel> createLabels(String[] textsLabel, int x, int y, int width, int height, int space, Composite compositeParent, String[] keyMapList, int labelStyle, CreateContentsDirection createContentsDirection) {
		Map<String, CLabel> labelMap = new HashMap<String, CLabel>();
		CLabel lbl = null;
		int heightFont = height - 6;
		SetterBoundsLabel setBoundsFunct = null;
		/* preset to print vertical or horizontal */
		switch (createContentsDirection) {
			case VERTICAL:
				final int hs = height + space;
				setBoundsFunct = (label, i) -> {
					label.setBounds(x, y + hs * i, width, height);
				};
				break;
			case HORIZONTAL:
				final int ws = width + space;
				setBoundsFunct = (label, i) -> {
					label.setBounds(x + ws * i, y, width, height);
				};
				break;
			default:
				return null;
		}
		/* ciclo per label */
		for (int i = 0, length = textsLabel.length; i < length; i++) {
			lbl = new CLabel(compositeParent, labelStyle);
			lbl.getFont().getFontData()[0].setHeight(heightFont);
			setBoundsFunct.set(lbl, i);
			lbl.setText(textsLabel[i]);
			labelMap.put(keyMapList[i], lbl);
		}
		return labelMap;
	}

	/* private interface to set bounds of label */
	@FunctionalInterface
	private interface SetterBoundsLabel {
		void set(CLabel label, int i);
	}

	/**
	 * method that create text inputs horizontally on the composite and insert them in the map
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
	public Map<String, Text> createTexts(int x, int y, int[] width, int height, int space, Composite compositeParent, String[] keyMapList, int textStyle, CreateContentsDirection createContentsDirection) {
		Map<String, Text> textMap = new HashMap<String, Text>();
		Text txt;
		int heightFont = height - 6;
		SetterBoundsText setBoundsFunct = null;
		/* preset to print vertical or horizontal */
		switch (createContentsDirection) {
			case VERTICAL:
				final int hs = height + space;
				setBoundsFunct = (text, i) -> {
					text.setBounds(x, y + hs * i, width[i], height);
				};
				break;
			case HORIZONTAL:
				setBoundsFunct = (text, i) -> {
					int ws = width[i] + space;
					text.setBounds(x + ws * i, y, width[i], height);
				};
				break;
			default:
				return null;
		}
		/* ciclo per aree di testo */
		for (int i = 0, length = keyMapList.length; i < length; i++) {
			txt = new Text(compositeParent, textStyle);
			txt.getFont().getFontData()[0].setHeight(heightFont);
			setBoundsFunct.set(txt, i);
			if (i == 0) txt.forceFocus();
			textMap.put(keyMapList[i], txt);
		}
		return textMap;
	}

	/* private interface to set bounds of text */
	@FunctionalInterface
	private interface SetterBoundsText {
		void set(Text text, int i);
	}

	/**
	 * method that create button horizontally on the composite
	 * 
	 * @param textsList          of buttons
	 * @param x                  coordinate on the composite
	 * @param y                  coordinate on the composite
	 * @param width              of button
	 * @param height             of button
	 * @param space              between two buttons
	 * @param compositeParent    parent
	 * @param selectListenerList list of SelectionListener for buttons
	 */
	public Map<String, Button> createButtons(String[] textsList, int x, int y, int width, int height, int space, Composite compositeParent, SelectionListener[] selectListenerList, String[] keyMapList, CreateContentsDirection createContentsDirection) {
		Map<String, Button> bttnMap = new HashMap<String, Button>();
		Button bttn;
		SetterBoundsButton setBoundsFunct = null;
		/* preset to print vertical or horizontal */
		switch (createContentsDirection) {
			case VERTICAL:
				final int hs = height + space;
				setBoundsFunct = (button, i) -> {
					button.setBounds(x, y + hs * i, width, height);
				};
				break;
			case HORIZONTAL:
				final int ws = width + space;
				setBoundsFunct = (button, i) -> {
					button.setBounds(x + ws * i, y, width, height);
				};
				break;
			default:
				return null;
		}
		/* ciclo per button */
		for (int i = 0, length = textsList.length; i < length; i++) {
			bttn = new Button(compositeParent, SWT.PUSH);
			setBoundsFunct.set(bttn, i);
			if (selectListenerList[i] != null) bttn.addSelectionListener(selectListenerList[i]);
			if (textsList[i] != null) bttn.setText(textsList[i]);
			bttn.setCursor(new Cursor(compositeParent.getDisplay(), SWT.CURSOR_HAND));
			bttnMap.put(keyMapList[i], bttn);
		}
		return bttnMap;
	}

	/* private interface to set bounds of button */
	@FunctionalInterface
	private interface SetterBoundsButton {
		void set(Button button, int i);
	}

	/* ############################################################################# */
	/* START CREATE CONTENTS METHODS */
	/* ############################################################################# */

	/* ############################################################################# */
	/* START OTHER METHODS */
	/* ############################################################################# */

	/* metodo che centra la finestra */
	/**
	 * method to center the window of application
	 * 
	 * @param shell to be centered
	 */
	public void centerWindow(Shell shell) {
		Dimension dmnsn = Toolkit.getDefaultToolkit().getScreenSize();
		int x = Double.valueOf((dmnsn.getWidth() - shell.getSize().x)/2).intValue();
		int y = Double.valueOf((dmnsn.getHeight() - shell.getSize().y)/2).intValue();
		shell.setLocation(x, y);
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
		pathStart = !(pathStart == null || pathStart.trim().isEmpty()) ? pathStart : JutilasSys.getInstance().getPathUsrHome();
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
		pathStart = !(pathStart == null || pathStart.trim().isEmpty()) ? pathStart : JutilasSys.getInstance().getPathUsrHome();
		fileDialog.setFilterPath(pathStart);
		return fileDialog.open();
	}

	/* ############################################################################# */
	/* END OTHER METHODS */
	/* ############################################################################# */

}
