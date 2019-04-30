package com.ala2i.online.store.data;

import java.io.Serializable;
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
@Table(name = "PHOTOS")
public class Photo implements Serializable{

	private static final long serialVersionUID = 547406060590899058L;
	
	@Id
	@Column(name = "PHOTO_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long photoId;
	
	@Column(name = "FILE_NAME")
	protected String fileName;
	
	@Column(name = "PATH")
	protected String path;
	
	protected transient String fileDownloadUri;
	
	protected transient long size;
	
	protected transient String fileType;
	
	@Column(name = "COMMENTS", length = 3000)
	protected String comments;
	
	@Column(name = "PHOTOGRAPHER")
	protected String photographer;
	
	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID", foreignKey = @ForeignKey(name = "FK_PHOTOS_PRODUCT_ID"))
	protected Product product;
	
	/*===================== Constructors =====================*/
	
	public Photo() {
		
	}

	public Photo(String fileName, String path, String photographer, Product product) {
		this.fileName = fileName;
		this.path = path;
		this.photographer = photographer;
		this.product = product;
	}
	
	/*===================== Getters and Setters =====================*/

	public Long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getPhotographer() {
		return photographer;
	}

	public void setPhotographer(String photographer) {
		this.photographer = photographer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}	

	public String getFileDownloadUri() {
		return fileDownloadUri;
	}

	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	/*===================== Other methods =====================*/
	
	@Override
	public int hashCode() {
		return Objects.hash(fileName, path, photographer, product);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null || getClass() != obj.getClass())
			return false;
		
		Photo other = (Photo) obj;
		return Objects.equals(fileName, other.fileName) 
				&& Objects.equals(path, other.path)
				&& Objects.equals(photographer, other.photographer) 
				&& Objects.equals(product, other.product);
	}
	

}
