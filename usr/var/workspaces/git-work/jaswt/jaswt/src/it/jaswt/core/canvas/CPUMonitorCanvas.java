package it.jaswt.core.canvas;

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

public class CPUMonitorCanvas extends Canvas {
	private CPUMonitorCanvas cpuMonitorCanvas = this;
	private ArrayBlockingQueue<Double> cpuUsageQueue;
	private int styleCPUMon = 0;

	/* costruttore */
	public CPUMonitorCanvas(Composite parent, int style) {
		super(parent, style);
	}
	public CPUMonitorCanvas(Composite parent, int style, ArrayBlockingQueue<Double> cpuUsageQueue) {
		super(parent, style);
		this.cpuUsageQueue = cpuUsageQueue;
		this.addPaintListener(new DrawerCpuUsagePaintListener());
		this.setBackground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
	}

	@Override  
	/* override checksubclass per evitare errore */
	protected void checkSubclass() {
	}

	/* get e set */
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

	/* paint listener per disegnare graffico */
	private class DrawerCpuUsagePaintListener implements PaintListener {
		@Override
		public void paintControl(PaintEvent se) {
			int maxX = cpuMonitorCanvas.getSize().x;
			int maxY = cpuMonitorCanvas.getSize().y;
			ArrayList<Double> ld = new ArrayList<Double>(cpuUsageQueue); 
			Collections.reverse(ld);
			Double d;
			int posX = maxX;
			for (int i = 0; i < ld.size(); i++) {
				d = ld.get(i);
				if (styleCPUMon == 0) {
					se.gc.setForeground(se.display.getSystemColor(SWT.COLOR_WHITE));
					se.gc.drawLine(posX, maxY - (d.intValue() * maxY/100), posX-1, maxY - (ld.get((i == ld.size() -1) ? i : i+1).intValue() * maxY/100));
					se.gc.setForeground(
							(d < 50d) ? se.display.getSystemColor(SWT.COLOR_GREEN) :
							(d < 80d) ? new Color(se.display, new RGB(255, 127, 80)) :
								se.display.getSystemColor(SWT.COLOR_RED)
					);
					se.gc.drawPoint(posX, maxY - (d.intValue() * maxY/100));
				} else if (styleCPUMon == 1) {
					se.gc.setForeground(
							(d < 50d) ? se.display.getSystemColor(SWT.COLOR_GREEN) :
							(d < 80d) ? new Color(se.display, new RGB(255, 127, 80)) :
								se.display.getSystemColor(SWT.COLOR_RED)
					);
					se.gc.drawLine(posX, maxY, posX, maxY - (d.intValue() * maxY/100));
				} else if (styleCPUMon == 2) {
					se.gc.setForeground(
							(d < 50d) ? se.display.getSystemColor(SWT.COLOR_GREEN) :
							(d < 80d) ? new Color(se.display, new RGB(255, 127, 80)) :
								se.display.getSystemColor(SWT.COLOR_RED)
					);
					se.gc.drawLine(posX, 0, posX, maxY - (d.intValue() * maxY/100));
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
