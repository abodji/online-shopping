package com.ala2i.online.store.data;

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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ADDRESS")
public class Address {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ADDRESS_ID")
    protected Long addressId;
    
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Addres type must be null")
    @Column(name = "ADDRESS_TYPE", length = 100)
    protected AddressType addressType;
    
    @Column(name = "COMPANY", length = 100)
    protected String company;
    
    @Column(name = "STREET", length = 100, nullable = false)
    @NotNull(message = "Street must be null")
    @Size(min = 2, max = 45, message = "Street must be between {min} - {max}")
    protected String street;
    
    @Column(name = "CITY", length = 100, nullable = false)
    @NotNull(message = "City must be null")
    @Size(min = 2, max = 45, message = "City must be between {min} - {max}")
    protected String city;
    
    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID", nullable = false, foreignKey = @ForeignKey(
        name = "FK_ADDRESS_COUNTRY_ID"
    ))
    protected Country country;
    
    @Column(name = "STATE_PROVINCE_REGION", length = 100, nullable = false)
    protected String stateProvinceRegion;
    
    @Column(name = "ZIP_CODE", length = 16, nullable = false)
    @NotNull(message = "Zip Code must be null")
    @Size(min = 2, max = 5, message = "Zip code must be between {min} - {max}")
    protected String zipCode;

    /*======================================================
     *          CONSTRUCTORS
     =======================================================*/

    public Address() {
    }

    public Address(AddressType addressType, String company, String street, String city, Country country, String stateProvinceRegion, String zipCode) {
        this.addressType = addressType;
        this.company = company;
        this.street = street;
        this.city = city;
        this.country = country;
        this.stateProvinceRegion = stateProvinceRegion;
        this.zipCode = zipCode;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getStateProvinceRegion() {
        return stateProvinceRegion;
    }

    public void setStateProvinceRegion(String stateProvinceRegion) {
        this.stateProvinceRegion = stateProvinceRegion;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    /*===================== Other methods =====================*/
    
	@Override
	public int hashCode() {
		return Objects.hash(addressType, city, company, country, stateProvinceRegion, street, zipCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null || getClass() != obj.getClass())
			return false;
		
		Address other = (Address) obj;
		return addressType == other.addressType 
				&& Objects.equals(city, other.city)
				&& Objects.equals(company, other.company) 
				&& Objects.equals(country, other.country)
				&& Objects.equals(stateProvinceRegion, other.stateProvinceRegion)
				&& Objects.equals(street, other.street) 
				&& Objects.equals(zipCode, other.zipCode);
	}

	@Override
	public String toString() {
		return String.format("%s (%s), Street %s, Zip Code: %s", city, country.getName(), street, zipCode);
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
