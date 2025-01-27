package org.example;

public interface SampledAudioInterface {
    
    int getBitDepth();
    
    // Method to get the samples
    int[] getSamples();

    // Method to set the samples
    void setSamples(int[] sample);

    // Method to get the sampling rate
    int getSampleRate();

    // Method to set the sampling rate
    void setSampleRate(int rate);

    // Method to get the size (number of samples)
    int getSampleCount();

    // Method to get the maximum sample value
    double getMax();

    // Method to get the minimum sample value
    double getMin();

    // Method to get the average of the sample values
    double getAvg();
}
