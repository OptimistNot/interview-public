package com.devexperts.model;

import java.util.Objects;

public class Transaction {
    private long sourceId;
    private long targetId;
    private double amount;

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return sourceId == that.sourceId &&
                targetId == that.targetId &&
                Double.compare(that.amount, amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceId, targetId, amount);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "sourceId=" + sourceId +
                ", targetId=" + targetId +
                ", amount=" + amount +
                '}';
    }


    public static final class Builder {
        long sourceId;
        long targetId;
        double amount;

        public Builder() {
        }

        public static Builder aTransaction() {
            return new Builder();
        }

        public Builder withSourceId(long sourceId) {
            this.sourceId = sourceId;
            return this;
        }

        public Builder withTargetId(long targetId) {
            this.targetId = targetId;
            return this;
        }

        public Builder withAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public Transaction build() {
            Transaction transaction = new Transaction();
            transaction.setSourceId(sourceId);
            transaction.setTargetId(targetId);
            transaction.setAmount(amount);
            return transaction;
        }
    }
}