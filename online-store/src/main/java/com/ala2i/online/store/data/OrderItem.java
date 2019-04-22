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
@Table(name = "ORDER_ITEM")
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ORDER_ITEM_ID")
	protected Long orderItemId;
	
	@ManyToOne
	@JoinColumn(name = "ORDER_ID", insertable = false, updatable = false, nullable = true,
	    foreignKey = @ForeignKey(name = "FK_ORDER_ITEM_ORDER_ID")
	)
	protected Order order;
	
	@ManyToOne
    @JoinColumn(name = "PRODUCT_ID", insertable = false, updatable = false, nullable = false,
        foreignKey = @ForeignKey(name = "FK_ORDER_ITEM_PRODUCT_ID")
    )
	protected Product product;
	
	@Column(name = "UNIT_PRICE")
    protected BigDecimal unitPrice;
	
	@Column(name = "QUANTITY")
    protected Integer quantity;
	
	/*======================================================
     *          CONSTRUCTORS
     =======================================================*/

    public OrderItem() {
    }
    
    public OrderItem(Order order, Product product, BigDecimal unitPrice, Integer quantity) {
		this.order = order;
		this.product = product;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
	}
    
    /*======================================================
     *          GETTERS AND SETTERS
     =======================================================*/

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
        hash = 89 * hash + Objects.hashCode(this.order);
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
        
        final OrderItem other = (OrderItem) obj;
        if (!Objects.equals(this.quantity, other.quantity)) {
            return false;
        }
        
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        
        if (!Objects.equals(this.unitPrice, other.unitPrice)) {
            return false;
        }
        
        if (!Objects.equals(this.order, other.order)) {
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
