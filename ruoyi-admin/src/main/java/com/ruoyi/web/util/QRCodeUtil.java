package com.ruoyi.web.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author DurantSimpson
 * @desc 二维码工具类
 * @create 2018-08-16 13:46
 **/
public class QRCodeUtil {

    private static final String CHARSET = "UTF-8"; // 字符集格式

    public static final String FORMAT_NAME = "png"; // 二维码图片格式

    private static final int QRCODE_SIZE = 1500; // 二维码尺寸

    private static final int WIDTH = 300; // LOGO宽度

    private static final int HEIGHT = 300; // LOGO高度

    /**
     * 生成图像
     */
    public static void encode(String content) {
        String filePath = "D://";
        String fileName = "zxing.png";

        int width = 200; // 图像宽度
        int height = 200; // 图像高度
        String format = "png";// 图像类型
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                    BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
            Path path = FileSystems.getDefault().getPath(filePath, fileName);
            MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像
            System.out.println("输出成功.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成二维码图片并转换成base64编码
     * @param content
     * @return
     */
    public static String encodeToBase64(String content) {
        int width = 200; // 图像宽度
        int height = 200; // 图像高度
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(MatrixToImageWriter.toBufferedImage(bitMatrix),"png",outputStream);
            String imgsrc = Base64.encodeBase64String(outputStream.toByteArray());
            return "data:image/png;base64,"+imgsrc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 解析图像
     */
    public static void decode() {
        String filePath = "D://zxing.png";
        BufferedImage image;
        try {
            image = ImageIO.read(new File(filePath));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
            System.out.println("图片中内容:" + result.getText());
            System.out.println("encode： " + result.getBarcodeFormat());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        encode("现在心情有没有好点");
        //decode();
    }

    /**
     * 生成二维码图片
     * @param content
     * @param title
     * @return
     * @throws Exception
     */
    public static BufferedImage createImage(String content, String title) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 2);


        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int h = height;
        if (StringUtils.isNotBlank(title)){
            h = height + 150;
        }
        BufferedImage image = new BufferedImage(width, h, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        ClassPathResource classPathResource = new ClassPathResource("/static/logo.png");//位于resources目录下
        insertImageAndTitle(image, classPathResource.getInputStream(), true, title); // 生成的二维码中添加logo
        return image;
    }

    /**
     * 二维码中添加LOGO和标题
     * @param source
     * @param inputStream
     * @param needCompress 是否压缩logo  true/false
     * @param title 标题
     * @throws Exception
     */
    public static void insertImageAndTitle(BufferedImage source, InputStream inputStream, boolean needCompress, String title) throws Exception {
        Image src = ImageIO.read(inputStream);
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
// 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);

        //Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        //graph.setStroke(new BasicStroke(3f));
        //graph.draw(shape);
        //添加标题
        if (StringUtils.isNotBlank(title)){
            Font font = new Font(null, Font.BOLD, 80);
            graph.setFont(font);
            FontMetrics metrics = new FontMetrics(font) {};
            Rectangle2D bounds = metrics.getStringBounds(title,null);
            int tx = (int) ((QRCODE_SIZE - bounds.getWidth()) / 2);
            graph.drawString(title,tx,QRCODE_SIZE+100);
        }
        graph.dispose();
    }

}
