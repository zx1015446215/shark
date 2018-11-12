package com.zx.shark.repository;

import com.zx.shark.model.Log;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<Log,Long> {
    @Override
    <S extends Log> S insert(S s);
}
