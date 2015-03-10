package me.jackmo.gameoflife;

import java.util.Random;
/**
 * Encrytion class encrypts and decypts Strings according to a given key.
 * 
 * @version 2.67
 */
public class Encryption
{
    /**
     * Encryption null constructor
     *
     */
    public Encryption()
    {
        key = null;
    }
    
    /**
     * Encryption constructor
     * @param aIn     input key
     */
    public Encryption(String aIn)
    {
        key = aIn;
    }
    
    /**
     * Method to encrypt a String
     * @param aIn     input String to encrypt
     * @return        returned encrypted String
     */
    public String encrypt(String aIn)
    {
        aIn = aIn.toUpperCase();// Checks input to uppercase
        Random rand = new Random();
        String out = "";
        while(!out.contains(key))
        {
            out += base.charAt(rand.nextInt(36));
        }//Randomly generates characters until the key is generated.
        String add = "";
        for(int i = 0; i < 20; i++)
        {
            add += base.charAt(rand.nextInt(36));
        }//Add 20 other characters for security.
        if(add.contains(key))
        {
            return encrypt(aIn);
        }//Logistical check
        out += add;
        String plaintext = "";
        for(int i = 0; i < aIn.length(); i++)
        {
            plaintext += base.charAt(35 - base.indexOf(aIn.charAt(i)));
        }//Simple reverse encryption for plaintext.
        out += plaintext;
        out += key;
        String temp = "";
        while(!temp.contains(key))
        {
            temp += base.charAt(rand.nextInt(36));
        }//Same as before, but behind plaintext.
        out += temp;
        add = "";
        for(int i = 0; i < 20; i++)
        {
            add += base.charAt(rand.nextInt(36));
        }//Adds another 20 random characters.
        if(add.contains(key))
        {
            return encrypt(aIn);
        }//Logistical check again.
        out += add;
        return out;
    }
    
    /**
     * A method to decrypt a given String.
     * @param aIn     input String to decrypt
     * @return        decrypted String
     */
    public String decrypt(String aIn)
    {
        aIn = aIn.toUpperCase();
        int pos1 = aIn.indexOf(key);
        int pos2 = aIn.indexOf(key, pos1 + 1);
        int pos3 = aIn.indexOf(key, pos2 + 1);
        int pos4 = aIn.indexOf(key, pos3 + 1);
        while(pos4 != -1)
        {
            pos2 = pos3;
            pos3 = pos4;
            pos4 = aIn.indexOf(key, pos3 + 1);
        }
        String tempTarget = aIn.substring(pos1 + key.length()+ 20, pos2);
        String target = "";
        for(int i = 0; i < tempTarget.length(); i++)
        {
            target += base.charAt(35 - base.indexOf(tempTarget.charAt(i)));
        }
        return target;
    }
    
    private String key;
    public static final String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
}