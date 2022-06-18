package com.khanh.import_student_file.model;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Student")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Student {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "nameSchool")
    private String nameSchool;

    @Column(name = "addressSchool")
    private String addressSchool;

    @Column(name = "codeStudent")
    private String codeStudent  ;

    @Column(name = "className")
    private String className;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "birthday")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date birthday;

    @Column(name = "gender")
    private String gender;

    @Column(name = "born")
    private String born;

    @Column(name = "ethnic")
    private String ethnic;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    private Double totalPoint1;
    private Double totalPoint2;
    private Double totalPoint3;
    private Double totalPoint4;
    private Double totalPoint5;
    private Double totalPoint;
    private Double priorityPoint;
    private Double prePoint;
    private String note;

}
