package ra.exam_webapp.exception;

/**
 * Ngoại lệ tùy chỉnh dùng để báo lỗi khi không thể xóa khóa học.
 * Kế thừa từ `RuntimeException` để có thể ném ngoại lệ mà không bắt buộc phải xử lý ngay lập tức.
 */
public class CourseNotDeleteException extends RuntimeException {

    /**
     * Constructor mặc định.
     * Khi ngoại lệ được ném mà không cần thông báo cụ thể.
     */
    public CourseNotDeleteException() {
        super("Không thể xóa khóa học vì có ràng buộc liên quan!");
    }

    /**
     * Constructor cho phép tùy chỉnh thông báo lỗi.
     *
     * @param message Thông báo lỗi tùy chỉnh.
     */
    public CourseNotDeleteException(String message) {
        super(message);
    }
}
