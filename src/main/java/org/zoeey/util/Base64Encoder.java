/*
 * MoXie (SysTem128@GMail.Com) 2009-8-9 18:51:49
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zoeey.constant.EnvConstants;

/**
 * <pre>
 * Base64 编码
 * Wikipedia: <a href="http://en.wikipedia.org/wiki/Base64" >Base64</a>
 * </pre>
 * @author MoXie
 */
class Base64Encoder {
//   The Base64 Alphabet
// Value Encoding  Value Encoding  Value Encoding  Value Encoding
//        0 A            17 R            34 i            51 z
//        1 B            18 S            35 j            52 0
//        2 C            19 T            36 k            53 1
//        3 D            20 U            37 l            54 2
//        4 E            21 V            38 m            55 3
//        5 F            22 W            39 n            56 4
//        6 G            23 X            40 o            57 5
//        7 H            24 Y            41 p            58 6
//        8 I            25 Z            42 q            59 7
//        9 J            26 a            43 r            60 8
//       10 K            27 b            44 s            61 9
//       11 L            28 c            45 t            62 +
//       12 M            29 d            46 u            63 /
//       13 N            30 e            47 v
//       14 O            31 f            48 w
//       15 P            32 g            49 x
//       16 Q            33 h            50 y
// ----------------------------------------------------------
// ISO Latin-1 Character Set
// 0 48 1 49 2 50  3 51 4 52  5 53
// 6 54 7 55 8 56  9 57
// A 65  B 66  C 67  D 68  E 69
// F 70  G 71  H 72  I 73  J 74
// K 75  L 76  M 77  N 78  O 79
// P 80  Q 81  R 82  S 83 T 84
// U 85  V 86  W 87  X 88  Y 89
// Z 90
// a 97  b 98  c 99 d 100  e 101
// f 102  g 103 h 104  i 105 j 106
// k 107 l 108  m 109 n 110  o 111
// p 112  q 113 r 114  s 115 t 116
// u 117 v 118  w 119 x 120  y 121
// z 122

    /**
     * 64位字母表
     */
    final static char[] alphabet = {
        // 0    1    2    3    4    5    6    7
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', // 0
        'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', // 1
        'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', // 2
        'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', // 3
        'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', // 4
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v', // 5
        'w', 'x', 'y', 'z', '0', '1', '2', '3', // 6
        '4', '5', '6', '7', '8', '9', '+', '/' // 7
    };

    /**
     * 锁定创建
     */
    Base64Encoder() {
    }

    /**
     * 对位编码
     * @param strBuilder
     * @param bytes
     * @param size
     */
    private void encode(StringBuilder strBuilder, byte[] bytes, int size) {
        if (size > 0) {
            strBuilder.append(alphabet[(bytes[0] >>> 2) & 0x3F]);
            strBuilder.append(alphabet[((bytes[0] << 4) & 0x30) + ((bytes[1] >>> 4) & 0xF)]);
        }
        if (size > 1) {
            strBuilder.append(alphabet[((bytes[1] << 2) & 0x3C) + ((bytes[2] >>> 6) & 0x3)]);
        }
        if (size > 2) {
            strBuilder.append(alphabet[bytes[2] & 0x3F]);
        }
        for (int i = size; i < 3; i++) {
            strBuilder.append('=');
        }
    }

    /**
     * 对字节序列进行编码
     * @param bytes
     * @return
     */
    public String encode(byte[] bytes) {
        int index = 0;
        int length = bytes.length;
        byte[] buffer;
        int i = 0;
        StringBuilder strBuilder = new StringBuilder();
        while (index < length) {
            buffer = new byte[3];
            for (i = 0; i < 3; i++) {
                buffer[i] = bytes[index++];
                if (index == length) {
                    break;
                }
            }
            encode(strBuilder, buffer, i + 1);
        }
        return strBuilder.toString();
    }

    /**
     * 对字符串进行编码
     * @param str
     * @return
     */
    public String encode(String str) {
        try {
            if (str == null) {
                return str;
            }
            return encode(str.getBytes(EnvConstants.CHARSET));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Base64Encoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
