package com.khanh.import_student_file.controller;


import com.khanh.import_student_file.model.Student;
import com.khanh.import_student_file.respositoty.StudentRepository;
import com.khanh.import_student_file.service.StudentUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class StudentController {

    @Autowired
    StudentUploadService studentUploadService;

    @Autowired
    StudentRepository repository;


    @RequestMapping(path = "/")
    public String index(Model model){
        List<Student> studentList = studentUploadService.getExcelDataList();
        model.addAttribute("studentsList", studentList);
        return "index";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes,Model model) throws IOException {
        studentUploadService.importFile(file);

        redirectAttributes.addFlashAttribute("message", "Successfully uploaded' " + file.getOriginalFilename() +"' ");
        List<Student> studentList = studentUploadService.getExcelDataList();
        model.addAttribute("studentsList", studentList);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        return "success";
    }

    @GetMapping("/saveData")
    public String saveExcelData(Model model) {
        return "success";
    }

    @PostMapping("/findById")
    public String findById(@RequestParam(value = "id",required = false) Integer id ,@RequestParam(value = "fullName",required = false) String fullName ,Model model) {
        ArrayList<Student> students = new ArrayList<>();
        if (id != null){
            Optional<Student> studentOptional = repository.findById( id);
            if (studentOptional.isPresent()) {
                Student student = studentOptional.get();
                students.add(student);
            }
            if (!fullName.isBlank()){
                List<Student> studentOptionals = repository.findStudentsByFullNameContainingIgnoreCase(fullName);
                students.addAll(studentOptionals);
                model.addAttribute("studentsList", students);
            }
            model.addAttribute("studentsList", students);
            return "success";
        }else
        if (!fullName.isBlank()){
            List<Student> studentOptional = repository.findStudentsByFullNameContainingIgnoreCase(fullName);
            students.addAll(studentOptional);
            model.addAttribute("studentsList", students);
            return "success";
        }
        model.addAttribute("studentsList", students);
        return "success";
    }
}
