package com.zhaoyong.imgtoascii;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageToAscii {

	/**
	 * @param path 图片路径
	 */
	public static void createAsciiPic(final String path) {
		StringBuffer stringBuffer = new StringBuffer();//暂存字符串替换结果
		
		//final String base = "@#&$%*o!;.";// 字符串由复杂到简单
		final String base = "QNH$C7>?!:-.";
		try {
			final BufferedImage image = ImageIO.read(new File(path));
			for (int y = 0; y < image.getHeight(); y += 2) {
				for (int x = 0; x < image.getWidth(); x++) {
					final int pixel = image.getRGB(x, y);
					final int r = (pixel & 0xff0000) >> 16, g = (pixel & 0xff00) >> 8, b = pixel & 0xff;
					final float gray = 0.299f * r + 0.578f * g + 0.114f * b; //计算灰度值
					final int index = Math.round(gray * (base.length() + 1) / 255);
					stringBuffer.append(index >= base.length() ? " " : String.valueOf(base.charAt(index)));
				}
				stringBuffer.append("\n");
			}
			
			//写入txt文件
			writeAsciiPicToTxt("resource/rat.txt", stringBuffer.toString());
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将字符串写入text文件
	 * @param text
	 */
	public static void writeAsciiPicToTxt(String fileName, String text) {
		File file = new File(fileName);
		
		FileOutputStream oStream = null;
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
			oStream = new FileOutputStream(file);
			oStream.write(text.getBytes());
			oStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(oStream != null) {
				try {
					oStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
