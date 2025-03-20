package vttp.batch4.csf.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp.batch4.csf.ecommerce.models.Order;
import vttp.batch4.csf.ecommerce.repositories.PurchaseOrderRepository;

@Service
public class PurchaseOrderService {

  @Autowired
  private PurchaseOrderRepository poRepo;

  @Transactional
  public void createNewPurchaseOrder(Order order) {
    // TODO Task 3
      poRepo.create(order);
      poRepo.createOrderDetails(order);
  }
}
