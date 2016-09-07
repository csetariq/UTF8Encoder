package com.mustang.utf8encoder.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * A decoder that reads characters from a UTF-32 encoded input stream * 
 * 
 * @author Tariq
 *
 */
public class UTF32InputStreamDecoder extends UTFInputStreamDecoder {

    public UTF32InputStreamDecoder(InputStream in, int size) {
        super(in, size);
    }

    public UTF32InputStreamDecoder(InputStream in) {
        this(in, 8192);
    }

    /**
     * In UTF-32, each character's Unicode code-point is encoded in 4 bytes
     * 
     * It reads 4 bytes at once and returns the code-point of one character
     * 
     * @return  code-point of the next character</br>
     *          or</br>
     *          <code>-1 if end-of-stream is reached</code>
     *          
     * @exception   IOException if the stream is closed or if I/O error occurs
     */
    @Override
    public synchronized int read() throws IOException {
        
        final int NUM_BYTES_UTF_32 = Integer.BYTES;
        int utf32Word = 0;
        
        for (int i = 0; i < NUM_BYTES_UTF_32; ++i) {
            int temp = stream.read();
            
            if (i == 0 && temp == -1)
                return temp;
            
            if (i > 0 && temp == -1)
                throw new IllegalArgumentException("Invalid UTF-32 input stream");
            
            utf32Word |= (temp & 0xff) << ((NUM_BYTES_UTF_32 - 1 - i) * Byte.SIZE);
        }
        
        return utf32Word;
    }
}
