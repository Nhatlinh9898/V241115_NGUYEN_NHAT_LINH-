package ra.exam_webapp.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class DispatcherServletInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{HibernateConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{MVCConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter("UTF-8", true);
        return new Filter[]{filter};
    }
}



//Dưới đây là phiên bản của mã nguồn với các ghi chú chi tiết để giải thích từng phần:
//

//package ra.demoproject.config;
//
//import org.springframework.web.filter.CharacterEncodingFilter;
//import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
//
//import javax.servlet.Filter;
//
///**
// * DispatcherServletInit là lớp khởi tạo Dispatcher Servlet, cấu hình ứng dụng Spring MVC.
// * Nó kế thừa AbstractAnnotationConfigDispatcherServletInitializer để xác định các cài đặt cấu hình ứng dụng.
// */
//public class DispatcherServletInit extends AbstractAnnotationConfigDispatcherServletInitializer {
//
//    /**
//     * Xác định các lớp cấu hình gốc (Root Configuration).
//     * Các lớp này chịu trách nhiệm cấu hình toàn bộ ứng dụng, bao gồm Hibernate, nguồn dữ liệu, giao dịch, v.v.
//     *
//     * @return Mảng các lớp cấu hình gốc của ứng dụng.
//     */
//    @Override
//    protected Class<?>[] getRootConfigClasses() {
//        return new Class[]{HibernateConfig.class}; // Cấu hình Hibernate và các thành phần backend
//    }
//
//    /**
//     * Xác định các lớp cấu hình dành riêng cho Servlet.
//     * Đây là nơi chứa các cấu hình liên quan đến Spring MVC như View Resolver, Controller Mapping, v.v.
//     *
//     * @return Mảng các lớp cấu hình dành riêng cho Servlet.
//     */
//    @Override
//    protected Class<?>[] getServletConfigClasses() {
//        return new Class[]{MVCConfig.class}; // Cấu hình Spring MVC
//    }
//
//    /**
//     * Xác định ánh xạ Servlet.
//     * "/"" có nghĩa là tất cả các request sẽ được xử lý bởi DispatcherServlet.
//     *
//     * @return Mảng ánh xạ Servlet.
//     */
//    @Override
//    protected String[] getServletMappings() {
//        return new String[]{"/"}; // Mọi request sẽ được định tuyến thông qua DispatcherServlet
//    }
//
//    /**
//     * Cấu hình các bộ lọc (Filters) cho Dispatcher Servlet.
//     * CharacterEncodingFilter đảm bảo rằng các request và response sử dụng mã hóa UTF-8.
//     *
//     * @return Mảng các bộ lọc được áp dụng.
//     */
//    @Override
//    protected Filter[] getServletFilters() {
//        return new Filter[]{new CharacterEncodingFilter("UTF-8", true)}; // Bộ lọc mã hóa ký tự UTF-8
//    }
//}

//
//        ### Giải thích tổng thể:
//        - **Lớp `DispatcherServletInit`**: Lớp này mở rộng `AbstractAnnotationConfigDispatcherServletInitializer`, cho phép cấu hình ứng dụng Spring thông qua các lớp Java thay vì tệp XML.
//        - **Phương thức `getRootConfigClasses()`**: Cung cấp các lớp cấu hình chính, thường bao gồm cài đặt Hibernate, kết nối cơ sở dữ liệu và các thành phần backend khác.
//- **Phương thức `getServletConfigClasses()`**: Chỉ định các lớp cấu hình liên quan đến Spring MVC, chẳng hạn như bộ điều khiển, view resolver, và các cài đặt frontend khác.
//        - **Phương thức `getServletMappings()`**: Xác định ánh xạ URL, quyết định cách DispatcherServlet xử lý các request từ client.
//        - **Phương thức `getServletFilters()`**: Cấu hình bộ lọc để xử lý mã hóa ký tự giúp tránh lỗi khi làm việc với Unicode và dữ liệu không phải ASCII.
//
//Việc thêm các ghi chú này giúp mã nguồn dễ hiểu hơn, giúp bạn hoặc các lập trình viên khác dễ dàng duy trì và mở rộng chức năng trong tương lai. Bạn có cần thêm ghi chú nào cụ thể hơn không? 🚀
