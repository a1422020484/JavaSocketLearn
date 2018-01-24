package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

public class MapTest {
	public static void main(String[] args) {
		for (int i = 1; i < 10000; i++) {
			try {
				tttt();
				System.out.println("=====" + i );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void tttt() {
		List<Player> idSet = new ArrayList<>();
		Set<Integer> idset = new HashSet<>();
		Random random = new Random();
		for (int i = 0; i < 100; i++) {
			int num = random.nextInt(100);
			if (idset.contains(num)) {
				continue;
			}
			idset.add(num);
			idSet.add(new Player("www" + num, num));
			// idSet.add(new Player("www21", 21));
		}
		for (Player l : idSet) {
//			System.out.println("init === " + l.getName() + " -- " + l.getLevel() + "|||||");
		}
		Collections.shuffle(idSet, new Random());
		// for (int i = 1; i <= 10; i++) {
		// }
		List<Player> testPlayer = chooseLuckPlayer(idSet, 100, 5);
		for (Player l : testPlayer) {
			System.out.print(l.getName() + " --  " + l.getLevel() + "|||||");
		}
		System.out.println();
		// List<Player> testPlayer = chooseLuckPlayer(idSet);
	}

	public static List<Player> chooseLuckPlayer(List<Player> PlayerList, int deltaLevel, int times) {
		int maxDeltaLevel = deltaLevel * times;
		int pre = maxDeltaLevel / deltaLevel;
		Map<Integer, List<Player>> allDeltaPlayer = new TreeMap<>();
		List<Player> luckPlayerList = new ArrayList<>();
		for (int i = 0; i <= pre; i++) {
			allDeltaPlayer.put(deltaLevel * i, new ArrayList<>());
		}
		Player playerMy = new Player("yang", 100);
		for (Player player : PlayerList) {
			int deltaPlayerLevel = Math.abs(player.getLevel() - playerMy.getLevel());
			if (deltaPlayerLevel > maxDeltaLevel) {
				continue;
			}
			int K = (deltaPlayerLevel + (deltaLevel - 1)) / deltaLevel * deltaLevel;
			allDeltaPlayer.get(K).add(player);
			if (allDeltaPlayer.get(K).size() >= 3) {
				break;
			}
		}
		for (Integer element : allDeltaPlayer.keySet()) {
			for (Player Player : allDeltaPlayer.get(element)) {
				luckPlayerList.add(Player);
				if (luckPlayerList.size() >= 3) {
					return luckPlayerList;
				}
			}
		}
		return luckPlayerList;
	}
}

class Player {
	private String name;
	private int level;

	public Player(String name, int level) {
		this.name = name;
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
