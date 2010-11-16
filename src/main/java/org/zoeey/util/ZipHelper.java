/*
 * MoXie (SysTem128@GMail.Com) 2009-8-4 9:18:16
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.zoeey.constant.EnvConstants;

/**
 * 常规文件压缩类，未处理中文乱码问题
 * @author MoXie
 */
public class ZipHelper {

    /**
     * 锁定创建
     */
    private ZipHelper() {
    }

    /**
     * 压缩文件或目录
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * @param file  需要压缩的文件,或目录
     * @param zipFile 压缩后的文件
     * @throws IOException
     */
    public static void zip(File file, File zipFile) throws IOException {
        zip(file, zipFile, null);
    }

    /**
     * 压缩文件或目录
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * @param file  需要压缩的文件,或目录
     * @param zipFile 压缩后的文件
     * @param filter 文件过滤
     * @throws IOException
     */
    public static void zip(File file, File zipFile, FileFilter filter) throws IOException {
        List<File> fileList = new ArrayList<File>();
        List<File> emptyList = new ArrayList<File>();
        {
            /**
             * fixed: empty dir
             */
            if (file.isDirectory()) {
                Stack<File> stack = new Stack<File>();
                stack.push(file);
                File[] files;
                File tmpFile;
                while (!stack.isEmpty()) {
                    tmpFile = stack.pop();
                    files = tmpFile.listFiles(filter);
                    if (files.length == 0) {
                        emptyList.add(tmpFile);
                    }
                    for (File _file : files) {
                        if (_file.isDirectory()) {
                            stack.push(_file);
                        } else if (_file.isFile()) {
                            fileList.add(_file);
                        }
                    }
                }
            } else {
                if (file.exists()) {
                    fileList.add(file);
                }
            }
            fileList.addAll(emptyList);
        }
        if (fileList.isEmpty()) {
            return;
        }
        if (file.isFile()) {
            zip(file.getParentFile(), fileList, zipFile);
        }
        if (file.isDirectory()) {
            zip(file, fileList, zipFile);
        }
    }

    /**
     * 压缩文件
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * @param parentDir 父级目录
     * @param fileList 需要压缩的文件列表
     * @param zipFile 压缩后的文件
     * @throws IOException
     */
    private static void zip(File parentDir, List<File> fileList, File zipFile) throws IOException {
        ZipOutputStream zos = null;
        FileInputStream fis = null;
        ZipEntry entry = null;
        byte[] bytes = null;
        FileHelper.tryMakeDirs(zipFile.getParentFile());
        FileLocker flock = new FileLocker(zipFile);
        try {
            flock.lockWrite();
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            for (File file : fileList) {
                if (file.isFile() && !file.equals(zipFile)) {
                    entry = new ZipEntry(StringHelper.replace(file.getAbsolutePath() //
                            , parentDir.getAbsolutePath()//
                            .concat(EnvInfo.getFileSeparator()), ""));
                    //
                    entry.setTime(System.currentTimeMillis());
                    zos.putNextEntry(entry);
                    /**
                     * writing
                     */
                    fis = new FileInputStream(file);
                    bytes = new byte[fis.available()];
                    fis.read(bytes);
                    zos.write(bytes);
                }
                /**
                 * fixed: empty dir
                 */
                if (file.isDirectory()) {
                    entry = new ZipEntry(StringHelper.replace(file.getAbsolutePath() //
                            , parentDir.getAbsolutePath()//
                            .concat(EnvInfo.getFileSeparator()), "").concat("/"));
                    //
                    entry.setTime(System.currentTimeMillis());
                    zos.putNextEntry(entry);
                }
            }

            /**
             * close
             */
            zos.closeEntry();
        } finally {

            if (fis != null) {
                fis.close();
                fis = null;
            }
            if (zos != null) {
                zos.close();
                zos = null;
            }
            flock.releaseWrite();
        }
    }

    /**
     * 释放压缩文件
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * @param zipFile  压缩文件
     * @param dir   释放目录
     * @throws IOException 压缩文件不存在、读取错误等、目标文件目录无法创建新文件。
     */
    public static void unzip(File zipFile, File dir) throws IOException {
        ZipInputStream zin = null;
        String entryPath;
        StringBuilder strBuilder = null;
        try {
            zin = new ZipInputStream(new FileInputStream(zipFile));
            ZipEntry entry;
            File targetFile;
            while ((entry = zin.getNextEntry()) != null) {
                strBuilder = new StringBuilder();
                strBuilder.append(dir.getAbsolutePath());
                strBuilder.append(EnvInfo.getFileSeparator());
                strBuilder.append(entry.getName());
                entryPath = strBuilder.toString();
                targetFile = new File(entryPath);
                if (entry.isDirectory()) {
                    if (!targetFile.exists()) {
                        FileHelper.tryMakeDirs(targetFile);
                    }
                    continue;
                }
                unzip(zin, targetFile);
            }
        } finally {
            if (zin != null) {
                zin.close();
            }
            zin = null;
        }
    }

    /**
     * 解压缩
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * @param zin
     * @param entryFile
     * @throws IOException 目标文件目录无法创建新文件
     */
    private static void unzip(ZipInputStream zin, File entryFile) throws IOException {
        FileOutputStream out = null;
        int read = 0;
        if (!entryFile.exists()) {
            FileHelper.tryCreate(entryFile);
        }
        FileLocker flock = new FileLocker(entryFile);
        try {
            flock.lockRead();
            out = new FileOutputStream(entryFile);
            byte[] buffer = new byte[EnvConstants.BUFFER_BYTE_SIZE];
            while ((read = zin.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } finally {
            if (out != null) {
                out.close();
            }
            out = null;
            flock.releaseRead();
        }

    }
}
