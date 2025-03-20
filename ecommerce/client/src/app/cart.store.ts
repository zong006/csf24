
// TODO Task 2
// Use the following class to implement your store
import { ComponentStore } from "@ngrx/component-store";
import { LineItem } from "./models";
import { Injectable } from "@angular/core";

export interface cartState{
    cartSlice : LineItem[]
}

@Injectable({
    providedIn:'root'
})

export class CartStore extends ComponentStore<cartState>{
    constructor(){
        super({cartSlice : []})
    }

    readonly items$ = this.select(currState => currState.cartSlice);

    readonly additem$ = this.updater<LineItem>(
        (currState : cartState, itemsToAdd : LineItem) => {
            const newState : cartState = {
                cartSlice : [...currState.cartSlice, itemsToAdd]
            }
            
            return newState;
        }
    )

    readonly clearCart = this.updater((currState) => ({
        cartSlice : []
    })
    );

}
