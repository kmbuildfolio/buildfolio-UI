package com.example.buildfolio.repository;

import com.example.buildfolio.model.Portfolio;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class PortfolioRepo {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public PortfolioRepo(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Portfolio savePortfolio(Portfolio portfolio){
       return mongoTemplate.save(portfolio);
    }

    public Portfolio getPortfolio(String username){
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));

        return mongoTemplate.findOne(query, Portfolio.class,"portfolio");
    }

    public boolean updatePortfolio(Portfolio portfolio, String username){
        Query query = new Query(Criteria.where("username").is(username));

        Update update = new Update().set("biodata",portfolio.getBiodata())
                .set("experience",portfolio.getExperience())
                        .set("projects",portfolio.getProjects())
                                .set("skills",portfolio.getSkills())
                                        .set("education",portfolio.getEducation())
                                                .set("achievements",portfolio.getAchievements());

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Portfolio.class,"portfolio");
        return updateResult.getMatchedCount() >= 1 ? true : false;
    }
}
