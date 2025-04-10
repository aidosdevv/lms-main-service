package kz.bitlab.main_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChapterDto {
    private Long id;
    private String name;
    private String description;
    private int sequenceOrder;
    private Long courseId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
