/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.covertatt;


import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Administrator
 */
public class hMac
{
static Socket s1;
    public String hm;
    public hMac()
    {
    	
    }
    private  byte[] digest;

    /**
    * Inner Padding.
    */
    private byte kIpad[];

    /**
    * Outer Padding.
    */
    private byte kOpad[];
    private MessageDigest md;
    public void Sourcemac1(String key,String text1)
    {
    	//System.out.println("THE VALUE IS "+ key+"  "+text1);
        String expectedHash;
        String digest;
        //System.out.println("Test Vectors from RFC 2104 - HMAC:Keyed-Hashing for Message Authentication");
        //System.out.println("This test uses HMAC-MD5.");
        byte[] keys = key.getBytes();
           try
		{
        	String host = InetAddress.getLocalHost().getHostName();
		}
        catch (UnknownHostException e3)
		{
        	e3.printStackTrace();
		}
        String hmvalue = " "+hm;
    	int kLen = keys.length;
        // if key is longer than 64 bytes reset it to key=MD5(key)
    	if (kLen > 64)
    	{
    		MessageDigest  md = null;
    		try
			{
    			md = MessageDigest.getInstance("MD5");
			}
    		catch (NoSuchAlgorithmException e)
			{
    			e.printStackTrace();
			}
    		md.update(keys);
    		keys = md.digest();
    	}
    	kIpad = new byte[64];   // inner padding - key XORd with ipad
    	kOpad = new byte[64];   // outer padding - key XORd with opad
    	// start out by storing key in pads
    	System.arraycopy(keys, 0, kIpad, 0, kLen);
    	System.arraycopy(keys, 0, kOpad, 0, kLen);

    	// 	XOR key with ipad and opad values
    	for (int i = 0; i < 64; i++)
    	{
    		kIpad[i] ^= 0x36;
    		kOpad[i] ^= 0x5c;
    	}
    	clear(); // Initialize the first digest.
    	addData(text1.getBytes());
    	digest = toString();
    	hm = digest;
    	//System.out.println("Signature Verification: " +hm );
    }
    public void clear()
    {
    	try
		{
    		md = MessageDigest.getInstance("MD5");
		}
    	catch (NoSuchAlgorithmException e)
		{
    		e.printStackTrace();
		}
    	md.update(kIpad); // Intialize the inner pad.
    	digest = null;          // mark the digest as incomplete.
    }
    public void addData(byte text[])
    {
    	//System.out.println(" thetext is going to be added");
        addData(text,0,text.length);
    }

    public void addData(byte text[],int textStart, int textLen)
    {
    	//System.out.println(" the text is  added");
    	md.update(text,textStart,textLen);   // then text of
    	// datagram.
    }

    public byte[] sign()
    {
    	digest = md.digest();            // finish up 1st pass.
    	md.update(kOpad);                     // Use outer pad.
    	md.update(digest);                    // Use results of first pass.
    	digest = md.digest();                 // Final result.
    	return digest;
    }
    public boolean verify(byte signature[])
    {
       if (digest == null)
          sign();
       int sigLen = signature.length;
       int digLen = digest.length;
       if (sigLen != digLen)
          return false;  // Different lengths, not a good sign.
       for (int i = 0; i < sigLen; i++)
          if (signature[i] != digest[i])
             return false;  // Mismatch. Misfortune.
       return true;   // Signatures matched. Perseverance furthers.
    }
    public String toString()
    {
    	// If not already calculated, do so.
    	if (digest == null)
    		sign();
    	StringBuffer r = new StringBuffer();
    	final String hex = "0123456789ABCDEF";
    	byte b[] = digest;
    	for (int i = 0; i < 16; i++)
    	{
    		int c = ((b[i]) >>> 4) & 0xf;
    		r.append(hex.charAt(c));
    		c = ((int)b[i] & 0xf);
    		r.append(hex.charAt(c));
    	}
    	return r.toString();
    }

}
