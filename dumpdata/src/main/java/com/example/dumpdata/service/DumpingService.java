package com.example.dumpdata.service;


import com.example.dumpdata.entity.MyRecord;
import com.example.dumpdata.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;




@Service
public class DumpingService {

    @Autowired
    private RecordRepository repository;

    public void saveDataFromFile(MultipartFile file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> jsonData = mapper.readValue(file.getBytes(), new TypeReference<List<Map<String, Object>>>() {});
            List<MyRecord> models = jsonData.stream()
                    .map(this::mapToMyRecord)
                    .collect(Collectors.toList());
            repository.saveAll(models);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as per your application's requirements
        }
    }


    private MyRecord mapToMyRecord(Map<String, Object> data) {
        MyRecord model = new MyRecord();
        model.setId((Long) data.get("id"));
        model.setContent((String) data.get("content"));
        model.setGroupValue((String) data.get("group"));

        String dateString = (String) data.get("start");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(dateString, formatter);
        model.setStart(java.sql.Date.valueOf(date));

        return model;
    }
}