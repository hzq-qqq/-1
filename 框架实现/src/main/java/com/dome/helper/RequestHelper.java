package com.dome.helper;

import com.dome.bean.FormParam;
import com.dome.bean.Param;
import com.dome.util.ArrayUtil;
import com.dome.util.CodecUtil;
import com.dome.util.StreamUtil;
import com.dome.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

/**
 * 请求助手类
 * @author hzq
 */
public class RequestHelper {
    /**
     * 创建请求对象  表单请求对象 —— 可以不是以表单的方式， 但是不是上传文件
     * @return
     */
    public static Param createParam(HttpServletRequest request)throws Exception{
        List<FormParam> formParamList = new ArrayList<FormParam>();
//        获取请求体中的KV
        formParamList.addAll(parseParameterNames(request));
//        获取请求URL中的KV
        formParamList.addAll(parseInputStream(request));
        return new Param(formParamList);
    }

    private static Collection<? extends FormParam> parseInputStream(HttpServletRequest request) throws Exception{
        List<FormParam>formParamList = new ArrayList<FormParam>();
//        从输入流中获取字符串  —— 获取URL中的参数
        String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
        if (StringUtil.isNotEmpty(body)){
            String[] kvs = StringUtil.splitString(body, "&");
            if (ArrayUtil.isNotEmpty(kvs)){
                for (String kv : kvs) {
                    String[] array = StringUtil.splitString(kv, "=");
                    if (ArrayUtil.isNotEmpty(array) && array.length == 2){
                        String key = array[0];
                        String value = array[1];
                        formParamList.add(new FormParam(key,value));
                    }
                }
            }
        }
        return formParamList;
    }

    /**
     * 返回请求参数集合
     * @param request
     * @return
     */
    private static Collection<? extends FormParam> parseParameterNames(HttpServletRequest request) {
        List<FormParam>formParamList = new ArrayList<FormParam>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()){
            String filedName = paramNames.nextElement();
            String[] filedValues = request.getParameterValues(filedName);
            if (ArrayUtil.isNotEmpty(filedValues)){
                Object filedValue;
                if (filedValues.length == 1){
                    filedValue = filedValues[0];
                }else {
//                    一个参数有多个值
                    StringBuilder sb = new StringBuilder("");
                    for (int i = 0; i < filedValues.length; i++) {
                        sb.append(filedValues[i]);
                        if (i != filedValues.length - 1){
//                            使用分割符号分割多个值
                            sb.append(StringUtil.SEPARATOR);
                        }
                    }
                    filedValue = sb.toString();
                }
                formParamList.add(new FormParam(filedName,filedValue));
            }
        }
        return formParamList;
    }

}
