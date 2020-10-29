package ucsc.mcs.topic_modelling;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import ucsc.mcs.topic_modelling.entity.NewsFeedMessage;
import ucsc.mcs.topic_modelling.entity.NewsSource;

public class RSSNewsFeedParser
{
	static final String TITLE = "title";
	static final String LINK = "link";
	static final String ITEM = "item";

	final URL url;

	public RSSNewsFeedParser( String feedUrl )
	{
		try
		{
			this.url = new URL( feedUrl );
		}
		catch ( MalformedURLException e )
		{
			throw new RuntimeException( e );
		}
	}

	/**
	 * read the text from source URL and create and return NewsFeedMessage
	 * 
	 * @param sourceURL
	 *            source URL
	 * @return text
	 */
	public List<NewsFeedMessage> readFeed( String source )
	{
		RSSFeedReader feedReader = new RSSFeedReader();
		NewsSource newsSource = null;
		List<NewsFeedMessage> newsFeedMessages = new ArrayList<NewsFeedMessage>();
		boolean isFeedHeader = true;
		// Initialize header values as empty strings
		String title = "";
		String link = "";

		// Instantiate a new XMLInputFactory
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		try
		{
			// Setup a new eventReader
			InputStream in = url.openStream();
			XMLEventReader eventReader = inputFactory.createXMLEventReader( in );

			// read the XML document
			while ( eventReader.hasNext() )
			{
				XMLEvent event = eventReader.nextEvent();
				if ( event.isStartElement() )
				{
					String localPart = event.asStartElement().getName().getLocalPart();
					switch ( localPart )
					{
						case ITEM:
							if ( isFeedHeader )
							{
								isFeedHeader = false;
								newsSource = new NewsSource( title, link, new Date() );
							}
							event = eventReader.nextEvent();
							break;
						case TITLE:
							title = getCharacterData( event, eventReader );
							break;
						case LINK:
							link = getCharacterData( event, eventReader );
							break;
					}
				}
				else if ( event.isEndElement() )
				{
					if ( event.asEndElement().getName().getLocalPart().equals( ITEM ) )
					{
						NewsFeedMessage message = new NewsFeedMessage();
						message.setUrl( link );
						message.setTitle( title );
						message.setDate( new Date() );
						message.setNewsSource( newsSource );

						String newsText = feedReader.getNewsText( link, source );
						message.setNewsText( newsText );

						newsFeedMessages.add( message );
						event = eventReader.nextEvent();
						continue;
					}
				}
			}
		}
		catch ( XMLStreamException e )
		{
			e.printStackTrace();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
		return newsFeedMessages;
	}

	/**
	 * @param event
	 *            XMLEvent
	 * @param eventReader
	 *            XMLEventReader
	 * @return extracted text
	 * @throws XMLStreamException
	 */
	private String getCharacterData( XMLEvent event, XMLEventReader eventReader ) throws XMLStreamException
	{
		String result = "";
		event = eventReader.nextEvent();
		if ( event instanceof Characters )
		{
			result = event.asCharacters().getData();
		}
		return result;
	}

}
