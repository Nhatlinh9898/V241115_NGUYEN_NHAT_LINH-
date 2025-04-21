package ra.exam_webapp.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ra.exam_webapp.dao.course.ICourseDao;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Lớp kiểm tra tính duy nhất của tên khóa học.
 * Triển khai `ConstraintValidator<CourseUnique, String>` để thực hiện logic kiểm tra.
 */
@Component // Đánh dấu lớp này là một Bean của Spring để có thể tự động tiêm phụ thuộc.
public class CourseValidator implements ConstraintValidator<CourseUnique, String> {

    private final ICourseDao courseDao;

    /**
     * Constructor injection giúp đảm bảo dependency rõ ràng và dễ kiểm thử.
     *
     * @param courseDao DAO để kiểm tra tính duy nhất của tên khóa học.
     */
    @Autowired
    public CourseValidator(ICourseDao courseDao) {
        this.courseDao = courseDao;
    }

    /**
     * Phương thức khởi tạo cho validator, không cần xử lý gì nên để trống.
     *
     * @param courseUnique Annotation kiểm tra tính duy nhất.
     */
    @Override
    public void initialize(CourseUnique courseUnique) {
        // Không cần thiết lập gì ở đây.
    }

    /**
     * Kiểm tra xem tên khóa học đã tồn tại trong hệ thống hay chưa.
     *
     * @param name Tên khóa học cần kiểm tra.
     * @param context Đối tượng cung cấp ngữ cảnh kiểm tra.
     * @return `true` nếu khóa học chưa tồn tại, `false` nếu đã có.
     */
    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null || name.trim().isEmpty()) {
            return false; // Tránh lỗi nếu tên khóa học bị bỏ trống
        }
        return !courseDao.existByName(name);
    }
}
