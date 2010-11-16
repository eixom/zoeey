/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.loader.fileupload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zoeey.util.ArrayHelper;

/**
 * 上传流分析
 * <a href="http://www.ietf.org/rfc/rfc1867.txt">Form-based File Upload in HTML</a>
 * @author MoXie(SysTem128@GMail.Com)
 */
public class StreamParser {

    /**
     * Post参数Map
     */
    private Map<String, List<String>> paramMap;
    /**
     * 文件列表
     */
    private Map<String, List<FileItem>> fileMap;
    /**
     * 分隔符
     */
    private String boundary;
    private UploadConfig config;

    /**
     * 
     * @param boundary
     */
    public StreamParser(String boundary) {
        this(boundary, null);
    }

    /**
     *
     * @param boundary
     * @param config
     */
    public StreamParser(String boundary, UploadConfig config) {
        //
        paramMap = new HashMap<String, List<String>>();
        fileMap = new HashMap<String, List<FileItem>>();
        //
        this.boundary = boundary;
        if (config == null) {
            config = new UploadConfig();
        }
        this.config = config;
    }

    /**
     * <pre>
     * 分析输入流
     * 注意：超过文件大小限制的部分将被忽略掉。
     * </pre>
     * @see FileError
     * @param inStream
     * @throws java.io.IOException
     */
    public void parse(InputStream inStream)
            throws IOException {
        if (boundary == null || boundary.length() < 1) {
            return;
        }
        File tmpFile = null;
        // 长分界线
        String longBoundary = "--" + boundary;
        int longBoundaryLength = longBoundary.length();
        PushbackInputStream pushbackInputStream = new PushbackInputStream(inStream, longBoundaryLength);
        // 
        int i = 0;
        Map<String, String> headerParams = null; // 参数列表 用于递给 $_POST
        String fieldName; //
        FileItem fileItem = null; // 文件单元信息
        String fileName = null; // 文件名
        boolean isFile = false;//
        if (!isBoundary(pushbackInputStream, longBoundary)) {
            return;
        }
        do {
            i++;
            if (isEnd(pushbackInputStream) || pushbackInputStream.available() <= 0) {
                break;
            }
            headerParams = parseHeader(pushbackInputStream);
            fieldName = headerParams.get(FileItem.NAME_FLAG);
            fileName = getFileName(headerParams.get(FileItem.FILENAME_FLAG));
            /**
             * 由文件名参数是否存在判断是否为文件字段。
             */
            isFile = (fileName != null) ? true : false;
            if (isFile) {
                fileItem = new FileItem();
                fileItem.setOriginalName(fileName);
                fileItem.setType(headerParams.get(FileItem.CONTENT_TYPE_FLAG));
                fileItem.setHeader(headerParams);
                if (fileName.length() < 1) {
                    fileItem.setError(FileError.NO_FILE);
                }
                if (config.getAllowTypes() != null) {
                    if (!ArrayHelper.inArray(config.getAllowTypes(), fileItem.getType())) {
                        fileItem.setError(FileError.BANED_TYPE);
                    }
                }
                tmpFile = newTempFile();
                fileItem.setTempFile(tmpFile);
                parseFileField(fileItem, pushbackInputStream, longBoundary);
                fileItem.setError(FileError.OK);
                /**
                 * 上传错误时清除临时文件
                 */
                if (fileItem.getError() != FileError.OK) {
                    if (tmpFile.exists()) {
                        tmpFile.delete();
                    }
                }
                fileItem.setFieldName(fieldName);
                addFileItem(fieldName, fileItem);
            } else {
                addStringItem(fieldName, parseStringField(pushbackInputStream, longBoundary));
            }
        } while (true);
    }

    /**
     * <pre>
     * 析出文件名
     * HttpUnit等工具或浏览器会将文件的原始路径信息提交上来
     * </pre>
     * @param fileName
     * @return
     */
    private String getFileName(String fileName) {
        if (fileName == null) {
            return null;
        }
        int backslashPos = fileName.lastIndexOf('\\');
        int slashPos = fileName.lastIndexOf('/');
        if (backslashPos != -1 || slashPos != 0) {
            fileName = fileName.substring((backslashPos > slashPos ? backslashPos : slashPos) + 1);
        }
        return fileName;
    }

