package com.spamreviews.firstmapreduce;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.sentdetect.SentenceSampleStream;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

public class SentenceDetectorTrainingExample {

	public static void main(String[] args) {
		try {
			new SentenceDetectorTrainingExample().trainSentDectectModel();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method generates s custom model file for sentence detection, in directory "custom_models".
	 * The training data used is "trainingDataSentences.txt". Training data contains a sentence per line in the text file.
	 * @throws IOException
	 */
	public void trainSentDectectModel() throws IOException {
		// directory to save the model file that is to be generated, create this directory in prior
		File destinationDirectory = new File("C:/Users/Nithya Kumar/eclipse-workspace/Team3Project/src/com/spamreviews/firstmapreduce/custom_models"); 

		// training data
		InputStreamFactory inputStream = new MarkableFileInputStreamFactory(new File("C:/Users/Nithya Kumar/eclipse-workspace/Team3Project/src/com/spamreviews/firstmapreduce/trainingDataSentences.txt"));

		// parameters used by machine learning algorithm, Maxent, to train its weights
		TrainingParameters mlParams = new TrainingParameters();
		mlParams.put(TrainingParameters.ITERATIONS_PARAM, Integer.toString(15));
		mlParams.put(TrainingParameters.CUTOFF_PARAM, Integer.toString(1));

		// train the model
		SentenceModel sentenceDetectModel = SentenceDetectorME.train(
				"en",
				new SentenceSampleStream(new PlainTextByLineStream(inputStream, StandardCharsets.UTF_8)),
				true,
				null,
				mlParams);

		// save the model, to a file, "en-sent-custom.bin", in the destDir : "custom_models"
		File outFile = new File(destinationDirectory,"en-sent-custom.bin"); 
		FileOutputStream outFileStream = new FileOutputStream(outFile); 
		sentenceDetectModel.serialize(outFileStream);  

		// loading the model
		SentenceDetectorME sentDetector = new SentenceDetectorME(sentenceDetectModel); 

		// detecting sentences in the test string
		String testString = ("I was looking for an ankle boot with a low heel and these fit that perfectly and are super cute.\\r\\n\" + \r\n" + 
				"				\"In the photos, even the ones posted by buyers, they looked a little darker brown to me so I was slightly disappointed when they arrived and they weren't as dark as I had hoped but I still like them.\\r\\n\" + \r\n" + 
				"				\"I ordered a size 7 which is usually what I wear and they were a tad snug around the wide part of my foot and on my toes for the first few hours wearing them but they loosened up and are more comfortable now.\\r\\n\" + \r\n" + 
				"				\"The top leg opening when zipped was kinda tight on my legs and rubbed when I walked, a 7.5 may actually have been a better fit for me but I figured all this out while wearing them out the first time and couldn't return them so I'm making it work.\\r\\n\" + \r\n" + 
				"				\"They seem to made well but I did find a couple small imperfections that could've been done better in my opinion. I will show in photos.\\r\\n\" + \r\n" + 
				"				\"I really like the buckles for decoration.");
		System.out.println("\nTest String: "+testString);
		String[] sents = sentDetector.sentDetect(testString);
		System.out.println("---------Sentences Detected by the SentenceDetector ME class using the generated model-------");
		for(int i=0;i<sents.length;i++){
			System.out.println("Sentence "+(i+1)+" : "+sents[i]);
		}
	} 
}