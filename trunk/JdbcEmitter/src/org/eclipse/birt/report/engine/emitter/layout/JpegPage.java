package org.eclipse.birt.report.engine.emitter.layout;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Map;

import org.eclipse.birt.report.engine.layout.emitter.IPage;
import org.eclipse.birt.report.engine.nLayout.area.style.TextStyle;

/**
 * Responsible for drawing individual pages. This has several
 * methods that get called based on the element that needs to 
 * be drawn.
 * @author John Ward
 *
 */
public class JpegPage implements IPage {
	private Graphics2D canvas;
	private double scale;
	
	/**
	 * Utility method to scale sizes accordingly
	 * @param size
	 * @return
	 */
	private int scale(int size)
	{
		return Math.round((float)(size * scale));
	}
	
	/**
	 * Constructor that will init the local canvas and set the scale
	 * @param canvas
	 * @param scale
	 */
	public JpegPage(Graphics2D canvas, double scale) {
		this.canvas = canvas;
		this.scale = scale;
	}

	/**
	 * Cleanup the current page
	 */
	@Override
	public void dispose() {
		//not implemented
	}
	
	/**
	 * Called to draw background colors for rectangular areas
	 */
	@Override
	public void drawBackgroundColor(Color color, int x, int y, int width,
			int height) {
		canvas.setBackground(color);		
		canvas.clearRect(scale(x), scale(y), scale(width), scale(height));
	}

	/**
	 * draw a background image, used for watermarks
	 */
	@Override
	public void drawBackgroundImage(int x, int y, int width, int height,
			int imageWidth, int imageHeight, int repeat, String imageUrl, byte[] imageData, int absPosX,
			int absPosY) throws IOException {
		//not implemented
	}

	/**
	 * Draw an image
	 */
	@Override
	public void drawImage(String uri, String extension, int imageX, int imageY,
			int height, int width, String helpText, Map params) throws Exception {
		//not implemented
	}

	/**
	 * Draw an image from embedded bytes which are passed in as imageData
	 */
	@Override
	public void drawImage(String imageId, byte[] imageData, String extension, int imageX,
			int imageY, int height, int width, String helpText, Map params)
			throws Exception {
		//not implemented
	}

	/**
	 * Draw lines for borders
	 */
	@Override
	public void drawLine(int startX, int startY, int endX, int endY, int width,
			Color color, int lineStyle) {
		//check color, if not set default to black
		if (color == null)
		{
			canvas.setColor(Color.BLACK);
		}
		else
		{
			canvas.setColor(color);
		}

		//set the size of the line stroke. Won't handle dash or dots at the moment
		canvas.setStroke(new BasicStroke(scale(width)));
		
		//draw the line
		canvas.drawLine(scale(startX), scale(startY), scale(endX), scale(endY));
		
		//reset back to a default size of 1
		canvas.setStroke(new BasicStroke(1));
	}

	/**
	 * Draw Text onto canvas
	 */
	@Override
	public void drawText(String textToRender, int x, int y, int width, int height,
			TextStyle textStyle) {
				
		//get font information
		int fontSize = textStyle.getFontSize();
		Color fontColor = textStyle.getColor();
		String fontName = textStyle.getFontInfo().getFontName();
		int fontStyle = textStyle.getFontInfo().getFontStyle();
		
		//set default color if not set
		if (fontColor == null)
		{
			this.canvas.setColor(Color.BLACK);
		}
		else
		{
			this.canvas.setColor(fontColor);
		}
		
		//do a little bit of adjustment on the font size, then set the canvas font 
		Font font = new Font(fontName, fontStyle, (Math.round(scale(fontSize) / (float)1.7)));
		canvas.setFont(font);

		//There is a minor shift in the Y position when scaled that causes alignment to be off. By taking
		//a fraction of the height of the text box shift the text down slightly. This is not a perfect
		//fix, but for the purpose of demonstration it will work. 
		this.canvas.drawString(textToRender, scale(x), scale(y) + (Math.round(scale(height) / 1.5)));
	}

	/**
	 * End a rectangular clip
	 */
	@Override
	public void endClip() {
		//not implemented
	}

	/**
	 * Draw help text. Used for mouse overs and image maps.
	 */
	@Override
	public void showHelpText(String text, int x, int y, int width, int height) {
		//not implemented
	}

	/**
	 * Called to create a rectangular clip area
	 */
	@Override
	public void startClip(int startX, int startY, int width, int height) {
		//not implemented
	}
}
