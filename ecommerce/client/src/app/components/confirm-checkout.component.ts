import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CartStore } from '../cart.store';
import { LineItem, Order, Cart } from '../models';
import { ProductService } from '../product.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-confirm-checkout',
  templateUrl: './confirm-checkout.component.html',
  styleUrl: './confirm-checkout.component.css',
  standalone:false
})
export class ConfirmCheckoutComponent implements OnInit{
  
  ngOnInit(): void {
    this.form = this.createForm();
    this.cartStore.items$.subscribe(
      (data) => this.items = data
    );
    this.cartStore.items$.subscribe(
      (cart) => {
        cart.forEach(i => {
          
          this.total += i.price * i.quantity
          
        })
      }
    )
    this.cart.lineItems = this.items;
  }

  // TODO Task 3
  fb = inject(FormBuilder);
  form !: FormGroup;

  cartStore = inject(CartStore);
  items !: LineItem[];
  total = 0;
  cart : Cart = {
    lineItems : []
  }

  private router = inject(Router)

  private productService = inject(ProductService);

  createForm():FormGroup {
    return this.fb.group({
      name: this.fb.control<string>('',[Validators.required]),
      address : this.fb.control<string>('', [Validators.required, Validators.minLength(3)]),
      priority : this.fb.control<boolean> (false),
      comments : this.fb.control<string>('')
    })
  }

  processForm(){
    const order : Order = this.form.value;
    
    order.cart = this.cart;
    console.info('>>>process form: ', order);

    this.productService.checkout(order)
    .subscribe(
      (response) => {
        console.info('>>> post form response: ', response)
        console.info('>>> status: ', response.status)
        alert('Order created. Order Id: ' + response.body?.orderId)
        if (response.status === 200){
          this.router.navigate(['/'])
        }
      }
    );
  }
  test(event:any){
    console.info('>> button: ', event.target.value)
  } 

}
