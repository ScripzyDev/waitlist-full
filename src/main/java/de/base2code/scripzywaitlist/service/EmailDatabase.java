package de.base2code.scripzywaitlist.service;

public class EmailDatabase {

    public boolean isEmailInDatabase(String email) {
        System.out.println("Checked if " + email + " is in database");
        return false;
    }

    public boolean addEmailToDatabase(String email, String referral) {
        System.out.println("Added " + email + " to database with referral " + referral);
        return true;
    }

}
