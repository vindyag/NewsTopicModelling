package ucsc.mcs.topic_modelling.lda;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import ucsc.mcs.topic_modelling.entity.NewsFeedMessage;
import cc.mallet.pipe.CharSequence2TokenSequence;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.TokenSequenceLowercase;
import cc.mallet.pipe.TokenSequenceRemoveStopwords;
import cc.mallet.pipe.iterator.ArrayIterator;
import cc.mallet.topics.MarginalProbEstimator;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.types.InstanceList;

public class LDAEvaluator
{

	/**
	 * @param trainingMessages
	 *            NewsFeedMessages for training the topic model
	 * @param testingMessages
	 *            NewsFeedMessages for testing the topic model
	 * @throws Exception
	 */
	public void evaluateTopicModel( Iterable<NewsFeedMessage> trainingMessages, Iterable<NewsFeedMessage> testingMessages ) throws Exception
	{
		int numOfTopics = 0;
		// numOfTopics = 3;
		// numOfTopics = 4;
		// numOfTopics = 5;
		// numOfTopics = 6;
		numOfTopics = 7;
		// numOfTopics = 8;
		// numOfTopics = 9;
		// numOfTopics = 10;
		// numOfTopics = 11;
		// numOfTopics = 12;
		// numOfTopics = 13;
		// numOfTopics = 14;
		// numOfTopics = 15;
		evaluateTopicModel( numOfTopics, trainingMessages, testingMessages );

	}

	/**
	 * Evaluate topic model based on the specified no of topics
	 * 
	 * @param numOfTopics
	 *            Number of Topics
	 * @param trainingMessages
	 *            NewsFeedMessages for training
	 * @param testingMessages
	 * @throws Exception
	 */
	public void evaluateTopicModel( int numOfTopics, Iterable<NewsFeedMessage> trainingMessages, Iterable<NewsFeedMessage> testingMessages ) throws Exception
	{
		ParallelTopicModel model = new ParallelTopicModel( numOfTopics, 50, 0.01 );
		List<String> trainingTexts = new ArrayList<String>();
		List<String> testingTexts = new ArrayList<String>();

		for ( NewsFeedMessage feedMessage : trainingMessages )
		{
			String newsText = feedMessage.getNewsText();
			if ( newsText != null && !newsText.isEmpty() )
			{
				trainingTexts.add( feedMessage.getNewsText() );
			}
		}
		InstanceList trainingInstances = getInstanceList( trainingTexts );

		model.addInstances( trainingInstances );
		model.estimate();

		File modelFile = new File( "model", "mallet-lda-model" + numOfTopics + ".ser" );
		writeToFile( modelFile, model );

		SerialPipes serialPipes = getSerialPipes();
		File pipesFile = new File( "model", "mallet-lda-pipes" + numOfTopics + ".ser" );
		writeToFile( pipesFile, serialPipes );

		MarginalProbEstimator evaluator = model.getProbEstimator();

		for ( NewsFeedMessage feedMessage : trainingMessages )
		{
			String newsText = feedMessage.getNewsText();
			if ( newsText != null && !newsText.isEmpty() )
			{
				testingTexts.add( feedMessage.getNewsText() );
			}
		}

		InstanceList testingInstances = getInstanceList( testingTexts );

		double logLikelihood = evaluator.evaluateLeftToRight( testingInstances, 10, false, null );
		System.out.println( "\nNo of topics: " + numOfTopics + " logLikelihood: " + logLikelihood );
		System.out.println( "-------------------------------------------------------------------------------------\n" );
	}

	/**
	 * Create and return InstanceList based on input text list
	 * 
	 * @param texts
	 *            News text as a String list
	 * @return InstanceList
	 * @throws IOException
	 *             IOException
	 */
	InstanceList getInstanceList( List<String> texts ) throws IOException
	{
		List<Pipe> pipes = new ArrayList<Pipe>();
		pipes.add( new CharSequence2TokenSequence() );
		pipes.add( new TokenSequenceLowercase() );
		pipes.add( new TokenSequenceRemoveStopwords( new File( "AdditionalStopWords.txt" ), "UTF-8", true, false, false ) );
		pipes.add( new TokenSequence2FeatureSequence() );
		InstanceList instanceList = new InstanceList( new SerialPipes( pipes ) );
		instanceList.addThruPipe( new ArrayIterator( texts ) );
		return instanceList;
	}

	/**
	 * @param serializedFile
	 *            File object to serialize
	 * @param obj
	 *            Object to serialize to File
	 */
	public void writeToFile( File serializedFile, Object obj )
	{
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream( serializedFile ) );
			oos.writeObject( obj );
			oos.close();
		}
		catch ( IOException e )
		{
			System.err.println( "Problem serializing ParallelTopicModel to file." + serializedFile + ": " + e );
		}
	}

	/**
	 * @return SerialPipes created using the Pipe list
	 */
	private SerialPipes getSerialPipes()
	{
		List<Pipe> pipes = new ArrayList<Pipe>();
		pipes.add( new CharSequence2TokenSequence() );
		pipes.add( new TokenSequenceLowercase() );
		pipes.add( new TokenSequenceRemoveStopwords( new File( "AdditionalStopWords.txt" ), "UTF-8", true, false, false ) );
		pipes.add( new TokenSequence2FeatureSequence() );
		SerialPipes serialPipes = new SerialPipes( pipes );
		return serialPipes;
	}
}
