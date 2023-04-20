package com.csvupload.csvupload.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.csvupload.csvupload.model.CSVParseException;
import com.csvupload.csvupload.model.GRecord;

public class CSVHelper {

    private static String dateFormat = "dd-mm-yyyy";
    private static SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

    public static List<GRecord> csvToGRecords(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                CSVParser csvParser = new CSVParser(fileReader,
                        CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<GRecord> GRecords = new ArrayList<GRecord>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                GRecord GRecord = new GRecord(
                        null,
                        csvRecord.get("source"),
                        csvRecord.get("codeListCode"),
                        csvRecord.get("code"),
                        csvRecord.get("displayValue"),
                        csvRecord.get("longDescription"),
                        tryParseDate(csvRecord.get("fromDate")),
                        tryParseDate(csvRecord.get("toDate")),
                        tryParseInteger(csvRecord.get("sortingPriority")));

                GRecords.add(GRecord);
            }

            return GRecords;
        } catch (NumberFormatException e) {
            throw new CSVParseException("Failed to parse CSV file", e);
        } catch (IOException e) {
            throw new RuntimeException("IO Exception", e);
        }
    }

    private static Date tryParseDate(String s) {
        try {
            return sdf.parse(s);
        } catch (ParseException e) {
            return null;
        }
    }

    private static Integer tryParseInteger(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
