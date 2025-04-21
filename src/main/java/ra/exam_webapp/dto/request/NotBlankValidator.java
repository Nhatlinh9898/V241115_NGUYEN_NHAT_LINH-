//package ra.exam_webapp.dto.request;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//
///**
// * Lớp kiểm tra xem chuỗi có bị bỏ trống hoặc chứa toàn khoảng trắng không.
// * Triển khai `ConstraintValidator<NotBlank, String>` để thực hiện logic kiểm tra.
// */
//public class NotBlankValidator implements ConstraintValidator<NotBlank, String> {
//
//    @Override
//    public void initialize(NotBlank notBlank) {
//
//    }
//
//    @Override
//    public boolean isValid(String value, ConstraintValidatorContext context) {
//        return value != null && !value.trim().isEmpty();
//    }
//}
