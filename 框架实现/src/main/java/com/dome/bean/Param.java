package com.dome.bean;

import com.dome.util.CastUtil;
import com.dome.util.CollectionUtil;
import com.dome.util.StringUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这里对Param 做了一个重构，用于封装表单和文件参数对象
 * @author hzq
 */
public class Param {
    private List<FormParam>formParamList;
    private List<FileParam>fileParamList;

    public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
        this.formParamList = formParamList;
        this.fileParamList = fileParamList;
    }

    public Param(List<FormParam> formParamList) {
        this.formParamList = formParamList;
    }




    /**
     * 获取请求参数映射         —— 用于根据参数名字可以获取到参数对象
     * 得到表参数 的  表单值的映射
     * @return
     */
    public Map<String,Object>getFiledMap(){
        Map<String,Object>filedMap = new HashMap<String, Object>();
        if (!CollectionUtil.isNotEmpty(formParamList)){
            for (FormParam formParam : formParamList) {
//                参数的名字
                String filedName = formParam.getFiledName();
                Object filedValue = formParam.getFiledValue();
                if (filedMap.containsKey(filedName)){
//                    这里是对同名的参数请求的value 使用了分割符号来处理
                    filedValue = filedMap.get(filedName) + StringUtil.SEPARATOR + filedValue;
                }
                filedMap.put(filedName,filedValue);
            }
        }
        return filedMap;
    }

    /**
     * 封装上传文件的映射
     * @return
     */
    public Map<String,List<FileParam>> getFileMap(){
        Map<String,List<FileParam>> fileMap = new HashMap<String, List<FileParam>>();
        if (!CollectionUtil.isNotEmpty(fileParamList)){
            for (FileParam fileParam : fileParamList) {
//                文件表单字段名
                String filedName = fileParam.getFiledName();
//                文件参数集合
                List<FileParam> fileParamList;
//                这里对同名的问价是用了List 进行封装，可以轻松实现多文件上传的需求
                if (fileMap.containsKey(filedName)){
//                   文件同名 ，则将文件放到一个相同的文件集合中
                    fileParamList = fileMap.get(filedName);
                }else {
                    fileParamList = new ArrayList<FileParam>();
                }
//                将文件对象加入到集合中
                fileParamList.add(fileParam);
//                问价表单名 到 文件集合的映射
                fileMap.put(filedName,fileParamList);
            }
        }
        return fileMap;
    }

    /**
     * 获得所有上传的文件 集合
     * @param filedName   文件字段名
     * @return
     */
    public List<FileParam> getFileList(String filedName){
        return getFileMap().get(filedName);
    }

    /**
     * 获得唯一上传的文件
     * @param filedName
     * @return
     */
    public FileParam getFile(String filedName){
        List<FileParam>fileParamList = getFileList(filedName);
        if (CollectionUtil.isNotEmpty(fileParamList) && fileParamList.size() == 1){
            return fileParamList.get(0);
        }
        return null;
    }

    /**
     * 检验表单参数是否为空
     * @return
     */
    public boolean isEmpty() {
      return CollectionUtil.isEmpty(formParamList) && CollectionUtil.isEmpty(fileParamList);
    }

    /**
     * 根据参数名，获取String 类型的参数值
     * @return
     */
    public String getString(String name){
        return CastUtil.castString(getFiledMap().get(name));
    }

    /**
     * 根据参数名获取double 类型的参数值
     * @param name
     * @return
     */
    public double getDouble(String name){
        return CastUtil.castDouble(getFiledMap().get(name));
    }

    /**
     * 根据参数名获取long 类型的参数值
     * @param name
     * @return
     */
    public long getLong(String name){
        return CastUtil.castLong(getFiledMap().get(name));
    }


    /**
     * 根据参数名获取int 类型的参数值
     * @param name
     * @return
     */
    public int getInt(String name){
        return CastUtil.castInt(getFiledMap().get(name));
    }
    /**
     * 根据参数名获取boolean 类型的参数值
     * @param name
     * @return
     */
    public boolean getBoolean(String name){
        return CastUtil.castBoolean(getFiledMap().get(name));
    }




}
