package com.example.buildfolio.service;

import com.example.buildfolio.model.Person;
import com.example.buildfolio.repository.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepo personRepo;

    public boolean savePerson(Person person){
        personRepo.savePerson(person);
        return true;
    }

    public boolean changePassByEmail(String email, String confirm_pass){
        return personRepo.updatePassByEmail(email,confirm_pass);
    }

    public boolean checkUserExistOrNot(String email, String userName){
        return personRepo.doesUserExistByEmailOrUsername(email,userName);
    }

    public String getEmailByUsername(String username){
        return personRepo.getEmailByUsername(username);
    }
}
