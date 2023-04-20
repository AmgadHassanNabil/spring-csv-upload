package com.csvupload.csvupload.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.csvupload.csvupload.model.CSVParseException;
import com.csvupload.csvupload.model.GRecord;
import com.csvupload.csvupload.model.GServiceResponse;
import com.csvupload.csvupload.service.GRecordService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/grecords")
public class GRecordController {

    private final GRecordService gRecordService;
    private static final Logger logger = Logger.getLogger(GRecordController.class.getSimpleName());

    @PostMapping
    public ResponseEntity<GServiceResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        String requestId = UUID.randomUUID().toString();
        try {
            List<GRecord> records = this.gRecordService.saveFile(file);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .build()
                    .toUri();
            return ResponseEntity.created(location).body(new GServiceResponse(records, "Ok", requestId));
        } catch (CSVParseException e) {
            final String message = "CSV File Parse Exception";
            logger.log(Level.SEVERE, message + ", " + requestId, e);
            return ResponseEntity.badRequest()
                    .body(new GServiceResponse(null, message, requestId));

        } catch (Exception e) {
            final String message = "Internal Server Error";
            logger.log(Level.SEVERE, message + ", " + requestId, e);
            return ResponseEntity.internalServerError()
                    .body(new GServiceResponse(null, message, requestId));
        }
    }

    @GetMapping
    public ResponseEntity<GServiceResponse> getAllData() {
        String requestId = UUID.randomUUID().toString();
        List<GRecord> records = this.gRecordService.getAllGRecords();
        return ResponseEntity.ok(new GServiceResponse(records, "OK", requestId));
    }

    @GetMapping("/{code}")
    public ResponseEntity<GServiceResponse> getDataBy(@PathVariable String code) {
        String requestId = UUID.randomUUID().toString();
        List<GRecord> records = this.gRecordService.getByCode(code);
        return ResponseEntity.ok(new GServiceResponse(records, "OK", requestId));
    }

    @DeleteMapping
    public ResponseEntity<GServiceResponse> deleteAll() {
        String requestId = UUID.randomUUID().toString();
        List<GRecord> records = this.gRecordService.getAllGRecords();
        this.gRecordService.deleteAll();
        return ResponseEntity.ok(new GServiceResponse(records, "Deleted", requestId));
    }

}
