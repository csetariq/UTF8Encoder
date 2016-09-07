package com.mustang.utf8encoder.io;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class UTFInputStreamDecoder implements AutoCloseable {
    
    protected BufferedInputStream stream;
    
    public UTFInputStreamDecoder(InputStream in, int size) {
        stream = new BufferedInputStream(in, size);
    }
    
    public UTFInputStreamDecoder(InputStream in) {
        this(in, 8192);
    }

    public abstract int read() throws IOException;

    @Override
    public void close() throws Exception {
        stream.close();
    }
    
    
}
