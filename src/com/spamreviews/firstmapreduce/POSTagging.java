package com.spamreviews.firstmapreduce;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class POSTagging {

	public static void main(String[] args) {

		InputStream tokenModelIn = null;
		InputStream posModelIn = null;
		
		try {
			String sentence = "I bought these for a costume but they are SO adorable looking. I am wearing them to break them in today and they are not causing any discomfort so far which is especially shocking considering the reasonable price. i will definitely be wearing these not just with the costume!";
			// tokenize the sentence
			tokenModelIn = new FileInputStream("en-token.bin");
			TokenizerModel tokenModel = new TokenizerModel(tokenModelIn);
			Tokenizer tokenizer = new TokenizerME(tokenModel);
			String tokens[] = tokenizer.tokenize(sentence);

			// Parts-Of-Speech Tagging
			// reading parts-of-speech model to a stream 
			posModelIn = new FileInputStream("en-pos-maxent.bin");
			// loading the parts-of-speech model from stream
			POSModel posModel = new POSModel(posModelIn);
			// initializing the parts-of-speech tagger with model 
			POSTaggerME posTagger = new POSTaggerME(posModel);
			// Tagger tagging the tokens
			String tags[] = posTagger.tag(tokens);
			// Getting the probabilities of the tags given to the tokens
			double probs[] = posTagger.probs();
			
			System.out.println("Token\t:\tTag\t:\tProbability\n---------------------------------------------");
			for(int i=0;i<tokens.length;i++){
				System.out.println(tokens[i]+"\t:\t"+tags[i]+"\t:\t"+probs[i]);
			}
			
		}
		catch (IOException e) {
			// Model loading failed, handle the error
			e.printStackTrace();
		}
		finally {
			if (tokenModelIn != null) {
				try {
					tokenModelIn.close();
				}
				catch (IOException e) {
				}
			}
			if (posModelIn != null) {
				try {
					posModelIn.close();
				}
				catch (IOException e) {
				}
			}
		}
	}
}