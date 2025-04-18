package kz.bitlab.main_service.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String message) {
        super(message);
    }

    public CourseNotFoundException(Long id) {
        super("Курс с ID " + id + " не найден");
    }
}
