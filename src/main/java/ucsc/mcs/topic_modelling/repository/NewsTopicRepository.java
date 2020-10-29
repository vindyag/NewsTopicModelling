package ucsc.mcs.topic_modelling.repository;

import org.springframework.data.repository.CrudRepository;

import ucsc.mcs.topic_modelling.entity.NewsTopic;

public interface NewsTopicRepository extends CrudRepository<NewsTopic, Long>
{

}
