package ra.exam_webapp.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.exam_webapp.dto.request.StudentAdd;
import ra.exam_webapp.dto.request.StudentUpdate;
import ra.exam_webapp.entity.Student;
import ra.exam_webapp.service.student.IStudentService;

import javax.validation.Valid;
import java.util.List;

/**
 * RESTful API Controller dành cho quản lý sinh viên.
 */
@RestController
@RequestMapping("/api/students")
public class StudentApiController {

    @Autowired
    private IStudentService studentService;

    /**
     * Lấy danh sách tất cả sinh viên.
     *
     * @return ResponseEntity chứa danh sách sinh viên.
     */
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.findAll());
    }

    /**
     * Lấy thông tin sinh viên theo ID.
     *
     * @param id Mã sinh viên.
     * @return ResponseEntity chứa thông tin sinh viên hoặc 404 nếu không tìm thấy.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable String id) {
        Student student = studentService.findById(id);
        return student != null ? ResponseEntity.ok(student) : ResponseEntity.notFound().build();
    }

    /**
     * Tạo mới một sinh viên.
     *
     * @param request DTO chứa thông tin sinh viên cần tạo.
     * @return ResponseEntity xác nhận tạo thành công.
     */
    @PostMapping
    public ResponseEntity<String> createStudent(@Valid @RequestBody StudentAdd request) {
        studentService.create(request);
        return ResponseEntity.ok("Sinh viên đã được tạo thành công!");
    }

    /**
     * Cập nhật thông tin sinh viên.
     *
     * @param id Mã sinh viên cần cập nhật.
     * @param request DTO chứa dữ liệu cập nhật.
     * @return ResponseEntity xác nhận cập nhật thành công hoặc 404 nếu không tìm thấy.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable String id, @Valid @RequestBody StudentUpdate request) {
        if (studentService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        studentService.update(request, id);
        return ResponseEntity.ok("Thông tin sinh viên đã được cập nhật!");
    }

    /**
     * Xóa sinh viên theo ID.
     *
     * @param id Mã sinh viên cần xóa.
     * @return ResponseEntity xác nhận xóa thành công hoặc 404 nếu không tìm thấy.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable String id) {
        if (studentService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        studentService.delete(id);
        return ResponseEntity.ok("Sinh viên đã được xóa thành công!");
    }

    /**
     * Cập nhật trạng thái sinh viên (ACTIVE/INACTIVE).
     *
     * @param id Mã sinh viên.
     * @param status Trạng thái mới.
     * @return ResponseEntity xác nhận cập nhật trạng thái.
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStudentStatus(@PathVariable String id, @RequestParam Boolean status) {
        if (studentService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        studentService.updateStudentStatus(id, status);
        return ResponseEntity.ok("Trạng thái sinh viên đã được cập nhật!");
    }
}

