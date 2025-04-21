package ra.exam_webapp.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource source = new DriverManagerDataSource();
        source.setDriverClassName("com.mysql.cj.jdbc.Driver");
        source.setUrl("jdbc:mysql://localhost:3306/WebApp_db?createDatabaseIfNotExist=true");
        source.setUsername("root");
        source.setPassword("Nhat68linh68#");
        return source;
    }

    public Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update"); // tự đong tạo CSDL cũng như các bang (DDL)
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect"); // cung cấp phiên bản và Hệ QTCSDL tương thích
        properties.setProperty("hibernate.show_sql", "true"); // hiển thị câu SQL mà hibernate thực thi
        properties.setProperty("hibernate.format_sql", "true"); // format câu SQL mà hibernate thực thi
        return properties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory =
                new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPackagesToScan("ra/exam_webapp/entity"); // phat hiện các ClassMapping vs Table
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactory.setJpaProperties(additionalProperties());
        return entityManagerFactory;
    }

    @Bean
    @Qualifier(value = "entityManager")
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }


}



//Dưới đây là phiên bản mã nguồn của bạn với các ghi chú chi tiết, giúp giải thích từng phần của cấu hình Hibernate:
//
//        ```java
//package ra.demoproject.config;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.JpaVendorAdapter;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//import java.util.Properties;
//
///**
// * HibernateConfig cấu hình Hibernate trong ứng dụng Spring Boot,
// * bao gồm cấu hình kết nối CSDL, EntityManagerFactory và TransactionManager.
// */
//@Configuration
//@EnableTransactionManagement // Bật quản lý giao dịch tự động trong Spring.
//public class HibernateConfig {
//
//    /**
//     * Cấu hình nguồn dữ liệu (DataSource).
//     * Sử dụng DriverManagerDataSource để cung cấp thông tin kết nối MySQL.
//     *
//     * @return Đối tượng DataSource cấu hình sẵn.
//     */
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource source = new DriverManagerDataSource();
//        source.setDriverClassName("com.mysql.cj.jdbc.Driver"); // Định nghĩa driver kết nối MySQL.
//        source.setUrl("jdbc:mysql://localhost:3306/final_project?createDatabaseIfNotExist=true"); // Địa chỉ CSDL.
//        source.setUsername("root"); // Tên đăng nhập MySQL.
//        source.setPassword("Nhat68linh68#"); // Mật khẩu MySQL.
//        return source;
//    }
//
//    /**
//     * Thiết lập các thuộc tính bổ sung cho Hibernate.
//     * Các thuộc tính này điều chỉnh hành vi của Hibernate khi tương tác với CSDL.
//     *
//     * @return Đối tượng Properties chứa các cấu hình Hibernate.
//     */
//    public Properties additionalProperties() {
//        Properties properties = new Properties();
//        properties.setProperty("hibernate.hbm2ddl.auto", "update"); // Hibernate tự động cập nhật hoặc tạo bảng nếu chưa có.
//        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect"); // Hibernate sử dụng dialect phù hợp với MySQL.
//        properties.setProperty("hibernate.show_sql", "true"); // Hiển thị câu lệnh SQL do Hibernate thực thi trên console.
//        properties.setProperty("hibernate.format_sql", "true"); // Định dạng câu SQL để dễ đọc.
//        return properties;
//    }
//
//    /**
//     * Cấu hình EntityManagerFactory, nơi Hibernate quản lý các entity và tương tác với CSDL.
//     *
//     * @return Đối tượng LocalContainerEntityManagerFactoryBean chứa cấu hình Hibernate.
//     */
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactory.setDataSource(dataSource()); // Thiết lập nguồn dữ liệu.
//        entityManagerFactory.setPackagesToScan("ra.demoproject.entity"); // Hibernate sẽ quét các entity trong package này.
//        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
//        entityManagerFactory.setJpaProperties(additionalProperties()); // Áp dụng các thuộc tính Hibernate.
//        return entityManagerFactory;
//    }
//
//    /**
//     * Tạo Bean EntityManager để thao tác với CSDL.
//     *
//     * @param entityManagerFactory Factory cung cấp EntityManager.
//     * @return Đối tượng EntityManager.
//     */
//    @Bean
//    @Qualifier(value = "entityManager")
//    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
//        return entityManagerFactory.createEntityManager();
//    }
//
//    /**
//     * Cấu hình TransactionManager để quản lý giao dịch.
//     *
//     * @param entityManagerFactory Factory EntityManager.
//     * @return Đối tượng PlatformTransactionManager.
//     */
//    @Bean
//    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory); // Thiết lập EntityManagerFactory cho TransactionManager.
//        return transactionManager;
//    }
//}
//```
//
//        ### Giải thích tổng thể:
//        - **`@Configuration`**: Đánh dấu đây là một lớp cấu hình Spring.
//- **`@EnableTransactionManagement`**: Kích hoạt quản lý giao dịch trong Spring.
//        - **`dataSource()`**: Cấu hình nguồn dữ liệu MySQL, với thông tin đăng nhập và URL CSDL.
//- **`additionalProperties()`**: Xác định các thuộc tính Hibernate như chế độ tạo bảng, dialect, hiển thị SQL.
//- **`entityManagerFactory()`**: Cấu hình EntityManagerFactory để Hibernate có thể quản lý các entity.
//- **`entityManager()`**: Tạo EntityManager để thao tác với CSDL.
//- **`transactionManager()`**: Cấu hình TransactionManager để đảm bảo các giao dịch được xử lý đúng cách.
//
//Việc thêm các ghi chú này giúp mã nguồn dễ hiểu hơn, tạo sự thuận tiện khi bảo trì hoặc mở rộng hệ thống. Bạn có muốn bổ sung thêm thông tin hoặc điều chỉnh gì không? 🚀
