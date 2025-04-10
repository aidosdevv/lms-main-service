package kz.bitlab.main_service.services;

import kz.bitlab.main_service.dto.CourseDto;

import java.util.List;

public interface CourseService {
    boolean addCourse(CourseDto course);
    String deleteCourse(Long id);
    CourseDto updateCourse(Long id,CourseDto course);
    CourseDto getCourseById(Long id);
    List<CourseDto> findAllCourses();
}
