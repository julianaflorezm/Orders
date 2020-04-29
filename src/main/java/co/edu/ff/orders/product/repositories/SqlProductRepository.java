package co.edu.ff.orders.product.repositories;

import co.edu.ff.orders.product.domain.*;
import co.edu.ff.orders.product.exceptions.ProductDoesNotExist;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.*;

public class SqlProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public SqlProductRepository(JdbcTemplate jdbcTemplate, SimpleJdbcInsert simpleJdbcInsert) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = simpleJdbcInsert;
    }

    private final RowMapper<Product> mapper = (resultSet, i) -> {
        ProductId id = ProductId.of(resultSet.getLong("id"));
        Name name = Name.of(resultSet.getString("name"));
        Description description = Description.of(resultSet.getString("description"));
        BasePrice basePrice = BasePrice.of(resultSet.getBigDecimal("base_price"));
        TaxRate taxRate = TaxRate.of(resultSet.getBigDecimal("tax_rate"));
        ProductStatus status = ProductStatus.valueOf(resultSet.getString("status"));
        InventoryQuantity inventoryQuantity = InventoryQuantity.of(resultSet.getInt("inventory_quantity"));
        return Product.from(id, name, description, basePrice, taxRate, status, inventoryQuantity);
    };

    @Override
    public ProductOperation insertOne(ProductOperationRequest product) {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", product.getName().getValue());
        parameters.put("description", product.getDescription().getValue());
        parameters.put("base_price", product.getBasePrice().getValue());
        parameters.put("tax_rate", product.getTaxRate().getValue());
        parameters.put("status", product.getProductStatus().toString());
        parameters.put("inventory_quantity", product.getInventoryQuantity().getValue());

        Number number = simpleJdbcInsert.executeAndReturnKey(parameters);
        //Long id = (long)number; //error de casteo de java.Integer a Java.long
        Long id = Long.parseLong(String.format("%s",number)); //Find another way
        return ProductOperationSuccess.of(
                Product.from(
                        ProductId.of(id),
                        product.getName(),
                        product.getDescription(),
                        product.getBasePrice(),
                        product.getTaxRate(),
                        product.getProductStatus(),
                        product.getInventoryQuantity()
                ));
    }

    @Override
    public Optional<ProductOperation> findByName(Name name) {
        String SQL = "SELECT id, name, description, base_price, tax_rate, status, inventory_quantity FROM products WHERE name = ?";
        Object[] objects = {name.getValue()};
        try{
            Product productExistence = jdbcTemplate.queryForObject(SQL, objects, mapper);
            return Optional.ofNullable(ProductOperationSuccess.of(productExistence));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<ProductOperation> findById(ProductId id) {
        String SQL = "SELECT id, name, description, base_price, tax_rate, status, inventory_quantity FROM products WHERE id = ?";
        Object[] objects = {id.getValue()};
        try{
            Product productExistence = jdbcTemplate.queryForObject(SQL, objects, mapper);
            return Optional.ofNullable(ProductOperationSuccess.of(productExistence));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findAll() {
        String SQL = "SELECT id, name, description, base_price, tax_rate, status, inventory_quantity FROM products";
        List<Product> products = jdbcTemplate.query(SQL, mapper);
        return products;
    }

    @Override
    public ProductOperation updateOne(ProductId id, ProductOperationRequest product) {
        String SQL = "UPDATE products SET name = ?, description = ?, base_price = ?, tax_rate = ?, status = ?, inventory_quantity = ?" +
                "WHERE id = ?";
        Number number = jdbcTemplate.update(SQL,
                    product.getName().getValue(),
                    product.getDescription().getValue(),
                    product.getBasePrice().getValue(),
                    product.getTaxRate().getValue(),
                    product.getProductStatus().name(),
                    product.getInventoryQuantity().getValue(),
                    id.getValue());
        return ProductOperationSuccess.of(Product.from(
                    id,
                    product.getName(),
                    product.getDescription(),
                    product.getBasePrice(),
                    product.getTaxRate(),
                    product.getProductStatus(),
                    product.getInventoryQuantity()));
    }

    @Override
    public ProductOperation deleteOne(ProductId id) {
        String SQL = "DELETE FROM products WHERE id = ?";
        Optional<ProductOperation> product = findById(id);
        if(!product.isPresent()){
            ProductDoesNotExist exception = ProductDoesNotExist.of(id);
            return ProductOperationFailure.of(exception);
        }else{
            Number number = jdbcTemplate.update(SQL, id.getValue().intValue());
            return ProductOperationSuccess.of(product.get().value());
        }
    }
}
