package com.arksh.common.security;


/**
 * 加解密方法的封装类
 * Created by Administrator on 2015/12/10 0010.
 */
public class CodeSecurity {

    public static String desEncode(String str){
        return DESBase64Util.encodeInfo(str);
    }


    public static String desDecodeInfo(String str){
        return DESBase64Util.decodeInfo(str);
    }

    /**
     * AES解码
     * @param str
     * @return
     */
    public static String aesDecodeInfo(String str){
        String result = "";
        try {
            result = AESUtil.Decrypt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * AES编码
     * @param str
     * @return
     */
    public static String aesEncode(String str){
        String result = "";
        try {
            result = AESUtil.Encrypt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String myEncode(String str){
        return aesEncode(str);
    }

    public static String myDecode(String str){
        return aesDecodeInfo(str);
    }

}
