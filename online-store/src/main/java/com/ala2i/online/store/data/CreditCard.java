package com.ala2i.online.store.data;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ala2i.online.store.data.converters.CreditCardNumberConverter;

/**
 *
 * @author Alassani ABODJI <a href="mailto:abodjialassani@gmail.com"><abodjialassani@gmail.com></a>
 */
@Entity
@Table(name = "CREDIT_CARD")
public class CreditCard implements Serializable {
    private static final long serialVersionUID = -7993968825648883367L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CC_ID")
    protected Long creditCardId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "CC_TYPE", nullable = false)
    protected CreditCardType type;
    
    @Column(name = "CC_NAME", length = 45, nullable = false)
    protected String name;
    
    @Column(name = "CC_NUMBER", length = 64, nullable = false, unique = true)
    @Convert(converter = CreditCardNumberConverter.class)
    protected String number;
    
    @Column(name = "CC_VV", length = 4, nullable = false)
    protected String cvv;
    
    @Column(name = "CC_EXP_DATE", length = 45, nullable = false)
    protected String expiryDate;
    
    /*======================================================
     *          CONSTRUCTORS
     =======================================================*/

    public CreditCard() {
    }

    public CreditCard(CreditCardType type, String name, String number, String cvv, String expiryDate) {
        this.type = type;
        this.name = name;
        this.number = number;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }
    
    /*======================================================
     *          GETTERS AND SETTERS
     =======================================================*/

    public Long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Long creditCardId) {
        this.creditCardId = creditCardId;
    }

    public CreditCardType getType() {
        return type;
    }

    public void setType(CreditCardType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    
    /*======================================================
     *          OTHER METHODS
     =======================================================*/
    
    public boolean checkCVV() {
        return cvv == null || number == null 
            || (number.lastIndexOf(cvv) == number.length() - cvv.length());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.type);
        hash = 43 * hash + Objects.hashCode(this.name);
        hash = 43 * hash + Objects.hashCode(this.number);
        hash = 43 * hash + Objects.hashCode(this.cvv);
        hash = 43 * hash + Objects.hashCode(this.expiryDate);
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
        final CreditCard other = (CreditCard) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.number, other.number)) {
            return false;
        }
        if (!Objects.equals(this.cvv, other.cvv)) {
            return false;
        }
        if (!Objects.equals(this.expiryDate, other.expiryDate)) {
            return false;
        }
        if (this.type != other.type) {
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
