package com.ala2i.online.store.data.converters;

import com.ala2i.online.store.security.Crypto;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author Alassani ABODJI <a href="mailto:abodjialassani@gmail.com"><abodjialassani@gmail.com></a>
 */
@Converter
public class CreditCardNumberConverter implements AttributeConverter<String, String> {

    @Override
    /**
     * Converts the value stored in the entity attribute into the 
     * data representation to be stored in the database.
     *
     * @param ccNumber  the entity(credit card entity) attribute value to be converted
     * @return  the converted data to be stored in the database 
     *          column
     */
    public String convertToDatabaseColumn(String ccNumber) {
        return Crypto.encrypt(ccNumber);
    }

    @Override
    /**
     * Converts the data stored in the database column into the 
     * value to be stored in the entity attribute.
     * Note that it is the responsibility of the converter writer to
     * specify the correct <code>dbData</code> type for the corresponding 
     * column for use by the JDBC driver: i.e., persistence providers are 
     * not expected to do such type conversion.
     *
     * @param dbData  the data from the database column to be 
     *                converted
     * @return  the converted value to be stored in the entity 
     *          attribute
     */
    public String convertToEntityAttribute(String dbData) {
        return Crypto.decrypt(dbData);
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
