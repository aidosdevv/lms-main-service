package kz.bitlab.main_service.mapper;

import kz.bitlab.main_service.dto.CourseDto;
import kz.bitlab.main_service.entities.Course;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseDto toDto(Course course);

    Course toEntity(CourseDto courseDto);

    List<CourseDto> toDtoList(List<Course> courses);
}
