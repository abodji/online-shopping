package com.ala2i.online.store.data.dao;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ala2i.online.store.data.Photo;
import com.ala2i.online.store.data.repository.PhotoRepository;
import com.ala2i.online.store.exceptions.FileStorageException;

@Component
public class PhotoDAOImpl implements PhotoDAO {
	
	private final Path fileStorageLocation;
	private final String path = "/assets/uploads/";
	
	@Autowired
	private PhotoRepository photoRepository;
	
	public PhotoDAOImpl() {
		this.fileStorageLocation =  Paths.get("./src/main/resources/static/" + path).toAbsolutePath().normalize();
		
		try {
			if(Files.notExists(fileStorageLocation))
				Files.createDirectory(this.fileStorageLocation);
		} catch (IOException ioe) {
			throw new FileStorageException("Could not the directory where the uploaded files will be stored.", ioe);
		}
	}
	
	@Override
	public String savePhotoImageToDisck(MultipartFile imageFile) throws Exception {
		String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
		
		try {
			if(fileName.contains(".."))
				throw new FileStorageException("Sorry ! File name contains invalid sequence " + fileName);
			
			// Copy file to the target location (replacing the existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(imageFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
		} catch (IOException ioe) {
			throw new FileStorageException(String.format("Could not store file %s. Please, try again.", fileName));
		}
		
		return fileName;
	}
	
	@Override
	public Photo save(Photo photo) {
		return photoRepository.save(photo);	
	}
	
	@Override
	public Photo save(Photo photo, MultipartFile imageFile) throws Exception {
		savePhotoImageToDisck(imageFile);

		return save(photo);	
	}
	
	public void deleteFilesOnDisc() {
		FileSystemUtils.deleteRecursively(fileStorageLocation.toFile());
	}
	
	@Override
	public Resource loadFile(String fileName) {
		Path pathToFile = fileStorageLocation.resolve(fileName);
		Resource resource;
		
		try {
			resource = new UrlResource(pathToFile.toUri());
		} catch (MalformedURLException e) {
			throw new FileStorageException("Fail to load resource from file system");
		}
		
		if(resource.exists() || resource.isReadable())
			return resource;
		else
			throw new FileStorageException("Unexisting or unreadable resource");
	}
	
	@Override
	public String getPath() {
		return path;
	}
}
