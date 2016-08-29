package com.mustang.utf16toutf8.io;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DualByteFetchInputStream extends BufferedInputStream {

	public DualByteFetchInputStream(InputStream in, int size) {
		super(in, size);
	}

	public DualByteFetchInputStream(InputStream in) {
		super(in);
	}

	@Override
	public synchronized int read() throws IOException {
		int utf16Word = 0;
		int temp = super.read();
		
		if (temp == -1)
			return temp;
		
		utf16Word = temp;
		
		temp = super.read();
		
		if (temp == -1)
			throw new IOException("invalid utf-16 stream; having odd number of bytes");
		
		utf16Word |= temp << 8;
		return utf16Word;
	}

	
}
