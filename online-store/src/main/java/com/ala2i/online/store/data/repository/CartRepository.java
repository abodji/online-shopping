package com.ala2i.online.store.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ala2i.online.store.data.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
