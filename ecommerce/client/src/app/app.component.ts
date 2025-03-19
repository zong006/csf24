import { Component, OnInit, inject } from '@angular/core';
import {Observable} from 'rxjs';
import {Router} from '@angular/router';
import { ProductService } from './product.service';
import { CartStore } from './cart.store';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  standalone: false
})
export class AppComponent implements OnInit {

  // NOTE: you are free to modify this component

  private router = inject(Router)
  private productSvc = inject(ProductService)
  private cartStore = inject(CartStore);

  itemCount!: number

  ngOnInit(): void {
    this.cartStore.items$.subscribe(
      (cartState) => this.itemCount=cartState.length
    )
  }

  checkout(): void {
    if (this.itemCount === 0){
      alert('Cart is empty!')
    }
    this.router.navigate([ '/checkout' ])
  }

  isCartEmpty(){
    return this.itemCount === 0;
  }
  
}
