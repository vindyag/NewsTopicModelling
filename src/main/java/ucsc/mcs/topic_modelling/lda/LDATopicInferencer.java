package ucsc.mcs.topic_modelling.lda;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import ucsc.mcs.topic_modelling.entity.NewsFeedMessage;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.topics.TopicInferencer;
import cc.mallet.types.Alphabet;
import cc.mallet.types.IDSorter;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;

public class LDATopicInferencer
{

	/**
	 * Infer the specified number of topics for the specified NewsFeedMessages
	 * 
	 * @param noOfTopics
	 *            no of topics
	 * @param messageIterator
	 *            NewsFeedMessage Iterator
	 * @throws Exception
	 */
	public void inferByModel( int noOfTopics, Iterable<NewsFeedMessage> messageIterator, boolean updateModel ) throws Exception
	{
		// load the model
		File modelFile = new File( "model", "mallet-lda-model" + noOfTopics + ".ser" );
		ParallelTopicModel model = ParallelTopicModel.read( modelFile );

		// load the pipe
		File pipesFile = new File( "model", "mallet-lda-pipes" + noOfTopics + ".ser" );
		SerialPipes serialPipes = ( SerialPipes ) loadFromFile( pipesFile );

		// if model and pipe is properly loaded
		if ( model != null && serialPipes != null )
		{
			System.out.print( "Successfully read the model and pipe list\n" );
			System.out.println( "Model LogLikelihood : " + model.modelLogLikelihood() );

			Writer writer = new FileWriter( "Output_topics_on_test_instances_with_model" + noOfTopics + ".txt" );
			writer.write( "Model LogLikelihood : " + model.modelLogLikelihood() );
			InstanceList instanceList = new InstanceList( serialPipes );
			for ( NewsFeedMessage feedMessage : messageIterator )
			{
				String newsText = feedMessage.getNewsText();
				if ( newsText != null && !newsText.isEmpty() )
				{
					Instance instance = getInstance( feedMessage );
					instanceList.addThruPipe( instance );
				}
			}
			writeTopicInfo( noOfTopics, model, instanceList, writer );
			writer.close();
			if ( updateModel )
			{
				writeToFile( pipesFile, serialPipes );
				writeToFile( modelFile, model );
			}
		}
	}

	/**
	 * @param feedMessage
	 *            NewsFeedMessage
	 * @return Instance created based on NewsFeedMessage
	 */
	public Instance getInstance( NewsFeedMessage feedMessage )
	{
		return new Instance( feedMessage.getNewsText(), null, feedMessage.getTitle(), feedMessage.getUrl() );
	}

	/**
	 * de-serialize the file and create the object representation and return
	 * 
	 * @param serializedFile
	 *            File to de-serialize
	 * @return Object
	 */
	public Object loadFromFile( File serializedFile )
	{
		try
		{
			ObjectInputStream oos = new ObjectInputStream( new FileInputStream( serializedFile ) );
			Object obj = oos.readObject();
			oos.close();
			return obj;
		}
		catch ( IOException e )
		{
			System.err.println( "Problem reading file " + serializedFile + ": " + e );
		}
		catch ( ClassNotFoundException e )
		{
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Write the object to File
	 * 
	 * @param serializedFile
	 * @param obj
	 */
	public void writeToFile( File serializedFile, Object objToSerialize )
	{
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream( serializedFile ) );
			oos.writeObject( objToSerialize );
			oos.close();
		}
		catch ( IOException e )
		{
			System.err.println( "Problem serializing ParallelTopicModel to file " + serializedFile + ": " + e );
		}
	}

	/**
	 * @param noOfTopics
	 *            no Of Topics
	 * @param modelParallelTopicModel
	 * @param instances
	 *            InstanceList
	 * @param writer
	 *            Writer to File
	 * @throws Exception
	 */
	public static void writeTopicInfo( int noOfTopics, ParallelTopicModel model, InstanceList instances, Writer writer ) throws Exception
	{

		// Get an array of sorted sets of word ID/count pairs
		List<TreeSet<IDSorter>> topicSortedWords = model.getSortedWords();

		// Estimate the topic distribution of the instance, given the current Gibbs state.
		TopicInferencer inferencer = model.getInferencer();
		Formatter out = new Formatter( new StringBuilder(), Locale.US );
		for ( Instance instance : instances )
		{
			// The data alphabet maps word IDs to strings
			Alphabet dataAlphabet = model.getAlphabet();
			// double[] topicDistribution = inferencer.getSampledDistribution( instance, 1000, 1, 5 );
			double[] topicDistribution = inferencer.getSampledDistribution( instance, 0, 1, 5 );

			System.out.println( "\ntop 6 words in each topic with proportions for document: " + instance.getName() );
			writer.write( "\ntop 6 words in each topic with proportions for document: " + instance.getName() );
			for ( int topic = 0; topic < noOfTopics; topic++ )
			{
				Iterator<IDSorter> iterator = topicSortedWords.get( topic ).iterator();

				out = new Formatter( new StringBuilder(), Locale.US );
				out.format( "%d\t%.3f\t", topic, topicDistribution[topic] );
				int rank = 0;
				while ( iterator.hasNext() && rank < 6 )
				{
					IDSorter idCountPair = iterator.next();
					out.format( "%s\t", dataAlphabet.lookupObject( idCountPair.getID() ) );
					rank++;
				}
				System.out.println( out );
				writer.write( "\n" + out.toString() );
			}
		}

		File outputFile = new File( "Mallet-Output_topics_on_test_instances_with_model" + noOfTopics + ".txt" );
		inferencer.writeInferredDistributions( instances, outputFile, 0, 1, 5, 0, 9 );
	}

}
