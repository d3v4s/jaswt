package jaswt.canvas;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import jaswt.exception.ArgumentException;

/**
 * Class for view a color on canvas
 * 
 * @author Andrea Serra
 *
 */
public class ViewerColorCanvas extends Canvas {
	private int red = 0;
	private int green = 0;
	private int blue = 0;

	/* override for bypass checksubclass error */
	@Override
	protected void checkSubclass() {
	}

	/* ############################################################################# */
	/* START CONSTRUCTORS */
	/* ############################################################################# */

	/**
	 * construct that set a composite parent
	 * @param parent composite
	 */
	public ViewerColorCanvas(Composite parent) {
		super(parent, SWT.NONE);
		setRedraw(true);
		setBackground(getSwtColor());
	}

	/**
	 * construct that set a composite parent and style
	 * @param parent composite
	 * @param style  of canvas
	 */
	public ViewerColorCanvas(Composite parent, int style) {
		super(parent, style);
		setRedraw(true);
		setBackground(getSwtColor());
	}

	/**
	 * construct that set a composite parent and RGB
	 * @param parent composite
	 * @param red    of RGB
	 * @param green  of RGB
	 * @param blue   of RGB
	 * @throws ArgumentException
	 */
	public ViewerColorCanvas(Composite parent, int red, int green, int blue) throws ArgumentException {
		super(parent, SWT.NONE);
		if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) throw new ArgumentException("Error!!! The number must be a value between 0 and 255");
		setRedraw(true);
		this.red = red;
		this.green = green;
		this.blue = blue;
		setBackground(getSwtColor());
	}

	/**
	 * construct that set a composite parent, style and RGB
	 * @param parent composite
	 * @param red    of RGB
	 * @param green  of RGB
	 * @param blue   of RGB
	 * @param style  of canvas
	 * @throws ArgumentException
	 */
	public ViewerColorCanvas(Composite parent, int red, int green, int blue, int style) throws ArgumentException {
		super(parent, style);
		if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) throw new ArgumentException("Error!!! The number must be a value between 0 and 255");
		setRedraw(true);
		this.red = red;
		this.green = green;
		this.blue = blue;
		setBackground(getSwtColor());
	}

	/* ############################################################################# */
	/* END CONSTRUCTORS */
	/* ############################################################################# */

	/* ############################################################################# */
	/* START GET AND SET */
	/* ############################################################################# */

	public int getRed() {
		return red;
	}
	public void setRed(int red) throws ArgumentException {
		if (red < 0 || red > 255) throw new ArgumentException("Error!!! The number must be a value between 0 and 255");
		this.red = red;
	}
	public int getGreen() {
		return green;
	}
	public void setGreen(int green) throws ArgumentException {
		if (green < 0 || green > 255) throw new ArgumentException("Error!!! The number must be a value between 0 and 255");
		this.green = green;
	}
	public int getBlue() {
		return blue;
	}
	public void setBlue(int blue) throws ArgumentException {
		if (blue < 0 || blue > 255) throw new ArgumentException("Error!!! The number must be a value between 0 and 255");
		this.blue = blue;
	}
	

	/* ############################################################################# */
	/* END GET AND SET */
	/* ############################################################################# */

	/* ############################################################################# */
	/* START DRAW METHODS */
	/* ############################################################################# */

	/* override redraw */
	@Override
	public void redraw() {
		setBackground(getSwtColor());
		super.redraw();
	}

	/* ############################################################################# */
	/* END DRAW METHODS */
	/* ############################################################################# */

	/* ############################################################################# */
	/* START PRIVATE METHODS */
	/* ############################################################################# */

	/* get swt color by rgb attribute */
	private Color getSwtColor() {
		return new Color(getShell().getDisplay(), new RGB(red, green, blue));
	}

	/* ############################################################################# */
	/* END PRIVATE METHODS */
	/* ############################################################################# */

}
