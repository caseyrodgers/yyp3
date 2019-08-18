package com.yyp.helper.server.tts;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;

import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import marytts.MaryInterface;
import marytts.client.RemoteMaryInterface;

import org.apache.log4j.Logger;

import com.yyp.helper.server.YogaServerProperties;

public class AudioCreator {
	
	Logger __logger = Logger.getLogger(AudioCreator.class);
	private String _baseDir;
	
	enum AudioType {POSE_NAME_ENGLISH, POSE_NAME_SANSKRIT, POSE_ALIGNMENT}
	public AudioCreator() {	
		_baseDir = YogaServerProperties.getInstance().getProperty("HomeDir");
	}
	
	
	public void createAudio() throws Exception {
		try {
			MaryInterface marytts = new RemoteMaryInterface("yogayourpractice.com", 59125);
			AudioInputStream audio = marytts.generateAudio("This is my remote text test.");
			
			
			
			File waveFile = new File(_baseDir, "temp/");
			AudioSystem.write(audio, AudioFileFormat.Type.WAVE, waveFile);
			
			
			
			//File audioBase= new File()
			
			File source = waveFile;
			File target = new File("/temp/junk/junk.mp3");
			AudioAttributes audioAttribs = new AudioAttributes();
			audioAttribs.setCodec("libmp3lame");
			audioAttribs.setBitRate(new Integer(128000));
			audioAttribs.setChannels(new Integer(2));
			audioAttribs.setSamplingRate(new Integer(44100));
			EncodingAttributes attrs = new EncodingAttributes();
			attrs.setFormat("mp3");
			attrs.setAudioAttributes(audioAttribs);
			Encoder encoder = new Encoder();
			encoder.encode(source, target, attrs);
		}		
		catch(Exception e) {
			__logger.error("Error create audio", e);
			throw e;
		}
		
	}


	public void createPoseName(String poseKey, String nameEnglish,String nameSanskrit) throws Exception {
		File nameMp3 = new File(_baseDir, "audio/pose_names/" + poseKey + "_n");
		createAudioMp3(nameEnglish, nameMp3);
		
		if(nameSanskrit != null && nameSanskrit.length() > 0) {
			nameMp3 = new File(_baseDir, "audio/pose_names/" + poseKey + "_sn");
			createAudioMp3(nameSanskrit, nameMp3);
		}
	}


	public void createPoseAlignment(String poseKey, boolean isAsymmetrical, String textAlign) throws Exception{
		File nameMp3 = new File(_baseDir, "/audio/alignments/" + poseKey + "_" + (isAsymmetrical?"r":"s") + "_a");
		createAudioMp3(textAlign, nameMp3);
	}


	private void createAudioMp3(String textToConvert, File mp3FileToCreate) throws Exception {
		
		MaryInterface marytts = new RemoteMaryInterface("yogayourpractice.com", 59125);
		AudioInputStream audio = marytts.generateAudio(textToConvert);
		
		File waveFile = new File(_baseDir, "temp/" + mp3FileToCreate.getName() + ".wav");
		AudioSystem.write(audio, AudioFileFormat.Type.WAVE, waveFile);
		
		File mp3File = new File(mp3FileToCreate.getCanonicalPath() +  ".mp3");
		AudioAttributes audioAttribs = new AudioAttributes();
		audioAttribs.setCodec("libmp3lame");
		audioAttribs.setBitRate(new Integer(128000));
		audioAttribs.setChannels(new Integer(2));
		audioAttribs.setSamplingRate(new Integer(44100));
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp3");
		attrs.setAudioAttributes(audioAttribs);
		Encoder encoder = new Encoder();
		encoder.encode(waveFile, mp3File, attrs);		
	}


	public void deletePoseAudio(String key) {
		new File(_baseDir, "audio/pose_names/" + key + "_n").delete();
		new File(_baseDir, "audio/pose_names/" + key + "_sn").delete();
		new File(_baseDir, "audio/alignment/" + key + "_s_a").delete();
		new File(_baseDir, "audio/alignment/" + key + "_r_a").delete();
		new File(_baseDir, "audio/alignment/" + key + "_l_a").delete();
	}

}
