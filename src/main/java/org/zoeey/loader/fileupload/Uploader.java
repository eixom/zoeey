/*
 * MoXie (SysTem128@GMail.Com) 2009-5-22 18:32:04
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.loader.fileupload;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class Uploader {

    private static final String MULTIPART_FORM_DATA_FLAG = "multipart/form-data";
    private static final String BOUNDARY_FLAG = "boundary=";
    private StreamParser streamParser = null;
    private boolean isMultipart = false;

    /**
     *
     * @param request
     * @throws IOException
     */
    public Uploader(ServletRequest request) throws IOException {
        this(request, null);
    }

    /**
     *
     * @param request
     * @param config
     * @throws IOException
     */
    public Uploader(ServletRequest request, UploadConfig config) throws IOException {
        String contentType = request.getContentType();
        if (contentType == null || !contentType.toLowerCase().startsWith(MULTIPART_FORM_DATA_FLAG)) {
            isMultipart = false;
            return;
        }
        isMultipart = true;
        int boundaryPos = contentType.indexOf(BOUNDARY_FLAG);
        if (boundaryPos == -1) {
            return;
        }
        String boundary = contentType.substring(boundaryPos + 9);
        streamParser = new StreamParser(boundary, config);
        streamParser.parse(request.getInputStream());
    }

    /**
     *
     * @param name
     * @return
     */
    public String getParamenter(String name) {
        if (streamParser == null) {
            return null;
        }
        List<String> list = streamParser.getParamMap().get(name);
        return list == null || list.size() < 1 ? null : list.get(0);
    }

    /**
     *
     * @param name
     * @return
     */
    public List<String> getParamenterValueList(String name) {
        if (streamParser == null) {
            return null;
        }
        return streamParser.getParamMap().get(name);
    }

    /**
     * 获取所有文件的Map
     * @return
     */
    public Map<String, List<FileItem>> getFileMap() {
        if (isMultipart == false) {
            return null;
        }
        return streamParser.getFileMap();
    }

    /**
     *
     * @param name
     * @return
     */
    public FileItem getFileItem(String name) {
        if (isMultipart == false) {
            return null;
        }
        List<FileItem> list = streamParser.getFileMap().get(name);
        return list == null || list.size() < 1 ? null : list.get(0);
    }

    /**
     *
     * @param name
     * @return
     */
    public List<FileItem> getFileItemValueList(String name) {
        if (isMultipart == false) {
            return null;
        }
        return streamParser.getFileMap().get(name);
    }

    /**
     *
     * @return
     */
    public StreamParser getStreamParser() {
        return streamParser;
    }

    /**
     * Multipart
     * @return
     */
    public boolean isMultipart() {
        return this.isMultipart;
    }
}
