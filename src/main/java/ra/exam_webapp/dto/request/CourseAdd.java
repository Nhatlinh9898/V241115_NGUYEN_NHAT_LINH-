package ra.exam_webapp.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;



/**
 * DTO (Data Transfer Object) dành cho việc thêm mới khóa học (Course).
 * Lớp này được sử dụng để nhận dữ liệu từ người dùng khi thêm một khóa học mới vào hệ thống.
 */
@Getter
@Setter
@NoArgsConstructor // Tạo constructor không tham số
@AllArgsConstructor // Tạo constructor có tham số
public class CourseAdd {

    /**
     * Tên khóa học, không được để trống và phải có độ dài hợp lý.
     * - `@NotBlank`: Không được để trống.
     * - `@Length(max = 100)`: Giới hạn tối đa 100 ký tự để phù hợp với cấu trúc CSDL.
     */
    @NotBlank(message = "Tên khóa học không được để trống!")
    @Length(max = 100, message = "Tên khóa học không được vượt quá 100 ký tự!")
    private String name;

    /**
     * Mô tả khóa học, không được để trống.
     * - `@NotBlank`: Đảm bảo rằng người dùng phải nhập mô tả khóa học.
     * - `@Length(min = 10)`: Đảm bảo mô tả có ít nhất 10 ký tự để tránh nhập nội dung quá ngắn.
     */
    @NotBlank(message = "Mô tả khóa học không được để trống!")
    @Length(min = 10, message = "Mô tả khóa học phải có ít nhất 10 ký tự!")
    private String description;
}
