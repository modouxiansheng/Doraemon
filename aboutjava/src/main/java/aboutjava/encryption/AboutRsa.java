package aboutjava.encryption;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.*;
import java.util.Base64;
import java.util.Map;

/**
 * @program: springBootPractice
 * @description: 关于RSA的非对称加密算法
 * @author: hu_pf
 * @create: 2020-05-08 17:27
 **/
public class AboutRsa {
    public static final String RSA_ALGORITHM = "RSA";

    public static final Charset UTF8 = Charset.forName("UTF-8");

    public static void main(String[] args) throws Exception {
        KeyPair keyPair = buildKeyPair();

        byte[] encryptData = encrypt(keyPair.getPublic(), "不学无数");

        System.out.println(String.format("加密后的数据：%s",base64Encode(encryptData)));

        System.out.println(String.format("解密后的数据：%s",new String(decrypt(keyPair.getPrivate(),encryptData),UTF8)));

        String context = "加签的字符串";

        String sign = signWithRSA(context, keyPair.getPrivate());

        System.out.println(String.format("生成的签名：%s",sign));

        Boolean checkSignWithRSA = checkSignWithRSA(context, keyPair.getPublic(), sign);

        System.out.println(String.format("校验的结果：%s",checkSignWithRSA.toString()));
    }

    public static KeyPair buildKeyPair() throws NoSuchAlgorithmException {
        final int keySize = 2048;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.genKeyPair();
    }

    public static byte[] decrypt(PrivateKey privateKey, byte [] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(encrypted);
    }

    public static byte[] encrypt(PublicKey publicKey, String message) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(message.getBytes(UTF8));
    }

    public static String base64Encode(byte[] data) {
        return new BASE64Encoder().encode(data);
    }
    public static byte[] base64Decode(String data) throws IOException {
        return new BASE64Decoder().decodeBuffer(data);
    }

    /**
     * 使用RSA签名
     */
    private static String signWithRSA(String content, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA1WithRSA");
        signature.initSign(privateKey);
        signature.update(content.getBytes("utf-8"));
        byte[] signed = signature.sign();
        return base64Encode(signed);
    }

    /**
     * 使用RSA验签
     */
    private static boolean checkSignWithRSA(String content, PublicKey publicKey,String sign) throws Exception {
        Signature signature = Signature.getInstance("SHA1WithRSA");
        signature.initVerify(publicKey);
        signature.update(content.getBytes("utf-8"));
        return signature.verify(base64Decode(sign));
    }
}
