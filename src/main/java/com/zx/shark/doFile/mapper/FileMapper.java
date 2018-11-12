package com.zx.shark.doFile.mapper;

import com.zx.shark.doFile.model.MyFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {
    void insert(MyFile myFile);
    void delete(Long id);
    List<MyFile> find(MyFile myFile);
}
