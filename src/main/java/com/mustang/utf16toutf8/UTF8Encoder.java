package com.mustang.utf16toutf8;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.HashMap;

import com.mustang.utf16toutf8.io.UTF32InputStreamDecoder;
import com.mustang.utf16toutf8.io.UTFInputStreamDecoder;

public class UTF8Encoder {

    private InputStream in;
    private OutputStream out;
    private boolean writeBOM;
    private boolean conversionDone;
    private Charset sourceEncoding;
    private HashMap<Charset, Class<? extends UTFInputStreamDecoder>> decoders;
    private byte[] BOM = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
    
    public UTF8Encoder(InputStream in, OutputStream out, Charset sourceEncoding) throws UnsupportedEncodingException {
        initializeDecoders();
        if (!decoders.containsKey(sourceEncoding))
            throw new UnsupportedEncodingException();
        this.sourceEncoding = sourceEncoding;
        this.in = in;
        this.out = out;
    }
    
    private void initializeDecoders() {
        decoders = new HashMap<Charset, Class<? extends UTFInputStreamDecoder>>();
        
        decoders.put(Charset.forName("UTF-32"), UTF32InputStreamDecoder.class);
    }

    public boolean isWriteBOM() {
        return writeBOM;
    }
    
    public void setWriteBOM(boolean writeBOM) {
        if (isConversionDone())
            throw new IllegalStateException("Conversion already done");
        this.writeBOM = writeBOM;
    }

    public boolean isConversionDone() {
        return conversionDone;
    }

    public void convert() throws IOException {
        if (conversionDone)
            throw new IllegalStateException("conversion already done");
        
        Class<? extends UTFInputStreamDecoder> decoderClass = decoders.get(sourceEncoding);
        UTFInputStreamDecoder decoder = null;
        BufferedOutputStream outStream = null;
        
        try {
            Constructor<? extends UTFInputStreamDecoder> constructor = decoderClass
                    .getConstructor(InputStream.class);
            decoder = constructor.newInstance(in);
            outStream = new BufferedOutputStream(out);
            
            if (writeBOM)
                outStream.write(BOM);
            
            int word;
            while ((word = decoder.read()) != -1) {
                byte[] encodedWord = encode(word);
                outStream.write(encodedWord);
            }
            
        } catch (NoSuchMethodException | SecurityException | InstantiationException
                | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            if (this.in != null)
                this.in.close();
            
            if (outStream != null) {
                outStream.flush();
                outStream.close();
            }
            conversionDone = true;
        }
        
    }

    private byte[] encode(int word) {
        
        byte[] encoded = null;
        
        if (word >= 0x0 && word <= 0x7f) {
            
            encoded = new byte[1];
            int bit7 = word & 0b111_1111;
            encoded[0] = (byte) (bit7 | 0b0_0000000);
            
        } else if (word >= 0x80 && word <= 0x7ff) {
            
            encoded = new byte[2];
            
            int i = encoded.length;
            
            while (--i > 0) {
                int bit6 = word & 0b111111;
                encoded[i] = (byte) (bit6 | 0b10_000000);
                word >>= 6;
            }
            
            int bit5 = word & 0b11111;
            encoded[i] = (byte) (bit5 | 0b110_00000);
            
        } else if (word >= 0x800 && word <= 0xffff) {
        
            encoded = new byte[3];
            
            int i = encoded.length;
            
            while (--i > 0) {
                int bit6 = word & 0b111111;
                encoded[i] = (byte) (bit6 | 0b10_000000);
                word >>= 6;
            }
            
            int bit4 = word & 0b1111;
            encoded[i] = (byte) (bit4 | 0b1110_0000);
        } else if (word >= 0x10000 && word <= 0x1fffff) {
        
            encoded = new byte[4];
            
            int i = encoded.length;
            
            while (--i > 0) {
                int bit6 = word & 0b111111;
                encoded[i] = (byte) (bit6 | 0b10_000000);
                word >>= 6;
            }
            
            int bit3 = word & 0b111;
            encoded[i] = (byte) (bit3 | 0b11110_000);
        } else if (word >= 0x200000 && word <= 0x3ffffff) {
        
            encoded = new byte[5];
            
            int i = encoded.length;
            
            while (--i > 0) {
                int bit6 = word & 0b111111;
                encoded[i] = (byte) (bit6 | 0b10_000000);
                word >>= 6;
            }
            
            int bit2 = word & 0b11;
            encoded[i] = (byte) (bit2 | 0b111110_00);
        } else if (word >= 0x4000000 && word <= 0x7fffffff) {
        
            encoded = new byte[6];
            
            int i = encoded.length;
            
            while (--i > 0) {
                int bit6 = word & 0b111111;
                encoded[i] = (byte) (bit6 | 0b10_000000);
                word >>= 6;
            }
            
            int bit1 = word & 0b1;
            encoded[i] = (byte) (bit1 | 0b1111110_0);
        } else {
            throw new IllegalArgumentException("invalid code point");
        }
        
        return encoded;
    }
}
