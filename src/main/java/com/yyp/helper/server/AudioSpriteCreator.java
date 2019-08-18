package com.yyp.helper.server;

import java.io.File;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class AudioSpriteCreator {

	public Boolean concatenateFiles(List<String> sourceFilesList, String destinationFileName) throws Exception {
		Boolean result = false;

		AudioInputStream audioInputStream = null;
		List<AudioInputStream> audioInputStreamList = null;
		AudioFormat audioFormat = null;
		Long frameLength = null;

		try {
			// loop through our files first and load them up
			for (String sourceFile : sourceFilesList) {
				audioInputStream = AudioSystem.getAudioInputStream(new File(sourceFile));

				// get the format of first file
				if (audioFormat == null) {
					audioFormat = audioInputStream.getFormat();
				}

				// add it to our stream list
				if (audioInputStreamList == null) {
					audioInputStreamList = new ArrayList<AudioInputStream>();
				}
				audioInputStreamList.add(audioInputStream);

				// keep calculating frame length
				if(frameLength == null) {
					frameLength = audioInputStream.getFrameLength();
				}
				else {
					frameLength += audioInputStream.getFrameLength();
				}
			}

			// now write our concatenated file
			AudioSystem.write(new AudioInputStream(new SequenceInputStream(Collections.enumeration(audioInputStreamList)), audioFormat, frameLength), Type.WAVE, new File(destinationFileName));

			// if all is good, return true
			result = true;
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (audioInputStream != null) {
				audioInputStream.close();
			}
			if (audioInputStreamList != null) {
				audioInputStreamList = null;
			}
		}

		return result;
	}
	
	
	static public void main(String as[]) {
		try {
			List<String> fileList = new ArrayList<String>();
			fileList.add("/dev/epic_yoga/src/main/webapp/audio/alignments/bu_s_a.wav");
			fileList.add("/dev/epic_yoga/src/main/webapp/audio/alignments/bw_s_a.wav");
			new AudioSpriteCreator().concatenateFiles(fileList,  "/temp/test_concat.wav");
		}
		catch(Exception e) {
			e.printStackTrace();
			
		}
	}
	
}
