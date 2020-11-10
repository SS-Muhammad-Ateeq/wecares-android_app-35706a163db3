package com.wecare.android.utils.events;

public class TermsAcceptClickedEvent {

    boolean termsAccepted;

    public TermsAcceptClickedEvent(boolean termsAccepted) {
        this.termsAccepted = termsAccepted;
    }

    public boolean isTermsAccepted() {
        return termsAccepted;
    }
}
