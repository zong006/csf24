package vttp.batch4.csf.ecommerce.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
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
  public ResponseEntity<String> postOrder(@RequestBody Order order) {
    System.out.println(order.toString());
    
    // TODO Task 3
    JsonObjectBuilder job = Json.createObjectBuilder();
    try {
      poSvc.createNewPurchaseOrder(order);
      System.out.println("order success");
    } catch (Exception e) {
      
      System.out.println("controller error!!!!");
      JsonObject errorMsg = job.add("message", e.getMessage()).build();
      return ResponseEntity.status(400).body(errorMsg.toString());
    }
    JsonObject successMsg = Json.createObjectBuilder().add("orderId", order.getOrderId()).build();

    return ResponseEntity.ok().body(successMsg.toString());
	 
  }
}
