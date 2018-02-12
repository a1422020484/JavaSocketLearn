package cn.saturn.web.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiezuojie
 */
public class EmojiFilter {

    public static final Pattern EMOJI_PATTERN = Pattern.compile("[^\u0000-\uFFFF]");

    /**
     * 将源字符串中的Emoji符号替换成'*'
     *
     * @param source
     * @return 过滤后的字符串
     */
    public static String filterEmoji(String source) {
        if (source != null) {
            try {
                Matcher emojiMatcher = EMOJI_PATTERN.matcher(source);
                if (emojiMatcher.find()) {
                    source = emojiMatcher.replaceAll("*");
                    return source;
                }
                return source;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return source;
    }

    /**
     *
     * @param source
     * @return 源字符串是否包含Emoji符号
     */
    public static boolean hasEmoji(String source) {
        if (source != null) {
            try {
                Matcher emojiMatcher = EMOJI_PATTERN.matcher(source);
                if (emojiMatcher.find()) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(filterEmoji("abc中文\uD83D\uDE01\uD83D\uDE42\uD83D\uDE1Ehello"));
    }
}
