package com.ironhack.MidtermProject.model.classes;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

@Embeddable
public class Money implements Transactional{
    private static final Currency USD = Currency.getInstance("USD");
    private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;
    private final Currency currency;
    private BigDecimal balance;
    /**
     * Class constructor specifying amount, currency, and rounding
     **/
    public Money(BigDecimal balance, Currency currency, RoundingMode rounding) {
        this.currency = currency;
        setAmount(balance.setScale(currency.getDefaultFractionDigits(), rounding));
    }
    /**
     * Class constructor specifying amount, and currency. Uses default RoundingMode HALF_EVEN.
     **/
    public Money(BigDecimal balance, Currency currency) {
        this(balance, currency, DEFAULT_ROUNDING);
    }
    /**
     * Class constructor specifying amount. Uses default RoundingMode HALF_EVEN and default currency USD.
     **/
    public Money(BigDecimal balance) {
        this(balance, USD, DEFAULT_ROUNDING);
    }

    public Money() {
        this.currency = USD;
    }

    public BigDecimal increaseAmount(Money money) {
        setAmount(this.balance.add(money.balance));
        return this.balance;
    }
    public BigDecimal increaseAmount(BigDecimal addAmount) {
        setAmount(this.balance.add(addAmount));
        return this.balance;
    }
    public BigDecimal decreaseAmount(Money money) {
        setAmount(this.balance.subtract(money.getAmount()));
        return this.balance;
    }
    public BigDecimal decreaseAmount(BigDecimal addAmount) {
        setAmount(this.balance.subtract(addAmount));
        return this.balance;
    }
    public Currency getCurrency() {
        return this.currency;
    }
    public BigDecimal getAmount() {
        return this.balance;
    }
    private void setAmount(BigDecimal amount) {
        this.balance = amount;
    }
    public String toString() {
        return getCurrency().getSymbol() + " " + getAmount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(currency, money.currency) &&
                Objects.equals(balance, money.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, balance);
    }
}
