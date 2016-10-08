package com.crowdscanner.controller.utils;


import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.mchange.util.AssertException;
import com.myownmotivator.model.User;
import com.myownmotivator.model.profile.Profile;

public class ImageUtils implements Serializable {

	/**
	 * 
	 */
	final static Logger logger = Logger.getLogger(ImageUtils.class);
	
	private static final long serialVersionUID = 1L;

	public final static int height = 125;

	public final static int width = 125;
	
	public final static int maxRatio = 130;	
	
	
	public static List<com.myownmotivator.model.profile.File> updateFileContent(byte[] imageContent,
			List<com.myownmotivator.model.profile.File> retrievedFiles) {

		com.myownmotivator.model.profile.File  updateFile = new com.myownmotivator.model.profile.File();
		updateFile.setFileName("profilePhoto");
		updateFile.setFileContent(imageContent);		
		
		for (Iterator<com.myownmotivator.model.profile.File> it = retrievedFiles.iterator(); it.hasNext();) {

			com.myownmotivator.model.profile.File file = it.next();
			if (file.getFileName().equalsIgnoreCase("profilePhoto")) {
				updateFile = file;
				updateFile.setFileContent(imageContent);
				it.remove();
				break;
			}
		}

		retrievedFiles.add(updateFile);		

		return retrievedFiles;
	}
	
	
	/**
	 * Processing of bite for the image
	 * 
	 * @param imageContent
	 * @return
	 * @throws Exception 
	 */
	public static byte[] processScalingImage(File imageFile) throws Exception {	
		
		logger.setLevel(Level.INFO);
		logger.info("initialize buffering...");
		BufferedImage srcImage = null;
		srcImage = ImageIO.read(imageFile);
		logger.info("image buffer read...");
	    srcImage = cropImage(srcImage);	    
	    srcImage = scaleAndThumbnailImage(width, height,srcImage);
		logger.info("scaling..done..");
	    
		ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
		ImageWriteParam iwp = jpgWriter.getDefaultWriteParam();
		iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		iwp.setCompressionQuality(.95f);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
		ImageOutputStream imgOut = new MemoryCacheImageOutputStream(baos);		
		jpgWriter.setOutput(imgOut);
		IIOImage image = new IIOImage(srcImage, null, null);
		srcImage.flush();
		jpgWriter.write(null, image, iwp);
		
		imgOut.flush();
		byte[] scaledBytes = baos.toByteArray();		
		imgOut.close();
		
		return scaledBytes;
	}

	private static BufferedImage scaleAndThumbnailImage(int imageWidth, int imageHeight, BufferedImage i_bufferedImage) throws Exception {
		float dividerVariable = 0;
		
	    if (imageWidth > imageHeight) {
			
	    	dividerVariable = (float)imageWidth / maxRatio;
	    	imageWidth = Math.round((float)imageWidth / dividerVariable);
	    	imageHeight = Math.round((float)imageHeight / dividerVariable);

		} else if (imageHeight > imageWidth) {
			
			dividerVariable = (float)imageHeight / maxRatio;
			imageWidth = Math.round((float)imageWidth / dividerVariable);
	    	imageHeight = Math.round((float)imageHeight / dividerVariable);
		}
	    
	    i_bufferedImage = ImageUtilsNewVersion.createThumbnail(i_bufferedImage, imageWidth, imageHeight);
	    
	    return i_bufferedImage;
	}

	public static BufferedImage realScaling(byte[] abyte) {

		Image imageFile = Toolkit.getDefaultToolkit().createImage(abyte);
		MediaTracker mediaTracker = new MediaTracker(new Container());
		mediaTracker.addImage(imageFile, 0);
		BufferedImage image = null;
		try {

			mediaTracker.waitForID(0);
			int imageWidth = imageFile.getWidth(null);
			int imageHeight = imageFile.getHeight(null);

			image = new BufferedImage(imageWidth, imageHeight,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = image.createGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			graphics2D.drawImage(imageFile, 0, 0, imageWidth, imageHeight, null);
			graphics2D.dispose();
			image = cropImage(image);
			int w = image.getWidth();
			int h = image.getHeight();
			int type = (image.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
					: BufferedImage.TYPE_INT_ARGB;
			do {
				if (w > width) {
					w /= 2;
					if (w < width) {
						w = width;
					}
				}
				if (h > height) {
					h /= 2;
					if (h < height) {
						h = height;
					}
				}

				BufferedImage tmp = new BufferedImage(w, h, type);
				Graphics2D g2 = tmp.createGraphics();
				g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_BICUBIC);
				g2.setRenderingHint(RenderingHints.KEY_RENDERING,
						RenderingHints.VALUE_RENDER_QUALITY);
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				g2.drawImage(image, 0, 0, w, h, null);
				g2.dispose();

				image = tmp;
			} while (w != width || h != height);

			return image;

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return image;
	}

	public static BufferedImage blurImage(BufferedImage image) {
		float ninth = 1.0f / 9.0f;
		float[] blurKernel = { ninth, ninth, ninth, ninth, ninth, ninth, ninth,
				ninth, ninth };

		Map map = new HashMap();

		map.put(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);

		map.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		map.put(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		RenderingHints hints = new RenderingHints(map);
		BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, blurKernel),
				ConvolveOp.EDGE_NO_OP, hints);
		return op.filter(image, null);
	}

	private static BufferedImage cropImage(BufferedImage thumbImage) {

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

	public static void verifyContentType(String i_contentType)
			throws AssertException {

		MimeTypes.SearchMIME.searchMimeType(i_contentType);

	}

	public enum MimeTypes {

		ImageGIF, ImageJPEG, ImageIEJPG, ImagePNG, ImageTIFF, ImageBMP, SearchMIME;

		public String getDescription() {
			switch (this) {
			case ImageGIF:
				return "image/gif";
			case ImageJPEG:
				return "image/jpeg";
			case ImageIEJPG:
				return "image/pjpeg";
			case ImagePNG:
				return "image/png";
			case ImageTIFF:
				return "image/tiff";
			case ImageBMP:
				return "image/bmp";
			default:
				return "Unknown MimeType";
			}
		}

		public void searchMimeType(String mimeType) throws AssertException {

			MimeTypes[] mimeTypeArray = MimeTypes.values();
			boolean mimeExists = false;

			for (int i = 0; i < mimeTypeArray.length; i++) {

				MimeTypes mimeTypeObject = mimeTypeArray[i];

				if (mimeTypeObject.getDescription().equalsIgnoreCase(mimeType)) {
					mimeExists = true;
					break;
				}
			}

			if (!mimeExists) {

				throw new AssertException("The type " + mimeType
						+ " is not a valid type");

			}
		}
	}

	/**
	 * gets image trough iterating over the objects
	 */
	public static void setImageID(User user) {
		Profile profile = user.getProfile();
		List<com.myownmotivator.model.profile.File> files = profile.getFiles();

		for (com.myownmotivator.model.profile.File file : files) {

			if (file.getFileName().equalsIgnoreCase("profilePhoto")) {

				user.setImageID(file.getId());

			}
		}

	}

	private final String IMAGE_MALE = "defaultMale";

	private final String IMAGE_FEMALE = "defaultFemale";

}
