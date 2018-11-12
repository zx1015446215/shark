package com.zx.shark.doFile.service.fileServiceImpl;

import com.zx.shark.doFile.mapper.FileMapper;
import com.zx.shark.doFile.model.MyFile;
import com.zx.shark.doFile.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void delete(Long id) {
        fileMapper.delete(id);
    }

    @Override
    @Transactional
    public List<MyFile> list(MyFile myFile) {
        return fileMapper.find(myFile);
    }
}
