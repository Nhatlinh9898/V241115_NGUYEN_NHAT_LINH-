package ra.exam_webapp.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Annotation tùy chỉnh dùng để kiểm tra tính duy nhất của tên khóa học.
 * Được sử dụng trên các trường (field) của một entity hoặc DTO.
 * Annotation này sẽ được xử lý bởi lớp `CourseValidator`.
 */
@Documented // Đánh dấu annotation này xuất hiện trong tài liệu Javadoc
@Constraint(validatedBy = CourseValidator.class) // Xác định lớp xử lý logic kiểm tra khóa học duy nhất
@Target({ElementType.FIELD, ElementType.PARAMETER}) // Annotation này có thể áp dụng cho biến hoặc tham số phương thức
@Retention(RetentionPolicy.RUNTIME) // Đảm bảo annotation có hiệu lực trong suốt thời gian chạy của chương trình
public @interface CourseUnique {

    /**
     * Thông báo lỗi khi khóa học bị trùng lặp.
     *
     * @return Nội dung thông báo lỗi.
     */
    String message() default "Tên khóa học đã tồn tại! Vui lòng chọn một tên khác.";

    /**
     * Nhóm kiểm tra (groups), giúp phân loại các ràng buộc trong validation framework.
     *
     * @return Mảng các nhóm kiểm tra.
     */
    Class<?>[] groups() default {};

    /**
     * Payload có thể được sử dụng để đính kèm thông tin metadata vào annotation.
     * Thường dùng trong các trường hợp đặc biệt như logging hoặc security.
     *
     * @return Mảng class kế thừa từ Payload.
     */
    Class<? extends Payload>[] payload() default {};
}
