package kz.bitlab.main_service.services.impl;

import kz.bitlab.main_service.dto.ChapterDto;
import kz.bitlab.main_service.entities.Chapter;
import kz.bitlab.main_service.entities.Course;
import kz.bitlab.main_service.exception.ResourceNotFoundException;
import kz.bitlab.main_service.mapper.ChapterMapper;
import kz.bitlab.main_service.repository.ChapterRepository;
import kz.bitlab.main_service.repository.CourseRepository;
import kz.bitlab.main_service.services.ChapterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {

    private final ChapterRepository chapterRepository;
    private final CourseRepository courseRepository;
    private final ChapterMapper chapterMapper;

    @Override
    public ChapterDto addChapter(ChapterDto chapterDto) {

        Course course = courseRepository.findById(chapterDto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id: " + chapterDto.getCourseId()));

        Chapter chapter = chapterMapper.toEntity(chapterDto);
        chapter.setCourse(course);
        chapter.setCreatedAt(LocalDateTime.now());
        log.info("Add chapter: {}", chapter);
        chapterRepository.save(chapter);

        return chapterMapper.toDto(chapter);
    }

    @Override
    public ChapterDto updateChapter(Long id, ChapterDto chapterDto) {
        Chapter existingChapter = chapterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chapter not found with id: " + id));

        Chapter chapter = chapterMapper.toEntity(chapterDto);

        if (chapterDto.getCourseId() != null &&
                !chapterDto.getCourseId().equals(existingChapter.getCourse().getId())) {

            Course newCourse = courseRepository.findById(chapterDto.getCourseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + chapterDto.getCourseId()));
            existingChapter.setCourse(newCourse);
        }

        existingChapter.setName(chapter.getName());
        existingChapter.setDescription(chapter.getDescription());
        existingChapter.setSequenceOrder(chapter.getSequenceOrder());
        existingChapter.setUpdatedAt(LocalDateTime.now());
        log.info("Update chapter: {}", existingChapter);
        Chapter updatedChapter = chapterRepository.save(existingChapter);

        return chapterMapper.toDto(updatedChapter);
    }


    @Override
    public ChapterDto getChapterById(Long id) {

        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Chapter not found with id: " + id));
        log.info("Get chapter: {}", chapter);
        return chapterMapper.toDto(chapter);
    }

    @Override
    public List<ChapterDto> getAllChapters() {
        log.info("Get all chapters");
        return chapterMapper.toDtoList(chapterRepository.findAll());
    }

    @Override
    public String deleteChapter(Long id) {
        if(chapterRepository.existsById(id)) {
           chapterRepository.deleteById(id);
           log.info("Delete chapter: {}", id);
           return "Chapter deleted successfully";
        }else{
            log.info("Delete chapter with id: {}", id);
            throw new ResourceNotFoundException("Chapter not found with id: " + id);
        }
    }
}
