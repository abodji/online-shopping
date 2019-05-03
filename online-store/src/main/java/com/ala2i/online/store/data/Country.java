package com.ala2i.online.store.data;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Alassani ABODJI <a href="mailto:abodjialassani@gmail.com"><abodjialassani@gmail.com></a>
 */
@Entity
@Table(name = "COUNTRY")
public class Country implements Serializable{
    private static final long serialVersionUID = -5016384215094193913L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COUNTRY_ID")
    protected Long countryId;
    
	@NotNull(message = "Country name must not be null")
	@Size(min = 4, message = "Country name must be at least {min} characters")
    @Column(name = "NAME", nullable = false, unique = true)
    protected String name;
    
	@NotNull(message = "Country Code ISO must not be null")
	@Size(min = 3, max = 3, message = "Country Code ISO must be {min} characters")
    @Column(name = "CODE", length = 3, nullable = false)
    protected String code;
    
	@NotNull(message = "Country Code ISO 2 must not be null")
	@Size(min = 2, max = 2, message = "Country Code ISO 2 must be {min} characters")
	@Column(name = "CODE_ISO2", length = 2, nullable = false)
    protected String codeIso2;
    
	/*@NotNull(message = "Capital must not be null")
	@Size(min = 3, max = 45, message = "Capital must be between {min} - {max} characters")
    @Column(name = "CAPITAL", length = 45, nullable = false)*/
    protected String capital;
    
	@NotNull(message = "Continent must not be null")
	@Size(min = 3, max = 45, message = "Continent must be between {min} - {max} characters")
    @Column(name = "CONTINENT", length = 45, nullable = false)
    protected String continent;
    
	@NotNull(message = "Region must not be null")
	@Size(min = 3, max = 45, message = "Region must be between {min} - {max} characters")
    @Column(name = "REGION", length = 45, nullable = false)
    protected String region;
    
	@NotNull(message = "Local name must not be null")
	@Size(min = 3, max = 100, message = "Local name must be between {min} - {max} characters")
    @Column(name = "LOCAL_NAME", length = 100, nullable = false)
    protected String localName;
    
    /*======================================================
     *          CONSTRUCTORS
     =======================================================*/

    public Country() {
    }

    public Country(String name, String code, String codeIso2, String capital, String continent, String region, String localName) {
        this.name = name;
        this.code = code;
        this.codeIso2 = codeIso2;
        this.capital = capital;
        this.continent = continent;
        this.region = region;
        this.localName = localName;
    }
    
    /*======================================================
     *          GETTERS AND SETTERS
     =======================================================*/

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeIso2() {
        return codeIso2;
    }

    public void setCodeIso2(String codeIso2) {
        this.codeIso2 = codeIso2;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    /*======================================================
     *          OTHER METHODS
     =======================================================*/

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.name);
        hash = 61 * hash + Objects.hashCode(this.code);
        hash = 61 * hash + Objects.hashCode(this.codeIso2);
        hash = 61 * hash + Objects.hashCode(this.capital);
        hash = 61 * hash + Objects.hashCode(this.continent);
        hash = 61 * hash + Objects.hashCode(this.region);
        hash = 61 * hash + Objects.hashCode(this.localName);
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
        final Country other = (Country) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        if (!Objects.equals(this.codeIso2, other.codeIso2)) {
            return false;
        }
        if (!Objects.equals(this.capital, other.capital)) {
            return false;
        }
        if (!Objects.equals(this.continent, other.continent)) {
            return false;
        }
        if (!Objects.equals(this.region, other.region)) {
            return false;
        }
        if (!Objects.equals(this.localName, other.localName)) {
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
