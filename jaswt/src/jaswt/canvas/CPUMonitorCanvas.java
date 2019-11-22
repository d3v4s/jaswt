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
	 * @author Andrea Serra
	 *
	 */
	private class DrawerCpuUsagePaintListener implements PaintListener {
		private final Color COLOR_ORANGE = new Color(getDisplay(), new RGB(255, 127, 80));
		@Override
		public void paintControl(PaintEvent se) {
			int maxX = cpuMonitorCanvas.getSize().x;
			int maxY = cpuMonitorCanvas.getSize().y;
			ArrayList<Double> avrgList = new ArrayList<Double>(cpuUsageQueue);
			Collections.reverse(avrgList);
			Double avrg;
			int posX = maxX;
			double maxYPerc = maxY/100.0;
			for (int i = 0, size = avrgList.size(); i < size; i++) {
				avrg = avrgList.get(i);
				int avrgY = Double.valueOf(avrg * maxYPerc).intValue();
				Color avrgColor = (avrg < 50d) ? se.display.getSystemColor(SWT.COLOR_GREEN) : (avrg < 80d) ? COLOR_ORANGE : se.display.getSystemColor(SWT.COLOR_RED);
				switch (styleCPUMon) {
					case 1:
						se.gc.setForeground(avrgColor);
						se.gc.drawLine(posX, 0, posX, maxY - avrgY);
						break;
					case 2:
						int iNext = (i == size - 1) ? i : i + 1;
						int avrgYNext = Double.valueOf(avrgList.get(iNext) * maxYPerc).intValue();
						se.gc.setForeground(se.display.getSystemColor(SWT.COLOR_WHITE));
						se.gc.drawLine(posX, maxY - avrgY, posX - 1, maxY - avrgYNext);
						se.gc.setForeground(avrgColor);
						se.gc.drawPoint(posX, maxY - avrgY);
						break;
					case 0:
					default:
						se.gc.setForeground(avrgColor);
						se.gc.drawLine(posX, maxY, posX, maxY - avrgY);
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
