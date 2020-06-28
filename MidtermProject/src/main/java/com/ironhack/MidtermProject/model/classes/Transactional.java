package com.ironhack.MidtermProject.model.classes;

import java.math.BigDecimal;

public interface Transactional {
    /**
     * This method increases the balance from an account
     * @param addAmount a BigDecimal value
     * @return a BigDecimal
     */
    BigDecimal increaseAmount(BigDecimal addAmount);

    /**
     * This method decreases the balance from an account
     * @param addAmount a BigDecimal value
     * @return a BigDecimal
     */
    BigDecimal decreaseAmount(BigDecimal addAmount);

    /**
     * This method transforms to String
     * @return a String value
     */
    String toString();
}