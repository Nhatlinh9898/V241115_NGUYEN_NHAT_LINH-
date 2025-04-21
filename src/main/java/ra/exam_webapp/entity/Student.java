package ra.exam_webapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Lớp thực thể `Student` đại diện cho bảng `student` trong cơ sở dữ liệu.
 * - Chứa thông tin về sinh viên như tên, email, số điện thoại, giới tính, ngày sinh, khóa học.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "student")
public class Student {

    @Id
    @Column(name = "id", length = 5, nullable = false) // Kiểu CHAR(5) - Mã sinh viên
    private String id;

    @Column(name = "name", length = 200, nullable = false) // Tên sinh viên - Không null
    private String name;

    @Column(name = "email", length = 255, nullable = false, unique = true) // Email duy nhất, giới hạn độ dài
    private String email;

    @Column(name = "phone", length = 15, nullable = false, unique = true) // Số điện thoại duy nhất
    private String phone;

    @Column(name = "sex", nullable = false) // Giới tính - BIT(1)
    private Boolean sex;

    @Column(name = "bod", nullable = false) // Ngày sinh - DATE
    private LocalDate bod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false) // Khóa ngoại liên kết với Course
    private Course course;

    @Column(name = "avatar", columnDefinition = "TEXT") // Ảnh đại diện
    private String avatar;

    @Column(name = "status", nullable = false) // Trạng thái - BIT(1)
    private Boolean status;

    public Student(String id, String name, String email, String phone, Boolean sex, LocalDate bod, Optional<Course> byId, String uploadPath, Boolean status) {
    }

    public Student(String avatar) {
        this.avatar = avatar;
    }

    public Student(String avatar, LocalDate bod, Course course, String email, String id, String name, String phone, Boolean sex, Boolean status) {
        this.avatar = avatar;
        this.bod = bod;
        this.course = course;
        this.email = email;
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.sex = sex;
        this.status = status;
    }

    /**
     * Kiểm tra tính hợp lệ của dữ liệu sinh viên trước khi lưu hoặc cập nhật.
     * - Kiểm tra định dạng email.
     * - Kiểm tra số điện thoại hợp lệ.
     */
    @PrePersist
    @PreUpdate
    private void validateStudentData() {
        if (!phone.matches("^(0[35789])[0-9]{8}$")) {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ!");
        }
        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            throw new IllegalArgumentException("Email không hợp lệ!");
        }
    }
}
