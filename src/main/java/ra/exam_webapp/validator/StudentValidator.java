package ra.exam_webapp.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ra.exam_webapp.dao.student.IStudentDao;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Lớp kiểm tra tính duy nhất của mã sinh viên.
 * Triển khai `ConstraintValidator<StudentUnique, String>` để kiểm tra xem mã sinh viên đã tồn tại chưa.
 */
@Component // Đánh dấu lớp này là một Bean của Spring để có thể tự động tiêm phụ thuộc.
public class StudentValidator implements ConstraintValidator<StudentUnique, String> {

    @Autowired
    private IStudentDao studentDao; // DAO dùng để kiểm tra xem mã sinh viên đã tồn tại chưa.

    /**
     * Phương thức khởi tạo cho validator.
     * Hiện tại không cần xử lý gì nên để trống.
     *
     * @param studentUnique Annotation kiểm tra tính duy nhất.
     */
    @Override
    public void initialize(StudentUnique studentUnique) {
        // Không cần thiết lập gì ở đây.
    }

    /**
     * Kiểm tra xem mã sinh viên đã tồn tại trong hệ thống hay chưa.
     *
     * @param studentId Mã sinh viên cần kiểm tra.
     * @param constraintValidatorContext Đối tượng cung cấp ngữ cảnh kiểm tra.
     * @return `true` nếu mã sinh viên chưa tồn tại, `false` nếu đã có.
     */
    @Override
    public boolean isValid(String studentId, ConstraintValidatorContext constraintValidatorContext) {
        return studentDao.findById(studentId) == null; // Nếu sinh viên chưa tồn tại, trả về `true`
    }


}
