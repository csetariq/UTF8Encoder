package com.mustang.utf16toutf8.io;

import java.io.IOException;
import java.io.InputStream;

public class UTF32InputStreamDecoder extends UTFInputStreamDecoder {

    public UTF32InputStreamDecoder(InputStream in, int size) throws IOException {
        super(in, size);
    }

    public UTF32InputStreamDecoder(InputStream in) throws IOException {
        this(in, 8192);
    }

    @Override
    public int read() throws IOException {
        
        int utf32Word = 0;
        
        for (int i = 3; i >= 0; --i) {
            int temp = stream.read();
            
            if (i == 3 && temp == -1)
                return temp;
            
            if (i != 3 && temp == -1)
                throw new IllegalArgumentException("Invalid UTF-32 input stream");
            
            utf32Word |= (temp & 0xff) << (i * 8);
        }
        
        return utf32Word;
    }
}
