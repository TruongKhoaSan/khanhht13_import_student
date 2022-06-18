package com.khanh.import_student_file.service;

import com.khanh.import_student_file.model.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface StudentDataService {
    List<Student> getExcelDataAsList();
    int saveExcelData(List<Student> students);

}
