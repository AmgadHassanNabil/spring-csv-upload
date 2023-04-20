package com.csvupload.csvupload.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GServiceResponse {

    List<GRecord> records;
    String message;
    String id;
}
