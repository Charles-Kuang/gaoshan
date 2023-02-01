package com.ruoyi.common.utils;

import cn.hutool.extra.qrcode.BufferedImageLuminanceSource;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import lombok.extern.slf4j.Slf4j;
import sun.font.FontDesignMetrics;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * 二维码工具
 * @Author:debug (SteadyJack)
 * @Link: weixin-> debug0868  qq-> 1948831260
 * @Date: 2020/11/16 22:38
 **/
@Slf4j
public class QRCodeUtil {


    //CODE_WIDTH：二维码宽度，单位像素
    private static final int CODE_WIDTH = 400;
    //CODE_HEIGHT：二维码高度，单位像素
    private static final int CODE_HEIGHT = 400;
    //FRONT_COLOR：二维码前景色，0x000000 表示黑色
    private static final int FRONT_COLOR = 0x000000;
    //BACKGROUND_COLOR：二维码背景色，0xFFFFFF 表示白色
    //演示用 16 进制表示，和前端页面 CSS 的取色是一样的，注意前后景颜色应该对比明显，如常见的黑白
    private static final int BACKGROUND_COLOR = 0xFFFFFF;

    public static String createCodeToFile(String content) {
        try {
            if (StringUtils.isBlank(content)) {
                return null;
            }
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            File fileBase=new File(filePath);
           if(!fileBase.exists()){
                fileBase.mkdirs();
            }
            //核心代码-生成二维码
            BufferedImage bufferedImage = getBufferedImage(content);
            String fileName = DateUtils.datePath() + "/" + IdUtils.fastUUID() + ".png";
            File absoluteFile = FileUploadUtils.getAbsoluteFile(RuoYiConfig.getUploadPath(), fileName);
            String pathFileName = FileUploadUtils.getPathFileName(RuoYiConfig.getUploadPath(), fileName);
            ImageIO.write(bufferedImage, "png", absoluteFile);
            return "http://localhost:8080"+pathFileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成二维码并输出到输出流, 通常用于输出到网页上进行显示，输出到网页与输出到磁盘上的文件中，区别在于最后一句 ImageIO.write
     * write(RenderedImage im,String formatName,File output)：写到文件中
     * write(RenderedImage im,String formatName,OutputStream output)：输出到输出流中
     * @param content  ：二维码内容
     * @param outputStream ：输出流，比如 HttpServletResponse 的 getOutputStream
     */
    public static void createCodeToOutputStream(String content, OutputStream outputStream) {
        try {
            if (StringUtils.isBlank(content)) {
                return;
            }
            content = content.trim();
            //核心代码-生成二维码
            BufferedImage bufferedImage = getBufferedImage(content);

            //区别就是这一句，输出到输出流中，如果第三个参数是 File，则输出到文件中
            ImageIO.write(bufferedImage, "png", outputStream);

            log.info("二维码图片生成到输出流成功...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //核心代码-生成二维码
    private static BufferedImage getBufferedImage(String content) throws WriterException {

        //com.google.zxing.EncodeHintType：编码提示类型,枚举类型
        Map<EncodeHintType, Object> hints = new HashMap<>();

        //EncodeHintType.CHARACTER_SET：设置字符编码类型
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        //EncodeHintType.ERROR_CORRECTION：设置误差校正
        //ErrorCorrectionLevel：误差校正等级，L = ~7% correction、M = ~15% correction、Q = ~25% correction、H = ~30% correction
        //不设置时，默认为 L 等级，等级不一样，生成的图案不同，但扫描的结果是一样的
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);

        //EncodeHintType.MARGIN：设置二维码边距，单位像素，值越小，二维码距离四周越近
        hints.put(EncodeHintType.MARGIN, 1);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, CODE_WIDTH, CODE_HEIGHT, hints);
        BufferedImage bufferedImage = new BufferedImage(CODE_WIDTH, CODE_HEIGHT, BufferedImage.TYPE_INT_BGR);
        for (int x = 0; x < CODE_WIDTH; x++) {
            for (int y = 0; y < CODE_HEIGHT; y++) {
                bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? FRONT_COLOR : BACKGROUND_COLOR);
            }
        }
        return bufferedImage;
    }

    /*
     * 二维码设置logo
     */
    public static void insertLogoImage(BufferedImage source, Image logo, int logoWidth, int logoHeight) {
        Graphics2D graph = source.createGraphics();
        int qrWidth = source.getWidth();
        int qrHeight = source.getHeight();
        int x = (qrWidth - logoWidth) / 2;
        int y = (qrHeight - logoHeight) / 2;
        graph.drawImage(logo, x, y, logoWidth, logoHeight, null);
        Shape shape = new RoundRectangle2D.Float(x, y, logoWidth, logoHeight, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /*
     * 将文明说明增加到二维码上
     */
    public static BufferedImage textToImage(String str, int width, int height, int fontSize) {
        BufferedImage textImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) textImage.getGraphics();
        //开启文字抗锯齿
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, width, height);
        g2.setPaint(Color.BLACK);
        FontRenderContext context = g2.getFontRenderContext();
        Font font = new Font("微软雅黑", Font.PLAIN, fontSize);
        g2.setFont(font);
        LineMetrics lineMetrics = font.getLineMetrics(str, context);
        FontMetrics fontMetrics = FontDesignMetrics.getMetrics(font);
        float offset = (width - fontMetrics.stringWidth(str)) / 2;
        float y = (height + lineMetrics.getAscent() - lineMetrics.getDescent() - lineMetrics.getLeading()) / 2;
        g2.drawString(str, (int) offset, (int) y);
        return textImage;
    }

    /*
     * 解析二维码
     */
    public static String decode(File file, DecodeHintType cherSet) throws Exception {
        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Hashtable<DecodeHintType, java.io.Serializable> hints = new Hashtable<DecodeHintType, java.io.Serializable>();
        hints.put(DecodeHintType.CHARACTER_SET, cherSet);
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
        Result result = new MultiFormatReader().decode(bitmap, hints);
        return result.getText();
    }

    public static void main(String[] args) {
        String imagePath="E:\\shFiles\\QRCode/20221213140632.png";
        File file=new File(imagePath);
        String decode = "GsAddressHouse";
        try {
            decode = QRCodeUtil.decode(file, DecodeHintType.CHARACTER_SET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(decode);
    }
}