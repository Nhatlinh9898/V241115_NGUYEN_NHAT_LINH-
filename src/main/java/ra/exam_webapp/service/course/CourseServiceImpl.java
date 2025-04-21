package ra.exam_webapp.service.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.exam_webapp.dao.course.ICourseDao;
import ra.exam_webapp.dto.request.CourseAdd;
import ra.exam_webapp.dto.request.CourseUpdate;
import ra.exam_webapp.entity.Course;
import ra.exam_webapp.exception.CourseNotDeleteException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Lớp triển khai dịch vụ (Service Layer) dành cho khóa học.
 * Xử lý logic nghiệp vụ trước khi tương tác với tầng DAO để truy vấn dữ liệu.
 */
@Service
public class CourseServiceImpl implements ICourseService {

    private final ICourseDao courseDao;

    /**
     * Constructor injection để đảm bảo tính rõ ràng của dependency.
     */
    @Autowired
    public CourseServiceImpl(ICourseDao courseDao) {
        this.courseDao = courseDao;
    }

    /**
     * Truy vấn tất cả khóa học có trong hệ thống.
     *
     * @return Danh sách khóa học.
     */
    @Override
    public List<Course> findAll() {
        return courseDao.findAll();
    }

    /**
     * Hỗ trợ tìm kiếm khóa học theo từ khóa và phân trang.
     *
     * @param keyword Từ khóa tìm kiếm (có thể là một phần của tên khóa học).
     * @param page    Số trang hiện tại.
     * @param size    Số lượng khóa học trên mỗi trang.
     * @return Danh sách khóa học phù hợp với tìm kiếm.
     */
    @Override
    public List<Course> paginationList(String keyword, int page, int size) {
        return courseDao.paginationList(keyword, size, page * size);
    }

    /**
     * Tính tổng số trang dựa trên tổng số khóa học và kích thước mỗi trang.
     *
     * @param size Số lượng khóa học trên mỗi trang.
     * @return Tổng số trang cần thiết.
     */
    @Override
    public int countTotalPages(int size) {
        long totalElements = courseDao.count(); // Tránh sử dụng `findAll().size()` để tối ưu hiệu suất
        return (int) Math.ceil((double) totalElements / size);
    }

    /**
     * Tìm khóa học theo ID.
     *
     * @param id ID của khóa học cần tìm.
     * @return Đối tượng khóa học nếu tìm thấy, hoặc `null` nếu không tồn tại.
     */
    @Override
    public Course findById(Integer id) {
        return courseDao.findById(id).orElse(null); // Xử lý Optional để tránh lỗi
    }

    /**
     * Tạo mới một khóa học.
     *
     * @param courseAdd Đối tượng DTO chứa thông tin khóa học cần tạo.
     */
    @Transactional
    @Override
    public void create(CourseAdd courseAdd) {
        if (courseDao.existByName(courseAdd.getName())) {
            throw new IllegalArgumentException("Tên khóa học đã tồn tại!");
        }

        Course newCourse = new Course(
                null,
                courseAdd.getName(),
                courseAdd.getDescription()

        );
        courseDao.save(newCourse);
    }

    /**
     * Cập nhật khóa học, kiểm tra dữ liệu đầu vào trước khi thay đổi.
     *
     * @param courseUpdate Đối tượng DTO chứa thông tin cập nhật.
     * @param id           ID của khóa học cần cập nhật.
     */
    @Transactional
    @Override
    public void update(CourseUpdate courseUpdate, Integer id) {
        Optional<Course> existingCourse = courseDao.findById(id);
        if (existingCourse.isEmpty()) {
            throw new IllegalArgumentException("Khóa học không tồn tại!");
        }

        Course course = existingCourse.get();
        course.setName(courseUpdate.getName());
        course.setDescription(courseUpdate.getDescription());


        courseDao.save(course);
    }

    /**
     * Xóa khóa học theo ID, xử lý lỗi nếu khóa học có sinh viên liên kết.
     *
     * @param id ID của khóa học cần xóa.
     */
    @Transactional
    @Override
    public void delete(Integer id) {
        if (courseDao.existByCourseId(id)) {
            throw new CourseNotDeleteException("Không thể xóa khóa học! Hiện tại có sinh viên liên kết.");
        }

        courseDao.deleteById(id);
    }
}
