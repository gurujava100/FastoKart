package org.fastokart.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Later we attach User here
    @Column(unique = true, nullable = false)
    private String cartId; //session
    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<CartItem> items = new ArrayList<>();

  /*  public void addItem(CartItem item) {
        items.add(item);
        item.setCart(this);
    }*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}
