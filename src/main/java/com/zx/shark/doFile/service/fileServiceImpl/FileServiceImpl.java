package com.zx.shark.doFile.service.fileServiceImpl;

import com.zx.shark.doFile.mapper.FileHisMapper;
import com.zx.shark.doFile.mapper.FileMapper;
import com.zx.shark.doFile.model.MyFile;
import com.zx.shark.doFile.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileMapper fileMapper;
    @Autowired
    FileHisMapper fileHisMapper;
    @Override
    @Transactional
    public void save(MyFile myFile) {
        fileMapper.insert(myFile);
        fileHisMapper.insert(myFile); //插入历史表
    }

    @Override
    @Transactional
    public void delete(String[] ids) {
        //从硬盘中删除
        List<MyFile> files = fileMapper.findByIds(ids);
        for(MyFile mf : files){
            File file = new File(mf.getFilePath());
            if(file.exists()&&file.delete()){ } //若文件存在则删除文件
        }
        //从数据库中删除
        fileMapper.delete(ids);
    }

    @Override
    @Transactional
    public List<MyFile> list(MyFile myFile) {
        return fileMapper.find(myFile);
    }

    @Override
    @Transactional
    public void updateNameById(MyFile myFile) {

        //文件中修改
        List<MyFile> files = fileMapper.findByIds(new String[]{String.valueOf(myFile.getId())});
        if(files.size()<1){return;}
        MyFile file = files.get(0);
        File f = new File(file.getFilePath());

        file.setFileName(myFile.getFileName());
        file.setFilePath(file.getFilePath().substring(0,file.getFilePath().lastIndexOf('/')+1)+file.getFileName());
        //在本地修改文件名
        f.renameTo(new File(file.getFilePath()));
        //数据库中修改文件名和路径
        fileMapper.updateNameById(file);
    }

}
