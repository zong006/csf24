package vttp.batch4.csf.ecommerce.controllers;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import vttp.batch4.csf.ecommerce.models.Cart;
import vttp.batch4.csf.ecommerce.models.LineItem;
import vttp.batch4.csf.ecommerce.models.Order;
import vttp.batch4.csf.ecommerce.services.PurchaseOrderService;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

  @Autowired
  private PurchaseOrderService poSvc;

  // IMPORTANT: DO NOT MODIFY THIS METHOD.
  // If this method is changed, any assessment task relying on this method will
  // not be marked

  @PostMapping(path = "/order")
  public ResponseEntity<String> postOrder(@RequestBody String orderString) {
    
    InputStream is = new ByteArrayInputStream(orderString.getBytes());
    JsonReader jr = Json.createReader(is);
    JsonObject job = jr.readObject();

    // System.out.println(job.toString());
    // System.out.println("=========================");

    Order order = new Order();
    order.setAddress(job.getString("address"));
    order.setName(job.getString("name"));
    order.setComments(job.getString("comments"));
    order.setPriority(job.getBoolean("priority"));
    
    List<LineItem> lineItems = new LinkedList<>();
    JsonArray jsonLineItems = job.getJsonObject("cart").getJsonArray("lineItems");
    for (int i=0 ; i<jsonLineItems.size() ; i++){
      JsonObject li = jsonLineItems.getJsonObject(i);
      LineItem lineItem = new LineItem();

      lineItem.setProductId(li.getString("prodId"));
      lineItem.setQuantity(li.getInt("quantity"));
      lineItem.setName(li.getString("name"));
      lineItem.setPrice((float)li.getJsonNumber("price").doubleValue());

      lineItems.add(lineItem);
    }

    Cart cart = new Cart();
    cart.setLineItems(lineItems);
    order.setCart(cart);
    
    JsonObjectBuilder jobjBuilder = Json.createObjectBuilder();
    try {
      poSvc.createNewPurchaseOrder(order);
      System.out.println("order success");
    } catch (Exception e) {
      
      System.out.println("controller error!!!!");
      JsonObject errorMsg = jobjBuilder.add("message", e.getMessage()).build();
      return ResponseEntity.status(400).body(errorMsg.toString());
    }
    JsonObject successMsg = Json.createObjectBuilder().add("orderId", order.getOrderId()).build();

    return ResponseEntity.ok().body(successMsg.toString());
	 
  }
}
