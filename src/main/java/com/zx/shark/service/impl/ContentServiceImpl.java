package com.zx.shark.service.impl;

import com.zx.shark.mapper.ContentDao;
import com.zx.shark.model.ContentDO;
import com.zx.shark.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    ContentDao contentDao;

    @Override
    public ContentDO get(Long cid) {
        return contentDao.get(cid);
    }

    @Override
    public List<ContentDO> list(Map<String, Object> map) {
        return contentDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return contentDao.count(map);
    }

    @Override
    public int save(ContentDO bContent) {
        return contentDao.save(bContent);
    }

    @Override
    public int update(ContentDO bContent) {
        return contentDao.update(bContent);
    }

    @Override
    public int remove(Long cid) {
        return contentDao.remove(cid);
    }

    @Override
    public int batchRemove(Long[] cids) {
        return contentDao.batchRemove(cids);
    }
}
