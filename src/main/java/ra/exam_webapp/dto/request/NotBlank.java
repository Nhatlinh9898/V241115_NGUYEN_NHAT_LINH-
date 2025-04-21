//package ra.exam_webapp.dto.request;
//
//import javax.validation.Constraint;
//import javax.validation.Payload;
//import java.lang.annotation.*;
//
///**
// * Annotation tùy chỉnh dùng để kiểm tra xem chuỗi có rỗng hoặc chỉ chứa khoảng trắng không.
// * Được sử dụng trên các trường (field) của một entity hoặc DTO để đảm bảo giá trị không được bỏ trống.
// */
//@Documented // Đánh dấu annotation này xuất hiện trong tài liệu Javadoc
//@Constraint(validatedBy = NotBlankValidator.class) // Chỉ định lớp xử lý kiểm tra giá trị hợp lệ
//@Target({ElementType.FIELD, ElementType.PARAMETER}) // Áp dụng cho biến hoặc tham số phương thức
//@Retention(RetentionPolicy.RUNTIME) // Có hiệu lực trong suốt thời gian chạy của chương trình
//public @interface NotBlank {
//
//    /**
//     * Thông báo lỗi khi giá trị bị bỏ trống hoặc chỉ chứa khoảng trắng.
//     *
//     * @return Nội dung thông báo lỗi.
//     */
//    String message() default "Trường này không được để trống!";
//
//    /**
//     * Nhóm kiểm tra (groups), giúp phân loại các ràng buộc trong validation framework.
//     *
//     * @return Mảng các nhóm kiểm tra.
//     */
//    Class<?>[] groups() default {};
//
//    /**
//     * Payload có thể được sử dụng để đính kèm thông tin metadata vào annotation.
//     * Thường dùng trong các trường hợp đặc biệt như logging hoặc security.
//     *
//     * @return Mảng class kế thừa từ Payload.
//     */
//    Class<? extends Payload>[] payload() default {};
//}
