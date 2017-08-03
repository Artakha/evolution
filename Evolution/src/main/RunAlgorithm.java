package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RunAlgorithm {
	
	public RunAlgorithm(){
		
	}
	
	public void runAlgorithm(){
		List<Organism> population = new ArrayList<Organism>();
		population = generatePopulation(17);
		for(int i = 0; i < 10; i++){
			System.out.println("\n\nPopulation " + (i) + ": ");
			printData(population);
			
			population = reproduceAndDie(population);
		}
		System.out.println("\n\nFinal population: ");
		printData(population);
	}
	
	public List<Organism> generatePopulation(int popSize){
		List<Organism> population = new ArrayList<Organism>();
		List<Organism> finalPop = new ArrayList<Organism>();
		String[] seqs = new String[20];
		for(int y = 0; y < 20; y++){
			seqs[y] = generateSequence();
		}
		for(int z = 0; z < popSize; z++){
			population.add(generateOrganism(seqs));
			
			finalPop.add(mutate(population.get(z)));
		}
		
		return finalPop;
	}
	
	public Organism generateOrganism(String seqs[]){
		Biocode[] heightBiocode = generateBiocodeHeight(20, seqs);
		Organism organism = new Organism(heightBiocode);
		return organism;
	}
	
	public Biocode[] generateBiocodeHeight(int x, String[] seqs){
		Biocode[] heightBiocode = new Biocode[x];
		
		for (int i = 0; i < x; i++){
			Biocode bio = new Biocode(seqs[i]);
			heightBiocode[i] = bio;
		}
		
		return heightBiocode;
	}
	
	public String generateSequence(){
		String [] genes = {"A", "C", "T", "G"};
		String sequence = "";
		Random r = new Random();
		for (int i = 0; i < 3; i++){
			int select = r.nextInt(genes.length);
			sequence = sequence + genes[select];
		}
		return sequence;
	}
	
	public List<Organism> reproduceAndDie(List<Organism> population){ //rank best 10, replace bottom 5 with new 5
		String[] scores = new String[population.size()];
		for(int i = 0; i < population.size(); i++){
			population.get(i).incrementAge();
			int score = population.get(i).getHeightScore();
			scores[i] = String.valueOf(score) + "," + String.valueOf(i);
		}
		Arrays.sort(scores, Collections.reverseOrder());
		List<Organism> bestHalf = new ArrayList<Organism>();
		List<Organism> worstHalf = new ArrayList<Organism>();
		String scoress;
		for(int x = 0; x < population.size(); x++){ //get top 10 scores and put the organisms in to best their own array
			scoress = scores[x].replaceAll(".*,", "");
			if(x < population.size() / 2){
				bestHalf.add(population.get(Integer.parseInt(scoress)));
			} else if(x < population.size()){
				worstHalf.add(population.get(Integer.parseInt(scoress)));
			}
		}
		List<Organism> newOffspring = new ArrayList<Organism>();
		for(int e = 0; e < bestHalf.size() / 2; e++){
			newOffspring.add(generateOffspring(bestHalf.get(e), bestHalf.get(bestHalf.size() - (e+1))));
		}
		for(int f = 0; f < newOffspring.size(); f++){
			population.add(newOffspring.get(f));
		}
		
		for(int g = population.size() - 1; g >= 0; g--){ //remove old organisms
			if(population.get(g).getAge() >= 4){
				population.remove(g);
			}
		}
		
		return population;
	}
	
	public Organism generateOffspring(Organism orgOne, Organism orgTwo){ //n point crossover
		Biocode[] heightBiocodeOne = orgOne.getHeightBiocode();
		Biocode[] heightBiocodeTwo = orgTwo.getHeightBiocode();
		Biocode[] heightBiocodeThree = new Biocode[heightBiocodeOne.length];
		String[] seqOne = new String[heightBiocodeOne.length];
		String[] seqTwo = new String[heightBiocodeOne.length];
		String[] seqThree = new String[heightBiocodeOne.length];
		int[] numbers = new int[10];       
	    for(int k = 0; k < numbers.length; k++) {
	      numbers[k] = (int)(Math.random()*20); //generate 10 numbers 0-19 for crossover
	    }
	    Arrays.sort(numbers);
		for(int i = 0; i < heightBiocodeOne.length; i++){
			seqOne[i] = heightBiocodeOne[i].getSequence();
			seqTwo[i] = heightBiocodeTwo[i].getSequence();
		}
		int count = 0;
		for(int w = 0; w < heightBiocodeOne.length; w++){
			if(numbers[count] == w){
				seqThree[w] = seqTwo[w];
				count++;
				if(count > 9){
					count = 9;
				}
			} else {
				seqThree[w] = seqOne[w];
			}
		}
		for(int x = 0; x < heightBiocodeOne.length; x++){
			heightBiocodeThree[x] = new Biocode(seqThree[x]);
		}
		
		Organism offspring = new Organism(heightBiocodeThree);
		Organism finalOffspring = mutate(offspring);
		finalOffspring.setOffspring(true);
		return finalOffspring;
	}
	
	public Organism mutate(Organism organism){
		int numMutations = (int)(Math.random() * 6); // num of mutations - change to better method later
		Biocode[] biocodes = organism.getHeightBiocode();
		for(int i = 0; i < numMutations; i++){
			int mutationPoint = (int)(Math.random() * 20);
			String seq = biocodes[mutationPoint].getSequence();
			char[] sequence = seq.toCharArray();
			char[] genes = {'A', 'C', 'T', 'G'};
			Random r = new Random();
			int select = r.nextInt(genes.length);
			sequence[(int)(Math.random() * 3)] = genes[select];
			seq = String.valueOf(sequence);
			biocodes[mutationPoint].setSequence(seq);
		}
		return organism;
	}
	
	public void printData(List<Organism> population){
		Biocode[] heightBiocodes;
		int totalHeightScore = 0;
		for(int x = 0; x < population.size(); x++){
			int heightScore = population.get(x).getHeightScore();
			heightBiocodes = population.get(x).getHeightBiocode();
			System.out.print("\nOrganism " + (x+1) + ": " + population.get(x).isOffspring() + ": ");
			for(int y = 0; y < heightBiocodes.length; y++){
				System.out.print(heightBiocodes[y].getSequence());
				System.out.print(" " + heightBiocodes[y].getBiocodeScore()[0] + " ");
			}
			System.out.print(" __Total height score = " + heightScore + " ___Age = " + population.get(x).getAge());
			totalHeightScore = totalHeightScore + heightScore;
		}
		System.out.println("\nAverage height score: " + ((double)totalHeightScore)/(population.size()));
	}
	
	public void printOrganismData(Organism organism){
		int len = organism.getHeightBiocode().length;
		System.out.print("\nOrg : ");
		for(int x = 0; x < len; x++){
			System.out.print(organism.getHeightBiocode()[x].getSequence() + " ");
		}
	}
}
