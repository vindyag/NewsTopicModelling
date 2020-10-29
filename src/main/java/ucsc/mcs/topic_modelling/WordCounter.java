package ucsc.mcs.topic_modelling;

import java.io.*;
import java.util.*;

import ucsc.mcs.topic_modelling.entity.NewsFeedMessage;

public class WordCounter
{
	/**
	 * Inner Class Word
	 */
	public static class Word implements Comparable<Word>
	{
		String wordText;
		int count;

		@Override
		public int hashCode()
		{
			return wordText.hashCode();
		}

		@Override
		public boolean equals( Object obj )
		{
			return wordText.equals( ( ( Word ) obj ).wordText );
		}

		@Override
		public int compareTo( Word w )
		{
			return w.count - count;
		}
	}

	/**
	 * Write top 30 words and the words composed of only 1 or 2 characters to AdditionalStopWords.txt file
	 * 
	 * @param messageIterator
	 *            NewsFeedMessage Iterator
	 * @throws IOException
	 *             IOException
	 */
	public void writeTopWords( Iterable<NewsFeedMessage> messageIterator ) throws IOException
	{
		long time = System.currentTimeMillis();

		Map<String, Word> wordCountMap = populateWordCountMap( messageIterator );

		Writer writer = new FileWriter( "AdditionalStopWords.txt" );
		SortedSet<Word> sortedWords = new TreeSet<Word>( wordCountMap.values() );
		int i = 0;
		for ( Word word : sortedWords )
		{
			if ( i > 29 )
			{
				break;
			}

			System.out.println( word.count + "\t" + word.wordText );
			writer.write( word.wordText + "\n" );
			i++;
		}

		// Removing words composed of only 1 or 2 characters
		System.out.println( "\nWords words composed of only 1 or 2 characters." );
		for ( Word word : sortedWords )
		{
			String singleWord = word.wordText;
			if ( singleWord.length() <= 2 )
			{
				System.out.println( word.count + "\t" + word.wordText );
				writer.write( word.wordText + "\n" );
			}
		}

		time = System.currentTimeMillis() - time;

		writer.close();
		System.out.println( "in " + time + " ms" );
	}

	/**
	 * Populate HashMap with key:word text, value: word object
	 * 
	 * @param messageIterator
	 * @return
	 */
	private Map populateWordCountMap( Iterable<NewsFeedMessage> messageIterator )
	{
		Map<String, Word> wordCountMap = new HashMap<String, Word>();
		for ( NewsFeedMessage feedMessage : messageIterator )
		{
			String newsText = feedMessage.getNewsText();
			if ( newsText != null && !newsText.isEmpty() )
			{
				String line = feedMessage.getNewsText();

				String[] words = line.split( "[^A-Za-z]+" );
				for ( String word : words )
				{
					if ( word.trim().isEmpty() )
					{
						continue;
					}

					Word wordObj = wordCountMap.get( word.toLowerCase() );
					if ( wordObj == null )
					{
						wordObj = new Word();
						wordObj.wordText = word.toLowerCase();
						wordObj.count = 0;
						wordCountMap.put( word.toLowerCase(), wordObj );
					}

					wordObj.count++;
				}
			}
		}
		return wordCountMap;
	}

	/**
	 * Count and return the word count
	 * 
	 * @param messageIterator
	 *            NewsFeedMessage iterator
	 * @throws IOException
	 *             IOException
	 */
	public void getWordCount( Iterable<NewsFeedMessage> messageIterator ) throws IOException
	{
		long time = System.currentTimeMillis();

		Map<String, Word> countMap = new HashMap<String, Word>();

		for ( NewsFeedMessage feedMessage : messageIterator )
		{
			String newsText = feedMessage.getNewsText();
			if ( newsText != null && !newsText.isEmpty() )
			{
				String line = feedMessage.getNewsText();

				String[] words = line.split( "[^A-Za-z]+" );
				for ( String word : words )
				{
					if ( word.trim().isEmpty() )
					{
						continue;
					}

					Word wordObj = countMap.get( word.toLowerCase() );
					if ( wordObj == null )
					{
						wordObj = new Word();
						wordObj.wordText = word.toLowerCase();
						wordObj.count = 0;
						countMap.put( word.toLowerCase(), wordObj );
					}

					wordObj.count++;
				}
			}
		}

		System.out.println( "word count :" + countMap.size() ); // word count :5606

		time = System.currentTimeMillis() - time;
		System.out.println( "in " + time + " ms" );
	}

}
