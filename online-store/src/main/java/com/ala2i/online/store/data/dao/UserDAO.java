package com.ala2i.online.store.data.dao;

import java.util.List;
import java.util.stream.Stream;

import com.ala2i.online.store.data.User;

public interface UserDAO {

	public boolean exists(User user);

	public boolean existsByEmail(String email);

	public boolean exists(String username);

	public boolean exists(Long userId);

	public void delete(long userId);

	public void deleteSelected(String[] selectedIds);

	public void deleteSelected(Long[] productIds);

	public void deleteSelected(Stream<Long> productIds);

	public void deleteAll();

	public User save(User user);

	public List<User> getAllUsers();

	public User getUser(Long userId);

	public User getUserByEmail(String email);

	public User getUserByUsername(String username);
}
