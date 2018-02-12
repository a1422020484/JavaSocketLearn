package cn.saturn.web.controllers.poke.dao;

public class PetModel {
	private int petId;
	
	private int nextPetId;
	
	private String petName;

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public int getNextPetId() {
		return nextPetId;
	}

	public void setNextPetId(int nextPetId) {
		this.nextPetId = nextPetId;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}
	
	
}
