package nettyServer.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import nettyServer.util.resource.ResourceLoader;

/**
 * 名字过滤
 */
@Component
public class NameFilter extends ResourceLoader {

	private static Node rootNode = null;

	private static final char FILTER = '*';

//	/**
//	 * 过滤字符串,将敏感字符替换为'*'
//	 *
//	 * @param content
//	 * @return 过滤后的字符串
//	 */
//	public static String filterWords(String content) {
//		int a = 0;
//		char[] chars = content.toCharArray();
//		Node node = rootNode;
//		List<String> word = new ArrayList<String>();
//		while (a < chars.length) {
//			node = findNode(node, chars[a]);
//			if (node == null) {
//				node = rootNode;
//				a = a - word.size();
//				word.clear();
//			} else if (node.flag == 1) {
//				word.add(String.valueOf(chars[a]));
//				for (int i = 0; i < word.size(); i++) {
//					chars[a - i] = FILTER;
//				}
//				a = a - word.size() + 1;
//				word.clear();
//				node = rootNode;
//			} else {
//				word.add(String.valueOf(chars[a]));
//			}
//			a++;
//		}
//		return String.valueOf(chars);
//	}

	/**
	 * @param content
	 * @return 字符串中是否包含敏感字符
	 */
	public static boolean hasBadWords(String content) {
		int a = 0;
		char[] chars = content.toCharArray();
		Node node = rootNode;
		List<String> word = new ArrayList<String>();
		while (a < chars.length) {
			node = findNode(node, chars[a]);
			if (node == null) {
				node = rootNode;
				a = a - word.size();
				word.clear();
			} else if (node.flag == 1) {
				return true;
			} else {
				word.add(String.valueOf(chars[a]));
			}
			a++;
		}
		return EmojiFilter.hasEmoji(content);
	}

	private static void insertNode(Node node, char[] cs, int index) {
		Node n = findNode(node, cs[index]);
		if (n == null) {
			n = new Node(cs[index]);
			node.nodes.add(n);
		}
		if (index == (cs.length - 1))
			n.flag = 1;

		index++;
		if (index < cs.length)
			insertNode(n, cs, index);
	}

	private static Node findNode(Node node, char c) {
		List<Node> nodes = node.nodes;
		Node rn = null;
		for (Node n : nodes) {
			if (n.c == c) {
				rn = n;
				break;
			}
		}
		return rn;
	}

	private static class Node {
		public char c;
		public int flag;
		public List<Node> nodes = new ArrayList<Node>();

		public Node(char c) {
			this.c = c;
			this.flag = 0;
		}
	}

	@Override
	public void load(InputStream is) throws IOException {
		List<String> badwords = new ArrayList<String>();
		rootNode = new Node('R');
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader reader = new BufferedReader(isr);
			String line;
			while ((line = reader.readLine()) != null) {
				badwords.add(line);
			}
			reader.close();
			isr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String str : badwords) {
			if (str != null && str.length() > 0) {
				char[] chars = str.toCharArray();
				if (chars.length > 0)
					insertNode(rootNode, chars, 0);
			}
		}
	}
	
	@Override
	public String getResourceName() {
		return "BadNames";
	}
}
