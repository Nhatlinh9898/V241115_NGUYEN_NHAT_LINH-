package ra.exam_webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ra.exam_webapp.dto.request.CourseAdd;
import ra.exam_webapp.dto.request.CourseUpdate;
import ra.exam_webapp.entity.Course;
import ra.exam_webapp.entity.Student;
import ra.exam_webapp.service.course.ICourseService;
import ra.exam_webapp.service.student.IStudentService;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller xử lý các yêu cầu liên quan đến khóa học (Course).
 * Sử dụng Spring MVC để điều hướng giao diện và gọi dịch vụ để thao tác với dữ liệu.
 */
@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private ICourseService courseService;

    @Autowired
    private IStudentService studentService;

    /**
     * Hiển thị danh sách khóa học với phân trang và tìm kiếm theo từ khóa.
     */
    @GetMapping({"", "/list"})
    public String list(@RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "5") int size,
                       @RequestParam(value = "keyword", defaultValue = "") String keyword,
                       @RequestParam(value = "errorMsg", required = false) String errorMsg,
                       Model model) {
        model.addAttribute("courses", courseService.paginationList(keyword, page, size));
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", courseService.countTotalPages(size));
        if (errorMsg != null && !errorMsg.isEmpty()) {
            model.addAttribute("error", errorMsg);
        }
        return "course/list";
    }

    /**
     * Hiển thị form thêm mới khóa học.
     */
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("course", new CourseAdd());
        return "course/add";
    }

    /**
     * Xử lý thêm mới khóa học.
     */
    @PostMapping("/add")
    public String doAdd(@Valid @ModelAttribute("course") CourseAdd request, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("course", request);
            return "course/add";
        }
        courseService.create(request);
        return "redirect:/course";
    }

    /**
     * Hiển thị form chỉnh sửa khóa học.
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        // Kiểm tra khóa học có tồn tại
        Course entity = courseService.findById(id);
        if (entity == null) {
            model.addAttribute("error", "Khóa học không tồn tại!");
            return "course/list"; // Điều hướng về danh sách khóa học nếu không tìm thấy
        }

        // Tạo DTO để chỉnh sửa khóa học
        CourseUpdate dto = new CourseUpdate(entity.getId(), entity.getName(), entity.getDescription());





        // Lấy danh sách sinh viên thuộc khóa học cần chỉnh sửa
        List<Student> students = studentService.findByCourseId(id);

        // Lấy danh sách tất cả khóa học để hiển thị lựa chọn chuyển sinh viên
        List<Course> courses = courseService.findAll();

        model.addAttribute("course", dto); // Truyền DTO thay vì thực thể
        model.addAttribute("courseId", id); // Truyền ID khóa học
        model.addAttribute("students", students);
        model.addAttribute("courses", courses);
        model.addAttribute("selectedCourse", entity); // Truyền khóa học hiện tại vào Thymeleaf

        return "course/edit";
    }

    /**
     * Xử lý cập nhật khóa học.
     */
    @PostMapping("/update")
    public String doUpdate(@Valid @ModelAttribute("course") CourseUpdate request, BindingResult result,
                           @RequestParam("courseId") Integer id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("course", request);
            model.addAttribute("id", id);
            return "course/edit";
        }

        Course existingCourse = courseService.findById(id);
        if (existingCourse == null) {
            model.addAttribute("error", "ID khóa học không hợp lệ!");
            return "course/edit";
        }

        courseService.update(request, id);
        return "redirect:/course";
    }

    /**
     * Hiển thị trang xác nhận xóa khóa học nếu có sinh viên bên trong.
     */
    @GetMapping("/delete/{id}")
    public String confirmDelete(@PathVariable("id") Integer id, Model model) {
        List<Student> students = studentService.findByCourseId(id);
        if (!students.isEmpty()) {
            model.addAttribute("courseId", id);
            model.addAttribute("students", students);
            model.addAttribute("courses", courseService.findAll());
            model.addAttribute("error", "Khóa học này chứa sinh viên. Vui lòng chọn khóa học để di chuyển trước khi xóa.");
            return "course/confirm-delete";
        }

        courseService.delete(id);
        return "redirect:/course";
    }

    /**
     * Xử lý di chuyển sinh viên sang khóa học khác trước khi xóa khóa học.
     */
    @PostMapping("/move-students")
    public String moveStudents(@RequestParam("fromCourseId") Integer fromCourseId,
                               @RequestParam("toCourseId") Integer toCourseId) {
        try {
            studentService.moveStudentsToAnotherCourse(fromCourseId, toCourseId);
            courseService.delete(fromCourseId);
        } catch (Exception e) {
            return "redirect:/course?errorMsg=" + e.getMessage();
        }
        return "redirect:/course";
    }
}
