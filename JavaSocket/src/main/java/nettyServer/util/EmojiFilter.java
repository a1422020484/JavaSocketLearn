package nettyServer.util;

import java.util.regex.Matcher;

/**
 * @author xiezuojie
 */
public class EmojiFilter {

    /**
     * 将源字符串中的Emoji符号替换成'*'
     *
     * @param source
     * @param replacement
     * @return 过滤后的字符串
     */
    public static String filterEmoji(String source, String replacement) {
        if (!GameConfig.EmojiFilter) {
            return source;
        }

        if (source != null && GameConfig.EmojiPattern != null) {
            try {
                Matcher emojiMatcher = GameConfig.EmojiPattern.matcher(source);
                if (emojiMatcher.find()) {
                    source = emojiMatcher.replaceAll(replacement);
                    return source;
                }
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
        if (!GameConfig.EmojiFilter) {
            return false;
        }

        if (source != null && GameConfig.EmojiPattern != null) {
            try {
                Matcher emojiMatcher = GameConfig.EmojiPattern.matcher(source);
                if (emojiMatcher.find()) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
