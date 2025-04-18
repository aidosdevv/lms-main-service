package kz.bitlab.main_service.controller;


import kz.bitlab.main_service.dto.LessonDto;
import kz.bitlab.main_service.services.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @PostMapping("/lesson")
    public ResponseEntity<LessonDto> createLesson(@RequestBody LessonDto lesson) {
        return  ResponseEntity.ok(lessonService.addLesson(lesson));
    }

    @GetMapping("/lesson/{id}")
    public ResponseEntity<LessonDto> getLessonById(@PathVariable Long id) {
        return  ResponseEntity.ok(lessonService.getLessonById(id));
    }

    @GetMapping("/lessons")
    public List<LessonDto> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @PutMapping("/lesson/{id}")
    public ResponseEntity<LessonDto> updateLesson(@PathVariable Long id, @RequestBody LessonDto lesson) {
        return  ResponseEntity.ok(lessonService.updateLesson(id, lesson));
    }

    @DeleteMapping("/lesson/{id}")
    public ResponseEntity<String> deleteLesson(@PathVariable Long id) {
        return ResponseEntity.ok(lessonService.deleteLesson(id));
    }

}
