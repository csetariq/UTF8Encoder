# UTF8Encoder

A java implementation to convert utf-32 encoded byte stream to utf-8 encoded byte stream

Snipped from Wikipedia

| Number of bytes | Bits for code point | First code point | Last code point |  Byte 1    |  Byte 2    |  Byte 3    |  Byte 4    |  Byte 5    |  Byte 6    |
|-----------------|---------------------|------------------|-----------------|------------|------------|------------|------------|------------|------------|
| 1               | 7                   | U+0000           | U+007F          | `0xxxxxxx` |            |            |            |            |            |
| 2               | 11                  | U+0080           | U+07FF          | `110xxxxx` | `10xxxxxx` |            |            |            |            |
| 3               | 16                  | U+0800           | U+FFFF          | `1110xxxx` | `10xxxxxx` | `10xxxxxx` |            |            |            |
| 4               | 21                  | U+10000          | U+1FFFFF        | `11110xxx` | `10xxxxxx` | `10xxxxxx` | `10xxxxxx` |            |            |
| 5               | 26                  | U+200000         | U+3FFFFFF       | `111110xx` | `10xxxxxx` | `10xxxxxx` | `10xxxxxx` | `10xxxxxx` |            |
| 6               | 31                  | U+4000000        | U+7FFFFFFF      | `1111110x` | `10xxxxxx` | `10xxxxxx` | `10xxxxxx` | `10xxxxxx` | `10xxxxxx` |

## Sample usage

```java
    byte[] buf = {
            // English
            0x00, 0x00, 0x00, 0x41,
            0x00, 0x00, 0x00, 0x42,
            0x00, 0x00, 0x00, 0x43,
            0x00, 0x00, 0x00, 0x44,
            
            0x00, 0x00, 0x00, 0x0A,
            
            // Chinese
            0x00, 0x00, 0x56, 0x05,
            0x00, 0x00, 0x56, 0x06,
            0x00, 0x00, 0x56, 0x07,
            0x00, 0x00, 0x56, 0x08
    };

    InputStream inputStream = new ByteArrayInputStream(buf);
    OutputStream fileOutputStream = new FileOutputStream("test.txt");
    UTF8Encoder utf8Encoder = new UTF8Encoder(inputStream, fileOutputStream, Charset.forName("UTF-32"));
    utf8Encoder.setWriteBOM(true);
    utf8Encoder.convert();
```