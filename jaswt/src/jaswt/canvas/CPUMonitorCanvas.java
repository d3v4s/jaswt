package jaswt.canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import jutilas.utils.JutilasSys;

/**
 * Class to create a CPU monitor with canvas
 * @author Andrea Serra
 *
 */
public class CPUMonitorCanvas extends Canvas {
	private CPUMonitorStyle CPUMonStyle = CPUMonitorStyle.CLASSIC;
	private CPUMonitorCanvas cpuMonitorCanvas = this;
	private BlockingQueue<Double> cpuAverageQueue;
	private CpuMonitorThread cpuMonitorThread;
	private boolean running = false;
	private int timeout = 1500;
	private CLabel infoLabel;

	/* override for bypass check subclass error */
	@Override
	protected void checkSubclass() {
	}

	/* ############################################################################# */
	/* START CONSTRUCTORS */
	/* ############################################################################# */

	/**
	 * Construct that set composite parent
	 * 
	 * @param parent composite
	 */
	public CPUMonitorCanvas(Composite parent) {
		super(parent, SWT.NONE);
		this.addPaintListener(new DrawerCpuUsagePaintListener());
		this.setBackground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
	}

	/**
	 * Construct that set composite parent and style
	 * 
	 * @param parent composite
	 * @param style  of canvas
	 */
	public CPUMonitorCanvas(Composite parent, int style) {
		super(parent, style);
		this.addPaintListener(new DrawerCpuUsagePaintListener());
		this.setBackground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
	}

	/**
	 * Construct that set composite parent and the queue with cpu usage
	 * 
	 * @param parent        composite
	 * @param cpuAverageQueue
	 */
	public CPUMonitorCanvas(Composite parent, BlockingQueue<Double> cpuAverageQueue) {
		super(parent, SWT.NONE);
		this.cpuAverageQueue = cpuAverageQueue;
		this.addPaintListener(new DrawerCpuUsagePaintListener());
		this.setBackground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
	}

	/**
	 * Construct that set composite parent, style and the queue with cpu usage
	 * 
	 * @param parent        composite
	 * @param cpuAverageQueue
	 * @param style         of canvas
	 */
	public CPUMonitorCanvas(Composite parent, BlockingQueue<Double> cpuAverageQueue, int style) {
		super(parent, style);
		this.cpuAverageQueue = cpuAverageQueue;
		this.addPaintListener(new DrawerCpuUsagePaintListener());
		this.setBackground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
	}

	/* ############################################################################# */
	/* 	END CONSTRUCTORS */
	/* ############################################################################# */

	/* ############################################################################# */
	/* START GET AND SET */
	/* ############################################################################# */

