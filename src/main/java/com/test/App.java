package com.test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.mustang.utf16toutf8.io.DualByteFetchInputStream;

public class App {
	public static void main(String... args) {
		try {
			FileOutputStream fileOut = new FileOutputStream(new File("test.txt"));
			BufferedOutputStream bufFileOut = new BufferedOutputStream(fileOut);
			toUTF8(System.in, bufFileOut);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
	}

	private static void toUTF8(InputStream in, OutputStream out) throws IOException {
		try (DualByteFetchInputStream dByteIn = new DualByteFetchInputStream(in);) {

			int temp;
			while ((temp = dByteIn.read()) != -1) {
				
			}
		}
	}
}
