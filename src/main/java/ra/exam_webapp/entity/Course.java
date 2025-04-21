package ra.exam_webapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Thực thể `Course` đại diện cho bảng `course` trong cơ sở dữ liệu.
 * Lưu trữ thông tin về khóa học, bao gồm tên, mô tả và danh sách sinh viên.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "course") // Định nghĩa bảng trong MySQL
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Khóa chính, tự tăng
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 100, nullable = false, unique = true) // Không trùng lặp
    private String name;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false) // Not null
    private String description;

    /**
     * Danh sách sinh viên thuộc khóa học.
     * - Một khóa học có thể có nhiều sinh viên.
     * - Nếu khóa học bị xóa, sinh viên vẫn có thể tồn tại (cascade = MERGE).
     */
    @OneToMany(mappedBy = "course", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Student> students;
    public Course(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

}
