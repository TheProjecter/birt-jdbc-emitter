package org.eclipse.birt.report.engine.emitter.layout;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.eclipse.birt.report.engine.layout.emitter.IPage;
import org.eclipse.birt.report.engine.layout.emitter.IPageDevice;

/**
 * This class is responsible for creating and managing pages during render.
 * Since this is a simple example, this class will only handle a single page.
 *
 */
public class JpegLayoutPageDevice implements IPageDevice {

	//the AWT canvas used to draw the image
	private Graphics2D canvas;
	//output stream to write to
	private OutputStream output;
	//buffered image that will be written to the output
	private BufferedImage bufferedImage;
	
	//set the scale of the image. 
	double scale = 0.01;

	/**
	 * Constructor just initializes the output stream read from the 
	 * render options
	 * @param output
	 */
	public JpegLayoutPageDevice(OutputStream output) {
		this.output = output;
	}

	/**
	 * Write file on close. 
	 */
	@Override
	public void close() throws Exception {
		ImageIO.write(bufferedImage, "jpg", output);
	}

	/**
	 * Utility method to calculate the scale size. 
	 * @param size
	 * @return
	 */
	private int scale(int size)
	{
		return Math.round((float)(size * scale));
	}
	
	/**
	 * Called by BIRT when a new page needs to be created
	 */
	@Override
	public IPage newPage(int width, int height, Color color) {
		//get the scaled width and height, and create an
		//image buffer based on that size
		int scaledWidth = scale(width);
		int scaledHeight = scale(height);
		bufferedImage = new BufferedImage(scaledWidth, 
										  scaledHeight, 
										  BufferedImage.TYPE_INT_RGB);
		
		//get the canvas to draw on from the buffered image
		canvas = bufferedImage.createGraphics();
		
		//check the background color. If one is not set, default
		//to white
		if (color == null)
		{
			canvas.setBackground(Color.WHITE);
		}
		else
		{
			canvas.setBackground(color);
		}
		
		//clear background color
		canvas.clearRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
		
		//create a new page device
		return new JpegPage(canvas, scale);
	}
}
