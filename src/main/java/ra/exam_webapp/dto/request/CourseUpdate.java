package ra.exam_webapp.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;

import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * DTO chứa dữ liệu cập nhật khóa học (Course).
 * Dùng để truyền dữ liệu từ frontend về backend khi cập nhật thông tin khóa học.
 */
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class CourseUpdate {

    /**
     * ID khóa học, bắt buộc phải có khi cập nhật.
     */
    @NotNull(message = "ID khóa học không được để trống!")
    private Integer id;

    /**
     * Tên khóa học, không được để trống và có giới hạn độ dài tối đa.
     */
    @NotBlank(message = "Tên khóa học không được để trống!")
    @Length(max = 100, message = "Tên khóa học không được vượt quá 100 ký tự!")
    private String name;

    /**
     * Mô tả khóa học, không được để trống và có độ dài tối thiểu.
     */
    @NotBlank(message = "Mô tả khóa học không được để trống!")
    @Length(min = 10, message = "Mô tả khóa học phải có ít nhất 10 ký tự!")
    private String description;

    /**
     * Constructor cho việc cập nhật khóa học mà không cần trạng thái.
     *
     * @param id ID của khóa học cần cập nhật.
     * @param name Tên khóa học mới.
     * @param description Mô tả khóa học mới.
     */
    public CourseUpdate(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
