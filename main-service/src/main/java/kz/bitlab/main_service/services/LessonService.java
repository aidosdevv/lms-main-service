package kz.bitlab.main_service.services;

import kz.bitlab.main_service.dto.LessonDto;
import kz.bitlab.main_service.entities.Lesson;

import java.util.List;

public interface LessonService {
    LessonDto addLesson(LessonDto lessonDto);
    LessonDto updateLesson(Long id,LessonDto lessonDto);
    String deleteLesson(Long id);
    List<LessonDto> getAllLessons();
    LessonDto getLessonById(Long id);
}
