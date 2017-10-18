package com.spamreviews.firstmapreduce;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class SentenceDetection {

	public static void main(String[] args) {
		try {
			new SentenceDetection().sentenceDetect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sentenceDetect() throws InvalidFormatException, IOException {
		String review = "I was looking for an ankle boot with a low heel and these fit that perfectly and are super cute.\r\n" + 
				"In the photos, even the ones posted by buyers, they looked a little darker brown to me so I was slightly disappointed when they arrived and they weren't as dark as I had hoped but I still like them.\r\n" + 
				"I ordered a size 7 which is usually what I wear and they were a tad snug around the wide part of my foot and on my toes for the first few hours wearing them but they loosened up and are more comfortable now.\r\n" + 
				"The top leg opening when zipped was kinda tight on my legs and rubbed when I walked, a 7.5 may actually have been a better fit for me but I figured all this out while wearing them out the first time and couldn't return them so I'm making it work.\r\n" + 
				"They seem to made well but I did find a couple small imperfections that could've been done better in my opinion. I will show in photos.\r\n" + 
				"I really like the buckles for decoration.\r\n" + 
				"The zipper works well but can catch sometimes.\r\n" + 
				"They're prett comfortable (if you get the perfect fit) to wear all day. And they are super cute with skinny jeans and I'm sure with dresses too.\r\n" + 
				"All in all, I would recommend these but I'd probably go up a half size maybe especially if you have wide feet. I didn't really think my feet were that wide but apparently they are according to most shoes. I love the look of them and I'm excited I got them. If I catch them on sale again, I may even but the black pair later on but will certainly try a 7.5 instead of 7 next time around.";

		InputStream inputStream = new FileInputStream("en-sent.bin");
		SentenceModel sentenceModel = new SentenceModel(inputStream);

		// load the model
		SentenceDetectorME sdetector = new SentenceDetectorME(sentenceModel);

		// detect sentences in the paragraph
		String sentences[] = sdetector.sentDetect(review);

		// print the sentences detected, to console
		for(int i=0;i<sentences.length;i++){
			System.out.println(sentences[i]);
		}
		inputStream.close();
	}
}