	public BlockingQueue<Double> getCpuAverageQueue() {
		return cpuAverageQueue;
	}
	public void setCpuAverageQueue(BlockingQueue<Double> cpuAverageQueue) {
		this.cpuAverageQueue = cpuAverageQueue;
	}
	public CPUMonitorStyle getCPUMonStyle() {
		return CPUMonStyle;
	}
	public void setCPUMonStyle(CPUMonitorStyle CPUMonStyle) {
		this.CPUMonStyle = CPUMonStyle;
	}
	public void setCPUMonStyle(int CPUMonStyle) {
		switch (CPUMonStyle) {
		case 0:
			this.CPUMonStyle = CPUMonitorStyle.CLASSIC;
			break;
		case 1:
			this.CPUMonStyle = CPUMonitorStyle.REVERSE;
			break;
		case 2:
			this.CPUMonStyle = CPUMonitorStyle.LINE;
			break;
		default:
			break;
		}
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public CLabel getInfoLabel() {
		return infoLabel;
	}
	public void setInfoLabel(CLabel infoLabel) {
		this.infoLabel = infoLabel;
	}
	
	/* ############################################################################# */
	/* END GET AND SET */
	/* ############################################################################# */

	/* ############################################################################# */
	/* START AUTOMATIC METHODS */
	/* ############################################################################# */

	/**
	 * method that start the thread to draw graphics
	 * @throws JaswtException
	 */
	public void start() {
		if (running) return;
		if (cpuAverageQueue == null) cpuAverageQueue = new ArrayBlockingQueue<Double>(this.getSize().x);
		cpuMonitorThread = new CpuMonitorThread();
		cpuMonitorThread.start();
	}

	/**
	 * method that stop the thread to draw graphics
	 * @throws JaswtException
	 */
	public void stop() {
		if (!running) return;
		cpuMonitorThread.interrupt();
	}

	/* ############################################################################# */
	/* END AUTOMATIC METHODS */
	/* ############################################################################# */

	/* ############################################################################# */
	/* START INNER CLASS */
	/* ############################################################################# */

	/* paint listener per disegnare graffico */
	/**
	 * Inner class listener for draw graphics
	 * @author Andrea Serra
	 *
	 */
	private class DrawerCpuUsagePaintListener implements PaintListener {
		private final Color COLOR_ORANGE = new Color(getDisplay(), new RGB(255, 127, 80));

		public DrawerCpuUsagePaintListener() {
			super();
		}

		@Override
		public void paintControl(PaintEvent se) {
			/* get arraylist of cpu average */
			ArrayList<Double> avrgList = new ArrayList<Double>(cpuAverageQueue);
			Collections.reverse(avrgList);

			/* loop to print graphics */
			int avrgY;
			int iNext;
			int avrgYNext;
			Double avrg;
			Color avrgColor;
			int maxX = cpuMonitorCanvas.getSize().x;
			int maxY = cpuMonitorCanvas.getSize().y;
			int posX = maxX;
			double maxYPerc = maxY/100.0;
			for (int i = 0, size = avrgList.size(); i < size; i++) {
				/* get average and calculate y position */
				avrg = avrgList.get(i);
				avrgY = Double.valueOf(avrg * maxYPerc).intValue();
				avrgY = maxY - avrgY;
				avrgColor = (avrg < 50d) ? se.display.getSystemColor(SWT.COLOR_GREEN) : (avrg < 80d) ? COLOR_ORANGE : se.display.getSystemColor(SWT.COLOR_RED);
				/* switch for style of graphics */
				switch (CPUMonStyle) {
					case REVERSE:
						se.gc.setForeground(avrgColor);
						se.gc.drawLine(posX, 0, posX, avrgY);//maxY - avrgY);
						break;
					case LINE:
						iNext = (i == size - 1) ? i : i + 1;
						avrgYNext = Double.valueOf(avrgList.get(iNext) * maxYPerc).intValue();
						se.gc.setForeground(se.display.getSystemColor(SWT.COLOR_WHITE));
						se.gc.drawLine(posX, avrgY, posX - 1, maxY - avrgYNext);
						se.gc.setForeground(avrgColor);
						se.gc.drawPoint(posX, avrgY); //maxY - avrgY);
						break;
					case CLASSIC:
					default:
						se.gc.setForeground(avrgColor);
						se.gc.drawLine(posX, maxY, posX, avrgY);//maxY - avrgY);
						break;
				}
				posX--;
			}

			/* if queue is full take an element */
			if (cpuAverageQueue.remainingCapacity() == 0) {
				try {
					cpuAverageQueue.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Inner class Thread for draw the graphics
	 * @author Andrea Serra
	 *
	 */
	private class CpuMonitorThread extends Thread {
		private StringBuilder textLabel;
		private Shell shellParent;
		private double sysAvrg;

		/* CONSTRUCT */
		public CpuMonitorThread() {
			super();
			this.shellParent = getShell();
		}

		/* run thread */
		@Override
		public void run() {
			/* loop while parent not disposed */
			while (!shellParent.isDisposed()) {
				try {
					/* get sys average and put it in the queue */
					sysAvrg = JutilasSys.getInstance().getSystemLoadAverage(timeout);
					cpuAverageQueue.put(sysAvrg);
					/* check if parent is not dispose and add runnable */
					if (!shellParent.isDisposed()) {
						cpuMonitorCanvas.getDisplay().asyncExec(new Runnable() {
							@Override
							public void run() {
								textLabel = new StringBuilder();
								textLabel.append("CPU: ").append(String.format("%.0f", sysAvrg)).append("%");
								cpuMonitorCanvas.setToolTipText(textLabel.toString());
								cpuMonitorCanvas.redraw();
								if (infoLabel != null) infoLabel.setText(JutilasSys.getInstance().getSystemInfo(sysAvrg));
							}
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/* ############################################################################# */
	/* END INNER CLASS */
	/* ############################################################################# */

}
