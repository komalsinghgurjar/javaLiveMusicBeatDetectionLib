package org.example;

import java.io.FileOutputStream;
import java.io.IOException;

public class SampledAudioFileSaver {

    private SampledAudio audio;

    public SampledAudioFileSaver(SampledAudio audio) {
        this.audio=audio;
    }

    /**
     * Saves the generated PCM audio data to a WAV file.
     *
     * @param filePath Path of the WAV file to save.
     * @throws IOException If an I/O error occurs while writing the file.
     */
    public void saveAsWAV(String filePath) throws IOException {
        int[] audioData = audio.getSamples();

        // Convert integer PCM data to byte array
        byte[] byteData = convertToPCMBytes(audioData);

        // WAV file parameters
        int channels = 1; // Mono
        int byteRate = audio.getSampleRate() * channels * (audio.getBitDepth() / 8);
        int subChunk2Size = byteData.length;
        int chunkSize = 36 + subChunk2Size;

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            // Write WAV header
            fos.write("RIFF".getBytes()); // Chunk ID
            fos.write(intToLittleEndian(chunkSize, 4)); // Chunk size
            fos.write("WAVE".getBytes()); // Format

            fos.write("fmt ".getBytes()); // Subchunk1 ID
            fos.write(intToLittleEndian(16, 4)); // Subchunk1 size (16 for PCM)
            fos.write(intToLittleEndian(1, 2)); // Audio format (1 for PCM)
            fos.write(intToLittleEndian(channels, 2)); // Number of channels
            fos.write(intToLittleEndian(audio.getSampleRate(), 4)); // Sample rate
            fos.write(intToLittleEndian(byteRate, 4)); // Byte rate
            fos.write(intToLittleEndian(channels * (audio.getBitDepth() / 8), 2)); // Block align
            fos.write(intToLittleEndian(audio.getBitDepth(), 2)); // Bits per sample

            fos.write("data".getBytes()); // Subchunk2 ID
            fos.write(intToLittleEndian(subChunk2Size, 4)); // Subchunk2 size

            // Write PCM audio data
            fos.write(byteData);
        }
    }

    /**
     * Converts the integer PCM audio data to a byte array based on bit depth.
     */
    private byte[] convertToPCMBytes(int[] audioData) {
        int bytesPerSample = audio.getBitDepth() / 8;
        byte[] byteData = new byte[audioData.length * bytesPerSample];

        for (int i = 0; i < audioData.length; i++) {
            int value = audioData[i];

            for (int b = 0; b < bytesPerSample; b++) {
                byteData[i * bytesPerSample + b] = (byte) ((value >> (8 * b)) & 0xFF);
            }
        }

        return byteData;
    }

    /**
     * Converts an integer to a little-endian byte array.
     */
    private byte[] intToLittleEndian(int value, int byteCount) {
        byte[] littleEndian = new byte[byteCount];
        for (int i = 0; i < byteCount; i++) {
            littleEndian[i] = (byte) (value & 0xFF);
            value >>= 8;
        }
        return littleEndian;
    }
}
