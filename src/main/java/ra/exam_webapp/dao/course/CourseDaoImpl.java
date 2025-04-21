package ra.exam_webapp.dao.course;


import java.util.List;
import org.springframework.stereotype.Repository;
import ra.exam_webapp.entity.Course;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public  class CourseDaoImpl implements ICourseDao {
    public interface ICourseDao {
        default List<Course> paginationList(String keyword, int limit, int offset) {
            return new ArrayList<>(); // Trả về danh sách rỗng mặc định
        }
    }



    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean existByName(String name) {
        Long count = entityManager.createQuery(
                        "SELECT COUNT(c) FROM Course c WHERE LOWER(c.name) = LOWER(:name)", Long.class)
                .setParameter("name", name)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public boolean existByCourseId(Integer courseId) {
        Long count = entityManager.createQuery(
                        "SELECT COUNT(s) FROM Student s WHERE s.course.id = :courseId", Long.class)
                .setParameter("courseId", courseId)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public Optional<Course> findById(Integer id) {
        Course course = entityManager.find(Course.class, id);
        return Optional.ofNullable(course);
    }

    @Transactional
    @Override
    public Course save(Course course) {
        if (course.getId() == null) {
            entityManager.persist(course);
            return course; // Trả về đối tượng vừa lưu
        } else {
            return entityManager.merge(course); // Trả về đối tượng đã cập nhật
        }
    }

    @Transactional
    @Override
    public void deleteById(Integer id) { // Đúng tên phương thức trong interface
        Optional<Course> courseOpt = findById(id);
        if (courseOpt.isEmpty()) {
            throw new IllegalArgumentException("Khóa học không tồn tại!");
        }
        if (existByCourseId(id)) {
            throw new IllegalStateException("Không thể xóa khóa học! Hiện tại có sinh viên liên kết.");
        }
        entityManager.remove(courseOpt.get());
    }


}
