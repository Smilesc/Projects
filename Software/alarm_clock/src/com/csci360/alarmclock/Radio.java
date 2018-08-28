package com.csci360.alarmclock;

public class Radio {
	public Double frequency;
	public String modulation;
	public String[] radio;

	//public int[] freq1 = [88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106];
	public int freqIndex1 = 0;
	//public int[] freq2 = [1,3,5,7,9];
	public int freqIndex2 = 0;


	public Radio() {
		//frequency = freq1[freqIndex1] + "." + freq2[freqIndex2];
		modulation = "FM";
		radio = new String[3];
	}
	public void setModulation(String mod){
		if(mod.equals("FM") || mod.equals("AM")){
			modulation = mod;
		}

	}


	public void tune(String mod, Double freq) {
		if(mod.equals("FM")) {
			if(freq > 88.0 && freq < 108.0 && freq % 0.1 == 0) {
				frequency = freq;
				modulation = mod;
			}
		}
		else if(mod.equals("AM")){
			if(freq > 540 && freq < 1700 && freq % 10 == 0) {
				frequency = freq;
				modulation = mod;
			}
		}

	}
	public String[] getRadio(){
		radio[0] = modulation;
		radio[1] = Double.toString(frequency);
		return radio;
	}

}
