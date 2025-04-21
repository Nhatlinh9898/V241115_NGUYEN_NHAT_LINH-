package ra.exam_webapp.dao.course;


import java.util.List;

import net.bytebuddy.description.annotation.AnnotationValue;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import ra.exam_webapp.entity.Course;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

@Repository
public  class CourseDaoImpl implements ICourseDao {
    @Override
    public List<Course> paginationList(String keyword, int limit, int offset) {
        return entityManager.createQuery(
                        "SELECT c FROM Course c WHERE LOWER(c.name) LIKE LOWER(:keyword)", Course.class)
                .setParameter("keyword", "%" + keyword + "%")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
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

    @Override
    public boolean existsById(Integer integer) {
        return false;
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

    @Override
    public void delete(Course entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Course> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Course> findAll(AnnotationValue.Sort sort) {
        return List.of();
    }


    @Override
    public List<Course> findAll() {
        return List.of();
    }

    @Override
    public List<Course> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Course> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Course> findAllById(Iterable<Integer> integers) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public <S extends Course> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Course> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Course> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Course> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Course getOne(Integer integer) {
        return null;
    }

    @Override
    public Course getById(Integer integer) {
        return null;
    }

    @Override
    public Course getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Course> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Course> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Course> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Course> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Course> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Course> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Course, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
