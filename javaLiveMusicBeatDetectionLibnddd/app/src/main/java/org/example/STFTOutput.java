package org.example;

import java.util.List;

public class STFTOutput{
	
    private int sampleRate;      // Sampling rate in Hz (e.g., 44100 for standard audio)
    private List<double[]> stftSpectrogram;
    double[] frequencies;
 
    public STFTOutput(List<double[]> stftSpectrogram, int sampleRate, int frameSize) {
        this.sampleRate = sampleRate;
        this.stftSpectrogram=stftSpectrogram;
        this.frequencies=this.computeFrequencies(frameSize, sampleRate);
    }
    
    private static double[] computeFrequencies(int frameSize, int samplingRate) {
    int halfSize = frameSize / 2;
    double[] frequencies = new double[halfSize];
    double frequencyResolution = (double) samplingRate / frameSize;
    for (int k = 0; k < halfSize; k++) {
        frequencies[k] = k * frequencyResolution;
    }
    return frequencies;
}

public double[] getFrequencies(){
	return this.frequencies;
	}
	
    public int getSampleRate() {
        return sampleRate;
    }
    
    public List<double[]> getStftSpectrogram(){
    	return this.stftSpectrogram;
    }
}
