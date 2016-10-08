package com.myownmotivator.processor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageProcessor extends HttpServlet {

	
	
	private static final long serialVersionUID = 1L;

	public  static int height = 130;

	public  static int width = 130;

	public final static int maxRatio = 135;

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException {

		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException {

		String imageFile = req.getParameter("imagefile");
		System.out.println("...Entering ImageProcessor..........");
		

		System.out.println("File name: "+imageFile);
		File image = null;
		ImageInputStream iis = null;
		ImageReader imageReader = null;

		boolean seekForwardOnly = true;
		boolean ignoreMetadata = false;
		BufferedImage srcImage = null;

		/*
		try {
			
			image = new File(imageFile);
			int pos = image.getName().lastIndexOf(".");
			String extension = image.getName().substring(pos).replace(".", "");
			ImageIO.setUseCache(false);
			Iterator<ImageReader> readerIter = ImageIO.getImageReadersByFormatName(extension);
			imageReader = readerIter.next();

			System.out.println("Entering resizing...." + imageFile);
			iis = ImageIO.createImageInputStream(new BufferedInputStream(new FileInputStream(image)));
			imageReader.setInput(iis, seekForwardOnly, ignoreMetadata);
			srcImage = imageReader.read(0);
			System.out.println("ImageProcessor image reduced...." + imageFile);
			image.delete();
			
		} catch (OutOfMemoryError e) {

			System.out.println("Out of memory brother......" + e.getMessage());

		} catch (Exception e) {

			System.err.println(e.getMessage());

		} finally {

			if (iis != null) {
				try {
					iis.flush();
					iis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		ImageWriter jpgWriter = null;
		ServletOutputStream out = null;
		ByteArrayOutputStream baos = null;
		ImageOutputStream imgOut = null;
		IIOImage imageIIO = null;

		try {

			srcImage = ImageProcessor.cropImage(srcImage);
			if (req.getParameter("width") != null) width = Integer.parseInt(req.getParameter("width"));
			if (req.getParameter("height") != null) height = Integer.parseInt(req.getParameter("height"));
			srcImage = ImageProcessor.scaleAndThumbnailImage(width, height,	srcImage);

			jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
			JPEGImageWriteParam iwp = (JPEGImageWriteParam) jpgWriter.getDefaultWriteParam();
			iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			iwp.setCompressionQuality(.95f);
			iwp.setOptimizeHuffmanTables(true);

			baos = new ByteArrayOutputStream(1000);
			imgOut = new MemoryCacheImageOutputStream(baos);
			jpgWriter.setOutput(imgOut);
			imageIIO = new IIOImage(srcImage, null, null);
			srcImage.flush();
			jpgWriter.write(null, imageIIO, iwp);

			File resizedFile = File.createTempFile("_resized", ".jpg");
			System.out.println(resizedFile.getName());
			FileOutputStream fos = new  FileOutputStream(resizedFile);
			fos.write(baos.toByteArray());
			fos.flush();
			fos.close();
			
			ServletOutputStream out = null;
			out = res.getOutputStream();
			out.print("HELLO TERERE");		

		} catch (Exception e) {

			System.err.println(e.getMessage());

		} finally {

			try {

				imgOut.flush();
				imgOut.close();
				out.flush();
				out.close();
				System.gc();
				width = 130;
				height = 130;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
		
		ServletOutputStream out = null;
		try {
			out = res.getOutputStream();
			out.print("HELLO TERERE");		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static BufferedImage cropImage(BufferedImage thumbImage) {

		int y = 0;
		int x = 0;
		int scale = 0;
		int imageWidth = thumbImage.getWidth();
		int imageHeight = thumbImage.getHeight();
		if (imageWidth > imageHeight) {
			x = (imageWidth - imageHeight) / 2;
			y = 0;
			scale = imageHeight;

		} else if (imageHeight > imageWidth) {
			y = (imageHeight - imageWidth) / 2;
			x = 0;
			scale = imageWidth;

		} else if (imageWidth == imageHeight) {

			return thumbImage;
		}
		thumbImage = thumbImage.getSubimage(x, y, scale, scale);

		return thumbImage;
	}

	public static BufferedImage scaleAndThumbnailImage(int imageWidth,
			int imageHeight, BufferedImage i_bufferedImage) throws Exception {
		float dividerVariable = 0;

		if (imageWidth > imageHeight) {

			dividerVariable = (float) imageWidth / maxRatio;
			imageWidth = Math.round((float) imageWidth / dividerVariable);
			imageHeight = Math.round((float) imageHeight / dividerVariable);

		} else if (imageHeight > imageWidth) {

			dividerVariable = (float) imageHeight / maxRatio;
			imageWidth = Math.round((float) imageWidth / dividerVariable);
			imageHeight = Math.round((float) imageHeight / dividerVariable);
		}

		i_bufferedImage = createThumbnail(i_bufferedImage, imageWidth,
				imageHeight);

		return i_bufferedImage;
	}

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

			BufferedImage tmp = new BufferedImage(w, h,
					BufferedImage.TYPE_INT_RGB);
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

}
