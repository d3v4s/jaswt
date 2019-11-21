package jaswt.canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ArrayBlockingQueue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

/**
 * Class for create a CPU monitor with canvas
 * 
 * @author Andrea Serra
 *
 */
public class CPUMonitorCanvas extends Canvas {
	private CPUMonitorCanvas cpuMonitorCanvas = this;
	private ArrayBlockingQueue<Double> cpuUsageQueue;
	private int styleCPUMon = 0;

	/* override checksubclass per evitare errore */
	/* override for bypass checksubclass error */
	@Override
	protected void checkSubclass() {
	}

	/* ############################################################################# */
	/* START CONSTRUCTORS */
	/* ############################################################################# */

	/**
	 * Construct that set composite parent and style
	 * 
	 * @param parent composite
	 * @param style  of canvas
	 */
	public CPUMonitorCanvas(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * Construct that set composite parent, style and the queue with cpu usage
	 * 
	 * @param parent        composite
	 * @param style         of canvas
	 * @param cpuUsageQueue
	 */
	public CPUMonitorCanvas(Composite parent, int style, ArrayBlockingQueue<Double> cpuUsageQueue) {
		super(parent, style);
		this.cpuUsageQueue = cpuUsageQueue;
		this.addPaintListener(new DrawerCpuUsagePaintListener());
		this.setBackground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
	}

	/* ############################################################################# */
	/* 	END CONSTRUCTORS */
	/* ############################################################################# */

	/* ############################################################################# */
	/* START GET AND SET */
	/* ############################################################################# */

	public ArrayBlockingQueue<Double> getCpuUsageQueue() {
		return cpuUsageQueue;
	}

	public void setCpuUsageQueue(ArrayBlockingQueue<Double> cpuUsage) {
		this.cpuUsageQueue = cpuUsage;
	}

	public int getStyleCPUMon() {
		return styleCPUMon;
	}

	public void setStyleCPUMon(int styleCPUMon) {
		this.styleCPUMon = styleCPUMon;
	}

	/* ############################################################################# */
	/* END GET AND SET */
	/* ############################################################################# */

	/* paint listener per disegnare graffico */
	/**
	 * Inner class listener for draw graphics
	 * 
	 * @author Andrea Serra
	 *
	 */
	private class DrawerCpuUsagePaintListener implements PaintListener {
		@Override
		public void paintControl(PaintEvent se) {
			int maxX = cpuMonitorCanvas.getSize().x;
			int maxY = cpuMonitorCanvas.getSize().y;
			ArrayList<Double> ld = new ArrayList<Double>(cpuUsageQueue);
			Collections.reverse(ld);
			Double d;
			int posX = maxX;
			int maxYPerc = maxY / 100;
			for (int i = 0, size = ld.size(); i < size; i++) {
				d = ld.get(i);
				switch (styleCPUMon) {
					case 1:
						se.gc.setForeground((d < 50d) ? se.display.getSystemColor(SWT.COLOR_GREEN) : (d < 80d) ? new Color(se.display, new RGB(255, 127, 80)) : se.display.getSystemColor(SWT.COLOR_RED));
						se.gc.drawLine(posX, maxY, posX, maxY - (d.intValue() * maxYPerc));
						break;
					case 2:
						se.gc.setForeground((d < 50d) ? se.display.getSystemColor(SWT.COLOR_GREEN) : (d < 80d) ? new Color(se.display, new RGB(255, 127, 80)) : se.display.getSystemColor(SWT.COLOR_RED));
						se.gc.drawLine(posX, 0, posX, maxY - (d.intValue() * maxYPerc));
						break;
					case 0:
					default:
						se.gc.setForeground(se.display.getSystemColor(SWT.COLOR_WHITE));
						se.gc.drawLine(posX, maxY - (d.intValue() * maxYPerc), posX - 1, maxY - (ld.get((i == size - 1) ? i : i + 1).intValue() * maxYPerc));
						se.gc.setForeground((d < 50d) ? se.display.getSystemColor(SWT.COLOR_GREEN) : (d < 80d) ? new Color(se.display, new RGB(255, 127, 80)) : se.display.getSystemColor(SWT.COLOR_RED));
						se.gc.drawPoint(posX, maxY - (d.intValue() * maxYPerc));
						break;
				}
				posX--;
			}
			if (cpuUsageQueue.remainingCapacity() == 0) {
				try {
					cpuUsageQueue.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
