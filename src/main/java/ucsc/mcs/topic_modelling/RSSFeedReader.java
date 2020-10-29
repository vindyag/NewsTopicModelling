package ucsc.mcs.topic_modelling;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RSSFeedReader
{
	/**
	 * retrieve and return news text
	 * 
	 * @param link
	 *            news URL
	 * @param source
	 *            news source
	 * @return news text
	 */
	public String getNewsText( String link, String source )
	{
		URL url;
		String newsText = "";
		try
		{
			url = new URL( link );
			Document doc = Jsoup.parse( url, 3 * 1000 );

			if ( source.equals( "DailyMirror" ) )
			{
				newsText = retreiveNewsTextFromDailyMirror( doc );
			}
			if ( source.equals( "Derana" ) )
			{
				newsText = retreiveNewsTextFromDerana( doc );
			}
			else if ( source.equals( "Hiru" ) )
			{
				newsText = retreiveNewsTextFromHiru( doc );
			}
			else if ( source.equals( "DailyMirror" ) )
			{
				newsText = retreiveNewsTextFromDailyMirror( doc );
			}
			else if ( source.equals( "DailyMirror_Sports" ) )
			{
				newsText = retreiveNewsTextFromDailyMirrorSports( doc );
			}
			else if ( source.equals( "TimesLanka" ) )
			{
				newsText = retreiveNewsTextFromTimesLanka( doc );
			}

		}
		catch ( MalformedURLException e )
		{
			e.printStackTrace();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}

		return newsText;
	}

	/**
	 * extract and return news text from 'Document' element of DailyMirror
	 * 
	 * @param doc
	 *            'Document' element
	 * @return news text extracted from 'Document' element
	 * @throws IOException
	 */
	private static String retreiveNewsTextFromDailyMirror( Document doc ) throws IOException
	{

		Elements elements = doc.select( "div.inner-text > p" );
		StringBuilder newsTest = new StringBuilder();
		for ( Element element : elements )
		{
			newsTest.append( "\n" );
			newsTest.append( element.text().trim() );
		}
		System.out.println( "news: " + newsTest );
		return newsTest.toString();
	}

	/**
	 * extract and return news text from 'Document' element of Derana
	 * 
	 * @param doc
	 *            'Document' element
	 * @return news text extracted from 'Document' element
	 * @throws IOException
	 */
	private static String retreiveNewsTextFromDerana( Document doc ) throws IOException
	{
		Elements elements = doc.select( "div.newsContent > p" );

		StringBuilder newsTest = new StringBuilder();
		for ( Element element : elements )
		{
			newsTest.append( "\n" );
			newsTest.append( element.text().trim() );
		}
		System.out.println( "news: " + newsTest );
		return newsTest.toString();
	}

	/**
	 * extract and return news text from 'Document' element of Hiru
	 * 
	 * @param doc
	 *            'Document' element
	 * @return news text extracted from 'Document' element
	 * @throws IOException
	 */
	private static String retreiveNewsTextFromHiru( Document doc ) throws IOException
	{
		Elements elements = doc.select( "div.lts-txt2 > p" );
		StringBuilder newsTest = new StringBuilder();
		for ( Element element : elements )
		{
			newsTest.append( "\n" );
			newsTest.append( element.text().trim() );
		}
		System.out.println( "news: " + newsTest );
		return newsTest.toString();
	}

	/**
	 * extract and return news text from 'Document' element of DailyMirrorSports
	 * 
	 * @param doc
	 *            'Document' element
	 * @return news text extracted from 'Document' element
	 * @throws IOException
	 */
	private static String retreiveNewsTextFromDailyMirrorSports( Document doc ) throws IOException
	{
		Elements elements = doc.select( "div.postarea > p" );
		StringBuilder newsTest = new StringBuilder();
		for ( Element element : elements )
		{
			newsTest.append( "\n" );
			newsTest.append( element.text().trim() );
		}
		System.out.println( "news: " + newsTest );
		return newsTest.toString();
	}

	/**
	 * extract and return news text from 'Document' element of TimesLanka
	 * 
	 * @param doc
	 *            'Document' element
	 * @return news text extracted from 'Document' element
	 * @throws IOException
	 */
	private static String retreiveNewsTextFromTimesLanka( Document doc ) throws IOException
	{
		Elements elements = doc.select( "div.entry > p" );
		StringBuilder newsTest = new StringBuilder();
		for ( Element element : elements )
		{
			newsTest.append( "\n" );
			newsTest.append( element.text().trim() );
		}
		System.out.println( "news: " + newsTest );
		return newsTest.toString();
	}

}
