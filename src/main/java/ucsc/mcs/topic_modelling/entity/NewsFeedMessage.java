package ucsc.mcs.topic_modelling.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class NewsFeedMessage implements Serializable
{
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "NewsSource_id")
	private NewsSource newsSource;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String url;
	private String title;
	@Temporal(TemporalType.DATE)
	private Date date;

	@Lob
	private String newsText;

	public NewsFeedMessage()
	{
	}

	public NewsFeedMessage( String url, String title, Date date )
	{
		super();
		this.url = url;
		this.title = title;
		this.date = date;
	}

	public long getId()
	{
		return id;
	}

	public void setId( long id )
	{
		this.id = id;
	}

	public NewsSource getNewsSource()
	{
		return newsSource;
	}

	public void setNewsSource( NewsSource newsSource )
	{
		this.newsSource = newsSource;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl( String url )
	{
		this.url = url;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle( String title )
	{
		this.title = title;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate( Date date )
	{
		this.date = date;
	}

	public String getNewsText()
	{
		return newsText;
	}

	public void setNewsText( String newsText )
	{
		this.newsText = newsText;
	}

}
