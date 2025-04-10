package kz.bitlab.main_service.services.impl;

import kz.bitlab.main_service.dto.CourseDto;
import kz.bitlab.main_service.entities.Course;
import kz.bitlab.main_service.mapper.CourseMapper;
import kz.bitlab.main_service.repository.CourseRepository;
import kz.bitlab.main_service.services.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public boolean addCourse(CourseDto course) {
        Course courseEntity = courseMapper.toEntity(course);
        if(courseEntity != null){
            courseEntity.setCreatedAt(LocalDateTime.now());
            course.setCreatedAt(LocalDateTime.now());
            log.info("Add course {}", course);
            courseRepository.save(courseEntity);
            return true;
        }else{
            log.info("Course {} is null", course);
            return false;
        }
    }

    @Override
    public String deleteCourse(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            log.info("Delete course {}", id);
            return "Course deleted successfully";
        } else {
            log.info("Course {} not found", id);
            return "Course not found with id: " + id;
        }
    }

    @Override
    public CourseDto updateCourse(Long id, CourseDto course) {
        Course courseEntity = courseMapper.toEntity(course);
        Course updateCourse = courseRepository.findById(id).orElse(null);

        if(updateCourse != null){

            updateCourse.setName(courseEntity.getName());
            updateCourse.setDescription(courseEntity.getDescription());
            updateCourse.setUpdatedAt(LocalDateTime.now());

            log.info("Update course {}", course);
            courseRepository.save(updateCourse);

            return courseMapper.toDto(updateCourse);
        }else{
            return null;
        }

    }

    @Override
    public CourseDto getCourseById(Long id) {
        log.info("Get course {}", id);
        Course courseEntity = courseRepository.findById(id).orElse(null);
        return courseMapper.toDto(courseEntity);
    }

    @Override
    public List<CourseDto> findAllCourses() {
        log.info("Find all courses");
        return courseMapper.toDtoList(courseRepository.findAll());
    }


}
