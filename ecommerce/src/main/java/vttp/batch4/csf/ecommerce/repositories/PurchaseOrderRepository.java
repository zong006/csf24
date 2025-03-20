package vttp.batch4.csf.ecommerce.repositories;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch4.csf.ecommerce.models.LineItem;
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
    System.out.println("ok");
  }

  public void createOrderDetails(Order order){
    String SQL_INSERT_LINE_ITEMS = """
        insert into order_details
        (order_id, product_id, name, quantity, price)
        values
        (?, ?, ?, ?, ?)
        """;
    List<LineItem> lineItems = order.getCart().getLineItems();
    
    template.batchUpdate(SQL_INSERT_LINE_ITEMS, lineItems, lineItems.size(), 
                          (PreparedStatement ps, LineItem item) -> {
                            ps.setString(1, order.getOrderId());
                            ps.setString(2, item.getProductId());
                            ps.setString(3, item.getName());
                            ps.setInt(4, item.getQuantity());
                            ps.setDouble(5, item.getPrice());
                          }
    );
  }
}

