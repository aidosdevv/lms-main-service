package kz.bitlab.main_service.services.impl;

import kz.bitlab.main_service.dto.LessonDto;
import kz.bitlab.main_service.entities.Chapter;
import kz.bitlab.main_service.entities.Lesson;
import kz.bitlab.main_service.exception.ResourceNotFoundException;
import kz.bitlab.main_service.mapper.LessonMapper;
import kz.bitlab.main_service.repository.ChapterRepository;
import kz.bitlab.main_service.repository.LessonRepository;
import kz.bitlab.main_service.services.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final ChapterRepository chapterRepository;
    private final LessonMapper lessonMapper;


    @Override
    public LessonDto addLesson(LessonDto lessonDto) {
        Chapter chapter = chapterRepository.findById((long) lessonDto.getChapterId()).orElse(null);

        Lesson lesson = lessonMapper.toEntity(lessonDto);
        lesson.setCreatedAt(LocalDateTime.now());
        lesson.setChapter(chapter);

        log.info("Add lesson: {}", lesson);
        return lessonMapper.toDto(lessonRepository.save(lesson));
    }

    @Override
    public LessonDto updateLesson(Long id, LessonDto lessonDto) {
            Lesson lesson = lessonRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Lesson not found"));

            Lesson updatedLesson = lessonMapper.toEntity(lessonDto);

            Chapter chapter = chapterRepository.findById((long) lessonDto.getChapterId()).orElse(null);

            lesson.setChapter(chapter);
            lesson.setUpdatedAt(LocalDateTime.now());
            lesson.setName(updatedLesson.getName());
            lesson.setDescription(updatedLesson.getDescription());
            lesson.setContent(updatedLesson.getContent());

            log.info("Update lesson: {}", lesson);
            return lessonMapper.toDto(lessonRepository.save(lesson));
    }

    @Override
    public String deleteLesson(Long id) {
        if(lessonRepository.existsById(id)) {
            lessonRepository.deleteById(id);
            log.info("Delete lesson: {}", id);
            return "Lesson deleted";
        }else{
            return "Lesson not found";
        }
    }

    @Override
    public List<LessonDto> getAllLessons() {
        log.info("Get all lessons");
        return lessonMapper.toDtoList(lessonRepository.findAll());
    }

    @Override
    public LessonDto getLessonById(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElse(null);
        if(lesson != null) {
            log.info("Get lesson: {}", id);
            return lessonMapper.toDto(lesson);
        }
        return null;
    }
}
