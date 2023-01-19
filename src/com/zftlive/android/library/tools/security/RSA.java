
package com.zftlive.android.library.tools.security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

/**
 * RSA加密解密工具类<br>
 * RSA加密速度比DES慢很多，适合少量数据加密，生成秘钥对需要引入开源加解密方案bouncycastle，不能使用默认缺省的实现获取秘钥对，Android
 * 、Windows不同平台默认算法不一样（DES/RSA都一样，MD5都一样）<br>
 * 
 * 目前被破解的最长RSA密钥是768个二进制位，RSA密钥一般是1024位，重要场合则为2048位，密钥长度越大加密越慢越安全，也容易OOM RSA
 * 工具类。提供加密，解密，生成密钥对等方法。 需要到http://www.bouncycastle.org下载bcprov-jdk14-123.jar。
 * RSA加密原理概述 RSA的安全性依赖于大数的分解，公钥和私钥都是两个大素数（大于100的十进制位）的函数。
 * 据猜测，从一个密钥和密文推断出明文的难度等同于分解两个大素数的积<br>
 * ===================================================================<br>
 * （该算法的安全性未得到理论的证明）<br>
 * =================================================================== <br>
 * 密钥的产生：<br>
 * 1.选择两个大素数 p,q ,计算 n=p*q; <br>
 * 2.随机选择加密密钥 e ,要求 e 和 (p-1)*(q-1)互质 <br>
 * 3.利用 Euclid算法计算解密密钥 d , 使其满足 e*d = 1(mod(p-1)*(q-1)) (其中 n,d 也要互质) <br>
 * 4:至此得出公钥为 (n,e)私钥为(n,d) <br>
 * ===================================================================<br>
 * 加解密方法：<br>
 *  1.首先将要加密的信息 m(二进制表示) 分成等长的数据块 m1,m <br>
 *  2,...,mi 块长 s(尽可能大) ,其中2^s<n2:对应的密文是： ci = mi^e(mod n) <br>
 *  3:解密时作如下计算： mi = ci^d(mod n)<br>
 * =================================================================== <br>
 * RSA速度
 * 由于进行的都是大数计算，使得RSA最快的情况也比DES慢上100倍，无论 是软件还是硬件实现。 速度一直是RSA的缺陷。一般来说只用于少量数据 加密。 <br>
 * 参考文献：<br>
 * http://repo1.maven.org/maven2/org/bouncycastle/
 * http://www.ruanyifeng.com/blog/2013/06/rsa_algorithm_part_one.html<br>
 * http://blog.csdn.net/yanzi1225627/article/details/26508035<br>
 * http://blog.csdn.net/bbld_/article/details/38777491<br>
 * 
 * @author 曾繁添
 * @version 1.0
 */
public class RSA {

