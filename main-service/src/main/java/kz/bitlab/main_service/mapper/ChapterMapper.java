package kz.bitlab.main_service.mapper;

import kz.bitlab.main_service.dto.ChapterDto;
import kz.bitlab.main_service.entities.Chapter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChapterMapper {

    @Mapping(target = "courseId", source = "course.id")
    ChapterDto toDto(Chapter chapter);

    @Mapping(target = "course", ignore = true)
    Chapter toEntity(ChapterDto chapterDto);

    List<ChapterDto> toDtoList(List<Chapter> chapters);

}
