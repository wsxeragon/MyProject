package com.inphase.sparrow.base.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;


public class EncryptUtil {
	
	private static final Logger logger = Logger.getLogger("errorfile");
	
	private static final String UTF8 = "utf-8";
	
    //定义 加密算法,可用 DES,DESede,Blowfish
    private static final String ALGORITHM_DESEDE = "DESede";

    private EncryptUtil() {}
    
    /**
     * MD5数字签名
     * 
     * @param src
     * @return
     */
    public static String md5Digest(String src,int bit) {
        // 定义数字签名方法, 可用：MD5, SHA-1
    	String result = null;
        try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] b = md.digest(src.getBytes(UTF8));
			if(bit==16){
				result = byte2HexStr(b).substring(8,24);
			}else{
				result = byte2HexStr(b);
			}
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		} 
        return result;
    }
    
    /**
     * BASE64 加密
     * 
     * @param src
     * @return
     */
    public static String base64Encoder(String src) {
    	String result = null;
        try {
			result = Base64.encodeBase64String(src.getBytes(UTF8)) ;
		} catch (UnsupportedEncodingException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
        return result;
    }
    
    /**
     * BASE64解密
     * 
     * @param dest
     * @return
     */
   public static String base64Decoder(String dest) {
	   String result = null;
        try {
			result = new String(Base64.decodeBase64(dest), UTF8);
		} catch (UnsupportedEncodingException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
        return result;
    }
    
    /**
     * 3DES加密
     * 
     * @param src
     * @param key
     * @return
     */
    public static String desedeEncoder(String src, String key) {
        String result = null;
		try {
			SecretKey secretKey = new SecretKeySpec(build3DesKey(key), ALGORITHM_DESEDE);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DESEDE);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] b = cipher.doFinal(src.getBytes(UTF8));
			byte2HexStr(b);
		} catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
        
        return result;
    }
    
    /**
     * 3DES解密
     * 
     * @param dest
     * @param key
     * @return
     */
    public static String desedeDecoder(String dest, String key) {
    	String result = null;
        try {
			SecretKey secretKey = new SecretKeySpec(build3DesKey(key), ALGORITHM_DESEDE);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DESEDE);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] b = cipher.doFinal(str2ByteArray(dest));
			result = new String(b, UTF8);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
        return result;
    
    }
    
    /**
     * 字节数组转化为大写16进制字符串
     * 
     * @param b
     * @return
     */
    private static String byte2HexStr(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            String s = Integer.toHexString(b[i] & 0xFF);
            if (s.length() == 1) {
                sb.append("0");
            }
            
            sb.append(s);
        }
        
        return sb.toString();
    }
    
    /**
     * 字符串转字节数组
     * 
     * @param s
     * @return
     */
    private static byte[] str2ByteArray(String s) {
        int byteArrayLength = s.length()/2;
        byte[] b = new byte[byteArrayLength];
        for (int i = 0; i < byteArrayLength; i++) {
            byte b0 = (byte) Integer.valueOf(s.substring(i*2, i*2+2), 16).intValue();
            b[i] = b0;
        }
        
        return b;
    }
    
    /**
     * 构造3DES加解密方法key
     * 
     * @param keyStr
     * @return
     */
    private static byte[] build3DesKey(String keyStr) throws UnsupportedEncodingException  {
        byte[] key = new byte[24];
        byte[] temp = keyStr.getBytes(UTF8);
        if (key.length > temp.length) {
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        
        return key;
    }
    
    /**
     * AES 加密
     * @param content 明文
     * @param password 生成秘钥的关键字
     * @return
     */
    
	public static String aesEncrypt(String content, String password) {
		String result = null;
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(password.getBytes());
			kgen.init(128, random);
			SecretKey secretKey = kgen.generateKey();// 分组秘钥
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 生成秘钥
			Cipher cipher = Cipher.getInstance("AES");
			byte[] byteContent = content.getBytes(UTF8);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] b = cipher.doFinal(byteContent);
			result = byte2HexStr(b);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return result;
	}
    
    /**
     * AES 解密
     * @param content 密文
     * @param password 生成秘钥的关键字
     * @return 
     */
    
    public static String aesDecrypt(String content,String password) {
    	String result = null;
    	try {
			byte[] temp = str2ByteArray(content);
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(password.getBytes());
			kgen.init(128, random);
			SecretKey secretKey = kgen.generateKey();//分组秘钥
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat,"AES");//生成秘钥
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] b = cipher.doFinal(temp);
			result = new String(b,UTF8);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
    	return result;
    }
}