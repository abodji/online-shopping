package com.ala2i.online.store.data.dao;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.ala2i.online.store.data.Photo;

public interface PhotoDAO {
	/**
	 * Writes uploaded image to disc
	 * @param imageFile A MultipartFile from the form to save
	 * @throws Exception
	 */
	public String savePhotoImageToDisck(MultipartFile imageFile) throws Exception;
	
	/**
	 * Saves photo to the database
	 * 
	 * @param photo Photo object to save
	 */
	public Photo save(Photo photo);
	
	/**
	 * Copy photo to disc and saves it into the database
	 * @param photo Photo object to save into the database
	 * @param imageFile A MultipartFile object from the form
	 * @return 
	 * @throws Exception
	 */
	public Photo save(Photo photo, MultipartFile imageFile) throws Exception;
	
	/**
	 * Loads a file from the file system or disc
	 * @param fileName The name of the file to load
	 * @return
	 */
	public Resource loadFile(String fileName);
	
	/**
	 * Path where images are stored
	 * @return
	 */
	public String getPath();
}