    /**
     * 生成密钥对（公钥和私钥）
     * 
     * @param mKeySize 密钥长度,默认1024
     * @return KeyPair 密钥对，包含公钥和私钥
     * @throws Exception 生成秘钥对异常
     */
    public static KeyPair genKeyPair(int mKeySize) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", new BouncyCastleProvider());
        kpg.initialize(mKeySize, new SecureRandom());
        return kpg.genKeyPair();
    }
    
    public void testRSA() {
        try {
            String str = "yanzi1225627";
            RSAPublicKey pubKey = getRSAPublicKey();
            RSAPrivateKey priKey = getRSAPrivateKey();
            byte[] enRsaBytes = encrypt(pubKey, str.getBytes());
            String enRsaStr = new String(enRsaBytes, "UTF-8");
            System.out.println("加密后==" + enRsaStr);
            System.out.println("解密后=="+ new String(decrypt(priKey, encrypt(pubKey, str.getBytes()))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 密钥对
    private KeyPair keyPair = null;
    
    /**
     * 初始化密钥对
     */
    public void init() {
        try {
            this.keyPair = this.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成密钥对
     * 
     * @return KeyPair
     * @throws Exception
     */
    private KeyPair generateKeyPair() throws Exception {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",
                    new BouncyCastleProvider());
            // 这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低
            final int KEY_SIZE = 1024;
            keyPairGen.initialize(KEY_SIZE, new SecureRandom());
            KeyPair keyPair = keyPairGen.genKeyPair();
            return keyPair;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    /**
     * 生成公钥
     * 
     * @param modulus
     * @param publicExponent
     * @return RSAPublicKey
     * @throws Exception
     */
    private RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent)
            throws Exception {

        KeyFactory keyFac = null;
        try {
            keyFac = KeyFactory.getInstance("RSA", new BouncyCastleProvider());
        } catch (NoSuchAlgorithmException ex) {
            throw new Exception(ex.getMessage());
        }
        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(
                publicExponent));
        try {
            return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
        } catch (InvalidKeySpecException ex) {
            throw new Exception(ex.getMessage());
        }

    }

    /**
     * 生成私钥
     * 
     * @param modulus 
     * @param privateExponent
     * @return RSAPrivateKey
     * @throws Exception
     */
    private RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent)
            throws Exception {
        KeyFactory keyFac = null;
        try {
            keyFac = KeyFactory.getInstance("RSA", new BouncyCastleProvider());
        } catch (NoSuchAlgorithmException ex) {
            throw new Exception(ex.getMessage());
        }
        RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus),
                new BigInteger(privateExponent));
        try {
            return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
        } catch (InvalidKeySpecException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * 返回公钥
     * 
     * @return
     * @throws Exception
     */
    public RSAPublicKey getRSAPublicKey() throws Exception {

        // 获取公钥
        RSAPublicKey pubKey = (RSAPublicKey) keyPair.getPublic();
        // 获取公钥系数(字节数组形式)
        byte[] pubModBytes = pubKey.getModulus().toByteArray();
        // 返回公钥公用指数(字节数组形式)
        byte[] pubPubExpBytes = pubKey.getPublicExponent().toByteArray();
        // 生成公钥
        RSAPublicKey recoveryPubKey = this.generateRSAPublicKey(pubModBytes, pubPubExpBytes);
        return recoveryPubKey;
    }

    /**
     * 获取私钥
     * 
     * @return
     * @throws Exception
     */
    public RSAPrivateKey getRSAPrivateKey() throws Exception {

        // 获取私钥
        RSAPrivateKey priKey = (RSAPrivateKey) keyPair.getPrivate();
        // 返回私钥系数(字节数组形式)
        byte[] priModBytes = priKey.getModulus().toByteArray();
        // 返回私钥专用指数(字节数组形式)
        byte[] priPriExpBytes = priKey.getPrivateExponent().toByteArray();
        // 生成私钥
        RSAPrivateKey recoveryPriKey = this.generateRSAPrivateKey(priModBytes, priPriExpBytes);
        return recoveryPriKey;
    }
    
    /**
     * 加密
     * 
     * @param key 加密的密钥
     * @param data 待加密的明文数据
     * @return 加密后的数据
     * @throws Exception
     */
    public byte[] encrypt(Key key, byte[] data) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 获得加密块大小，如:加密前数据为128个byte，而key_size=1024 加密块大小为127
            // byte,加密后为128个byte;
            // 因此共有2个加密块，第一个127 byte第二个为1个byte
            int blockSize = cipher.getBlockSize();
            int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
            int leavedSize = data.length % blockSize;
            int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length
                    / blockSize;
            byte[] raw = new byte[outputSize * blocksSize];
            int i = 0;
            while (data.length - i * blockSize > 0) {
                if (data.length - i * blockSize > blockSize)
                    cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
                else
                    cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i
                            * outputSize);
                // 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到ByteArrayOutputStream中
                // ，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了OutputSize所以只好用dofinal方法。
                i++;
            }
            return raw;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 解密
     * 
     * @param key 解密的密钥
     * @param raw 已经加密的数据
     * @return 解密后的明文
     * @throws Exception
     */
    public byte[] decrypt(Key key, byte[] raw) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(cipher.DECRYPT_MODE, key);
            int blockSize = cipher.getBlockSize();
            ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
            int j = 0;
            while (raw.length - j * blockSize > 0) {
                bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
                j++;
            }
            return bout.toByteArray();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
