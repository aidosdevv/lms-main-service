package kz.bitlab.main_service.services;

import kz.bitlab.main_service.dto.ChapterDto;

import java.util.List;

public interface ChapterService {

    ChapterDto addChapter(ChapterDto chapterDto);
    ChapterDto updateChapter(Long id,ChapterDto chapterDto);
    ChapterDto getChapterById(Long id);
    List<ChapterDto> getAllChapters();
    String deleteChapter(Long id);

}
