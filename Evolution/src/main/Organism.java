package main;

public class Organism {
	
	
	Biocode[] heightBiocode;
	boolean offspring = false;
	int age = 0;

	public Organism(Biocode[] heightBiocode){
		this.heightBiocode = heightBiocode;
	}

	public Biocode[] getHeightBiocode() {
		return heightBiocode;
	}

	public void setHeightBiocode(Biocode[] heightBiocode) {
		this.heightBiocode = heightBiocode;
	}

	public boolean isOffspring() {
		return offspring;
	}

	public void setOffspring(boolean offspring) {
		this.offspring = offspring;
	}
	
	public int getHeightScore(){
		int heightScore = 0;
		for(int i = 0; i < heightBiocode.length; i++){
			heightScore = heightScore + heightBiocode[i].getBiocodeScore()[0];
		}
		return heightScore;
	}

	public int getAge() {
		return age;
	}

	public void incrementAge() {
		age++;
	}
	
	
}
