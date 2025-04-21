package ra.exam_webapp.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import static ra.exam_webapp.service.student.StudentServiceImpl.UPLOAD_DIR;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "ra.exam_webapp")
public class MVCConfig implements WebMvcConfigurer, ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    // cấu hình view thymeleaf
    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }
    @Bean
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setEnableSpringELCompiler(true);
        engine.setTemplateResolver(templateResolver());
        return engine;
    }
    @Bean
    public ThymeleafViewResolver viewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }


    // cấu hình upload file
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(20971520); // 20MB
        return multipartResolver;
    }
    // cấu hình đường dẫn
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/uploads/**")
//                .addResourceLocations("/uploads/");
//    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + UPLOAD_DIR);
    }


}

//Dưới đây là phiên bản mã nguồn của bạn với các ghi chú chi tiết giúp làm rõ từng phần của cấu hình MVC trong Spring:
//

//package ra.demoproject.config;
//
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.multipart.commons.CommonsMultipartResolver;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
//import org.thymeleaf.spring5.view.ThymeleafViewResolver;
//import org.thymeleaf.templatemode.TemplateMode;
//
///**
// * Cấu hình Spring MVC cho ứng dụng.
// * Sử dụng Thymeleaf làm công cụ render view, hỗ trợ upload file, và định nghĩa các tài nguyên tĩnh.
// */
//@Configuration
//@EnableWebMvc // Kích hoạt Spring MVC.
//@ComponentScan(basePackages = "ra.demoproject") // Quét các component trong package chính.
//public class MVCConfig implements WebMvcConfigurer, ApplicationContextAware {
//
//    private ApplicationContext applicationContext;
//
//    /**
//     * Thiết lập ApplicationContext để sử dụng trong việc cấu hình các thành phần khác.
//     *
//     * @param applicationContext Context của Spring được truyền vào
//     */
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }
//
//    // ---------------- Cấu hình Thymeleaf ----------------
//
//    /**
//     * Cấu hình TemplateResolver cho Thymeleaf, giúp xác định vị trí file HTML và cách xử lý nội dung.
//     *
//     * @return Đối tượng SpringResourceTemplateResolver đã cấu hình.
//     */
//    @Bean
//    public SpringResourceTemplateResolver templateResolver() {
//        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
//        templateResolver.setApplicationContext(applicationContext); // Sử dụng context của ứng dụng.
//        templateResolver.setPrefix("/views/"); // Định nghĩa thư mục chứa các template Thymeleaf.
//        templateResolver.setSuffix(".html"); // Định nghĩa phần mở rộng của file template.
//        templateResolver.setTemplateMode(TemplateMode.HTML); // Chế độ xử lý HTML.
//        templateResolver.setCharacterEncoding("UTF-8"); // Đảm bảo hiển thị tiếng Việt và Unicode đúng cách.
//        return templateResolver;
//    }
//
//    /**
//     * Khởi tạo SpringTemplateEngine để quản lý render template.
//     *
//     * @return Đối tượng SpringTemplateEngine đã cấu hình.
//     */
//    @Bean
//    public SpringTemplateEngine templateEngine() {
//        SpringTemplateEngine engine = new SpringTemplateEngine();
//        engine.setEnableSpringELCompiler(true); // Kích hoạt Spring Expression Language (SpEL) Compiler.
//        engine.setTemplateResolver(templateResolver()); // Áp dụng TemplateResolver đã cấu hình.
//        return engine;
//    }
//
//    /**
//     * Cấu hình Thymeleaf View Resolver, giúp Spring MVC biết cách tìm và render giao diện.
//     *
//     * @return Đối tượng ThymeleafViewResolver đã cấu hình.
//     */
//    @Bean
//    public ThymeleafViewResolver viewResolver() {
//        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
//        viewResolver.setTemplateEngine(templateEngine()); // Sử dụng SpringTemplateEngine để xử lý template.
//        viewResolver.setCharacterEncoding("UTF-8"); // Đảm bảo hỗ trợ Unicode.
//        return viewResolver;
//    }
//
//    // ---------------- Cấu hình Upload File ----------------
//
//    /**
//     * Cấu hình bộ xử lý upload file, giới hạn dung lượng tối đa là 20MB.
//     *
//     * @return Đối tượng CommonsMultipartResolver đã cấu hình.
//     */
//    @Bean(name = "multipartResolver")
//    public CommonsMultipartResolver multipartResolver() {
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setMaxUploadSize(20971520); // Giới hạn file upload: 20MB.
//        return multipartResolver;
//    }
//
//    // ---------------- Cấu hình Đường Dẫn Tài Nguyên ----------------
//
//    /**
//     * Định nghĩa các đường dẫn tài nguyên tĩnh, giúp truy xuất file tĩnh trong ứng dụng.
//     *
//     * @param registry Đối tượng ResourceHandlerRegistry dùng để đăng ký đường dẫn.
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/uploads/**") // Bất kỳ yêu cầu nào đến /uploads/** sẽ được xử lý.
//                .addResourceLocations("/uploads/"); // Tài nguyên sẽ được lấy từ thư mục /uploads/.
//    }
//}

//
//        ### **Giải thích tổng thể**:
//        - **Kích hoạt Spring MVC**: `@EnableWebMvc` đảm bảo ứng dụng được thiết lập đúng để xử lý request và render view.
//- **Quét các package**: `@ComponentScan("ra.demoproject")` giúp Spring tìm thấy các component tự động.
//        - **Cấu hình Thymeleaf**:
//        - `templateResolver()` xác định thư mục và kiểu tệp HTML.
//        - `templateEngine()` kích hoạt trình biên dịch Thymeleaf với Spring EL Compiler.
//        - `viewResolver()` giúp Spring hiểu cách render view bằng Thymeleaf.
//        - **Hỗ trợ upload file**: `multipartResolver()` cho phép người dùng tải lên file, với dung lượng tối đa 20MB.
//- **Định nghĩa đường dẫn tài nguyên**: `addResourceHandlers()` giúp truy xuất tài nguyên tĩnh như ảnh, video từ thư mục `/uploads/`.
//
//Các ghi chú này sẽ giúp bạn dễ dàng hiểu và bảo trì mã nguồn hơn. Có phần nào bạn muốn làm rõ thêm không? 🚀