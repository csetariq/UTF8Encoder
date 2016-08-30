package com.mustang.utf16toutf8;

import static org.junit.Assert.assertArrayEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

import org.junit.BeforeClass;
import org.junit.Test;

public class UTF8EncoderTest {

    private static UTF8Encoder encodeTestInstance;
    private static Method encodeMethod;
    
    @BeforeClass
    public static void setUp() {
        
        try {
            encodeMethod = UTF8Encoder.class.getDeclaredMethod("encode", int.class);
            encodeMethod.setAccessible(true);
            
            encodeTestInstance = new UTF8Encoder(new ByteArrayInputStream(new byte[] {}),
                    new ByteArrayOutputStream(), Charset.forName("UTF-32"));
            
        } catch (UnsupportedEncodingException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * tests the code-point in the range
     * 
     *      0x00        -   0x7F
     *      0b000_0000  -   0b111_1111
     * 
     * encoded as
     * 
     *      0b0_xxxxxxx
     */
    @Test
    public void test7BitsEncode() {
        byte[] result;
        try {
            
            result = (byte[]) encodeMethod.invoke(encodeTestInstance, 0b000_0000);
            assertArrayEquals(new byte[]{(byte) 0b0_000_0000}, result);
            
            result = (byte[]) encodeMethod.invoke(encodeTestInstance, 0b111_1111);
            assertArrayEquals(new byte[]{(byte) 0b0_111_1111}, result);
            
            result = (byte[]) encodeMethod.invoke(encodeTestInstance, 0b101_0101);
            assertArrayEquals(new byte[]{(byte) 0b0_101_0101}, result);
            
            result = (byte[]) encodeMethod.invoke(encodeTestInstance, 0b010_1010);
            assertArrayEquals(new byte[]{(byte) 0b0_010_1010}, result);
            
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * tests the code-point in the range
     * 
     *      0x80            -   0x7FF
     *      0b000_1000_0000 -   0b111_1111_1111
     * 
     * encoded as
     * 
     *      0b110_xxxxx 0b10_xxxxxx
     */
    @Test
    public void test11BitsEncode() {
        byte[] result;
        try {
            
            result = (byte[]) encodeMethod.invoke(encodeTestInstance, 0b000_1000_0000);
            assertArrayEquals(new byte[] { (byte) 0b110_00010, (byte) 0b10_000000 }, result);
            
            result = (byte[]) encodeMethod.invoke(encodeTestInstance, 0b111_1111_1111);
            assertArrayEquals(new byte[] { (byte) 0b110_11111, (byte) 0b10_111111 }, result);

            result = (byte[]) encodeMethod.invoke(encodeTestInstance, 0b101_0101_0101);
            assertArrayEquals(new byte[] { (byte) 0b110_10101, (byte) 0b10_010101 }, result);

            result = (byte[]) encodeMethod.invoke(encodeTestInstance, 0b010_1010_1010);
            assertArrayEquals(new byte[] { (byte) 0b110_01010, (byte) 0b10_101010 }, result);
            
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
