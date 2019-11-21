package jaswt.label;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import jaswt.exception.ParameterException;

public class ViewColorLabel extends Label {
	private int red = 0;
	private int green = 0;
	private int blue = 0;

	/* override for checksublclass bypass error */
	@Override
	protected void checkSubclass() {
	}

	/* ############################################################################# */
	/* START CONSTRUCTORS */
	/* ############################################################################# */

	/**
	 * construct that set a composite parent and style
	 * @param parent composite
	 * @param style  of label
	 */
	public ViewColorLabel(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * construct that set a composite parent, style and RGB
	 * @param parent composite
	 * @param style  of label
	 * @param red    of RGB
	 * @param green  of RGB
	 * @param blue   of RGB
	 * @throws ParameterException
	 */
	public ViewColorLabel(Composite parent, int style, int red, int green, int blue) throws ParameterException {
		super(parent, style);
		if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) throw new ParameterException("Error!!! The number must be a value between 0 and 255");
		setRedraw(true);
		this.red = red;
		this.green = green;
		this.blue = blue;
		setBackground(getColorSet());
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
	public void setRed(int red) throws ParameterException {
		if (red < 0 || red > 255) throw new ParameterException("Error!!! The number must be a value between 0 and 255");
		this.red = red;
	}
	public int getGreen() {
		return green;
	}
	public void setGreen(int green) throws ParameterException {
		if (green < 0 || green > 255) throw new ParameterException("Error!!! The number must be a value between 0 and 255");
		this.green = green;
	}
	public int getBlue() {
		return blue;
	}
	public void setBlue(int blue) throws ParameterException {
		if (blue < 0 || blue > 255) throw new ParameterException("Error!!! The number must be a value between 0 and 255");
		this.blue = blue;
	}

	/* ############################################################################# */
	/* END GET AND SET */
	/* ############################################################################# */

	/* override redraw */
	@Override
	public void redraw() {
		setBackground(getColorSet());
		super.redraw();
	}

	/* get color set by rgb attribute */
	private Color getColorSet() {
		return new Color(getShell().getDisplay(), new RGB(red, green, blue));
	}
}
