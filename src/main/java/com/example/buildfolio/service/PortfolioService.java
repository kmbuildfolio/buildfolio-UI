package com.example.buildfolio.service;

import com.example.buildfolio.model.Portfolio;
import com.example.buildfolio.repository.PortfolioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortfolioService {
    @Autowired
    private PortfolioRepo portfolioRepo;

    public Portfolio savePortfolio(Portfolio portfolio, String username){
        portfolio.setUsername(username);
        return portfolioRepo.savePortfolio(portfolio);
    }

    public Portfolio getPortfolio(String username){
        return portfolioRepo.getPortfolio(username);
    }

    public boolean updatePortfolio(String username, Portfolio portfolio){
        return portfolioRepo.updatePortfolio(portfolio,username);
    }
}
