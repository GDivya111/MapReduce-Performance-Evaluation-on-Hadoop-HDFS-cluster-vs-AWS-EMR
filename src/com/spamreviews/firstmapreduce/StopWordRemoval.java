package com.spamreviews.firstmapreduce;

import java.io.IOException;

public class StopWordRemoval {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
	
		String myString = "I bought these for a costume but they are SO adorable looking. I am wearing them to break them in today and they are not causing any discomfort so far which is especially shocking considering the reasonable price. i will definitely be wearing these not just with the costume!";
		String stopWords = "a|an|the|I|it|its|with|within|but";
		String afterStopWords = myString.replaceAll("(" + stopWords + ")\\s*|\\W", "");
		System.out.println(afterStopWords);
	}
}


