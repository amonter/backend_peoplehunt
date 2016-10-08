package com.crowdscanner.controller.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

import com.myownmotivator.model.profile.File;

public class ImageUtilsNewVersion {

	final static Logger logger = Logger.getLogger(ImageUtilsNewVersion.class);
	
	public static BufferedImage createThumbnail(BufferedImage image,
			int thWidth, int thHeight) throws Exception {

		int w = image.getWidth();
		int h = image.getHeight();

		BufferedImage thumbnail = new BufferedImage(thWidth, thHeight,
				BufferedImage.TYPE_INT_RGB);

		float scalex = ((float) w) / ((float) thWidth);
		float scaley = ((float) h) / ((float) thHeight);

		float scale = Math.min(scalex, scaley);
		if (scale < 1)
			scale = 1;

		BufferedImage scaled = createResizedImage(image, Math.round(w / scale),
				Math.round(h / scale));

		Graphics2D g = thumbnail.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, thWidth, thHeight);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(scaled, (thWidth - scaled.getWidth()) / 2,
				(thHeight - scaled.getHeight()) / 2, null);
		g.dispose();

		return thumbnail;

	}

	private static BufferedImage createResizedImage(BufferedImage img,
			int targetWidth, int targetHeight) {

		if (img.getWidth() == targetHeight && img.getHeight() == targetHeight)
			return img;

		boolean higherQuality = true;
		boolean isIndexColorModel = img.getColorModel() instanceof IndexColorModel;
		int type;

		if (!isIndexColorModel) {
			type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
					: BufferedImage.TYPE_INT_ARGB;
		} else {
			type = img.getType();
		}

		BufferedImage ret = (BufferedImage) img;

		int w, h;
		if (higherQuality) {
			w = img.getWidth();
			h = img.getHeight();
		} else {
			w = targetWidth;
			h = targetHeight;
		}

		do {
			if (higherQuality && w > targetWidth) {
				w /= 2;
				if (w < targetWidth) {
					w = targetWidth;
				}
			}
			if (higherQuality && h > targetHeight) {
				h /= 2;
				if (h < targetHeight) {
					h = targetHeight;
				}
			}

			BufferedImage tmp = isIndexColorModel ? new BufferedImage(w, h,
					type, (IndexColorModel) img.getColorModel())
					: new BufferedImage(w, h, type);

			Graphics2D g2 = tmp.createGraphics();
			g2.setColor(Color.white);
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.drawImage(ret, 0, 0, w, h, null);
			g2.dispose();

			ret = tmp;
		} while (w != targetWidth || h != targetHeight);

		return ret;
	}

	
	public static byte[] doRemoteImageProcessing(java.io.File theImageFile, String urlString, Integer width, Integer height) throws UnsupportedEncodingException {		

		File file = new File();
		file.setFileName("profilePhoto");
		byte[] theBuffer = null;
		String data = URLEncoder.encode("imagefile", "UTF-8") + "=" + URLEncoder.encode(theImageFile.getPath(), "UTF-8");
		if (width != null) data +="&width="+ String.valueOf(width);
		if (height != null) data +="&height="+ String.valueOf(height);
		
		try {

			logger.info("ImageUtils url..."+urlString);
			URL url = new URL(urlString);			
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type",	"application/x-www-form-urlencoded");
		 	OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();
			
			
			InputStream rd = conn.getInputStream();
			StringBuffer resizedFilePath = new StringBuffer();
			BufferedReader in = new BufferedReader(new InputStreamReader(rd));
			String inputLine;
			
			while ((inputLine = in.readLine()) != null) {
				
				resizedFilePath.append(inputLine);
			}
			
			wr.close();
			rd.close();
			
			theBuffer = readResizedBytes(resizedFilePath);			

		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return theBuffer;
	}

	private static byte[] readResizedBytes(StringBuffer buffer)throws FileNotFoundException, IOException {
		
		byte[] theBuffer;
		java.io.File resizedFile = new java.io.File(buffer.toString());
		InputStream is = new FileInputStream(resizedFile);

		long length = resizedFile.length();
		 // Create the byte array to hold the data
		theBuffer = new byte[(int)length];
		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < theBuffer.length && (numRead=is.read(theBuffer, offset, theBuffer.length-offset)) >= 0) {
		    offset += numRead;
		}
   
		// Ensure all the bytes have been read in
		if (offset < theBuffer.length) {
		    throw new IOException("Could not completely read file "+resizedFile.getName());
		}
		is.close();
		resizedFile.delete();
		
		return theBuffer;
	}	
}