package com.KyMann.EmperorTrump.Models.data;

import com.KyMann.EmperorTrump.Models.EmperorTweet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Kyle on 6/20/2017.
 */

@Repository
@Transactional
public interface EmperorTweetsDao extends CrudRepository<EmperorTweet, Integer> {
}
