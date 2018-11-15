package com.zx.shark.doFile.service.fileServiceImpl;

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
    @Override
    @Transactional
    public void save(MyFile myFile) {
        fileMapper.insert(myFile);
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

}
