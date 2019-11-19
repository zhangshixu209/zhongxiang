package com.chmei.nzbcommon.qr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;

import org.apache.log4j.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;


/**
 * @author zcf  
 * @Date:2019年5月15日 上午11:35:09
 * @version 二维码生成工具类,可以包含log内嵌和打印水印
 */
public class QrCodeUtil {
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		String url = "http://www.baidu.com";
		String path = FileSystemView.getFileSystemView().getHomeDirectory() + File.separator + "testQrcode";
		String fileName = "temp.jpg";
		createQrCode(url, path, fileName);
	}


    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(QrCodeUtil.class);
    
	/**
	 * 二维码生成工具入口方法
	 * @param url
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static String createQrCode(String url, String path, String fileName) {
		try {
			Map<EncodeHintType, String> hints = new HashMap<>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 400, 400, hints);
			File file = new File(path, fileName);
			if (file.exists()
					|| ((file.getParentFile().exists() || file.getParentFile().mkdirs()) && file.createNewFile())) {
				writeToFile(bitMatrix, "jpg", file);
				System.out.println("搞定：" + file);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	static public void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format " + format + " to " + file);
		}
	}

	static public void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format " + format);
		}
	}

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	private static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

}
