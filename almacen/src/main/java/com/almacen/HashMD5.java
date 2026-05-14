package com.almacen;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import javax.swing.JOptionPane;

public class HashMD5 {
    public HashMD5(String cadena){
        generarMD5Hash(cadena);
    }
    
    public static String generarMD5Hash(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] valorHash = digest.digest(value.getBytes());
            String hashHexadecimal = HexFormat.of().formatHex(valorHash);
            return hashHexadecimal;
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
    }
}
