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