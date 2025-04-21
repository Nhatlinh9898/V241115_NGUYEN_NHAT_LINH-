package ra.exam_webapp.service.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ra.exam_webapp.dao.student.IStudentDao;
import ra.exam_webapp.dao.course.ICourseDao;
import ra.exam_webapp.dto.request.StudentAdd;
import ra.exam_webapp.dto.request.StudentUpdate;
import ra.exam_webapp.entity.Student;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Lớp triển khai dịch vụ dành cho sinh viên (Student).
 * Thực hiện các thao tác CRUD trên thực thể Student và xử lý logic nghiệp vụ.
 */
@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private IStudentDao studentDao;

    @Qualifier("courseDaoImpl")
    @Autowired
    private ICourseDao courseDao;

    public static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @Autowired
    private EntityManager entityManager;

    /**
     * Đảm bảo thư mục lưu trữ file tồn tại trước khi upload.
     */
    private void createUploadDirIfNotExists() {
        Path path = Paths.get(UPLOAD_DIR);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException("Lỗi khi tạo thư mục upload", e);
            }
        }
    }

    /**
     * Xử lý upload ảnh đại diện sinh viên.
     *
     * @param file Đối tượng `MultipartFile` chứa file ảnh.
     * @return Đường dẫn của file ảnh đã lưu hoặc `null` nếu không có file.
     */
    private String uploadFile(MultipartFile file) {
        createUploadDirIfNotExists();
        if (file == null || file.isEmpty()) return null;

        String contentType = file.getContentType();
        if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType)) {
            throw new IllegalArgumentException("Chỉ chấp nhận file định dạng JPG hoặc PNG!");
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        try {
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi lưu file", e);
        }
        return "/uploads/" + fileName;
    }

    /**
     * Lấy danh sách tất cả sinh viên.
     *
     * @return Danh sách sinh viên.
     */
    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    /**
     * Tìm sinh viên theo ID.
     *
     * @param id Mã sinh viên.
     * @return Đối tượng Student hoặc `null` nếu không tìm thấy.
     */
    @Override
    public Student findById(String id) {
        return studentDao.findById(id);
    }

    /**
     * Tạo mới một sinh viên.
     *
     * @param request DTO chứa dữ liệu sinh viên.
     */
    @Transactional
    @Override
    public void create(StudentAdd request) {
        String uploadPath = uploadFile(request.getAvatar());
        Student student = new Student(
                request.getId(),
                request.getName(),
                request.getEmail(),
                request.getPhone(),
                request.getSex(),
                request.getBod(),
                courseDao.findById(request.getCourseId()),
                uploadPath,
                request.getStatus()
        );
        studentDao.save(student);
    }

    /**
     * Cập nhật thông tin sinh viên.
     *
     * @param request DTO chứa dữ liệu cập nhật.
     * @param id ID của sinh viên cần cập nhật.
     */
    @Transactional
    @Override
    public void update(StudentUpdate request, String id) {
        Student entity = studentDao.findById(id);
        if (entity == null) throw new IllegalArgumentException("Sinh viên không tồn tại!");

        String uploadPath = request.getAvatar() != null ? uploadFile(request.getAvatar()) : entity.getAvatar();
        entity.setName(request.getName());
        entity.setEmail(request.getEmail());
        entity.setPhone(request.getPhone());
        entity.setSex(request.getSex());
        entity.setBod(request.getBod());
//        entity.setCourse(courseDao.findById(request.getCourseId()));
        entity.setAvatar(uploadPath);
        entity.setStatus(request.getStatus());

        studentDao.save(entity);
    }

    /**
     * Xóa sinh viên theo ID.
     *
     * @param id Mã sinh viên cần xóa.
     */
    @Transactional
    @Override
    public void delete(String id) {
        if (studentDao.findById(id) == null) throw new IllegalArgumentException("Sinh viên không tồn tại!");
        studentDao.delete(id);
    }

    /**
     * Truy vấn danh sách sinh viên theo từ khóa, hỗ trợ phân trang.
     *
     * @param keyword Từ khóa tìm kiếm.
     * @param page Số trang hiện tại.
     * @param size Số lượng sinh viên trên mỗi trang.
     * @return Danh sách sinh viên phù hợp.
     */
    @Override
    public List<Student> paginationList(String keyword, int page, int size) {
        int offset = page * size;
        return entityManager.createQuery("SELECT s FROM Student s WHERE s.name LIKE :keyword", Student.class)
                .setParameter("keyword", "%" + keyword + "%")
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }

    /**
     * Tính tổng số trang dựa trên tổng số sinh viên và kích thước mỗi trang.
     *
     * @param size Số lượng sinh viên trên mỗi trang.
     * @return Tổng số trang cần thiết.
     */
    @Override
    public int countTotalPages(int size) {
        long totalStudents = entityManager.createQuery("SELECT COUNT(s) FROM Student s", Long.class).getSingleResult();
        return (int) Math.ceil((double) totalStudents / size);
    }

    /**
     * Kiểm tra xem khóa học có chứa sinh viên hay không.
     *
     * @param courseId ID của khóa học cần kiểm tra.
     * @return `true` nếu khóa học có sinh viên, `false` nếu không.
     */
    @Override
    public boolean existStudentByCourseId(Integer courseId) {
        return studentDao.existByCourseId(courseId);
    }

    /**
     * Chuyển tất cả sinh viên từ một khóa học sang khóa học khác.
     *
     * @param fromCourseId ID khóa học cũ.
     * @param toCourseId ID khóa học mới.
     */
    @Transactional
    @Override
    public void moveStudentsToAnotherCourse(Integer fromCourseId, Integer toCourseId) {
        List<Student> students = studentDao.findByCourseId(fromCourseId);
//        students.forEach(student -> student.setCourse(courseDao.findById(toCourseId)));
        students.forEach(studentDao::save);
    }

    /**
     * Truy vấn danh sách sinh viên thuộc một khóa học cụ thể.
     *
     * @param courseId ID của khóa học cần lấy danh sách sinh viên.
     * @return Danh sách sinh viên thuộc khóa học hoặc danh sách rỗng nếu không có sinh viên nào.
     */
    @Override
    public List<Student> findByCourseId(Integer courseId) {
        if (courseId == null) {
            throw new IllegalArgumentException("ID khóa học không được để trống!");
        }

        List<Student> students = studentDao.findByCourseId(courseId);

        if (students.isEmpty()) {
            System.out.println("Không có sinh viên nào trong khóa học ID: " + courseId);
        }

        return students;
    }


    /**
     * Cập nhật trạng thái của sinh viên (ACTIVE hoặc INACTIVE).
     *
     * @param studentId ID của sinh viên.
     * @param status Trạng thái mới (`true` hoặc `false`).
     */
    @Transactional
    @Override
    public void updateStudentStatus(String studentId, Boolean status) {
        Student student = studentDao.findById(studentId);
        if (student == null) {
            throw new IllegalArgumentException("Sinh viên không tồn tại!");
        }
        student.setStatus(status);
        studentDao.save(student);
    }

    /**
     * Tìm sinh viên theo số điện thoại.
     *
     * @param phone Số điện thoại của sinh viên.
     * @return Đối tượng Student hoặc `null` nếu không tìm thấy.
     */
    @Override
    public Student findByPhone(String phone) {
        return studentDao.findByPhone(phone);
    }


}
