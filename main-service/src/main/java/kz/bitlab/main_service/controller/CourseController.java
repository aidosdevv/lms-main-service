package kz.bitlab.main_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.bitlab.main_service.dto.CourseDto;
import kz.bitlab.main_service.exception.GlobalExceptionHandler.ErrorResponse;
import kz.bitlab.main_service.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Курсы", description = "API для управления курсами")
public class CourseController {

    private final CourseService courseService;

    @Operation(
            summary = "Получить курс по ID",
            description = "Возвращает курс по указанному ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Курс найден",
                    content = @Content(schema = @Schema(implementation = CourseDto.class))),
            @ApiResponse(responseCode = "404", description = "Курс не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/course/{id}")
    public ResponseEntity<CourseDto> getCourseById(
            @Parameter(description = "ID курса", required = true)
            @PathVariable Long id) {
        CourseDto courseDto = courseService.getCourseById(id);
        return ResponseEntity.ok(courseDto);
    }

    @Operation(
            summary = "Получить все курсы",
            description = "Возвращает список всех доступных курсов"
    )
    @ApiResponse(responseCode = "200", description = "Список курсов успешно получен",
            content = @Content(schema = @Schema(implementation = CourseDto.class)))
    @GetMapping("/courses")
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        List<CourseDto> courses = courseService.findAllCourses();
        return ResponseEntity.ok(courses);
    }

    @Operation(
            summary = "Создать новый курс",
            description = "Создает новый курс на основе предоставленных данных"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Курс успешно создан",
                    content = @Content(schema = @Schema(implementation = CourseDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные курса",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/course")
    public ResponseEntity<CourseDto> createCourse(
            @Parameter(description = "Данные курса", required = true)
            @RequestBody CourseDto courseDto) {
        boolean status = courseService.addCourse(courseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseDto);
    }

    @Operation(
            summary = "Обновить курс",
            description = "Обновляет существующий курс на основе предоставленных данных"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Курс успешно обновлен",
                    content = @Content(schema = @Schema(implementation = CourseDto.class))),
            @ApiResponse(responseCode = "404", description = "Курс не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные курса",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/course")
    public ResponseEntity<CourseDto> updateCourse(
            @Parameter(description = "Данные курса для обновления", required = true)
            @RequestBody CourseDto courseDto) {
        CourseDto updatedCourse = courseService.updateCourse(courseDto.getId(), courseDto);
        return ResponseEntity.ok(updatedCourse);
    }

    @Operation(
            summary = "Удалить курс",
            description = "Удаляет курс по указанному ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Курс успешно удален"),
            @ApiResponse(responseCode = "404", description = "Курс не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/course/{id}")
    public ResponseEntity<String> deleteCourse(
            @Parameter(description = "ID курса для удаления", required = true)
            @PathVariable Long id) {
        String result = courseService.deleteCourse(id);
        return ResponseEntity.ok(result);
    }
}