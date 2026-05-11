package com.budgetapp.service;

import com.budgetapp.dao.GoalDAO;
import com.budgetapp.model.Goal;
import com.budgetapp.model.GoalStatus;
import java.time.LocalDate;
import java.util.List;

public class GoalService {
    private static GoalService instance;
    private final GoalDAO goalDAO;

    private GoalService() { goalDAO = new GoalDAO(); }
    public static synchronized GoalService getInstance() {
        if (instance == null) instance = new GoalService();
        return instance;
    }

    public boolean createGoal(int userId, String name, double targetAmount, double initialAmount, LocalDate deadline) {
        if (targetAmount <= 0 || deadline.isBefore(LocalDate.now())) return false;
        Goal g = new Goal();
        g.setUserId(userId);
        g.setName(name);
        g.setTargetAmount(targetAmount);
        g.setCurrentAmount(initialAmount);
        g.setDeadline(deadline);
        g.setStatus(GoalStatus.IN_PROGRESS);
        return goalDAO.create(g) > 0;
    }

    public List<Goal> getGoalsForUser(int userId) {
        return goalDAO.findByUserId(userId);
    }
}