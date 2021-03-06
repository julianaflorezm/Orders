package co.edu.ff.orders.product.configuration;

import co.edu.ff.orders.product.repositories.ProductRepository;
import co.edu.ff.orders.product.repositories.SqlProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

@Configuration
public class ProductRepositoryConfiguration {

    @Bean
    public ProductRepository productRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("PRODUCTS")
                .usingGeneratedKeyColumns("id");

        return new SqlProductRepository(jdbcTemplate, simpleJdbcInsert);
    }
}
