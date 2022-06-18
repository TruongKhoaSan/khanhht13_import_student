package com.khanh.import_student_file.service_impl;

import com.khanh.import_student_file.model.Student;
import com.khanh.import_student_file.respositoty.StudentRepository;
import com.khanh.import_student_file.service.StudentDataService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public abstract class StudentImpl implements StudentDataService {
    @Value("${app.upload.file:${user.home}}")
    public String EXCEL_FILE_PATH;

    @Autowired
    StudentRepository repo;

    Workbook workbook;

    public List<Student> getExcelDataAsList() {

        List<String> list = new ArrayList<String>();

        DataFormatter dataFormatter = new DataFormatter();

        // Create the Workbook
        try {
            workbook = WorkbookFactory.create(new File(EXCEL_FILE_PATH));
        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
        }

        System.out.println("-------Workbook has '" + workbook.getNumberOfSheets() + "' Sheets-----");

        Sheet sheet = workbook.getSheetAt(0);

        int noOfColumns = sheet.getRow(0).getLastCellNum();
        System.out.println("-------Sheet has '"+noOfColumns+"' columns------");

        for (Row row : sheet) {
            for (Cell cell : row) {
                String cellValue = dataFormatter.formatCellValue(cell);
                list.add(cellValue);
            }
        }

        List<Student> invList = createList(list, noOfColumns);

        try {
            workbook.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return invList;
    }

    private List<Student> createList(List<String> excelData, int noOfColumns) {

        ArrayList<Student> invList = new ArrayList<Student>();

        int i = noOfColumns;
        do {
            Student inv = new Student();

            inv.setId(Integer.parseInt(excelData.get(i)));
            inv.setNameSchool(excelData.get(i + 1));
            inv.setAddressSchool(excelData.get(i + 2));
            inv.setCodeStudent(excelData.get(i +3));
            inv.setClassName(excelData.get(i + 4));
            inv.setFullName(excelData.get(i + 5));
            inv.setGender(excelData.get(i + 7));
            inv.setBorn(excelData.get(i + 8));
            inv.setEthnic(excelData.get(i + 9));
            inv.setAddress(excelData.get(i + 10));
            inv.setPhone(excelData.get(i + 11));
            inv.setTotalPoint1(Double.valueOf(excelData.get(i + 12)));
            inv.setTotalPoint2(Double.valueOf(excelData.get(i + 13)));
            inv.setTotalPoint3(Double.valueOf(excelData.get(i + 14)));
            inv.setTotalPoint4(Double.valueOf(excelData.get(i + 15)));
            inv.setTotalPoint5(Double.valueOf(excelData.get(i + 16)));
            inv.setTotalPoint(Double.valueOf(excelData.get(i + 17)));
            inv.setPriorityPoint(Double.valueOf(excelData.get(i + 18)));
            inv.setPrePoint(Double.valueOf(excelData.get(i + 19)));
            inv.setNote(String.valueOf(excelData.get(i + 20)));

            invList.add(inv);
            i = i + (noOfColumns);

        } while (i < excelData.size());
        return invList;
    }

    @Override
    public int saveExcelData(List<Student> students) {
        students = repo.saveAll(students);
        return students.size();
    }


}
