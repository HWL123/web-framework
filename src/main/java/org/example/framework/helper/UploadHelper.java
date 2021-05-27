package org.example.framework.helper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.example.framework.bean.FileParam;
import org.example.framework.bean.FormParam;
import org.example.framework.bean.Param;
import org.example.framework.util.CollectionUtil;
import org.example.framework.util.FileUtil;
import org.example.framework.util.StreamUtil;
import org.example.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class UploadHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadHelper.class);

    private static ServletFileUpload servletFileUpload;

    /**
     * init
     */
    public static void init(ServletContext servletContext) {
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
        int uploadLimit = ConfigHelper.getAppUploadLimit();
        if (uploadLimit != 0) {
            servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);
        }
    }


    public static boolean isMultipart(HttpServletRequest request) {
        return ServletFileUpload.isMultipartContent(request);
    }

    public static Param createParam(HttpServletRequest request) throws IOException{
        List<FormParam> formParamList = new ArrayList<FormParam>();
        List<FileParam> fileParamList = new ArrayList<FileParam>();
        try{
            Map<String, List<FileItem>> fileParamMap = servletFileUpload.parseParameterMap(request);
            if(CollectionUtil.isNotEmpty(fileParamMap)){
                for (Map.Entry<String, List<FileItem>> fileParamMapEntry : fileParamMap.entrySet()) {
                    String fieldName = fileParamMapEntry.getKey();
                    List<FileItem> fileItems = fileParamMapEntry.getValue();
                    if(CollectionUtil.isNotEmpty(fileItems)){
                        for (FileItem fileItem : fileItems) {
                            if(fileItem.isFormField()){
                                String fieldValue = fileItem.getString("UTF-8");
                                formParamList.add(new FormParam(fieldName,fieldValue));
                            }else{
                                String fileName = FileUtil.getRealFileName(new String(fileItem.getName().getBytes(), "UTF-8"));
                                if(StringUtil.isNotEmpty(fileName)){
                                    long size = fileItem.getSize();
                                    String contentType = fileItem.getContentType();
                                    InputStream inputStream = fileItem.getInputStream();
                                    fileParamList.add(new FileParam(fieldName, fileName, size, contentType, inputStream));

                                }

                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            LOGGER.error("create param failure", e);
            throw new RuntimeException(e);
        }
        return new Param(fileParamList, formParamList);

    }
    public static void uploadFile(String basePath, FileParam fileParam) {
        try {
            if(fileParam != null){
                FileUtil.createFile(basePath + fileParam.getFieldName());
                InputStream bufferedInputStream = new BufferedInputStream(fileParam.getInputStream());
                OutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(basePath + fileParam.getFieldName()));
                StreamUtil.copyStream(bufferedInputStream,bufferedOutputStream);
            }
        } catch (Exception e) {
            LOGGER.error("upload file failure", e);
            throw new RuntimeException(e);
        }
    }

    public static void uploadFile(String basePath, List<FileParam> fileParamList) {
        try {
            if (CollectionUtil.isNotEmpty(fileParamList)) {
                for (FileParam fileParam : fileParamList) {
                    uploadFile(basePath, fileParam);
                }
            }
        } catch (Exception e) {
            LOGGER.error("upload file failure", e);
            throw new RuntimeException(e);
        }
    }

}
