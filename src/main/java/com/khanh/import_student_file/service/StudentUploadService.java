package com.khanh.import_student_file.service;

import com.khanh.import_student_file.model.Student;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface StudentUploadService {
    public void importFile(MultipartFile file) throws IOException;

    List<Student> getExcelDataList();
}
