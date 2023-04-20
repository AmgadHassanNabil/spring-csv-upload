package com.csvupload.csvupload.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.csvupload.csvupload.helper.CSVHelper;
import com.csvupload.csvupload.model.GRecord;
import com.csvupload.csvupload.repository.GRecordRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class GRecordService {

    private GRecordRepository gRecordRepository;

    public List<GRecord> saveFile(MultipartFile file) throws IOException {
        List<GRecord> gRecords = CSVHelper.csvToGRecords(file.getInputStream());
        List<GRecord> listOfSavedRecords = new ArrayList<>();
        Iterable<GRecord> iterableOfSavedRecords = this.gRecordRepository.saveAll(gRecords);
        iterableOfSavedRecords.forEach(listOfSavedRecords::add);
        return listOfSavedRecords;
    }

    public List<GRecord> getAllGRecords() {
        List<GRecord> listOfAllRecords = new ArrayList<>();
        Iterable<GRecord> iterableOfAllRecords = this.gRecordRepository.findAll();
        iterableOfAllRecords.forEach(listOfAllRecords::add);
        return listOfAllRecords;
    }

    public List<GRecord> getByCode(String code) {
        return this.gRecordRepository.findByCode(code);
    }

    public void deleteAll() {
        this.gRecordRepository.deleteAll();
    }

}