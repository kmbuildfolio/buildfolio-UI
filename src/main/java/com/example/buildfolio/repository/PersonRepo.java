package com.example.buildfolio.repository;

import com.example.buildfolio.model.AuthCredential;
import com.example.buildfolio.model.Person;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class PersonRepo {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public PersonRepo(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    public Person savePerson(Person person){
        return mongoTemplate.save(person);
    }

    public AuthCredential getCredentialByUserName(String username){
        Query query = new Query();
        query.addCriteria(Criteria.where("userName").is(username));

        Person person = mongoTemplate.findOne(query, Person.class,"person");
        return person != null ? new AuthCredential(person.getEmail(),person.getUserName(),person.getPassword(),"ROLE_NORMAL") : null;
    }

    public AuthCredential getCredentialByEmail(String email){
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));

        Person person = mongoTemplate.findOne(query, Person.class,"person");
        return person != null ? new AuthCredential(person.getEmail(),person.getUserName(),person.getPassword(),"ROLE_NORMAL") : null;
    }

    public boolean doesUserExistByEmailOrUsername(String email, String username) {
        Criteria criteria = new Criteria().orOperator(
                Criteria.where("email").is(email),
                Criteria.where("userName").is(username)
        );
        return doesEmailOrUserNameExist(criteria);
    }

    public boolean doesEmailExist(String email){
        Criteria criteria = new Criteria().orOperator(
                Criteria.where("email").is(email)
        );
        return doesEmailOrUserNameExist(criteria);
    }

    public boolean doesUserNameExist(String userName){
        Criteria criteria = new Criteria().orOperator(
                Criteria.where("userName").is(userName)
        );
        return doesEmailOrUserNameExist(criteria);
    }

    public boolean doesEmailOrUserNameExist(Criteria criteria){
        Query query = new Query(criteria);
        return mongoTemplate.exists(query,Person.class,"person");
    }

    public boolean updatePassByEmail(String email, String newPassword){
        Query query = new Query(Criteria.where("email").is(email));

        Update update = new Update().set("password",newPassword);

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Person.class,"person");
        return updateResult.getMatchedCount() >= 1 ? true : false;
    }
}
