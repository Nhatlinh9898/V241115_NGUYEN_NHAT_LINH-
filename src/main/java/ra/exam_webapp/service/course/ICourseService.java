package ra.exam_webapp.service.course;

import ra.exam_webapp.dto.request.CourseAdd;
import ra.exam_webapp.dto.request.CourseUpdate;
import ra.exam_webapp.entity.Course;
import ra.exam_webapp.service.IGenericService;

import java.util.List;

/**
 * Giao diện dịch vụ dành cho khóa học (Course).
 * Kế thừa từ `IGenericService`, cung cấp các phương thức xử lý logic liên quan.
 */
public interface ICourseService extends IGenericService<Course, Integer, CourseAdd, CourseUpdate> {

    /**
     * Truy vấn danh sách khóa học theo từ khóa, hỗ trợ phân trang.
     *
     * @param keyword Từ khóa tìm kiếm khóa học.
     * @param page Số trang hiện tại.
     * @param size Số lượng khóa học trên mỗi trang.
     * @return Danh sách khóa học phù hợp với tìm kiếm.
     */
    List<Course> paginationList(String keyword, int page, int size);

    /**
     * Tính tổng số trang dựa trên tổng số khóa học và kích thước mỗi trang.
     *
     * @param size Số lượng khóa học trên mỗi trang.
     * @return Tổng số trang cần thiết.
     */
    int countTotalPages(int size);
}
