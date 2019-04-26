package com.code.codepractice;

/**
 * @Author: dongxin
 * @Date: 2019/4/17 15:01
 **/
public class Top {
    private Double amount;
    private String orderNo;

    private Top(Builder builder) {
        this.amount = builder.amount;
        this.orderNo = builder.orderNo;
    }

    public static Builder newTop() {
        return new Builder();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public static final class Builder {
        private Double amount;
        private String orderNo;

        private Builder() {
        }

        public Top build() {
            return new Top(this);
        }

        public Builder amount(Double amount) {
            this.amount = amount;
            return this;
        }

        public Builder orderNo(String orderNo) {
            this.orderNo = orderNo;
            return this;
        }
    }
}
