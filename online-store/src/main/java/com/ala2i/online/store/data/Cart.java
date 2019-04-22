package com.ala2i.online.store.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CART")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CART_ID")
	protected Long cartId;
	
	@OneToOne
	@JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(name = "FK_CART_USER_ID"), updatable = false)
	protected User user;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "cart")
	protected List<CartItem> cartItems = new ArrayList<>();
	
	@Column(name = "TOTAL")
    protected BigDecimal total;
	
	@Column(name = "CREATED_ON")
    protected LocalDateTime createdOn = LocalDateTime.now();
	
	/*======================================================
     *          CONSTRUCTORS
     =======================================================*/

    public Cart() {
    }

    public Cart(User user) {
        this.user = user;
    }
    
    /*======================================================
     *          GETTERS AND SETTERS
     =======================================================*/

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems);
    }

    public void setItems(List<CartItem> cartItems) {
        Objects.requireNonNull(cartItems);
        
        cartItems.forEach(this::addItem);
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public User getUser() {
		return user;
	}
    
    public void setUser(User user) {
		this.user = user;
	}

    public BigDecimal getTotal() {
    	this.total = this.cartItems.stream()
    			.map(item -> item.getAmount())
    			.reduce(BigDecimal.ZERO, (b1, b2) -> b1.add(b2));
    	
        return total;
    }
    
    /*======================================================
     *          OTHER METHODS
     =======================================================*/

    public int getCount() {
        return cartItems.size();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.user);
        hash = 97 * hash + Objects.hashCode(this.createdOn);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        final Cart other = (Cart) obj;
        return Objects.equals(this.user, other.user) && Objects.equals(this.createdOn, other.createdOn);
    }

    public void addItem(CartItem item) {
        Objects.requireNonNull(item, "You cannot add an item that does not exist!");
        
        Optional<CartItem> optCI =  this.cartItems.stream()
            .filter(i -> i.getProduct().equals(item.getProduct()))
            .findFirst();
        
        if(optCI.isPresent()) {
            optCI.get().setQuantity(optCI.get().getQuantity() + item.getQuantity());
        } else {
            this.cartItems.add(item);
        }  
    }
    
    public void setQuantity(CartItem item) {
        Objects.requireNonNull(item, "You cannot add an item that does not exist!");
        
        this.cartItems.stream()
            .filter(i -> i.getProduct().equals(item.getProduct()))
            .findFirst()
            .ifPresent(i -> i.setQuantity(item.getQuantity()));
    }
    
    public void update(CartItem item) {
        Objects.requireNonNull(item, "You cannot add an item that does not exist!");
        
        this.cartItems.stream().filter(i -> i.getProduct().equals(item.getProduct()))
            .findFirst()
            .ifPresent(i -> i.setQuantity(i.getQuantity() + item.getQuantity()));
    }
    
    public void remove(CartItem item) {
        if(item == null)
            return;
        
        this.cartItems.removeIf(i -> i.getProduct().equals(item.getProduct()));
    }
}

/*=============================================================================
 * Copyright (C) 2017 ALA2I <http://ala2i.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 =============================================================================*/
