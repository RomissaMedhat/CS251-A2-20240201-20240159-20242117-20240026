package com.budgetapp.model;

import java.time.LocalDate;

public class Goal {
    private int goalId;
    private int userId;
    private String name;
    private double targetAmount;
    private double currentAmount;
    private LocalDate deadline;
    private GoalStatus status;

    public Goal(double currentAmount, LocalDate deadline, int goalId, String name, GoalStatus status, double targetAmount, int userId) {
        this.currentAmount = currentAmount;
        this.deadline = deadline;
        this.goalId = goalId;
        this.name = name;
        this.status = status;
        this.targetAmount = targetAmount;
        this.userId = userId;
    }

    public Goal() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getGoalId() { return goalId; }
    public void setGoalId(int goalId) { this.goalId = goalId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getTargetAmount() { return targetAmount; }
    public void setTargetAmount(double targetAmount) { this.targetAmount = targetAmount; }
    public double getCurrentAmount() { return currentAmount; }
    public void setCurrentAmount(double currentAmount) { this.currentAmount = currentAmount; }
    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    public GoalStatus getStatus() { return status; }
    public void setStatus(GoalStatus status) { this.status = status; }

    public double calculateProgress() {
        if (targetAmount == 0) return 0;
        return (currentAmount / targetAmount) * 100;
    }
    public boolean addContribution(double amount) {
        if (amount <= 0) return false;
        this.currentAmount += amount;
        if (this.currentAmount >= this.targetAmount) this.status = GoalStatus.COMPLETED;
        return true;
    }
}