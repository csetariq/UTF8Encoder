package com.mustang.utf16toutf8.io;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class UTFInputStreamDecoder {
	
	protected BufferedInputStream stream;
	
	public UTFInputStreamDecoder(InputStream in, int size) throws IOException {
		stream = new BufferedInputStream(in, size);
	}
	
	public UTFInputStreamDecoder(InputStream in) throws IOException {
		this(in, 8192);
	}

	public abstract int read() throws IOException;
}
