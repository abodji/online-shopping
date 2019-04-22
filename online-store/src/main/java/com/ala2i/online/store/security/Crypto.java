package com.ala2i.online.store.security;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * A very simple class to encrypt and decrypt strings. 
 * To encrypt strings, it uses the AES algorithm with a PKCS5Padding for encryption. Then a base64 encoding 
 * is used to convert the encrypted byte[] into a String. Conversely, this class takes the encrypted String, 
 * uses a base64 decoding to transform it to a byte[] and performs the decryption. 
 * 
 * @author Alassani ABODJI <a href="mailto:abodjialassani@gmail.com"><abodjialassani@gmail.com></a>
 */
public class Crypto {
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final byte[] KEY = "14AcA02Jeudi".getBytes();
    
    public static String encrypt(String toBeEncrypted ){
        // do some encryption
        Key key = new SecretKeySpec(KEY, "AES");
        
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
         
            return Base64.getEncoder().encodeToString(cipher.doFinal(toBeEncrypted.getBytes()));
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String toBeDecrypted) {
        // do some decryption
        Key key = new SecretKeySpec(KEY, "AES");
      
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            
            return new String(cipher.doFinal(Base64.getDecoder().decode(toBeDecrypted)));
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
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
