package com.jane.school;

import com.jane.school.client.StudentClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private final StudentClient client;

    public void save(School school) {
        schoolRepository.save(school);
        System.out.println(school);
        schoolRepository.findAll().forEach(System.out::println);
    }

    public List<School> findAllSchools() {

        return schoolRepository.findAll();
    }

    public FullSchoolResponse findSchoolWithStudents(Integer schoolId) {
        var school = schoolRepository.findById(schoolId).orElse(
                School.builder()
                        .name("NOT_FOUND")
                        .email("NOT_FOUND")
                        .build()
        );
        var students = client.findAllStudentsBySchool(schoolId);  // find students in microservices. todo
        return FullSchoolResponse.builder()
                .name(school.getName())
                .email(school.getEmail())
                .students(students)
                .build();
    }

}
