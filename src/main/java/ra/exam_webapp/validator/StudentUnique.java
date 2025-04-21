package ra.exam_webapp.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Annotation tùy chỉnh dùng để kiểm tra tính duy nhất của mã sinh viên.
 * Annotation này được sử dụng trên các trường dữ liệu trong DTO, giúp đảm bảo sinh viên không bị trùng mã.
 */
@Documented // Đánh dấu annotation này sẽ xuất hiện trong tài liệu Javadoc.
@Constraint(validatedBy = {StudentValidator.class}) // Liên kết với lớp xử lý kiểm tra `StudentValidator`.
@Target({ElementType.FIELD}) // Chỉ áp dụng trên các thuộc tính của class.
@Retention(RetentionPolicy.RUNTIME) // Có hiệu lực trong suốt vòng đời của ứng dụng.
public @interface StudentUnique {

    /**
     * Thông báo lỗi mặc định khi mã sinh viên bị trùng.
     *
     * @return Nội dung thông báo lỗi.
     */
    String message() default "Mã sinh viên đã tồn tại!";

    /**
     * Nhóm kiểm tra (groups) dùng để phân loại các ràng buộc trong validation framework.
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
