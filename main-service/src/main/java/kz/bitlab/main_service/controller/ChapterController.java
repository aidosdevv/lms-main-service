package kz.bitlab.main_service.controller;

import kz.bitlab.main_service.dto.ChapterDto;
import kz.bitlab.main_service.services.ChapterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChapterController {

    private final ChapterService chapterService;

    @PostMapping("/chapter")
    public ResponseEntity<ChapterDto> createChapter(@RequestBody ChapterDto chapterDto) {
        return ResponseEntity.ok(chapterService.addChapter(chapterDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChapterDto> getChapter(@PathVariable Long id) {
        return ResponseEntity.ok(chapterService.getChapterById(id));
    }

    @PutMapping("/chapter/{id}")
    public ResponseEntity<ChapterDto> updateChapter(
            @PathVariable Long id,
            @RequestBody ChapterDto chapterDto) {
        return ResponseEntity.ok(chapterService.updateChapter(id, chapterDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChapter(@PathVariable Long id) {
        String str = chapterService.deleteChapter(id);
        return ResponseEntity.ok(str);
    }

    @GetMapping("/chapters")
    public ResponseEntity<List<ChapterDto>> getAllChapters() {
        return ResponseEntity.ok(chapterService.getAllChapters());
    }
}
