/*
 * MoXie (SysTem128@GMail.Com) 2009-1-18 23:40:16
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.EnvInfo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class EnvInfoTest {

    /**
     *
     */
    public EnvInfoTest() {
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
    }

    /**
     * Test of getJavaVersion method, of class EnvInfo.
     */
    @Test
    public void testGetJavaVersion() {
        System.out.println("getJavaVersion");

        String result = EnvInfo.getJavaVersion();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getJavaVendor method, of class EnvInfo.
     */
    @Test
    public void testGetJavaVendor() {
        System.out.println("getJavaVendor");

        String result = EnvInfo.getJavaVendor();
        if (result.length() < 1) {
            fail();
        }
    }

    /**
     * Test of getJavaVendorUrl method, of class EnvInfo.
     */
    @Test
    public void testGetJavaVendorUrl() {
        System.out.println("getJavaVendorUrl");

        String result = EnvInfo.getJavaVendorUrl();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getJavaHome method, of class EnvInfo.
     */
    @Test
    public void testGetJavaHome() {
        System.out.println("getJavaHome");

        String result = EnvInfo.getJavaHome();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getJavaVMSpecificationVersion method, of class EnvInfo.
     */
    @Test
    public void testGetJavaVMSpecificationVersion() {
        System.out.println("getJavaVMSpecificationVersion");

        String result = EnvInfo.getJavaVMSpecificationVersion();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getJavaVMSpecificationVendor method, of class EnvInfo.
     */
    @Test
    public void testGetJavaVMSpecificationVendor() {
        System.out.println("getJavaVMSpecificationVendor");

        String result = EnvInfo.getJavaVMSpecificationVendor();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getJavaVMSpecificationName method, of class EnvInfo.
     */
    @Test
    public void testGetJavaVMSpecificationName() {
        System.out.println("getJavaVMSpecificationName");

        String result = EnvInfo.getJavaVMSpecificationName();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getJavaVMVersion method, of class EnvInfo.
     */
    @Test
    public void testGetJavaVMVersion() {
        System.out.println("getJavaVMVersion");

        String result = EnvInfo.getJavaVMVersion();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getJavaVMVendor method, of class EnvInfo.
     */
    @Test
    public void testGetJavaVMVendor() {
        System.out.println("getJavaVMVendor");

        String result = EnvInfo.getJavaVMVendor();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getJavaVMName method, of class EnvInfo.
     */
    @Test
    public void testGetJavaVMName() {
        System.out.println("getJavaVMName");

        String result = EnvInfo.getJavaVMName();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getJavaSpecificationVersion method, of class EnvInfo.
     */
    @Test
    public void testGetJavaSpecificationVersion() {
        System.out.println("getJavaSpecificationVersion");

        String result = EnvInfo.getJavaSpecificationVersion();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getJavaSpecificationVendor method, of class EnvInfo.
     */
    @Test
    public void testGetJavaSpecificationVendor() {
        System.out.println("getJavaSpecificationVendor");

        String result = EnvInfo.getJavaSpecificationVendor();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getJavaSpecificationName method, of class EnvInfo.
     */
    @Test
    public void testGetJavaSpecificationName() {
        System.out.println("getJavaSpecificationName");

        String result = EnvInfo.getJavaSpecificationName();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getJavaClassVersion method, of class EnvInfo.
     */
    @Test
    public void testGetJavaClassVersion() {
        System.out.println("getJavaClassVersion");

        String result = EnvInfo.getJavaClassVersion();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getJavaClassPath method, of class EnvInfo.
     */
    @Test
    public void testGetJavaClassPath() {
        System.out.println("getJavaClassPath");

        String result = EnvInfo.getJavaClassPath();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getJavaLibraryPath method, of class EnvInfo.
     */
    @Test
    public void testGetJavaLibraryPath() {
        System.out.println("getJavaLibraryPath");

        String result = EnvInfo.getJavaLibraryPath();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getJavaIoTmpdir method, of class EnvInfo.
     */
    @Test
    public void testGetJavaIoTmpdir() {
        System.out.println("getJavaIoTmpdir");

        String result = EnvInfo.getJavaIoTmpdir();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getJavaCompiler method, of class EnvInfo.
     */
    @Test
    public void testGetJavaCompiler() {
        System.out.println("getJavaCompiler");
        String result = EnvInfo.getJavaCompiler();
        if (result != null && result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getJavaExtDirs method, of class EnvInfo.
     */
    @Test
    public void testGetJavaExtDirs() {
        System.out.println("getJavaExtDirs");

        String result = EnvInfo.getJavaExtDirs();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getOsName method, of class EnvInfo.
     */
    @Test
    public void testGetOsName() {
        System.out.println("getOsName");

        String result = EnvInfo.getOsName();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getOsArch method, of class EnvInfo.
     */
    @Test
    public void testGetOsArch() {
        System.out.println("getOsArch");

        String result = EnvInfo.getOsArch();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getOsVersion method, of class EnvInfo.
     */
    @Test
    public void testGetOsVersion() {
        System.out.println("getOsVersion");

        String result = EnvInfo.getOsVersion();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getFileSeparator method, of class EnvInfo.
     */
    @Test
    public void testGetFileSeparator() {
        System.out.println("getFileSeparator");

        String result = EnvInfo.getFileSeparator();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getPathSeparator method, of class EnvInfo.
     */
    @Test
    public void testGetPathSeparator() {
        System.out.println("getPathSeparator");

        String result = EnvInfo.getPathSeparator();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getLineSeparator method, of class EnvInfo.
     */
    @Test
    public void testGetLineSeparator() {
        System.out.println("getLineSeparator");

        String result = EnvInfo.getLineSeparator();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getUserName method, of class EnvInfo.
     */
    @Test
    public void testGetUserName() {
        System.out.println("getUserName");

        String result = EnvInfo.getUserName();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getUserHome method, of class EnvInfo.
     */
    @Test
    public void testGetUserHome() {
        System.out.println("getUserHome");

        String result = EnvInfo.getUserHome();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getUserDir method, of class EnvInfo.
     */
    @Test
    public void testGetUserDir() {
        System.out.println("getUserDir");

        String result = EnvInfo.getUserDir();
        if (result.length() < 1) {
            fail();
        }


    }

    /**
     * Test of getFileEncoding method, of class EnvInfo.
     */
    @Test
    public void testGetFileEncoding() {
        System.out.println("getFileEncoding");
        String result = EnvInfo.getFileEncoding();
        if (result.length() < 1) {
            fail();
        }
    }

    /**
     * Test of getLanguage method, of class EnvInfo.
     */
    @Test
    public void testGetLanguage() {
        System.out.println("getLanguage");
        String result = EnvInfo.getLanguage();
        if (result.length() < 1) {
            fail();
        }
    }
}