    /**
     * <pre>
     * 文件字段
     * 注意：超过文件大小限制的部分将被忽略掉。
     * </pre>
     * @see FileError
     * @param fileItem
     * @param pushbackInputStream
     * @param longBoundary
     * @throws java.io.IOException
     */
    private void parseFileField(FileItem fileItem, PushbackInputStream pushbackInputStream, //
            String longBoundary)
            throws IOException {
        try {
            OutputStream os = new FileOutputStream(fileItem.getTempFile());
            PushbackOutputStream pbos = new PushbackOutputStream(os, longBoundary.length());
            int ich = 0;
            char ch = 0;
            char latestCh = 0;
            long i = 0L;
            boolean isBound = false;
            int filesizeMax = config.getFilesizeMax();
            do {
                if ((ich = pushbackInputStream.read()) == -1) {
                    /**
                     * 文件只有部分被上传
                     */
                    fileItem.setError(FileError.PARTIAL);
                    break;
                }
                ch = (char) ich;
                if (isLineSeparator(ch)) {
                    if (isBoundary(pushbackInputStream, longBoundary)) {
                        if (latestCh == '\r' && ch == '\n') {
                            pbos.unwrite();
                        }
                        break;
                    }
                }
                if (fileItem.getError() != FileError.INIT) {
                    isBound = true;
                }
                if (!isBound) {
                    if (i > filesizeMax) {
                        isBound = true;
                    }
                    i++;
                    pbos.write(ich);
                }
                latestCh = ch;
            } while (true);
            fileItem.setSize(i - 1);
            if (isBound) {
                fileItem.setError(FileError.MAXSIZE);
            }

            pbos.flush();
            pbos.close();
            pbos = null;
            os = null;
        } catch (IOException ex) {
            fileItem.setError(FileError.IOEX);
            throw new IOException(ex.getLocalizedMessage());
        }
    }

    /**
     * 分析文本字段
     * @param pushbackInputStream
     * @param longBoundary
     * @return
     * @throws java.io.IOException
     */
    private String parseStringField(PushbackInputStream pushbackInputStream, //
            String longBoundary)
            throws IOException {
        List<Byte> list = new ArrayList<Byte>(50);
        int ich = 0;
        char ch = 0;
        char latestCh = 0;
        do {
            if ((ich = pushbackInputStream.read()) == -1) {
                break;
            }
            ch = (char) ich;
            if (isLineSeparator(ch)) {
                if (isBoundary(pushbackInputStream, longBoundary)) {
                    if (latestCh == '\r' && ch == '\n') { // \r\r \n\n [\r\n]
                        list.remove(list.size() - 1);
                    }
                    break;
                }
            }
            list.add((byte) ich);
            latestCh = ch;
        } while (true);
        /**
         * fix character bug~
         * 问题：byte转换为char时丢失了部分数据
         * 处理：直接使用byte，不进行转换
         */
        return new String(ArrayHelper.fill(list), config.getCharset());
    }

    /**
     * 分析文件描述头
     * @param pushbackInputStream
     * @return
     * @throws java.io.IOException
     */
    private Map<String, String> parseHeader(PushbackInputStream pushbackInputStream)
            throws IOException {
        StringBuilder strBuilder = new StringBuilder(200);
        int ich = 0;
        char ch = 0;
        do {
            if ((ich = pushbackInputStream.read()) == -1) {
                break;
            }
            ch = (char) ich;
            if (isDoubleLineSeparator(ch, pushbackInputStream)) {
                break;
            }
            strBuilder.append(ch);
        } while (true);
        return DescriptionParser.getParam(strBuilder.toString(), true);
    }

    /**
     * 判断是否为行分割符
     * @param ch
     * @return
     */
    private boolean isLineSeparator(char ch) {
        boolean isLineSep = false;
        switch (ch) {
            case '\r':
            case '\n':
                isLineSep = true;
        }
        return isLineSep;
    }

