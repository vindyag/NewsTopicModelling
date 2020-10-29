package ucsc.mcs.topic_modelling;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ucsc.mcs.topic_modelling.lda.LDAEvaluator;
import ucsc.mcs.topic_modelling.lda.LDATopicInferencer;
import ucsc.mcs.topic_modelling.repository.NewsFeedRepository;
import ucsc.mcs.topic_modelling.repository.NewsSourceRepository;
import ucsc.mcs.topic_modelling.entity.NewsFeedMessage;

@Component
public class TopicModellingMainBean implements CommandLineRunner
{
	@Autowired
	private NewsSourceRepository newsSourceRepository;

	@Autowired
	private NewsFeedRepository newsFeedRepository;

	public void run( String... strings ) throws Exception
	{
		boolean retreiveNewsData = false;
		boolean writeStopWords = false;
		boolean getWordCount = false;
		boolean inferTopicWithModel = false;
		boolean evaluateTopicModel = false;

		if ( retreiveNewsData )
		{

			// Start of Save
			List<NewsFeedMessage> newsFeedMessages = new ArrayList<NewsFeedMessage>();
			// RSSFeedReader rssFeedReader = new RSSFeedReader();
			RSSNewsFeedParser rssNewsFeedParser = null;

			// Daily Mirror Breaking News
			System.out.println( "Retreiving Daily Mirror Breaking News feeds ..." );
			rssNewsFeedParser = new RSSNewsFeedParser( "http://www.dailymirror.lk/RSS_Feeds/breaking-news" );
			newsFeedMessages.addAll( rssNewsFeedParser.readFeed( "DailyMirror" ) );
			System.out.println( "Successfully retrieved and stored Daily Mirror Breaking News feeds ..." );

			// Daily Mirror Business News
			System.out.println( "Retreiving Daily Mirror Business News feeds ..." );
			rssNewsFeedParser = new RSSNewsFeedParser( "http://www.dailymirror.lk/RSS_Feeds/business-main" );
			newsFeedMessages.addAll( rssNewsFeedParser.readFeed( "DailyMirror" ) );

			System.out.println( "Successfully retrieved and stored Daily Mirror Business News feeds ..." );

			// Daily Mirror Technology News
			System.out.println( "Retreiving Daily Mirror Technology News feeds ..." );
			rssNewsFeedParser = new RSSNewsFeedParser( "http://www.dailymirror.lk/RSS_Feeds/technology" );
			newsFeedMessages.addAll( rssNewsFeedParser.readFeed( "DailyMirror" ) );
			System.out.println( "Successfully retrieved and stored Daily Mirror Technology News feeds ..." );

			// Daily Mirror Travel News
			System.out.println( "Retreiving Daily Mirror Travel News feeds ..." );
			rssNewsFeedParser = new RSSNewsFeedParser( "http://www.dailymirror.lk/RSS_Feeds/travel-main" );
			newsFeedMessages.addAll( rssNewsFeedParser.readFeed( "DailyMirror" ) );
			System.out.println( "Successfully retrieved and stored Daily Mirror Travel News feeds ..." );

			// Daily Mirror Sports News
			System.out.println( "Retreiving Daily Mirror Sports News feeds ..." );
			rssNewsFeedParser = new RSSNewsFeedParser( "http://origin.mirrorsports.lk/feed/" );
			newsFeedMessages.addAll( rssNewsFeedParser.readFeed( "DailyMirror_Sports" ) );
			System.out.println( "Successfully retrieved and stored Daily Mirror Sports News feeds ..." );

			// Ada Derana
			System.out.println( "Retreiving adaderana feeds ..." );
			rssNewsFeedParser = new RSSNewsFeedParser( "http://adaderana.lk/rss.php" );
			newsFeedMessages.addAll( rssNewsFeedParser.readFeed( "Derana" ) );
			System.out.println( "Successfully retrieved and stored adaderana feeds ..." );

			// HiruNews breaking News
			System.out.println( "Retreiving Hiru News feeds ..." );
			rssNewsFeedParser = new RSSNewsFeedParser( "http://www.hirunews.lk/rss/english.xml" );
			newsFeedMessages.addAll( rssNewsFeedParser.readFeed( "Hiru" ) );
			System.out.println( "Successfully retrieved and stored Hiru News feeds ..." );

			newsFeedRepository.save( newsFeedMessages );
			// End of Save
		}
		if ( writeStopWords )
		{
			WordCounter wordCounter = new WordCounter();
			wordCounter.writeTopWords( newsFeedRepository.findAll() );
		}
		if ( getWordCount )
		{
			WordCounter wordCounter = new WordCounter();
			wordCounter.getWordCount( newsFeedRepository.findAll() );
		}
		if ( inferTopicWithModel )
		{

			List<NewsFeedMessage> newMessage = new ArrayList<NewsFeedMessage>();
			// newMessage.add( newsFeedRepository.findOne( new Long(179)) );
			// newMessage.add( newsFeedRepository.findOne( new Long(220)) );
			// newMessage.add( newsFeedRepository.findOne( new Long(250)) );
			// newMessage.add( newsFeedRepository.findOne( new Long(251)) );
			// infer.inferByModel( 9, newMessage );

			LDATopicInferencer newInfer = new LDATopicInferencer();
			// newInfer.inferByModel( 5, newsFeedRepository.findAll() );
			// newInfer.inferByModel( 6, newsFeedRepository.findAll() );
			newInfer.inferByModel( 7, newsFeedRepository.findAll(), false );
			// newInfer.inferByModel( 8, newsFeedRepository.findAll() );
			// newInfer.inferByModel( 9, newsFeedRepository.findAll() );
			// newInfer.inferByModel( 10, newsFeedRepository.findAll() );
		}
		if ( evaluateTopicModel )
		{
			LDAEvaluator evaluator = new LDAEvaluator();

			List<NewsFeedMessage> trainingMessages = new ArrayList<NewsFeedMessage>();
			List<NewsFeedMessage> testingMessages = new ArrayList<NewsFeedMessage>();
			trainingMessages = ( List<NewsFeedMessage> ) newsFeedRepository.findAll();
			NewsFeedMessage message1 = trainingMessages.remove( 125 );
			System.out.println( "message1 : " + message1.getTitle() );
			testingMessages.add( message1 );
			NewsFeedMessage message2 = trainingMessages.remove( 86 );
			System.out.println( "message2 : " + message2.getTitle() );
			testingMessages.add( message2 );
			NewsFeedMessage message3 = trainingMessages.remove( 76 );
			System.out.println( "message3 : " + message3.getTitle() );
			testingMessages.add( message3 );
			NewsFeedMessage message4 = trainingMessages.remove( 43 );
			System.out.println( "message4 : " + message4.getTitle() );
			testingMessages.add( message4 );
			NewsFeedMessage message5 = trainingMessages.remove( 28 );
			System.out.println( "message5 : " + message5.getTitle() );
			testingMessages.add( message5 );
			NewsFeedMessage message6 = trainingMessages.remove( 28 );
			System.out.println( "message6 : " + message6.getTitle() );
			testingMessages.add( message6 );
			evaluator.evaluateTopicModel( trainingMessages, testingMessages );
		}
	}

}