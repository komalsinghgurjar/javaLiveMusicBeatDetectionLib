package org.example;

import java.util.Arrays;

// Sampled Audio Data Container Class PCM audio data
public class SampledAudio implements SampledAudioInterface {
	
    private int[] sample;        // Array of audio samples
    private int sampleRate;      // Sampling rate in Hz (e.g., 44100 for standard audio)
    private int bitDepth;        // Bit depth (e.g., 16 for CD-quality audio)
    private int sampleCount;     // Number of audio samples

    // Constructor updated to match the new fields
    public SampledAudio(int[] sample, int sampleRate, int bitDepth) {
        this.sample = sample;
        this.sampleRate = sampleRate;
        this.bitDepth = bitDepth;
        this.sampleCount = sample.length;
    }

    @Override
    public int[] getSamples() {
        return sample;
    }
    
    @Override
    public int getBitDepth(){
    	return bitDepth;
    }

    @Override
    public void setSamples(int[] sample) {
        this.sample = sample;
        this.sampleCount = sample.length; // Update sampleCount when samples change
    }

    @Override
    public int getSampleRate() {
        return sampleRate;
    }

    @Override
    public void setSampleRate(int rate) {
        this.sampleRate = rate;
    }

    @Override
    public int getSampleCount() {
        return sampleCount;
    }

    @Override
    public double getMax() {
        return Arrays.stream(sample).max().orElse(Integer.MIN_VALUE);
    }

    @Override
    public double getMin() {
        return Arrays.stream(sample).min().orElse(Integer.MAX_VALUE);
    }

    @Override
    public double getAvg() {
        return Arrays.stream(sample).average().orElse(Double.NaN);
    }

    @Override
    public String toString() {
        return "SampledAudio{" +
               "sample=" + Arrays.toString(sample) +
               ", sampleRate=" + sampleRate +
               ", bitDepth=" + bitDepth +
               ", sampleCount=" + sampleCount +
               '}';
    }
}
