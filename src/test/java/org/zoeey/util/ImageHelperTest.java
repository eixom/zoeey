/*
 * MoXie (SysTem128@GMail.Com) 2009-12-12 10:49:34
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.common.TestUtil;
import org.zoeey.common.ZObject;
import org.zoeey.util.ImageHelper.ImgSize;
import static org.junit.Assert.*;

/**
 *
 * @author MoXie
 */
public class ImageHelperTest {

    /**
     *
     */
    public ImageHelperTest() {
    }

    /**
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    /**
     *
     * @throws Exception
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     *
     */
    @Before
    public void setUp() {
    }

    /**
     *
     */
    @After
    public void tearDown() {
        File tempDir = new File(TestUtil.getResource("imageHelper/temp"));
        FileHelper.tryDelete(tempDir, true);
        tempDir.deleteOnExit();

    }

    /**
     * Test of resize method, of class ImageHelper.
     * @throws Exception
     */
    @Test
    public void testResize() throws Exception {
        System.out.println("resize");
        File originImg = new File(TestUtil.getResourceDir().concat("/imageHelper/rendering.jpg"));
        File resizedImg_force_small = new File(TestUtil.getResourceDir().concat("/imageHelper/temp/rendering_resized-froce-small.jpg"));
        File resizedImg_force_big = new File(TestUtil.getResourceDir().concat("/imageHelper/temp/rendering_resized-froce-big.jpg"));
        File resizedImg_suite_small = new File(TestUtil.getResourceDir().concat("/imageHelper/temp/rendering_resized-suite-small.jpg"));
        File resizedImg_suite_big = new File(TestUtil.getResourceDir().concat("/imageHelper/temp/rendering_resized-suite-big.jpg"));
        ImgSize targetSize = null;
        /**
         * force small
         */
        int width = 300;
        int height = 200;
        boolean isResized = ImageHelper.resize(originImg, resizedImg_force_small, width, height);
        assertTrue(isResized);
        /**
         * force big
         */
        width = 1300;
        height = 1200;
        isResized = ImageHelper.resize(originImg, resizedImg_force_big, width, height);
        assertTrue(isResized);
        /**
         * suite small
         */
        width = 300;
        height = 200;
        targetSize = new ImgSize(width, height);
        ImgSize suitSize = ImageHelper.suiteSize(originImg, targetSize);
        assertTrue(suitSize.equals(new ImgSize(200, 200)));
        assertTrue(ImageHelper.resize(originImg, resizedImg_suite_small, targetSize));
        /**
         * suite bug
         */
        width = 1300;
        height = 1200;
        targetSize = new ImgSize(width, height);
        suitSize = ImageHelper.suiteSize(originImg, targetSize);

        assertTrue(suitSize.equals(new ImgSize(300, 300)));
        FileHelper.tryDelete(resizedImg_suite_big);
        assertFalse(ImageHelper.resize(originImg, resizedImg_suite_big, suitSize));
        if (resizedImg_suite_big.exists()) {
            fail("文件不需要被建立。");
        }
        /**
         * 
         */
        targetSize = new ImgSize(10, 20);
        suitSize = ImageHelper.suiteSize(new ImgSize(396, 532), targetSize);
        assertTrue(suitSize.equals(new ImgSize(10, 13)));
        //
        targetSize = new ImgSize(10, 20);
        suitSize = ImageHelper.suiteSize(new ImgSize(16, 16), targetSize);
        assertTrue(suitSize.equals(new ImgSize(10, 10)));
        //
        targetSize = new ImgSize(10, 10);
        suitSize = ImageHelper.suiteSize(new ImgSize(16, 16), targetSize);
        assertTrue(suitSize.equals(new ImgSize(10, 10)));
        //
        targetSize = new ImgSize(160, 160);
        suitSize = ImageHelper.suiteSize(new ImgSize(16, 16), targetSize);
        assertTrue(suitSize.equals(new ImgSize(16, 16)));
        //
        targetSize = new ImgSize(123, 678);
        suitSize = ImageHelper.suiteSize(new ImgSize(200, 300), targetSize);
        assertTrue(suitSize.equals(new ImgSize(123, 184)));


        /**
         *
         */
        originImg = new File(TestUtil.getResourceDir().concat("/imageHelper/alpha.png"));
        resizedImg_suite_small = new File(TestUtil.getResourceDir().concat("/imageHelper/temp/alpha_resized.png"));
        width = 11;
        height = 11;
        targetSize = new ImgSize(width, height);
        assertTrue(ImageHelper.suiteSize(originImg, targetSize).equals(new ImgSize(11, 11)));

        assertTrue(ImageHelper.resize(originImg, resizedImg_suite_small, targetSize));
        assertTrue(ImageHelper.size(resizedImg_suite_small).equals(targetSize));
    }

