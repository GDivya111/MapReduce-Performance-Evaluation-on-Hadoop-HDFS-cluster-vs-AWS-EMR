package com.spamreviews.firstmapreduce;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class PunctuationRemoval {

	public static void main(String[] args) {
		InputStream modelIn = null;

		try {
			modelIn = new FileInputStream("en-token.bin");
			TokenizerModel model = new TokenizerModel(modelIn);
			TokenizerME tokenizer = new TokenizerME(model);
			String tokens[] = tokenizer.tokenize("I bought these for a costume but they are SO adorable looking. I am wearing them to break them in today and they are not causing any discomfort so far which is especially shocking considering the reasonable price. i will definitely be wearing these not just with the costume!\";\r\n");
			double tokenProbs[] = tokenizer.getTokenProbabilities();
			
			System.out.println("Token\t: Probability\n-------------------------------");
			for(int i=0;i<tokens.length;i++){
				System.out.println(tokens[i]+"\t: "+tokenProbs[i]);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (modelIn != null) {
				try {
					modelIn.close();
				}
				catch (IOException e) {
				}
			}
		}
	}
}