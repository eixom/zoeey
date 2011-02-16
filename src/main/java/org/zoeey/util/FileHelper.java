/*
 * MoXie (SysTem128@GMail.Com) 2009-3-9 11:28:21
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import org.zoeey.constant.EnvConstants;

/**
 * <pre>
 * 文件操作辅助类
 * 注意：某些操作比较危险，如 {@link   tryDelete(java.io.File, boolean)}
 * </pre>
 * @author MoXie(SysTem128@GMail.Com)
 */
public class FileHelper {

    /**
     * 锁定创建
     */
    private FileHelper() {
    }
    /**
     * 目标文件存在则替换
     */
    public static final int COPY_REPLACE = ParamHelper.genParam(0);
    /**
     * 复制成功则删除源文件 
     */
    public static final int COPY_DELETE_ORIGINAL = ParamHelper.genParam(1);
    /**
     * 复制成功则删除源文件时递归删除
     */
    public static final int COPY_DELETE_ORIGINAL_RECURSIVE = ParamHelper.genParam(2);
    /**
     * 当目标文件夹不存在时自动创建（递归式创建）
     */
    public static final int COPY_DIR_AUTOCREATE = ParamHelper.genParam(3);

    /**
     * <pre>
     * 移动文件
     * 注意：源文件将会被删除，目标文件自动建立，且会覆盖已有文件。
     * </pre>
     * @param original
     * @param target
     * @throws IOException
     */
    public static void move(File original, File target) throws IOException {
        copy(original, target, COPY_DELETE_ORIGINAL & COPY_DIR_AUTOCREATE & COPY_REPLACE);
    }

    /**
     * <pre>
     * 文件复制
     *      目标目录不存在自动创建
     *      目标文件存在则立即中断，即不覆盖
     * </pre>
     * @param original 源文件
     * @param target 目标文件
     * @throws IOException
     */
    public static void copy(File original, File target)
            throws IOException {
        copy(original, target, COPY_DIR_AUTOCREATE);
    }

    /**
     * <pre>
     * 文件/目录 的 复制/移动
     * 文件 -> 文件 覆盖/非覆盖
     * 文件 -> 目录 
     * 目录 -> 目录 互不嵌套
     * 注意：
     *      1、源为目录，目标不存在，则目标被创建为目录。
     *      2、源为文件，目标不存在，则目标被创建为文件。
     * </pre>
     * @see #COPY_REPLACE
     * @see #COPY_DELETE_ORIGINAL
     * @see #COPY_DELETE_ORIGINAL_RECURSIVE
     * @see #COPY_DIR_AUTOCREATE
     * @param original 
     * @param options
     * @param target
     * @throws IOException
     */
    public static void copy(File original, File target, int options)
            throws IOException {
        do {

            /**
             * 目标与源相同
             */
            if (target.equals(original)) {
                break;
            }
            /**
             * 源文件不存在,无法复制
             */
            if (!original.exists()) {
                throw new FileNotFoundException(original.getAbsolutePath());
            }
            /**
             *目标文件存在且不允许替换
             */
            if (target.exists() && !ParamHelper.contain(options, COPY_REPLACE)) {
                break;
            }
            /**
             * 目标文件目录不存在且不允许创建
             */
            if (!target.exists() && !ParamHelper.contain(options, COPY_DIR_AUTOCREATE)) {
                break;
            } else if (!target.exists()) {
                if (original.isDirectory()) {
                    target.mkdirs();
                } else {
                    tryCreate(target);
                }
            }
            /**
             * 文件夹到文件，不可复制
             */
            if (original.isDirectory() && target.isFile()) {
                break;
            }
            /**
             * 文件到未知类型 转为 文件到文件
             */
            if (original.isFile() && !target.isDirectory()) {
                target.createNewFile();
            }
            /**
             * 文件夹到未知类型 转为 文件夹到文件夹
             */
            if (original.isDirectory() && !target.isFile()) {
                if (!target.isDirectory()) {
                    target.mkdirs();
                }
            }
            String oriDir = original.getAbsolutePath();
            String tarDir = target.getAbsolutePath();

            /**
             * 目录有包含关系，不可复制
             */
            if (original.isDirectory() && target.isDirectory()) {

                if (oriDir.endsWith(tarDir) || tarDir.endsWith(oriDir)) {
                    break;
                }
                /**
                 * 目录 到 目录的复制
                 */
                List<File> oriList = listFilesRecusive(original);
                File copyFile;
                for (File oriFile : oriList) {
                    copyFile = new File(oriFile.getAbsolutePath().replace(oriDir, tarDir));
                    if (copyFile.exists() && !ParamHelper.contain(options, COPY_DIR_AUTOCREATE)) {
                        continue;
                    }
                    if (!copyFile.exists()) {
                        if (!ParamHelper.contain(options, COPY_DIR_AUTOCREATE)) {
                            continue;
                        } else {
                            tryCreate(copyFile);
                        }
                    }
                    _copy(oriFile, copyFile);
                }
            }
            /**
             * 文件 到 文件 的复制
             */
            if (original.isFile() && target.isFile()) {
                _copy(original, target);
                /**
                 * 文件到目录的复制
                 */
            } else if (original.isFile() && target.isDirectory()) {
                _copy(original, new File(backToslash(target.getAbsolutePath() + '/' + original.getName())));
            }
        } while (false);
        /**
         * 删除源文件
         */
        if (ParamHelper.contain(options, COPY_DELETE_ORIGINAL)) {
            tryDelete(original, false);
        } else if (ParamHelper.contain(options, COPY_DELETE_ORIGINAL_RECURSIVE)) {
            tryDelete(original, true);
        }
    }

