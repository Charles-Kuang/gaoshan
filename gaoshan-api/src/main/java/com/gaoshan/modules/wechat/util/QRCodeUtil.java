package com.gaoshan.modules.wechat.util;//package com.gaoshan.modules.wechat.util;
//
//import java.awt.Graphics2D;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.util.HashMap;
//import java.util.Hashtable;
//import java.util.Map;
//
//import javax.imageio.ImageIO;
//import javax.servlet.http.HttpServletResponse;
//
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.EncodeHintType;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.WriterException;
//import com.google.zxing.common.BitMatrix;
//import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
//
///**
// * @Author zufeichao
// * @ProjectName web-interaction-center
// * @Description TODO
// * @Date 2019-07-29 10:14
// * @T: QrCodeUtil
// **/
//public class QRCodeUtil {
//
//    private static final int QRCOLOR = 0xFF000000; // 默认是黑色
//    private static final int BGWHITE = 0xFFFFFFFF; // 背景颜色
//    private static final int WIDTH = 400; // 二维码宽
//    private static final int HEIGHT = 400; // 二维码高
//
//    // 用于设置QR二维码参数
//    private static Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>() {
//        private static final long serialVersionUID = 1L;
//        {
//            put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
//            put(EncodeHintType.CHARACTER_SET, "utf-8");// 设置编码方式
//        }
//    };
//
//    // 生成带logo的二维码图片
//    public static void drawLogoQRCode(File logoFile, String qrUrl, String note, HttpServletResponse response) {
//    	OutputStream os=null;
//        try {
//        	os=response.getOutputStream();
//        	ImageIO.setUseCache(false);
//            // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
////            BitMatrix bm = multiFormatWriter.encode(qrUrl, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
////            BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
//            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
//    		//设置容错级别最高
//            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
//            //设置字符编码为utf-8
//            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//            //二维码空白区域,最小为0也有白边,只是很小,最小是6像素左右
//            hints.put(EncodeHintType.MARGIN, 1);
//
//            BitMatrix bitMatrix = new MultiFormatWriter().encode(qrUrl, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
//            //转为图片对象格式
//            BufferedImage image = toBufferedImage(bitMatrix);
//            // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
//            int width = image.getWidth();
//            int height = image.getHeight();
//            if (logoFile !=null && logoFile.exists()) {
//                // 构建绘图对象
//                Graphics2D g = image.createGraphics();
//                // 读取Logo图片
//                BufferedImage logo = ImageIO.read(logoFile);
//                // 开始绘制logo图片
//                g.drawImage(logo, width * 2 / 5, height * 2 / 5, width * 2 / 10, height * 2 / 10, null);
//                g.dispose();
//                logo.flush();
//            }
//            // 自定义文本描述
//            image.flush();
//            ImageIO.write(image, "png", os);
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }finally {
//        	try {
//        		if(null!=os){
//        			os.close();
//        			os.flush();
//        			os=null;
//        		}
//        		System.gc();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//    }
//    /*
//    * image流数据处理
//    */
//   public static BufferedImage toBufferedImage(BitMatrix matrix) {
//       int width = matrix.getWidth();
//       int height = matrix.getHeight();
//       BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//       for (int x = 0; x < width; x++) {
//           for (int y = 0; y < height; y++) {
//               image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
//           }
//       }
//       return image;
//   }
//    public static void main(String[] args) throws WriterException {
//        File logoFile = new File("C:/Users/luoqy/Desktop/文澜项目资料/开发资料/微信公众号资料/logo.png"); //logo
//        File QrCodeFile = new File("D:\\123.png"); //二维码生成地址以及名称格式
//        String url = "https://www.baidu.com/"; // 扫描二维码进入网址
//        String note = "我的二维码";    //标题
//    }
//
//}
//
