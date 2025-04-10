package kz.bitlab.main_service.controller;

import kz.bitlab.main_service.dto.CourseDto;
import kz.bitlab.main_service.services.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/course/{id}")
    public CourseDto getCourseById(@PathVariable Long id) {
        log.info("CourseDto for id {}", id);
        CourseDto courseDto = courseService.getCourseById(id);
        return courseDto;
    }

    @GetMapping("/courses")
    public List<CourseDto> getAllCourses() {
        log.info("Courses list");
        return courseService.findAllCourses();
    }

    @PostMapping("/course")
    public CourseDto createCourse(@RequestBody CourseDto courseDto) {
        log.info("Create course {}", courseDto.getName());
        boolean status = courseService.addCourse(courseDto);
        if (status) {
            log.debug("Course created {}", courseDto.getName());
            return courseDto;
        }else {
            log.error("Course creation failed");
            return null;
        }
    }

    @PutMapping("/course")
    public CourseDto updateCourse(@RequestBody CourseDto courseDto) {
        log.info("Update course {}", courseDto.getName());
        CourseDto dto = courseService.updateCourse(courseDto.getId(), courseDto);
        return dto;
    }

    @DeleteMapping("/course/{id}")
    public String deleteCourse(@PathVariable Long id) {
        log.info("Delete course {}", id);
        String result = courseService.deleteCourse(id);
        return result;
    }
}