    /**
     * 复制文件
     * @param originalFile
     * @param targetFile
     * @throws java.io.IOException
     */
    private static void _copy(File originalFile, File targetFile)
            throws IOException {
        FileLocker flockOri = new FileLocker(originalFile);
        FileLocker flockTar = new FileLocker(targetFile);
        InputStream is = null;
        BufferedOutputStream bout = null;
        try {
            flockOri.lockRead();
            flockTar.lockWrite();
            is = new FileInputStream(originalFile);
            bout = new BufferedOutputStream(new FileOutputStream(targetFile));
            byte[] buffer = new byte[EnvConstants.BUFFER_BYTE_SIZE];
            int read = 0;
            while ((read = is.read(buffer)) > 0) {
                bout.write(buffer, 0, read);
            }
            buffer = null;
            bout.flush();
        } finally {
            if (is != null) {
                is.close();
                is = null;
            }
            if (bout != null) {
                bout.close();
                bout = null;
            }
        }
        flockOri.releaseRead();
        flockTar.releaseWrite();
    }

    /**
     * <pre>
     * 尝试建立目录。
     * 父级目录将被自动创建。
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * @see FileLocker#writeLock(java.io.File)
     * </pre>
     * @param dir
     */
    public static void tryMakeDirs(File dir) {
        FileLocker flock = new FileLocker(dir);
        try {
            flock.lockWrite();
            if (!dir.isFile()) {
                dir.mkdirs();
            }
        } finally {
            flock.releaseWrite();
        }
    }

    /**
     * <pre>
     * 设定文件的访问和修改时间
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * @see FileLocker#writeLock(java.io.File)
     * </pre>
     * @param file 
     */
    public static void touch(File file) throws IOException {
        FileLocker flock = new FileLocker(file);
        try {
            flock.lockWrite();
            if (file.exists()) {
                file.setLastModified(System.currentTimeMillis());
            }
        } finally {
            flock.releaseWrite();
        }
    }

