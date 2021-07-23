package com.dome.helper;

import com.dome.bean.FileParam;
import com.dome.bean.FormParam;
import com.dome.bean.Param;
import com.dome.util.CollectionUtil;
import com.dome.util.FileUtil;
import com.dome.util.StreamUtil;
import com.dome.util.StringUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文件上传助手类
 * @author hzq
 */
public class UploadHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadHelper.class);

    /**
     * Apache Commons FileUpload 提供的Servlet 文件上传对象, 用来解析请求参数
     */
    private static ServletFileUpload servletFileUpload;

    /**
     * 初始化
     */
    public static void init(ServletContext servletContext) {
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
        int uploadLimit = ConfigHelper.getAppUploadLimit();
        if (uploadLimit != 0) {
//            这里是设置上传文件的大小
            servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);
        }
    }

    /**
     * 判断是否是 multipart 请求  只有在上传文件时，对应的请求才是 multipart 类型
     * @param request
     * @return
     */
    public static boolean isMultipart(HttpServletRequest request){
        return ServletFileUpload.isMultipartContent(request);
    }

    /**
     * 从当前请求中创建Param 对象
     * @param request
     * @return
     * @throws Exception
     */
    public static Param createParam(HttpServletRequest request) throws Exception{
//        表单参数list
        List<FormParam> formParamList = new ArrayList<FormParam>();
//        文件参数List
        List<FileParam> fileParamList = new ArrayList<FileParam>();
        try {
//            文件上传对象  从请求中解析的FileItem实例的映射。
            Map<String, List<FileItem>> fileItemListMap = servletFileUpload.parseParameterMap(request);
            if (CollectionUtil.isEmpty(fileItemListMap)){
//                遍历
                for (Map.Entry<String, List<FileItem>> fileItemListEntry : fileItemListMap.entrySet()) {
//                   得到参数名
                    String filedName = fileItemListEntry.getKey();
//                    得到文件对象集合
                    List<FileItem> fileList = fileItemListEntry.getValue();
                    if (CollectionUtil.isNotEmpty(fileList)){
                        for (FileItem fileItem : fileList) {
//                            是否是一个表单对象
                            if (fileItem.isFormField()){
                                String fileValue = fileItem.getString("UTF-8");
                                formParamList.add(new FormParam(filedName,fileValue));
                            }else {
//                                文件对象
//                                获取传输文件的真实的名字
                                String fileName = FileUtil.getRealFileName(new String(fileItem.getName().getBytes(), "UTF-8"));
                                if (StringUtil.isNotEmpty(fileName)){
                                    long size = fileItem.getSize();
                                    String contentType = fileItem.getContentType();
                                    InputStream inputStream = fileItem.getInputStream();
                                    fileParamList.add(new FileParam(filedName,fileName,size,contentType,inputStream));
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            LOGGER.error("create param failure ",e);
            throw new RuntimeException(e);
        }
        return new Param(formParamList,fileParamList);
    }

    /**
     * 上传文件
     * @param basePath    文件路径
     * @param fileParam   文件对象
     */
    public static void uploadFile(String basePath,FileParam fileParam){
        try {
            String filePath = basePath + fileParam.getFileName();
//           创建文件 —— 在指定路径下的文件
            FileUtil.createFile(filePath);
//            得到  待上传文件的输入流
            InputStream inputStream = new BufferedInputStream(fileParam.getInputStream());
//            得到输入对应位置的文件的输出流
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
//            完成上传 将输入流赋值到输出流
            StreamUtil.copyStream(inputStream,outputStream);

        }catch (Exception e){
            LOGGER.error("upload failure ",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量上传
     * @param basePath
     * @param fileParamList
     */
    public static void uploadFile(String basePath,List<FileParam> fileParamList){
        try {
            if (CollectionUtil.isNotEmpty(fileParamList)){
                for (FileParam fileParam : fileParamList) {
                    uploadFile(basePath,fileParam);
                }
            }
        }catch (Exception e){
            LOGGER.error("upload file failure ", e);
            throw new RuntimeException(e);
        }
    }

}
