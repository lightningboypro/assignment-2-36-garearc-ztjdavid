package com.csc301group36.Covid19API.Utils;

import com.csc301group36.Covid19API.Entities.QueryResponse;
import com.csc301group36.Covid19API.Exceptions.InternalError;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class QueryParser {
    @Autowired
    private CSVManager csvManager;

    public String parseCSV(List<CSVRecord> records) throws InternalError {
        StringWriter writer = new StringWriter();
        Collection<String> headers = csvManager.getHeaders(records);
        CSVPrinter printer = csvManager.getPrinter(headers, writer);
        try{
            printer.printRecords(records);
        }catch (IOException ioe){
            throw new InternalError("Server failed to parse query data, please try again.");
        }
        return writer.toString();
    }

    public QueryResponse parseJSON(List<CSVRecord> records){
        List<Map<String, String>> queryResult = new ArrayList<>();
        for(CSVRecord record : records){
            queryResult.add(record.toMap());
        }
        return new QueryResponse(queryResult);
    }
}