    /**
     * <pre>
     * 尝试自动建立文件。
     * 父级目录将被自动创建。
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * @see FileLocker#writeLock(java.io.File)
     * </pre>
     * @param file
     * @throws java.io.IOException
     */
    public static void tryCreate(File file)
            throws IOException {
        FileLocker flock = new FileLocker(file);
        try {
            flock.lockWrite();
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                if (!file.isDirectory() && !file.isFile()) {
                    file.createNewFile();
                }
            }
        } finally {
            flock.releaseWrite();
        }

    }

    /**
     * <pre>
     * 递归的列出文件
     * </pre>
     * @param file
     * @return
     * @throws java.io.IOException
     */
    public static List<File> listFilesRecusive(File file) throws IOException {
        return listFiles(file, true, null);
    }

    /**
     * <pre>
     * 递归的列出所有文件
     * </pre>
     * @param file
     * @return
     * @throws java.io.IOException
     */
    public static List<File> listFiles(File file) throws IOException {
        return listFiles(file, true, null);
    }

    /**
     * <pre>
     * 递归的列出文件
     * 
     * fileFilter ex.
     * fileFilter = new FileFilter() {
     *       public boolean accept(File pathname) {
     *           return !".svn".equals(pathname.getAbsolutePath());
     *       }
     *  };
     * </pre>
     * @param file
     * @param recusive
     * @param filter
     * @return
     * @throws java.io.IOException
     */
    public static List<File> listFiles(File file, boolean recusive, FileFilter filter) //
            throws IOException {
        List<File> fileList = new ArrayList<File>();
        if (file.isDirectory()) {
            Stack<File> stack = new Stack<File>();
            stack.push(file);
            File[] files;
            while (!stack.isEmpty()) {
                files = stack.pop().listFiles();
                for (File _file : files) {
                    if (_file.isDirectory() && recusive) {
                        stack.push(_file);
                    } else if (_file.isFile()) {
                        if (filter == null) {
                            fileList.add(_file);
                        } else if (filter != null && filter.accept(_file)) {
                            fileList.add(_file);
                        }
                    }
                }
            }
        } else {
            if (file.exists()) {
                fileList.add(file);
            }
        }
        return fileList;
    }

    /**
     * <pre>
     * 删除文件或文件夹（不删除主目录）
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * </pre>
     * @param file 文件或文件夹
     */
    public static void tryDelete(File file) {
        tryDelete(file, false);
    }

    /**
     * <pre>
     * 删除文件或文件夹（不删除主目录）
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * </pre>
     * @param file 文件或文件夹
     * @param recursive 在file为目录时 {@code true}<b color="red">递归删除此目录下所有目录内的文件</b>
     *         {@code false}非递归删除file目录下所有文件遇到文件夹时跳过
     */
    public static void tryDelete(File file, boolean recursive) {
        tryDelete(file, recursive, 0);
    }

    /**
     * <pre>
     * 删除文件或文件夹（不删除主目录）
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * </pre>
     * @see FileLocker#writeLock(java.io.File) 
     * @param file 文件或文件夹
     * @param recursive 在file为目录时 {@code true}<b color="red">递归删除此目录下所有目录内的文件</b>
     *         {@code false}非递归删除file目录下所有文件遇到文件夹时跳过
     * @param deep 所处位置标识
     */
    private static void tryDelete(File file, boolean recursive, int deep) {
        if (file == null || !file.exists()) {
            return;
        }
        deep++;
        FileLocker flock = new FileLocker(file);
        try {
            flock.lockWrite();
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File _file : files) {
                    if (_file.isFile()) {
                        tryDelete(_file, false, deep);
                    }
                    if (recursive) {
                        if (_file.isDirectory()) {
                            tryDelete(_file, true, deep);
                        }
                    }
                }
            }
            /**
             * 删除目录
             */
            if (recursive && deep != 1) { // 不删除主目录
                file.delete();
            }
        } finally {
            flock.releaseWrite();
        }
    }

    /**
     * <pre>
     * 将相对路径转化为绝对路径
     * 
     * ex.1
     *  relativePath ../../index.html
     *  absoluteDir d:/webRoot/script/pages/
     *  replacePartCount -1
     *  return d:/webRoot/index.html
     *
     *  ex.2
     *  relativePath ../../index.html
     *  absoluteDir d:/webRoot/script/pages/
     *  replacePartCount 1
     *  return d:/webRoot/script/../index.html
     * </pre>
     * @param relativePath 相对路径
     * @param absoluteDir 绝对目录
     * @param replacePartCount 允许转换的范围 当为 -1 时 转换所有相对目录,0 不进行转换。
     *         可用于控制目录访问权限。
     * @return
     */
    public static String getAbsolutePath(String relativePath, String absoluteDir, int replacePartCount) {
        String absolutePath = null;
        do {
            absoluteDir = backToslash(absoluteDir + relativePath);
            if (replacePartCount == -1) {
                replacePartCount = absoluteDir.length();
            }
            for (int i = 0; i < replacePartCount; i++) {
                if (absoluteDir.indexOf("../") == -1) {
                    break;
                }
                absoluteDir = absoluteDir.replaceFirst("/[^/]+/\\.\\./", "/");
            }
            absolutePath = absoluteDir;
        } while (false);
        return absolutePath;
    }

    /**
     * <pre>
     *
     * 反斜线转换为斜线
     * 
     * 1. "\" -> "/"
     * 2. "./" -> ""
     * 3. "//" -> "/" , "///////" -> "/"
     * </pre>
     * @param fileStr
     * @return 文件名为 null 时 返回 null
     */
    public static String backToslash(String fileStr) {
        if (fileStr != null) {
            return fileStr.replaceAll("\\\\", "/").replaceAll("[^.]\\./", "").replaceAll("//+", "/");
        }
        return null;
    }
    /**
     * 非法文件名字符
     */
    private static char[] FILE_UNSAFE_CHARS = new char[]{'"', '\'', '*', '/', ':', '<', '>', '?', '\\', '|',};

    /**
     * <pre>
     *  替换字符串中文件名非法字符
     *  backslash and slash to instead
     *  注意：单引号 ' 也被视为非法字符。 当 instead 也为非法字符时 instead 会被自动设定为 - 。
     *   " ' * / : < > ? \ | (已排列顺序)
     *  params: ("script/article/edit.js" , '-') or ("script\\article\\edit.js" , '-') or ("script/article\\edit.js" , '-')
     *  result: "script-article-edit.js"
     * </pre>
     * @param fileName
     * @param instead   非法字符替换为
     * @param isRemoveSpace 是否移除空白字符
     * @return fileStr 为 null 时,返回 null
     */
    public static String fileNameEscape(String fileName, char instead, boolean isRemoveSpace) {
        if (fileName != null) {
            /**
             * 需要替换的也是非法字符时改用 - 
             */
            if (Arrays.binarySearch(FILE_UNSAFE_CHARS, instead) != -1) {
                instead = '-';
            }

            StringBuilder strBuilder = new StringBuilder(fileName.length());
            char[] names = fileName.toCharArray();
            /**
             * [/\\\\:\\*\\?\"'<>|]
             */
            for (char ch : names) {
                //
                if (Arrays.binarySearch(FILE_UNSAFE_CHARS, ch) > -1) {
                    strBuilder.append(instead);
                    continue;
                }
                if (isRemoveSpace && Character.isWhitespace(ch)) {
                    continue;
                }
                strBuilder.append(ch);
            }
            return strBuilder.toString();
        }
        return null;
    }
}
