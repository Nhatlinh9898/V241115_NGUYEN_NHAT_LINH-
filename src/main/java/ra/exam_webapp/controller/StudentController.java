package ra.exam_webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ra.exam_webapp.dto.request.StudentAdd;
import ra.exam_webapp.dto.request.StudentUpdate;
import ra.exam_webapp.entity.Student;
import ra.exam_webapp.service.course.ICourseService;
import ra.exam_webapp.service.student.IStudentService;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Controller quản lý các thao tác liên quan đến sinh viên (Student).
 * Hỗ trợ thêm, cập nhật, xóa, tìm kiếm, và phân trang.
 */
@Controller
@RequestMapping("/student") // Định tuyến tất cả các yêu cầu bắt đầu bằng "/student".
public class StudentController {

    @Autowired
    private ICourseService courseService; // Tiêm service khóa học để lấy dữ liệu.

    @Autowired
    private IStudentService studentService; // Tiêm service sinh viên để thao tác dữ liệu.

    /**
     * Hiển thị danh sách sinh viên với phân trang và tìm kiếm từ khóa.
     *
     * @param page Số trang hiện tại (mặc định `0`).
     * @param size Số lượng sinh viên trên mỗi trang (mặc định `5`).
     * @param keyword Từ khóa tìm kiếm sinh viên.
     * @param model Đối tượng Model giúp truyền dữ liệu sang giao diện.
     * @return Giao diện danh sách sinh viên (`student/list`).
     */
    @GetMapping({"", "/list"})
    public String list(@RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "5") int size,
                       @RequestParam(value = "keyword", defaultValue = "") String keyword,
                       Model model) {
        model.addAttribute("students", studentService.paginationList(keyword, page, size));
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", studentService.countTotalPages(size));
        return "student/list";
    }

    /**
     * Hiển thị form thêm mới sinh viên.
     */
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("student", new StudentAdd());
        model.addAttribute("courses", courseService.findAll());
        return "student/add";
    }

    /**
     * Xử lý thêm mới sinh viên.
     */
    @PostMapping("/add")
    public String doAdd(@Valid @ModelAttribute("student") StudentAdd request,
                        BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("student", request);
            model.addAttribute("courses", courseService.findAll());
            return "student/add";
        }
        studentService.create(request);
        return "redirect:/student";
    }

    /**
     * Hiển thị form chỉnh sửa sinh viên.
     * Nếu không tìm thấy sinh viên, điều hướng về danh sách sinh viên với thông báo lỗi.
     *
     * @param id Mã sinh viên cần chỉnh sửa.
     * @param model Đối tượng Model giúp truyền dữ liệu sang giao diện Thymeleaf.
     * @return Giao diện chỉnh sửa sinh viên hoặc danh sách sinh viên nếu không tìm thấy.
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        // Kiểm tra sinh viên có tồn tại không
        Student entity = studentService.findById(id);
        if (entity == null) {
            model.addAttribute("errorMsg", "Không tìm thấy sinh viên!");
            return "redirect:/student"; // Điều hướng về danh sách sinh viên
        }

        // Tạo DTO để cập nhật sinh viên
        StudentUpdate dto = new StudentUpdate(
                entity.getId(), // ID sinh viên
                entity.getName(), // Tên sinh viên
                entity.getEmail(), // Email sinh viên
                entity.getPhone(), // Số điện thoại
                entity.getSex(), // Giới tính
                entity.getBod(), // Ngày sinh
                entity.getCourse() != null ? entity.getCourse().getId() : null, // ID khóa học (tránh NullPointerException)
                null, // Avatar sẽ được cập nhật từ input file, không lấy từ entity
                entity.getStatus() // Trạng thái sinh viên
        );

        // Truyền dữ liệu cần thiết sang giao diện
        model.addAttribute("student", dto);
        model.addAttribute("id", id);
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("avatarUrl", entity.getAvatar()); // Truyền URL ảnh nếu có

        return "student/edit"; // Trả về giao diện chỉnh sửa
    }


    /**
     * Xử lý cập nhật sinh viên.
     */
    @PostMapping("/update")
    public String doUpdate(@Valid @ModelAttribute("student") StudentUpdate request,
                           @RequestParam("studentId") String id, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("student", request);
            model.addAttribute("courses", courseService.findAll());
            return "student/edit";
        }
        studentService.update(request, id);
        return "redirect:/student";
    }

    /**
     * Cập nhật trạng thái sinh viên (ACTIVE hoặc INACTIVE).
     */
    @PostMapping("/update-status")
    public String updateStatus(@RequestParam("studentId") String id,
                               @RequestParam("status") Boolean status) {
        studentService.updateStudentStatus(id, status);
        return "redirect:/student";
    }

    /**
     * Xóa sinh viên theo ID.
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        if (studentService.findById(id) == null) {
            return "redirect:/student?errorMsg=Sinh viên không tồn tại hoặc đã bị xóa!";
        }
        studentService.delete(id);
        return "redirect:/student";
    }
}
