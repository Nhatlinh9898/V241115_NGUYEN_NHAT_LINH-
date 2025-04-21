package ra.exam_webapp.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) dành cho việc thêm mới sinh viên (Student).
 * Lớp này được sử dụng để nhận dữ liệu từ người dùng khi tạo một sinh viên mới trong hệ thống.
 */
@Getter
@Setter
public class StudentAdd {

    /**
     * Mã sinh viên.
     * - Không được để trống (`@NotBlank`).
     * - Giới hạn độ dài 5 ký tự (`@Size`).
     */
    @NotBlank(message = "Mã sinh viên không được để trống!")
    @Size(min = 5, max = 5, message = "Mã sinh viên phải có đúng 5 ký tự!")
    private String id;

    /**
     * Tên sinh viên.
     * - Không được để trống (`@NotBlank`).
     * - Giới hạn độ dài tối đa 200 ký tự (`@Size`).
     */
    @NotBlank(message = "Tên sinh viên không được để trống!")
    @Size(max = 200, message = "Tên sinh viên không được dài quá 200 ký tự!")
    private String name;

    /**
     * Email sinh viên.
     * - Không được để trống (`@NotBlank`).
     * - Phải đúng định dạng email (`@Email`).
     */
    @NotBlank(message = "Email sinh viên không được để trống!")
    @Email(message = "Email sinh viên phải đúng định dạng!")
    private String email;

    /**
     * Số điện thoại sinh viên.
     * - Không được để trống (`@NotBlank`).
     * - Định dạng số điện thoại di động Việt Nam (`@Pattern`).
     */
    @NotBlank(message = "Số điện thoại không được để trống!")
    @Pattern(regexp = "^(0[35789])[0-9]{8}$", message = "Số điện thoại không hợp lệ!")
    private String phone;

    /**
     * Giới tính của sinh viên.
     * - Không được để trống (`@NotNull`).
     * - `true` nếu là Nam, `false` nếu là Nữ.
     */
    @NotNull(message = "Giới tính không được để trống!")
    private Boolean sex;

    /**
     * Ngày sinh của sinh viên.
     * - Không được để trống (`@NotNull`).
     */
    @NotNull(message = "Ngày sinh không được để trống!")
    private LocalDate bod;

    /**
     * ID của khóa học.
     * - Không được để trống (`@NotNull`).
     */
    @NotNull(message = "Khóa học không được để trống!")
    private Integer courseId;

    /**
     * Ảnh đại diện của sinh viên.
     * - Không được để trống (`@NotNull`).
     */
    @NotNull(message = "Ảnh đại diện không được để trống!")
    private MultipartFile avatar;

    /**
     * Trạng thái của sinh viên.
     * - Không được để trống (`@NotNull`).
     * - `true` nếu đang học, `false` nếu nghỉ học.
     */
    @NotNull(message = "Trạng thái sinh viên không được để trống!")
    private Boolean status;
}