    /**
     * 判断是否为双行分割符 \r\r \n\n \r\n\r\n
     * @param ch
     * @return
     */
    private boolean isDoubleLineSeparator(char ch, PushbackInputStream pushbackInputStream)
            throws IOException {
        boolean isDBLineSep = false;
        int nch = 0;
        switch (ch) {
            case '\r':
                nch = pushbackInputStream.read();
                if (nch == '\r') {
                    isDBLineSep = true;
                } else if (nch == '\n') {
                    nch = pushbackInputStream.read();
                    if (nch == '\r') {
                        nch = pushbackInputStream.read();
                        if (nch == '\n') {
                            isDBLineSep = true;
                        } else {
                            pushbackInputStream.unread(nch);
                        }
                    } else {
                        pushbackInputStream.unread(nch);
                    }
                } else {
                    pushbackInputStream.unread(nch);
                }
                break;
            case '\n':
                nch = pushbackInputStream.read();
                if (nch == '\n') {
                    isDBLineSep = true;
                } else {
                    pushbackInputStream.unread(nch);
                }
                break;
        }
        return isDBLineSep;
    }

    /**
     * 判断是否为分隔符
     * @param pushbackInputStream
     * @param boundary
     * @return
     * @throws java.io.IOException
     */
    private boolean isBoundary(PushbackInputStream pushbackInputStream, String boundary)
            throws IOException {
        int boundaryLen = boundary.length();
        int ich = 0;
        char ch = 0;
        int idx = 0;
        boolean isBoun = false;
        do {
            if ((ich = pushbackInputStream.read()) == -1) {
                break;
            }
            if (idx >= boundaryLen) {
                isBoun = true;
                break;
            }
            ch = (char) ich;

            if (ch != boundary.charAt(idx)) {
                break;
            }
            idx++;
        } while (true);

        if (!isBoun) {
            if (ich != -1) { // bug fix
                pushbackInputStream.unread(ich);
            }
            pushbackInputStream.unread(boundary.substring(0, idx).getBytes());
        } else {
            if (ich != -1) { // bug fix
                pushbackInputStream.unread(ich);
            }
        }

        return isBoun;
    }

    /**
     * 是否为结尾
     * @param pushbackInputStream
     * @return
     * @throws java.io.IOException
     */
    private boolean isEnd(PushbackInputStream pushbackInputStream)
            throws IOException {
        boolean isEnd = true;
        int ich = 0;
        int[] ichs = new int[2];
        int i = 0;
        for (i = 0; i < 2; i++) {
            ich = pushbackInputStream.read();
            ichs[i] = ich;
            if (ich == -1) {
                break;
            }
            isEnd &= (ich == '-');
            if (!isEnd) {
                pushbackInputStream.unread(ich);
                break;
            }
        }
        if (isEnd) {
            isEnd = (pushbackInputStream.read() == -1);
        } else {
            if (i == 2) {
                pushbackInputStream.unread(ichs[0]);
            }
        }
        return isEnd;
    }

    /**
     * 新临时文件
     * @param prefix
     * @return
     * @throws java.io.IOException
     */
    private File newTempFile() throws IOException {
        return File.createTempFile(config.getTempFilePrefix(), config.getTempFileSuffix(), config.getTempFileDir());
    }

    /**
     * 新增文件项
     * @param fieldName
     * @param fileItem
     */
    void addFileItem(String fieldName, FileItem fileItem) {
        if (!(fileMap.get(fieldName) != null)) {
            fileMap.put(fieldName, new ArrayList<FileItem>());
        }
        fileMap.get(fieldName).add(fileItem);
    }

    /**
     * 新增文字项
     * @param fieldName
     * @param fileItem
     */
    void addStringItem(String fieldName, String value) {
        if (!(paramMap.get(fieldName) != null)) {
            paramMap.put(fieldName, new ArrayList<String>());
        }
        paramMap.get(fieldName).add(value);
    }

    /**
     * 文件列表
     * @return
     */
    public Map<String, List<FileItem>> getFileMap() {
        return fileMap;
    }

    /**
     * 获取参数Map
     * @return
     */
    public Map<String, List<String>> getParamMap() {
        return paramMap;
    }
}
