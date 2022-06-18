package com.khanh.import_student_file.service_impl;

import com.khanh.import_student_file.model.Student;
import com.khanh.import_student_file.respositoty.StudentRepository;
import com.khanh.import_student_file.service.StudentUploadService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StudentUploadImpl implements StudentUploadService {

    @Autowired
    StudentRepository studentRepository;

    public List<Student> studentsExcelReaderService() {
        return null;
    }

    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;

    public void uploadFile(MultipartFile file) {

        try {
            Path copyLocation = Paths
                    .get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not store file " + file.getOriginalFilename()
                    + ". Please try again!");
        }
    }

    @Override
    public void importFile(MultipartFile file) throws IOException {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = "C:\\Users\\Truong Khoa San\\Desktop\\New folder\\" +file.getOriginalFilename();
        System.out.println(fileLocation);
        FileInputStream filein = new FileInputStream(fileLocation);
        //Create Workbook instance holding reference to .xlsx file
        XSSFWorkbook workbook = new XSSFWorkbook(filein);

        //Get first/desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);

        //Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();
        rowIterator.next();
        List<Student> students = new ArrayList<>();
        while (rowIterator.hasNext())
        {
            Row row = rowIterator.next();

                Student student = new Student();
                student.setNameSchool(checkType(row.getCell(1)));
                student.setAddressSchool(checkType(row.getCell(2)));
                student.setCodeStudent(checkType(row.getCell(3)));
                student.setClassName(checkType(row.getCell(4)));
                student.setFullName(checkType(row.getCell(5)));
                student.setGender(checkType(row.getCell(9)));
                student.setBorn(checkType(row.getCell(10)));
                Date birthDay = stringToDate(checkType(row.getCell(6)).toString()+"/"+checkType(row.getCell(7)).toString()+"/"+checkType(row.getCell(8)).toString());
                student.setBirthday(birthDay);
                student.setEthnic(checkType(row.getCell(11)));
                student.setAddress(checkType(row.getCell(12)));
                student.setPhone(checkType(row.getCell(13)));
                student.setTotalPoint1(checkType(row.getCell(14)));
                student.setTotalPoint2(checkType(row.getCell(15)));
                student.setTotalPoint3(checkType(row.getCell(16)));
                student.setTotalPoint4(checkType(row.getCell(17)));
                student.setTotalPoint5(checkType(row.getCell(18)));
                student.setTotalPoint(checkType(row.getCell(19)));
                student.setPriorityPoint(checkType(row.getCell(20)));
                student.setPrePoint(checkType(row.getCell(21)));
                student.setNote(checkType(row.getCell(22)));
                students.add(student);
        }
        System.out.println(students);
        studentRepository.saveAll(students);
        filein.close();
    }

    @Override
    public List<Student> getExcelDataList() {
        return studentRepository.findAll();
    }

    public <T> T checkType(Cell t) {
        if (t.getCellType().toString().equals("STRING")) {
            return (T) ((String) t.getStringCellValue());
        } else if (t.getCellType().toString().equals("NUMERIC") || t.getCellType().toString().equals("FORMULA")){
            return (T) ((Double) t.getNumericCellValue());
        }
        return null;
    }

    private Date stringToDate(String stringDate){
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(stringDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
