/*
 * MoXie (SysTem128@GMail.Com) 2009-12-12 10:00:30
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * 图片文件辅助类
 * @author MoXie
 */
public class ImageHelper {

    /**
     * 图片尺寸
     */
    public static class ImgSize {

        /**
         * 宽度
         */
        private int width = 0;
        /**
         * 高度
         */
        private int height = 0;

        /**
         * 图片尺寸
         * @param width 宽度
         * @param height    高度
         */
        public ImgSize(int width, int height) {
            this.width = width;
            this.height = height;
        }

        /**
         * 获取高度
         * @return
         */
        public int getHeight() {
            return height;
        }

        /**
         * 获取宽度
         * @return
         */
        public int getWidth() {
            return width;
        }

        /**
         * 设置高度
         * @param height    高度
         */
        public void setHeight(int height) {
            this.height = height;
        }

        /**
         * 设置宽度
         * @param width 宽度
         */
        public void setWidth(int width) {
            this.width = width;
        }

        /**
         * 对比尺寸是否相同
         * @param obj
         * @return
         */
        @Override
        @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            return this.hashCode() == obj.hashCode();
        }

        /**
         * 尺寸hash
         * @return
         */
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 83 * hash + this.width;
            hash = 83 * hash + this.height;
            return hash;
        }

        @Override
        public String toString() {
            return "ImgSize{" + "width=" + width + " height=" + height + '}';
        }
    }

    /**
     * 获取尺寸
     * @param image 源图形
     * @return 图形尺寸
     * @throws IOException
     */
    public static ImgSize size(File image) throws IOException {
        BufferedImage buf = ImageIO.read(image);
        return new ImgSize(buf.getWidth(), buf.getHeight());
    }

    /**
     * 获取适应尺寸
     * @param originImg     源图形
     * @param targetSize  目标固定尺寸
     * @return 依照固定尺寸比例进行缩放的适应尺寸
     * @throws IOException
     */
    public static ImgSize suiteSize(File originImg, ImgSize targetSize) throws IOException {
        return suiteSize(size(originImg), targetSize);
    }

    /**
     * 获取适应尺寸
     * @param originSize  源尺寸
     * @param targetSize  目标固定尺寸
     * @return  依照固定尺寸比例进行缩放的适应尺寸
     */
    public static ImgSize suiteSize(ImgSize originSize, ImgSize targetSize) {
        int originWidth = originSize.getWidth();
        int originHeight = originSize.getHeight();
        int width = targetSize.getWidth();
        int height = targetSize.getHeight();
        // 不需要缩略
        if ((originWidth <= width && originHeight <= height)) {
            return new ImgSize(originWidth, originHeight);
        }


        /**
         * 制取同比例宽高
         */
        float newWidth = 0F;
        float newHeight = 0F;
        float cop = width / (float) height; // 最优宽高比例
        float copOrigin = originWidth / (float) originHeight; // 当前宽高比例
        //
        float copChange = 0F;
        // 按照比例缩略
        if (copOrigin > cop) { // 当前图片较宽
            // 将宽度缩略到限制的最宽
            newWidth = width;
            copChange = newWidth / (float) originWidth;
            newHeight = originHeight * copChange;
        } else {
            newHeight = height;
            copChange = newHeight / (float) originHeight;
            newWidth = originWidth * copChange;
        }
        return new ImgSize((int) newWidth, (int) newHeight);
    }

    /**
     * <pre>
     * 图片缩放
     * 注意：输出尺寸与原尺寸相同时，目标文件不会被建立。
     * </pre>
     * @param originImg     源图片文件
     * @param resizedImg    输出图片文件
     * @param size     输出尺寸
     * @return  生成是否成功
     * @throws IOException
     */
    public static boolean resize(File originImg, File resizedImg, ImgSize size) throws IOException {
        return resize(originImg, resizedImg, size.getWidth(), size.getHeight());
    }

    /**
     * <pre>
     * 图片缩放
     * 注意：输出尺寸与原尺寸相同时，目标文件将不会被建立。
     * </pre>
     * @param originImg     源图片文件
     * @param resizedImg    输出图片文件
     * @param width     输出宽度
     * @param height    输出高度
     * @return  生成是否成功
     * @throws IOException
     */
    public static boolean resize(File originImg, File resizedImg, int width, int height) throws IOException {
        boolean isResized = false;
        FileLocker oflock = new FileLocker(originImg);
        FileLocker rflock = null;
        try {

            oflock.lockRead();

            BufferedImage originBuf = ImageIO.read(originImg);
            {
                if (originBuf.getWidth() == width //
                        && originBuf.getHeight() == height) {
                    return false;
                }
                FileHelper.tryCreate(resizedImg);
                rflock = new FileLocker(resizedImg);
                rflock.lockWrite();
                /**
                 * resize
                 */
                BufferedImage resizedBuf = new BufferedImage(width, height, Transparency.TRANSLUCENT);
                Graphics2D graphics = resizedBuf.createGraphics();
                // 抗锯齿
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.drawImage(originBuf, 0, 0, width, height, new ImageObserver() {

                    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                        return true;
                    }
                });
                graphics.dispose();
                {
                    ImageIO.write(resizedBuf, "png", resizedImg);
                }
                isResized = true;

            }
        } finally {
            oflock.releaseRead();
            if (rflock != null) {
                rflock.releaseWrite();
            }
        }

        return isResized;
    }

    /**
     * 为图片打水印
     * @param originImg     源图片文件
     * @param markedImg     输出图片文件
     * @param markImg       水印文件
     * @param x             水印位置，横坐标
     * @param y             水印位置，纵坐标
     * @param alpha         透明度 0.0f～1.0f
     * @return      生成是否成功
     * @throws IOException
     */
    public static boolean watermark(File originImg, File markedImg, File markImg, int x, int y, float alpha)
            throws IOException {
        FileHelper.tryCreate(markedImg);
        FileLocker oflock = new FileLocker(originImg);
        FileLocker mflock = new FileLocker(markedImg);
        boolean isMarked = false;
        try {
            oflock.lockRead();
            mflock.lockWrite();
            BufferedImage originBuf = ImageIO.read(originImg);
            int width = originBuf.getWidth();
            int height = originBuf.getHeight();
            {
                /**
                 * marked image
                 */
                BufferedImage markedBuf = new BufferedImage(width, height, Transparency.TRANSLUCENT);
                Graphics2D graphics = markedBuf.createGraphics();
                graphics.drawImage(originBuf, 0, 0, width, height, new ImageObserver() {

                    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                        return true;
                    }
                });
                /**
                 * draw mark image
                 */
                BufferedImage markBuf = ImageIO.read(markImg);
                // 抗锯齿
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // 覆盖规则
                graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                graphics.drawImage(markBuf, x, y, markBuf.getWidth(), markBuf.getHeight(), new ImageObserver() {

                    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                        return true;
                    }
                });

                graphics.dispose();
                {
                    ImageIO.write(markedBuf, "png", markedImg);
                }
                isMarked = true;
            }
        } finally {
            oflock.releaseRead();
            mflock.releaseWrite();
        }
        return isMarked;
    }

    /**
     * 在图片上写文字
     * @param originImg     源图片文件
     * @param drawedImg     输出图片文件
     * @param line          要写的文字
     * @param x             文字位置，横坐标
     * @param y             文字位置，纵坐标
     * @param alpha         透明度 0.0f～1.0f
     * @return      生成是否成功
     * @throws IOException
     */
    public static boolean drawLine(File originImg, File drawedImg, char[] line, int x, int y, float alpha) throws IOException {
        return drawLine(originImg, drawedImg, line, x, y, alpha, null);
    }

    /**
     * 在图片上写文字
     * @param originImg     源图片文件
     * @param drawedImg     输出图片文件（自动创建、且覆盖原有文件）
     * @param line          要写的文字
     * @param x             文字位置，横坐标
     * @param y             文字位置，纵坐标
     * @param alpha         透明度 0.0f～1.0f
     * @param handler       在写入字符前对Graphics对象进行操作。如：graphics.setColor(Color.RED)
     * @return      生成是否成功
     * @throws IOException JDK 1.6以下，PNG读取有时会抛出：javax.imageio.IIOException: Error reading PNG metadata
     */
    public static boolean drawLine(File originImg, File drawedImg, char[] line, int x, int y, float alpha, GraphicsHandlAble handler) throws IOException {
        FileHelper.tryCreate(drawedImg);
        FileLocker oflock = new FileLocker(originImg);
        FileLocker dflock = new FileLocker(drawedImg);
        boolean isMarked = false;
        try {
            oflock.lockRead();
            dflock.lockWrite();


            BufferedImage originBuf = ImageIO.read(originImg);
            int width = originBuf.getWidth();
            int height = originBuf.getHeight();
            {
                /**
                 * marked image
                 */
                BufferedImage drawedBuf = new BufferedImage(width, height, Transparency.TRANSLUCENT);
                Graphics2D graphics = drawedBuf.createGraphics();
                graphics.drawImage(originBuf, 0, 0, width, height, new ImageObserver() {

                    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                        return true;
                    }
                });
                /**
                 * write line
                 */
                // 抗锯齿
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // 覆盖规则,透明度
                graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                if (handler != null) {
                    handler.handle(graphics);
                }
                graphics.drawChars(line, 0, line.length, x, y);

                graphics.dispose();
                {
                    ImageIO.write(drawedBuf, "png", drawedImg);
                }
                isMarked = true;
            }
        } finally {
            oflock.releaseRead();
            dflock.releaseWrite();
        }
        return isMarked;
    }

    /**
     *  Graphics操作接口
     */
    public static interface GraphicsHandlAble {

        /**
         * Graphics操作
         * @param graphics
         */
        public void handle(Graphics2D graphics);
    }
}
