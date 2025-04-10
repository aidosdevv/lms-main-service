package kz.bitlab.main_service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto {

    private Long id;

    private String name;

    private String description;

    private String content;

    private int sequenceOrder;

    private int chapterId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
