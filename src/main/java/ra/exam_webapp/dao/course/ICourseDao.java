package ra.exam_webapp.dao.course;

import net.bytebuddy.description.annotation.AnnotationValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ra.exam_webapp.entity.Course;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Giao diện `ICourseDao` mở rộng từ `JpaRepository`, cung cấp các phương thức quản lý khóa học.
 * Hỗ trợ CRUD, truy vấn khóa học theo điều kiện và phân trang.
 */
@Repository
public interface ICourseDao extends JpaRepository<Course, Integer> {

    /**
     * Lấy danh sách khóa học có phân trang và tìm kiếm theo từ khóa.
     *
     * @param keyword Từ khóa tìm kiếm.
     * @param limit Số lượng bản ghi trên mỗi trang.
     * @param offset Vị trí bắt đầu lấy dữ liệu.
     * @return Danh sách các khóa học phù hợp với từ khóa.
     */
    @Query(value = "SELECT * FROM course WHERE name LIKE %:keyword% ORDER BY id ASC LIMIT :limit OFFSET :offset",
            nativeQuery = true)
    List<Course> paginationList(@Param("keyword") String keyword, @Param("limit") int limit, @Param("offset") int offset);

    /**
     * Kiểm tra xem khóa học có tồn tại theo tên hay không.
     *
     * @param name Tên khóa học.
     * @return `true` nếu tồn tại, ngược lại `false`.
     */
    @Query("SELECT COUNT(c) > 0 FROM Course c WHERE LOWER(c.name) = LOWER(:name)")
    boolean existByName(@Param("name") String name);

    /**
     * Kiểm tra xem có sinh viên nào thuộc khóa học hay không.
     *
     * @param courseId ID của khóa học cần kiểm tra.
     * @return `true` nếu có sinh viên thuộc khóa học, ngược lại `false`.
     */
    @Query("SELECT COUNT(s) > 0 FROM Student s WHERE s.course.id = :courseId")
    boolean existByCourseId(@Param("courseId") Integer courseId);

    /**
     * Xóa khóa học theo ID. Sử dụng `@Modifying` để cập nhật dữ liệu trong database.
     *
     * @param id ID khóa học cần xóa.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Course c WHERE c.id = :id")
    void deleteById(@Param("id") Integer id);

    List<Course> findAll(AnnotationValue.Sort sort);
}
