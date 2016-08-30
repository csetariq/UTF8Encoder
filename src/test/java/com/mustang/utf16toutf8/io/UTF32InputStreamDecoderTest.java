package com.mustang.utf16toutf8.io;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

public class UTF32InputStreamDecoderTest {

    @Test
    public void testNormalInAndOut() {
        byte[] buf = {
                0x00, 0x00, 0x33, 0x41,
                0x00, 0x22, 0x00, 0x42,
                0x11, 0x00, 0x33, 0x43,
                0x00, 0x00, 0x00, 0x44
                };
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        
        try {
            UTF32InputStreamDecoder decoder = new UTF32InputStreamDecoder(in);
            assertEquals(0x3341, decoder.read());
            assertEquals(0x220042, decoder.read());
            assertEquals(0x11003343, decoder.read());
            assertEquals(0x44, decoder.read());
            assertEquals(-1, decoder.read());
            assertEquals(-1, decoder.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectNumberOfBytesOver4Bytes() {
        byte[] buf = {
                0x00, 0x00, 0x00, 0x41,
                0x00, 0x00, 0x00, 0x42,
                0x00, 0x00, 0x00, 0x43,
                0x00, 0x00, 0x00, 0x44,
                0x00, 0x00, 0x00
        };
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        
        try {
            UTF32InputStreamDecoder decoder = new UTF32InputStreamDecoder(in);
            assertEquals(0x41, decoder.read());
            assertEquals(0x42, decoder.read());
            assertEquals(0x43, decoder.read());
            assertEquals(0x44, decoder.read());
            decoder.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectNumberOfBytesUnder4Bytes() {
        byte[] buf = {
                0x00, 0x00, 0x41,
        };
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        
        try {
            UTF32InputStreamDecoder decoder = new UTF32InputStreamDecoder(in);
            assertEquals(0x41, decoder.read());
            assertEquals(0x42, decoder.read());
            assertEquals(0x43, decoder.read());
            assertEquals(0x44, decoder.read());
            decoder.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testNoBytes() {
        byte[] buf = {};
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        
        try {
            UTF32InputStreamDecoder decoder = new UTF32InputStreamDecoder(in);
            assertEquals(-1, decoder.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
