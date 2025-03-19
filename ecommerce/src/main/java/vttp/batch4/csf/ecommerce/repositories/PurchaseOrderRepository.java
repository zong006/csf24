package vttp.batch4.csf.ecommerce.repositories;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch4.csf.ecommerce.models.Order;

@Repository
public class PurchaseOrderRepository {

  @Autowired
  private JdbcTemplate template;

  public void create(Order order) {
    // TODO Task 3
    String SQL_INSERT_ORDER = """
        insert into orders
        (order_id, name, address, priority, order_date, comments)
        values 
        (?, ?, ?, ?, ?, ?)
        """;

    Date orderDate = new Date(order.getDate().getTime());
    template.update(SQL_INSERT_ORDER, order.getOrderId(), order.getName(), order.getAddress(), order.getPriority(), orderDate, order.getComments());
    
    // template.update(SQL_INSERT_ORDER, order.getOrderId(), null, order.getAddress(), order.getPriority(), orderDate, order.getComments());
    
  }
}
