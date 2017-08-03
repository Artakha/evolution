package main;

public class Biocode {
	
	String sequence;
	
	public Biocode(String sequence){
		this.sequence = sequence;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	
	public int[] getBiocodeScore(){
		int[] scores = new int[1];
		int heightScore = 0;
		if (sequence.charAt(0) == 'G'){
			heightScore+=1;
			if (sequence.charAt(1) == 'C' || sequence.charAt(1) == 'T'){
				heightScore+=1;
				if (sequence.charAt(2) == 'A' || sequence.charAt(2) == 'G'){
					heightScore+=1;
				}
			}
		}
		else if (sequence.charAt(0) == 'T'){
			heightScore+=1;
			if (sequence.charAt(1) == 'G' || sequence.charAt(1) == 'A'){
				heightScore+=1;
				if (sequence.charAt(2) == 'A' || sequence.charAt(2) == 'G'){
					heightScore+=1;
				}
			}
		}
		else if (sequence.charAt(0) == 'C'){
			heightScore+=1;
			if (sequence.charAt(1) == 'T'){
				heightScore+=1;
				if (sequence.charAt(2) == 'A' || sequence.charAt(2) == 'T'){
					heightScore+=1;
				}
			}
		}
		else if (sequence.charAt(0) == 'A'){
			heightScore+=1;
			if (sequence.charAt(1) == 'T' || sequence.charAt(1) == 'A'){
				heightScore+=1;
				if (sequence.charAt(2) == 'C' || sequence.charAt(2) == 'G'){
					heightScore+=2;
				}
			}
		}
		scores[0] = heightScore;
		return scores;
	}
}
