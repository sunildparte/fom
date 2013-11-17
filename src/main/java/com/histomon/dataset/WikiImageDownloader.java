package com.histomon.dataset;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.aspectj.util.FileUtil;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class WikiImageDownloader {

	public static void main(String[] args) throws Exception {
		InputStreamReader str = new InputStreamReader(WikiImageDownloader.class.getClassLoader().getResourceAsStream("sample.txt"));
		BufferedReader reader = new BufferedReader(str);
		
		String line = reader.readLine();
		
		while ( line != null ) {
			String url = getImageURL(line);
			downloadImage ( url );
			System.out.println("Downloaded - " + url);
			line = reader.readLine();
		}
	}
	
	private static void downloadImage(String url) throws Exception {
		Response response = Jsoup.connect(url).ignoreContentType(true).execute();
		byte[] bytes = response.bodyAsBytes();
		
		FileOutputStream out = new FileOutputStream("c://hs//ws//photos//" + url.substring(url.lastIndexOf("/") + 1));
		out.write(bytes);
		out.flush();
		out.close();
  }

	public static String getImageURL ( String site ) throws Exception {
		Document doc = Jsoup.connect(site).get();
		Element div = doc.getElementById("file");
		return "http:" + div.childNode(0).attr("href");
	}
}
