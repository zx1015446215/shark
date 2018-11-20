package com.zx.shark.doFile.service;

import com.zx.shark.doFile.model.MyFile;

import java.util.List;

public interface FileService {
    /**
     * 保存文件信息
     * @param myFile
     */
    void save(MyFile myFile);

    /**
     * 删除文件信息
     * @param id
     */
    void delete(String[] ids);

    /**
     * 查找所有文件信息
     * @param myFile
     */
    List<MyFile> list(MyFile myFile);

    void updateNameById(MyFile myFile);
}
