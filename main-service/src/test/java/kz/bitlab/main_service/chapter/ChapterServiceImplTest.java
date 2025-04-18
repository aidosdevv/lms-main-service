package kz.bitlab.main_service.chapter;


import kz.bitlab.main_service.dto.ChapterDto;
import kz.bitlab.main_service.entities.Chapter;
import kz.bitlab.main_service.entities.Course;
import kz.bitlab.main_service.exception.ResourceNotFoundException;
import kz.bitlab.main_service.mapper.ChapterMapper;
import kz.bitlab.main_service.repository.ChapterRepository;
import kz.bitlab.main_service.repository.CourseRepository;
import kz.bitlab.main_service.services.impl.ChapterServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChapterServiceImplTest {

    @Mock
    private ChapterRepository chapterRepository;

    @Mock
    private ChapterMapper chapterMapper;

    @InjectMocks
    private ChapterServiceImpl chapterService;

    @Mock
    private CourseRepository courseRepository;

    @Test
    public void testGetChapterById_Found(){
        Long chapterId = 1L;

        Chapter chapterEntity = new Chapter();
        chapterEntity.setId(chapterId);
        chapterEntity.setName("Test Chapter");

        ChapterDto expectedDto = new ChapterDto();
        expectedDto.setId(chapterId);
        expectedDto.setName("Test Chapter");

        when(chapterRepository.findById(chapterId)).thenReturn(java.util.Optional.of(chapterEntity));
        when(chapterMapper.toDto(chapterEntity)).thenReturn(expectedDto);

        ChapterDto result = chapterService.getChapterById(chapterId);

        assertNotNull(result);
        assertEquals("Test Chapter", result.getName());
        verify(chapterRepository).findById(chapterId);
    }

    @Test
    public void testGetChapterById_NotFound() {
        Long invalidId = 999L;

        when(chapterRepository.findById(invalidId)).thenReturn(java.util.Optional.empty());

        ChapterDto result = chapterService.getChapterById(invalidId);

        assertNull(result);
        verify(chapterRepository).findById(invalidId);
    }

    @Test
    public void testGetAllChapters() {
        Chapter chapter1 = new Chapter();
        chapter1.setId(1L);
        chapter1.setName("Java Basics Test");

        Chapter chapter2 = new Chapter();
        chapter2.setId(2L);
        chapter2.setName("Java Basics Test 2");

        List<Chapter> chapterEntities = List.of(chapter1, chapter2);

        ChapterDto dto1 = new ChapterDto();
        dto1.setId(1L);
        dto1.setName("Java Basics Test");

        ChapterDto dto2 = new ChapterDto();
        dto2.setId(2L);
        dto2.setName("Java Basics Test 2");

        List<ChapterDto> expectedDtos = List.of(dto1, dto2);

        when(chapterRepository.findAll()).thenReturn(chapterEntities);
        when(chapterMapper.toDtoList(chapterEntities)).thenReturn(expectedDtos);

        List<ChapterDto> result = chapterService.getAllChapters();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Java Opp", result.getFirst().getName());
        verify(chapterRepository).findAll();
    }

    @Test
    public void testCreateChapter_Success() {
        Long courseId = 1L;

        ChapterDto chapterDto = new ChapterDto();
        chapterDto.setName("Java Core");
        chapterDto.setDescription("Generics");
        chapterDto.setCourseId(courseId);

        Course mockCourse = new Course();
        mockCourse.setId(courseId);
        mockCourse.setName("Java Developer");

        Chapter chapterEntity = new Chapter();
        chapterEntity.setName("Java OOP");
        chapterEntity.setDescription("Abstract");
        chapterEntity.setCourse(mockCourse);

        ChapterDto expectedDto = new ChapterDto();
        expectedDto.setId(1L);
        expectedDto.setName("Java Basic");
        expectedDto.setDescription("Loops");

        when(courseRepository.findById(courseId)).thenReturn(java.util.Optional.of(mockCourse));
        when(chapterMapper.toEntity(any(ChapterDto.class))).thenReturn(chapterEntity);
        when(chapterRepository.save(any())).thenReturn(chapterEntity);
        when(chapterMapper.toDto(chapterEntity)).thenReturn(expectedDto);

        ChapterDto result = chapterService.addChapter(chapterDto);

        assertNotNull(result);
        assertEquals("Chapter 1", result.getName());
        verify(courseRepository).findById(courseId);
        verify(chapterRepository).save(chapterEntity);
    }

    @Test
    public void testUpdateChapter_Success() {
        Long chapterId = 1L;

        ChapterDto updateDto = new ChapterDto();
        updateDto.setName("Updated Name");
        updateDto.setDescription("Updated Desc");
        updateDto.setSequenceOrder(2);

        Chapter existingChapter = new Chapter();
        existingChapter.setId(chapterId);
        existingChapter.setName("Old Name");
        existingChapter.setDescription("Old Desc");
        existingChapter.setSequenceOrder(1);

        Chapter updatedChapter = new Chapter();
        updatedChapter.setId(chapterId);
        updatedChapter.setName("Updated Name");
        updatedChapter.setDescription("Updated Desc");
        updatedChapter.setSequenceOrder(2);

        ChapterDto expectedDto = new ChapterDto();
        expectedDto.setId(chapterId);
        expectedDto.setName("Updated Name");
        expectedDto.setDescription("Updated Desc");
        expectedDto.setSequenceOrder(2);

        when(chapterRepository.findById(chapterId)).thenReturn(java.util.Optional.of(existingChapter));
        when(chapterRepository.save(existingChapter)).thenReturn(updatedChapter);
        when(chapterMapper.toDto(updatedChapter)).thenReturn(expectedDto);

        ChapterDto result = chapterService.updateChapter(chapterId, updateDto);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        assertEquals(2, result.getSequenceOrder());
        verify(chapterRepository).findById(chapterId);
        verify(chapterRepository).save(existingChapter);
    }

    @Test
    public void testDeleteChapter_Success() {
        Long chapterId = 1L;

        when(chapterRepository.existsById(chapterId)).thenReturn(true);

        String result = chapterService.deleteChapter(chapterId);

        assertEquals("Chapter deleted successfully", result);
        verify(chapterRepository).deleteById(chapterId);
    }

    @Test
    public void testDeleteChapter_NotFound() {
        Long chapterId = 999L;

        when(chapterRepository.existsById(chapterId)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> chapterService.deleteChapter(chapterId)
        );

        assertEquals("Chapter not found with id: " + chapterId, exception.getMessage());
        verify(chapterRepository, never()).deleteById(any());
    }
}
