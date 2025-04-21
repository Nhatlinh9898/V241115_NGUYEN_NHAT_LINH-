package ra.exam_webapp.dao.student;

import ra.exam_webapp.dao.IGenericDao;
import ra.exam_webapp.entity.Student;

import java.util.List;

/**
 * Giao diện DAO (Data Access Object) dành cho Student.
 * Cung cấp các phương thức truy vấn dữ liệu sinh viên trong cơ sở dữ liệu.
 */
public interface IStudentDao extends IGenericDao<Student, String> {

    /**
     * Truy vấn danh sách sinh viên theo từ khóa, hỗ trợ phân trang.
     *
     * @param keyword Từ khóa tìm kiếm sinh viên.
     * @param limit Số lượng sinh viên tối đa trong một trang.
     * @param offset Vị trí bắt đầu truy vấn.
     * @return Danh sách sinh viên phù hợp với điều kiện tìm kiếm.
     */
    List<Student> paginationList(String keyword, int limit, int offset);

    /**
     * Kiểm tra xem sinh viên có tồn tại dựa trên email.
     * Tránh tạo trùng sinh viên trong cơ sở dữ liệu.
     *
     * @param email Email sinh viên cần kiểm tra.
     * @return `true` nếu sinh viên đã tồn tại, `false` nếu chưa có.
     */
    boolean existByEmail(String email);

    /**
     * Kiểm tra xem khóa học có chứa sinh viên hay không.
     * Điều này giúp tránh xóa khóa học nếu vẫn có sinh viên liên kết.
     *
     * @param courseId ID của khóa học cần kiểm tra.
     * @return `true` nếu khóa học có sinh viên, `false` nếu không.
     */
    boolean existByCourseId(Integer courseId);

    /**
     * Truy vấn danh sách sinh viên theo khóa học.
     * Giúp lấy danh sách sinh viên thuộc một khóa học cụ thể.
     *
     * @param courseId ID của khóa học cần lấy sinh viên.
     * @return Danh sách sinh viên thuộc khóa học.
     */
    List<Student> findByCourseId(Integer courseId);

    /**
     * Truy vấn sinh viên theo số điện thoại.
     * Số điện thoại là một giá trị duy nhất trong hệ thống.
     *
     * @param phone Số điện thoại của sinh viên.
     * @return Đối tượng sinh viên nếu tìm thấy, hoặc `null` nếu không có.
     */
    Student findByPhone(String phone);
}

