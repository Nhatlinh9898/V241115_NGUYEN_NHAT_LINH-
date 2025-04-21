package ra.exam_webapp.service.student;

import ra.exam_webapp.dto.request.StudentAdd;
import ra.exam_webapp.dto.request.StudentUpdate;
import ra.exam_webapp.entity.Student;
import ra.exam_webapp.service.IGenericService;

import java.util.List;

/**
 * Giao diện dịch vụ dành cho sinh viên, cung cấp các phương thức thao tác với dữ liệu sinh viên.
 * Mở rộng từ `IGenericService<Student, String, StudentAdd, StudentUpdate>` để tái sử dụng các phương thức CRUD.
 */
public interface IStudentService extends IGenericService<Student, String, StudentAdd, StudentUpdate> {

    /**
     * Truy vấn danh sách sinh viên theo từ khóa, hỗ trợ phân trang.
     *
     * @param keyword Từ khóa tìm kiếm sinh viên.
     * @param page Số trang hiện tại.
     * @param size Số lượng sinh viên trên mỗi trang.
     * @return Danh sách sinh viên phù hợp với điều kiện tìm kiếm.
     */
    List<Student> paginationList(String keyword, int page, int size);

    /**
     * Tính tổng số trang dựa trên tổng số sinh viên và kích thước mỗi trang.
     *
     * @param size Số lượng sinh viên trên mỗi trang.
     * @return Tổng số trang cần thiết.
     */
    int countTotalPages(int size);

    /**
     * Kiểm tra xem khóa học có chứa sinh viên hay không.
     * Điều này giúp tránh xóa khóa học nếu vẫn có sinh viên liên kết.
     *
     * @param courseId ID của khóa học cần kiểm tra.
     * @return `true` nếu khóa học có sinh viên, `false` nếu không.
     */
    boolean existStudentByCourseId(Integer courseId);

    /**
     * Chuyển tất cả sinh viên từ một khóa học sang khóa học khác.
     * - Trước khi xóa khóa học, cần di chuyển sinh viên sang khóa học mới.
     * - Tránh trường hợp mất sinh viên khi khóa học bị xóa.
     *
     * @param fromCourseId ID của khóa học cần chuyển sinh viên đi.
     * @param toCourseId ID của khóa học nhận sinh viên.
     */
    void moveStudentsToAnotherCourse(Integer fromCourseId, Integer toCourseId);

    /**
     * Truy vấn danh sách sinh viên thuộc một khóa học cụ thể.
     *
     * @param courseId ID của khóa học cần lấy sinh viên.
     * @return Danh sách sinh viên thuộc khóa học.
     */
    List<Student> findByCourseId(Integer courseId);

    /**
     * Tìm sinh viên theo số điện thoại.
     * Số điện thoại là một giá trị duy nhất trong hệ thống.
     *
     * @param phone Số điện thoại của sinh viên.
     * @return Đối tượng sinh viên nếu tìm thấy, hoặc `null` nếu không có.
     */
    Student findByPhone(String phone);

    /**
     * Cập nhật trạng thái sinh viên (ACTIVE hoặc INACTIVE).
     * - ACTIVE: Sinh viên đang theo học.
     * - INACTIVE: Sinh viên đã nghỉ học hoặc bảo lưu.
     *
     * @param studentId ID của sinh viên.
     * @param status Trạng thái mới của sinh viên (`true` hoặc `false`).
     */
    void updateStudentStatus(String studentId, Boolean status);
}
