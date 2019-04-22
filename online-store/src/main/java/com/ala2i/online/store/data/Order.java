package com.ala2i.online.store.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Alassani ABODJI <a href="mailto:abodjialassani@gmail.com"><abodjialassani@gmail.com></a>
 */
@Entity
@Table(name = "ORDERS")
public class Order implements Serializable {

	private static final long serialVersionUID = 3917475653516096386L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ORDER_ID")
    protected Long orderId;
    
    @Column(name = "ORDERED", nullable = false)
    protected LocalDateTime orderDate;
    
    @Column(name = "SHIPPED", nullable = true)
    protected LocalDate shipped;
    
    @ManyToOne
    @JoinColumn(
        name = "USER_ID", nullable = false, 
        foreignKey = @ForeignKey(name = "FK_ORDERS_USER_ID")
    )
    protected User user;
    
    @OneToMany(mappedBy = "order")
    protected List<OrderItem> orderItems = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    @Column(name = "ORDERS_STATUS", length = 45)
    protected OrderStatus status;
    
    @Column(name = "TOTAL")
    protected BigDecimal total;
    
    /*======================================================
     *          CONSTRUCTORS
     =======================================================*/

    public Order() {
    }

    public Order(LocalDateTime orderDate, LocalDate shipped, User user, OrderStatus status, BigDecimal total) {
        this.orderDate = orderDate;
        this.shipped = shipped;
        this.user = user;
        this.status = status;
        this.total = total;
    }
    
    /*======================================================
     *          GETTERS AND SETTERS 
     =======================================================*/

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getShipped() {
        return shipped;
    }

    public void setShipped(LocalDate shipped) {
        this.shipped = shipped;
    }

    public User getUser() {
		return user;
	}
    
    public void setUser(User user) {
		this.user = user;
	}

    public List<OrderItem> getItems() {
        return new ArrayList<>(orderItems);
    }

    public void setItems(List<OrderItem> items) {
        Objects.requireNonNull(items);
        
        this.orderItems.addAll(items);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
    	this.total = this.orderItems.stream()
    			.map(item -> item.getAmount())
    			.reduce(BigDecimal.ZERO, (b1, b2) -> b1.add(b2));
    	
        return total;
    }
    
    /*======================================================
     *          OTHER METHODS
     =======================================================*/

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.orderDate);
        hash = 17 * hash + Objects.hashCode(this.shipped);
        hash = 17 * hash + Objects.hashCode(this.user);
        hash = 17 * hash + Objects.hashCode(this.status);
        hash = 17 * hash + Objects.hashCode(this.total);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        
        if (!Objects.equals(this.orderDate, other.orderDate)) {
            return false;
        }
        if (!Objects.equals(this.shipped, other.shipped)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
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
