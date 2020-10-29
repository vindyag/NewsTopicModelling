package ucsc.mcs.topic_modelling.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name = "NewsTopic")
public class NewsTopic implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String topic;
	private String topiclabel;

	public NewsTopic( long id, String topic )
	{
		super();
		this.id = id;
		this.topic = topic;
	}

	public long getId()
	{
		return id;
	}

	public void setId( long id )
	{
		this.id = id;
	}

	public String getTopic()
	{
		return topic;
	}

	public void setTopic( String topic )
	{
		this.topic = topic;
	}

	public String getTopiclabel()
	{
		return topiclabel;
	}

	public void setTopiclabel( String topiclabel )
	{
		this.topiclabel = topiclabel;
	}

}
