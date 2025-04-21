package ra.exam_webapp.dao.student;

import org.springframework.stereotype.Repository;
import ra.exam_webapp.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Lớp DAO (Data Access Object) dành cho Student.
 * Xử lý truy vấn dữ liệu từ cơ sở dữ liệu.
 */
@Repository
public class StudentDaoImpl implements IStudentDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Truy vấn danh sách sinh viên theo từ khóa, hỗ trợ phân trang.
     *
     * @param keyword Từ khóa tìm kiếm sinh viên.
     * @param limit   Số lượng sinh viên tối đa trong một trang.
     * @param offset  Vị trí bắt đầu truy vấn.
     * @return Danh sách sinh viên phù hợp với điều kiện tìm kiếm.
     */
    @Override
    public List<Student> paginationList(String keyword, int limit, int offset) {
        return entityManager.createQuery("FROM Student S WHERE S.name LIKE :key", Student.class)
                .setParameter("key", "%" + keyword + "%")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * Truy vấn tất cả sinh viên trong cơ sở dữ liệu.
     *
     * @return Danh sách sinh viên.
     */
    @Override
    public List<Student> findAll() {
        return entityManager.createQuery("FROM Student", Student.class).getResultList();
    }

    /**
     * Kiểm tra xem sinh viên có tồn tại dựa trên email.
     *
     * @param email Email sinh viên cần kiểm tra.
     * @return `true` nếu sinh viên đã tồn tại, `false` nếu chưa có.
     */
    @Override
    public boolean existByEmail(String email) {
        return !entityManager.createQuery("SELECT S FROM Student S WHERE S.email = :email", Student.class)
                .setParameter("email", email)
                .getResultList().isEmpty();
    }

    /**
     * Kiểm tra xem khóa học có chứa sinh viên hay không.
     *
     * @param courseId ID của khóa học cần kiểm tra.
     * @return `true` nếu khóa học có sinh viên, `false` nếu không.
     */
    @Override
    public boolean existByCourseId(Integer courseId) {
        return !entityManager.createQuery("SELECT S FROM Student S WHERE S.course.id = :courseId", Student.class)
                .setParameter("courseId", courseId)
                .getResultList().isEmpty();
    }

    /**
     * Truy vấn sinh viên theo ID.
     *
     * @param id ID của sinh viên cần tìm.
     * @return Đối tượng `Student` nếu tìm thấy, hoặc `null` nếu không có.
     */
    @Override
    public Student findById(String id) {
        return entityManager.find(Student.class, id);
    }

    /**
     * Truy vấn danh sách sinh viên theo khóa học.
     *
     * @param courseId ID của khóa học cần lấy sinh viên.
     * @return Danh sách sinh viên thuộc khóa học.
     */
    @Override
    public List<Student> findByCourseId(Integer courseId) {
        return entityManager.createQuery("SELECT S FROM Student S WHERE S.course.id = :courseId", Student.class)
                .setParameter("courseId", courseId)
                .getResultList();
    }

    /**
     * Truy vấn sinh viên theo số điện thoại.
     *
     * @param phone Số điện thoại của sinh viên.
     * @return Đối tượng `Student` nếu tìm thấy, hoặc `null` nếu không có.
     */
    @Override
    public Student findByPhone(String phone) {
        List<Student> students = entityManager.createQuery("SELECT S FROM Student S WHERE S.phone = :phone", Student.class)
                .setParameter("phone", phone)
                .getResultList();
        return students.isEmpty() ? null : students.get(0);
    }

    /**
     * Lưu hoặc cập nhật thông tin sinh viên.
     *
     * @param student Đối tượng `Student` cần lưu hoặc cập nhật.
     */
    @Transactional
    @Override
    public void save(Student student) {
        if (entityManager.find(Student.class, student.getId()) == null) {
            entityManager.persist(student);
        } else {
            entityManager.merge(student);
        }
    }

    /**
     * Xóa sinh viên theo ID.
     *
     * @param id ID của sinh viên cần xóa.
     */
    @Transactional
    @Override
    public void delete(String id) {
        Student student = findById(id);
        if (student != null) {
            entityManager.remove(student);
        }
    }
}

