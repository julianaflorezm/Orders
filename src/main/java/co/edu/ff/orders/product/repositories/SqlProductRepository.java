package co.edu.ff.orders.product.repositories;

import co.edu.ff.orders.product.domain.*;
import co.edu.ff.orders.product.exceptions.ProductDoesNotExist;
import jdk.net.SocketFlow;
import org.postgresql.replication.fluent.physical.PhysicalReplicationOptions;
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
        ProductId id = ProductId.of(resultSet.getLong("ID"));
        Name name = Name.of(resultSet.getString("PRODUCTNAME"));
        Description description = Description.of(resultSet.getString("DESCRIPTION"));
        BasePrice basePrice = BasePrice.of(resultSet.getBigDecimal("BASEPRICE"));
        TaxRate taxRate = TaxRate.of(resultSet.getBigDecimal("TAXRATE"));
        ProductStatus status = ProductStatus.valueOf(resultSet.getString("STATUS"));
        InventoryQuantity inventoryQuantity = InventoryQuantity.of(resultSet.getInt("INVENTORYQUANTITY"));
        return Product.from(id, name, description, basePrice, taxRate, status, inventoryQuantity);
    };

    @Override
    public ProductOperation insertOne(ProductOperationRequest product) {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("PRODUCTNAME", product.getName().getValue());
        parameters.put("DESCRIPTION", product.getDescription().getValue());
        parameters.put("BASEPRICE", product.getBasePrice().getValue());
        parameters.put("TAXRATE", product.getTaxRate().getValue());
        parameters.put("STATUS", product.getProductStatus().toString());
        parameters.put("INVENTORYQUANTITY", product.getInventoryQuantity().getValue());

        Long number = (long)(simpleJdbcInsert.executeAndReturnKey(parameters));

        return ProductOperationSuccess.of(
                Product.from(
                        ProductId.of(number),
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
        String SQL = "SELECT ID, PRODUCTNAME, DESCRIPTION, BASEPRICE, TAXRATE, STATUS, INVENTORYQUANTITY FROM PRODUCTS WHERE PRODUCTNAME = ?";
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
        String SQL = "SELECT ID, PRODUCTNAME, DESCRIPTION, BASEPRICE, TAXRATE, STATUS, INVENTORYQUANTITY FROM PRODUCTS WHERE ID = ?";
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
        String SQL = "SELECT ID, PRODUCTNAME, DESCRIPTION, BASEPRICE, TAXRATE, STATUS, INVENTORYQUANTITY FROM PRODUCTS";
        List<Product> products = jdbcTemplate.query(SQL, mapper);
        return products;
    }

    @Override
    public ProductOperation updateOne(ProductId id, ProductOperationRequest product) {
        String SQL = "UPDATE PRODUCTS SET PRODUCTNAME = ?, DESCRIPTION = ?, BASEPRICE = ?, TAXRATE = ?, STATUS = ?, INVENTORYQUANTITY = ?" +
                "WHERE ID = ?";
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
        String SQL = "DELETE FROM PRODUCTS WHERE ID = ?";
        Optional<ProductOperation> product = findById(id);
        if(!product.isPresent()){
            ProductDoesNotExist exception = ProductDoesNotExist.of(id);
            return ProductOperationFailure.of(exception);
        }else{
            Number number = jdbcTemplate.update(SQL, id.getValue());
            return ProductOperationSuccess.of(product.get().value());
        }
    }
}
