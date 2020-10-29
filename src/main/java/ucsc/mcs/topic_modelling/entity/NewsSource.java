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
@XmlRootElement(name = "NewsSource")
public class NewsSource implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String newsSource;
	private String newsUrl;
	@Temporal(TemporalType.TIMESTAMP)
	private Date pubDate;

	public NewsSource()
	{
	}

	public NewsSource( String newsSource, String newsUrl, Date date )
	{
		super();
		this.newsSource = newsSource;
		this.newsUrl = newsUrl;
		this.pubDate = date;
	}

	public long getId()
	{
		return id;
	}

	public void setId( long id )
	{
		this.id = id;
	}

	public String getNewsUrl()
	{
		return newsUrl;
	}

	public void setNewsUrl( String newsUrl )
	{
		this.newsUrl = newsUrl;
	}

	public String getNewsSource()
	{
		return newsSource;
	}

	public void setNewsSource( String newsSource )
	{
		this.newsSource = newsSource;
	}

	public Date getDate()
	{
		return pubDate;
	}

	public void setDate( Date date )
	{
		this.pubDate = date;
	}

}
