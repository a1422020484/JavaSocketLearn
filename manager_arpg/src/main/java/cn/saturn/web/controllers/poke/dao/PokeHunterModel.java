package cn.saturn.web.controllers.poke.dao;

public class PokeHunterModel {
	private int pokeId;
	private String pokeName;
	private int huntingType;
	private int weight;
	private int hotPoke;
	private int least;
	private int most;
	private int hornID;

	public static PokeHunterModel create(PokeHunterModel model, int least, int most) {
		PokeHunterModel pNew = new PokeHunterModel();
		pNew.setPokeId(model.getPokeId());
		pNew.setPokeName(model.getPokeName());
		pNew.setHuntingType(model.getHuntingType());
		pNew.setWeight(100);
		pNew.setHotPoke(2);
		pNew.setLeast(least);
		pNew.setMost(most);
		pNew.setHornID(model.getHornID());

		return pNew;
	}

	public int getPokeId() {
		return pokeId;
	}

	public String getPokeName() {
		return pokeName;
	}

	public int getHuntingType() {
		return huntingType;
	}

	public int getWeight() {
		return weight;
	}

	public int getHotPoke() {
		return hotPoke;
	}

	public int getLeast() {
		return least;
	}

	public int getMost() {
		return most;
	}

	public int getHornID() {
		return hornID;
	}

	public void setPokeId(int pokeId) {
		this.pokeId = pokeId;
	}

	public void setPokeName(String pokeName) {
		this.pokeName = pokeName;
	}

	public void setHuntingType(int huntingType) {
		this.huntingType = huntingType;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void setHotPoke(int hotPoke) {
		this.hotPoke = hotPoke;
	}

	public void setLeast(int least) {
		this.least = least;
	}

	public void setMost(int most) {
		this.most = most;
	}

	public void setHornID(int hornID) {
		this.hornID = hornID;
	}

	@Override
	public String toString() {
		return "PokeHunterModel{" + "pokeId=" + pokeId + ", pokeName='" + pokeName + '\'' + ", huntingType="
				+ huntingType + ", weight=" + weight + ", hotPoke=" + hotPoke + ", least=" + least + ", most=" + most
				+ ", hornID=" + hornID + '}';
	}
}
