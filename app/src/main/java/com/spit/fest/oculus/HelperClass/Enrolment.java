package com.spit.fest.oculus.HelperClass;

public class Enrolment
{
    private boolean hasPaid;
    private String ePass;
    private int registrationNo;
    private String transactionId;

    public Enrolment() {
    }

    public Enrolment(boolean hasPaid, String ePass, int registrationNo, String transactionId) {

        this.hasPaid = hasPaid;
        this.ePass = ePass;
        this.registrationNo = registrationNo;
        this.transactionId = transactionId;
    }

    public boolean isHasPaid() {
        return hasPaid;
    }

    public void setHasPaid(boolean hasPaid) {
        this.hasPaid = hasPaid;
    }

    public String getePass() {
        return ePass;
    }

    public void setePass(String ePass) {
        this.ePass = ePass;
    }

    public int getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(int registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
