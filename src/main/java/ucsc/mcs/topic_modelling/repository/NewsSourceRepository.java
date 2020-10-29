package ucsc.mcs.topic_modelling.repository;

import org.springframework.data.repository.CrudRepository;

import ucsc.mcs.topic_modelling.entity.NewsSource;

public interface NewsSourceRepository extends CrudRepository<NewsSource, Long> 
{

}