    /**
     * Test of resize method, of class ImageHelper.
     * @throws Exception
     */
    @Test
    public void testWatermark() throws Exception {
        System.out.println("watermark");
        File originImg = new File(TestUtil.getResourceDir().concat("/imageHelper/rendering.jpg"));
        File markedImg = new File(TestUtil.getResourceDir().concat("/imageHelper/temp/rendering_marked.jpg"));
        File markImg = new File(TestUtil.getBugPng());
        int x = 188;
        int y = 180;
        float alpha = 1.0f;
        boolean expResult = true;
        boolean result = ImageHelper.watermark(originImg, markedImg, markImg, x, y, alpha);
        assertEquals(result, expResult);
        /**
         * 
         */
        originImg = new File(TestUtil.getResourceDir().concat("/imageHelper/alpha.png"));
        markedImg = new File(TestUtil.getResourceDir().concat("/imageHelper/temp/alpha_marked.png"));
        x = 188;
        y = 180;
        alpha = 1.0f;
        expResult = true;
        result = ImageHelper.watermark(originImg, markedImg, markImg, x, y, alpha);
        assertEquals(result, expResult);


    }

    /**
     * Test of drawLine method, of class ImageHelper.
     * @throws Exception
     */
    @Test
    public void testDrawLine() throws Exception {
        System.out.println("drawLine");
        File originImg = new File(TestUtil.getResourceDir().concat("/imageHelper/rendering.jpg"));
        File writedImg = new File(TestUtil.getResourceDir().concat("/imageHelper/temp/rendering_writed.jpg"));
        char[] line = "大家好我是一段中文".toCharArray();
        int x = 50;
        int y = 50;
        float alpha = 0.9F;
        boolean expResult = true;
        boolean result = ImageHelper.drawLine(originImg, writedImg, line, x, y //
                , alpha, new ImageHelper.GraphicsHandlAble() {

            public void handle(Graphics2D graphics) {
                graphics.setColor(Color.BLACK);
                Font font = new Font("仿宋", Font.BOLD, 20);
                graphics.setFont(font);
            }
        });
        assertEquals(expResult, result);
        /**
         *
         */
        if (ZObject.conv(EnvInfo.getJavaVersion()).toDouble() < 1.6D) {
            System.out.println("JDK 1.6以下，PNG读取有时会抛出：javax.imageio.IIOException: Error reading PNG metadata");
            return;
        }

        originImg = new File(TestUtil.getResourceDir().concat("/imageHelper/z.png"));
        writedImg = new File(TestUtil.getResourceDir().concat("/imageHelper/temp/z_writed.png"));
        line = "Zoeey".toCharArray();
        x = 12;
        y = 70;
        alpha = 0.9f;
        expResult = true;
        result = ImageHelper.drawLine(originImg, writedImg, line, x, y, alpha, new ImageHelper.GraphicsHandlAble() {

            public void handle(Graphics2D graphics) {
                graphics.setColor(Color.RED);
                Font font = new Font("仿宋", Font.BOLD, 50);
                graphics.setFont(font);

            }
        });
        assertEquals(expResult, result);
        /**
         *
         */
        originImg = new File(TestUtil.getResourceDir().concat("/imageHelper/alpha.png"));
        writedImg = new File(TestUtil.getResourceDir().concat("/imageHelper/temp/alpha_writed.png"));
        line = "Zoeey".toCharArray();
        x = 1;
        y = 11;
        alpha = 0.9F;
        expResult = true;
        result = ImageHelper.drawLine(originImg, writedImg, line, x, y, alpha, new ImageHelper.GraphicsHandlAble() {

            public void handle(Graphics2D graphics) {
                graphics.setColor(Color.RED);
                Font font = new Font("仿宋", Font.BOLD, 15);
                graphics.setFont(font);

            }
        });
        assertEquals(expResult, result);
    }
}
