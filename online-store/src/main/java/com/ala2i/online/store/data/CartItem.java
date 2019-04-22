package com.ala2i.online.store.data;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CART_ITEM")
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CART_ITEM_ID")
	protected Long cartItemId;
	
	@ManyToOne
	@JoinColumn(name = "CART_ID", insertable = false, updatable = false, nullable = true,
	    foreignKey = @ForeignKey(name = "FK_CART_ITEM_CART_ID")
	)
	protected Cart cart;
	
	@ManyToOne
    @JoinColumn(name = "PRODUCT_ID", insertable = false, updatable = false, nullable = false,
        foreignKey = @ForeignKey(name = "FK_CART_ITEM_PRODUCT_ID")
    )
	protected Product product;
	
	@Column(name = "UNIT_PRICE")
    protected BigDecimal unitPrice;
	
	@Column(name = "QUANTITY")
    protected Integer quantity;
	
	/*======================================================
     *          CONSTRUCTORS
     =======================================================*/

    public CartItem() {
    }
    
    public CartItem(Cart cart, Product product, BigDecimal unitPrice, Integer quantity) {
		this.cart = cart;
		this.product = product;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
	}
    
    /*======================================================
     *          GETTERS AND SETTERS
     =======================================================*/

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
		return product;
	}

    public void setProduct(Product product) {
		this.product = product;
	}

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
    
    
    public BigDecimal getAmount() {
        return getUnitPrice().multiply(BigDecimal.valueOf(quantity));
    }
    
    /*======================================================
     *          OTHER METHODS
     =======================================================*/

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.quantity);
        hash = 89 * hash + Objects.hashCode(this.product);
        hash = 89 * hash + Objects.hashCode(this.unitPrice);
        hash = 89 * hash + Objects.hashCode(this.cart);
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
        
        final CartItem other = (CartItem) obj;
        if (!Objects.equals(this.quantity, other.quantity)) {
            return false;
        }
        
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        
        if (!Objects.equals(this.unitPrice, other.unitPrice)) {
            return false;
        }
        
        if (!Objects.equals(this.cart, other.cart)) {
            return false;
        }
        
        return true;
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
