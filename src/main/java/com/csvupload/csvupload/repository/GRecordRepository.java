package com.csvupload.csvupload.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csvupload.csvupload.model.GRecord;

@Repository
public interface GRecordRepository extends CrudRepository<GRecord, Long> {

    List<GRecord> findByCode(String code);

}
