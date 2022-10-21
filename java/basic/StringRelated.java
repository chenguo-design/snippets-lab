import org.junit.jupiter.api.Test;

public class StringRelated{
    //CODE
    @Test
    /**
     * java字符串由char值序列表示，char数据类型是一个采用utf-16编码表示unicode码点的
     * 代码单元。大多数的常用unicode字符使用一个代码单元就可以表示，而辅助字符需要一堆
     * 代码单元表示
     char：Java中，char类型为16个二进制位，原本用于表示一个字符。但后来发现，16位已经不够表示所有的字符，所以后来发展出了代码点表示字符的方法。

     代码点(code point)：是指编码字符集中，字符所对应的数字。有效范围从U+0000到U+10FFFF。其中U+0000到U+FFFF为基本字符，U+10000到U+10FFFF为增补字符。

     代码单元(code unit)：对代码点进行编码得到的1或2个16位序列。其中基本字符的代码点直接用一个相同值的代码单元表示，增补字符的代码点用两个代码单元的进行编码，这个范围内没有数字用于表示字符，因此程序可以识别出当前字符是单单元的基本字符，还是双单元的增补字符。

     Java中String.length()方法返回的是代码单元(code unit)的个数，而String.codePointCount(0, length)返回的是码点(code point)个数，即字符的个数。当然，通常这两个值是一致的。
     *
     */
    public void test_codePoint(){
        String str = "\u03C0\uD835\uDD6B";
        //代码单元数量
        int codeUnitCount = str.length();

        //代码点的数量
        int count = str.codePointCount(0,str.length());
        //得到第i个码点
        int i = 1;
        int cp = str.codePointAt(str.offsetByCodePoints(0, i));



    }

}