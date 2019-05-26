package it.jaswt.core.canvas;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import it.jaswt.exception.ParameterException;

public class ViewerColorCanvas extends Canvas {
	private int red = 0;
	private int green = 0;
	private int blue = 0;

	@Override
	/* override checksubclass per evitare errore */
	protected void checkSubclass() {
	}

	/* costruttori */
	public ViewerColorCanvas(Composite parent, int style) {
		super(parent, style);
		setRedraw(true);
		setBackground(getColorSet());
	}
	public ViewerColorCanvas(Composite parent, int style, int red, int green, int blue) throws ParameterException {
		super(parent, style);
		if (red > 255 || green > 255 || blue > 255)
			throw new ParameterException("Errore!!! Il numero della quantita' di colore non puo' essere superione di 255");
		setRedraw(true);
		this.red = red;
		this.green = green;
		this.blue = blue;
		setBackground(getColorSet());
	}

	/* get e set */
	public int getRed() {
		return red;
	}
	public void setRed(int red) throws ParameterException {
		if (red > 255)
			throw new ParameterException("Errore!!! Il numero della quantita' di colore non puo' essere superione di 255");
		this.red = red;
	}
	public int getGreen() {
		return green;
	}
	public void setGreen(int green) throws ParameterException {
		if (green > 255)
			throw new ParameterException("Errore!!! Il numero della quantita' di colore non puo' essere superione di 255");
		this.green = green;
	}
	public int getBlue() {
		return blue;
	}
	public void setBlue(int blue) throws ParameterException {
		if (blue > 255)
			throw new ParameterException("Errore!!! Il numero della quantita' di colore non puo' essere superione di 255");
		this.blue = blue;
	}

	@Override
	/* override redraw */
	public void redraw() {
		setBackground(getColorSet());
		super.redraw();
	}

	/* get colo set on rgb attribute */
	private Color getColorSet() {
		return new Color(getShell().getDisplay(), new RGB(red, green, blue));
	}
}
