package com.platform.spring.encrypt;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA工具类
 * Created at 2017/11/7 18:25 by GaoPan
 * com.foxconn.ace_data_platform.util.RSAUtil
 */
public class RSAUtil {
    private static Logger logger = LogManager.getLogger(RSAUtil.class);

    /**
     * 密钥类型
     */
    public static final int PRIVATE_KEY_TYPE = 0;
    public static final int PUBLIC_KEY_TYPE = 1;
    public static final String PUBLIC_KEY = "publicKey";
    public static final String PRIVATE_KEY = "privateKey";

    /** RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024 */
    public static final int KEY_SIZE = 1024;

    public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    public static final String KEY_ALGORITHM = "RSA";
    /**
     * 字节数组转字符专用集合
     */
    private static final char[] HEX_CHAR= {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    /**
     * 签名算法
     */
    private static final String SIGN_ALGORYTHM = "SHA1WithRSA";
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * 读取文件中的字符串
     * @param path 文件路径
     * @return 文件中的字符串
     * @throws IOException IO异常
     */
    public static String readFile(String path) throws IOException {
        FileInputStream fis = null;
        BufferedReader br = null;
        try {
            fis = new FileInputStream(path);
            br = new BufferedReader(new InputStreamReader(fis));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                if (line.charAt(0) != '-') {
                    sb.append(line);
                    sb.append('\r');
                }
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
           throw new RuntimeException("文件不存在，请检查文件路径：" + path);
        } catch (IOException e) {
           throw new RuntimeException("读取文件数据出错，请检查文件：" + path);
        } finally {
            if (br != null) {
                br.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
    }

    /**
     * 从文件中加载公钥或私钥
     * @param path 密钥路径
     * @param type 密钥类型
     * @return 密钥
     */
    public static Key loadKeyFromFile(String path, int type) {
        return loadKeyFromFile(path, type, true);
    }

    /**
     * 从给定字符串中加载密钥
     * @param str 包含密钥的字符串
     * @param type 密钥的类型
     * @return 密钥
     */
    public static Key loadKeyFromString(String str, int type) {
        return loadKeyFromFile(str, type, false);
    }

    /**
     * 从文件或字符串中加载公钥或私钥，通过type来标识加载哪种密钥
     * @param path 密钥路径或字符串密钥
     * @param type 密钥类型
     * @param fromPath 是否从路径加载
     * @return 公钥或私钥
     */
    private static Key loadKeyFromFile(String path, int type, boolean fromPath)  {
        try {
            String keyStr = fromPath ? readFile(path) : path;
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Base64.decodeBase64(keyStr);
            byte[] buffer = Base64.decodeBase64(keyStr);
            EncodedKeySpec keySpec ;
            Key key ;
            if (type == PRIVATE_KEY_TYPE) {
                keySpec = new PKCS8EncodedKeySpec(buffer);
                key = keyFactory.generatePrivate(keySpec);
            } else if (type == PUBLIC_KEY_TYPE) {
                keySpec = new X509EncodedKeySpec(buffer);
                key = keyFactory.generatePublic(keySpec);
            } else {
                throw new RuntimeException("无效的Key类型：" + type);
            }
            return key;
        } catch (NoSuchAlgorithmException e) {
           throw new RuntimeException("不存在此算法！");
        } catch (IOException e) {
            throw new RuntimeException("读取密钥内容失败！");
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("密钥无效！");
        }
    }

    /**
     * 使用RSA加密算法加密数据
     * @param key 密钥
     * @param plaintText 待加载的数据
     * @return 加密后的密文数据
     */
    public static String encryptByRSA(String key, String plaintText) {
        if (StringUtils.isBlank(key)) {
            throw new RuntimeException("加密公钥为空!");
        }
        Cipher cipher = null;
        ByteArrayOutputStream outputStream = null;
        try {
            byte[] decoded = Base64.decodeBase64(key);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            //RSA/ECB/PKCS1Padding
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            byte[] data = plaintText.getBytes("UTF-8");
            int dataLen = data.length;
            outputStream = new ByteArrayOutputStream();
            int offSet = 0, index = 0;
            byte[] cache;
            //对数据进行分段加密
            while (dataLen - offSet > 0) {
                cache = (dataLen - offSet > MAX_ENCRYPT_BLOCK) ?
                        (cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK)) : (cipher.doFinal(data, offSet, dataLen - offSet));
                outputStream.write(cache, 0, cache.length);
                index++;
                offSet = index * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = outputStream.toByteArray();
            return Base64.encodeBase64String(encryptedData);
        } catch (NoSuchAlgorithmException e) {
           throw new RuntimeException("无此加密算法！");
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException("填充长度异常！");
        } catch (InvalidKeyException e) {
            throw new RuntimeException("加密密钥无效，请检查！");
        } catch (BadPaddingException e) {
           throw new RuntimeException("明文数据已损坏！");
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException("明文数据长度非法！");
        }catch (InvalidKeySpecException e) {
            throw new RuntimeException("获取私钥失败!");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("加密字符串转换bytes失败!");
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("关闭输出流失败！", e);
                }
            }
        }
    }

    /**
     * 使用RSA算法解密密文
     * @param key 解密用的密钥
     * @param cipherData 加密后的密文
     * @return 解密后的明文数据
     */
    public static String decryptByRSA(String key, String cipherData) {
        if (key == null) {
            throw new RuntimeException("解密私钥为空!");
        }
        Cipher cipher = null;
        try {
            //base64编码的私钥
            byte[] decodedPrivate = Base64.decodeBase64(key);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA" ).generatePrivate(new PKCS8EncodedKeySpec(decodedPrivate));
            //RSA解密

            //注意:Java 这里使用RSA,android则要使用RSA/ECB/PKCS1Padding
            cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            InputStream inputStream = new ByteArrayInputStream(Base64.decodeBase64(cipherData.getBytes("UTF-8")));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
            byte[] buf = new byte[128];
            int size;

            while ((size = inputStream.read(buf)) != -1) {
                outputStream.write(cipher.doFinal(buf, 0, size));
            }
            byte[] decryptedData = outputStream.toByteArray();
            outputStream.close();
            return new String(decryptedData);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("无此加密算法！");
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException("没有这样的填充数据！");
        } catch (InvalidKeyException e) {
            throw new RuntimeException("解密密钥无效，请检查！");
        } catch (BadPaddingException e) {
            throw new RuntimeException("明文数据已损坏！");
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException("明文数据长度非法！");
        } catch (IOException e) {
            throw new RuntimeException("读取流失败!");
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("获取私钥失败!");
        }
    }

    /**
     * 直接对数据进行签名
     * @param content 待签名数据
     * @param key 签名所用密钥
     * @return 签名后的数据
     */
    public static String sign(String content, Key key) {
        Signature signature ;
        try {
            //获取签名算法
            signature = Signature.getInstance(SIGN_ALGORYTHM);
            signature.initSign((PrivateKey) key);
            signature.update(content.getBytes());
            //签名后的数据用Base64加密
            return Base64.encodeBase64String(signature.sign());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("无此算法！");
        } catch (SignatureException e) {
           throw new RuntimeException("签名错误！");
        } catch (InvalidKeyException e) {
            throw new RuntimeException("签名所用私钥无效！");
        }
    }

    /**
     * 对给定参数用特定密钥进行签名
     * @param params 参数
     * @param keyPath 密钥路径
     * @return 签名后的数据
     */
    public static String signParams(Map<String, String> params, String keyPath) {
        String[] names = params.keySet().toArray(new String[0]);
        Arrays.sort(names);

        StringBuilder sb = new StringBuilder();
        for (String key : names) {
            String value = params.get(key);
            sb.append(key).append("=").append(value);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("待签名字符串：" + sb.toString());
        }
        //数据在签名前先进行MD5加密
        String encodedData = MD5Utils.MD5(sb.toString());
//        Key privateKey = loadKeyFromFile(keyPath, PRIVATE_KEY_TYPE);
        Key privateKey = loadKeyFromString(keyPath, PRIVATE_KEY_TYPE);
        return sign(encodedData, privateKey);
    }

    /**
     * RSA签名校验
     * @param content 待签名数据
     * @param sign 签名值
     * @param key 校验的公钥
     * @return
     */
    public static boolean verify(String content, String sign, Key key) {
        Signature signature;
        try {
            signature = Signature.getInstance(SIGN_ALGORYTHM);
            signature.initVerify((PublicKey) key);
            String encodedStr = MD5Utils.MD5(content);
            signature.update(encodedStr.getBytes());
            return signature.verify(Base64.decodeBase64(sign));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("无此算法！");
        } catch (InvalidKeyException e) {
            throw new RuntimeException("校验所用私钥无效！");
        } catch (SignatureException e) {
            throw new RuntimeException("校验签名错误！");
        }
    }

    /**
     * 字节数组转字符串
     * @param data 字节数组
     * @return 字符串
     */
    public static String byteArrayToString(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<data.length;i++) {
            sb.append(HEX_CHAR[(data[i]&0xf0)>>>4]);
            sb.append(HEX_CHAR[data[i]&0x0f]);
            if (i < data.length-1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }




    /**
     * 随机生成密钥对
     *
     * @return
     */
    public static Map<String, String> generateKeyBytes() {

        try {
            // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGenerator = KeyPairGenerator
                    .getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE,new SecureRandom());
            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            //得到公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            //得到私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            // 得到公钥字符串
            String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
            // 得到私钥字符串
            String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
            Map<String, String> keyMap = new HashMap<>();
            keyMap.put(PUBLIC_KEY, publicKeyString);
            keyMap.put(PRIVATE_KEY, privateKeyString);
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            logger.info("generateKeyBytes error",e);
        }
        return null;
    }

    /**
     * 还原公钥，X509EncodedKeySpec 用于构建公钥的规范
     *
     * @param keyBytes
     * @return
     */
    public static PublicKey restorePublicKey(byte[] keyBytes) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);

        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.info("restorePublicKey error",e);
        }
        return null;
    }

    /**
     * 还原私钥，PKCS8EncodedKeySpec 用于构建私钥的规范
     *
     * @param keyBytes
     * @return
     */
    public static PrivateKey restorePrivateKey(byte[] keyBytes) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = factory
                    .generatePrivate(pkcs8EncodedKeySpec);
            return privateKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.info("restorePrivateKey error",e);
        }
        return null;
    }



    public static void main(String[] args) throws Exception {

        Map<String, String> keyMap = generateKeyBytes();

        // 加密

        String encodedText = encryptByRSA(keyMap.get(PUBLIC_KEY), "中国abc123");
        System.out.println("RSA encoded: " + encodedText);

        // 解密
        System.out.println("RSA decoded: "
                + decryptByRSA(keyMap.get(PRIVATE_KEY), encodedText));


        logger.info(keyMap.get(PUBLIC_KEY));
        logger.info(keyMap.get(PRIVATE_KEY));

       /* String privateKeyPath = "D:\\Software\\Programs\\CompanyProjects\\ace_fu\\src\\main\\resources\\rsa_private_key_pkcs8.pem";
        String publicKeyPath = "D:\\Software\\Programs\\CompanyProjects\\ace_fu\\src\\main\\resources\\rsa_public_key.pem";
        PublicKey publicKey = (PublicKey) loadKeyFromFile(publicKeyPath, PUBLIC_KEY_TYPE);
        PrivateKey privateKey = (PrivateKey) loadKeyFromFile(privateKeyPath, PRIVATE_KEY_TYPE);
        String plainText = "这是待加密的字符串";
        String encryptedString = encryptByRSA(publicKey, plainText);
        String decryptedString = decryptByRSA(privateKey, encryptedString);
        System.out.println("加密前的内容：" + plainText);
        System.out.println("加密后的内容：" + encryptedString);
        System.out.println("解密后的内容：" + decryptedString);

        Map<String, String> params = new HashMap<>();
        params.put("version", "1");
        params.put("servicecode", "FZFGetEmplByDate");

        String sign = signParams(params, privateKeyPath);
        String[] names = params.keySet().toArray(new String[0]);
        Arrays.sort(names);

        StringBuilder sb = new StringBuilder();
        for (String key : names) {
            String value = params.get(key);
            sb.append(key).append("=").append(value);
        }
        logger.info("接收到的字符串：{}", sb.toString());
        System.out.println(verify(sb.toString(), sign, publicKey));*/
    }
}
