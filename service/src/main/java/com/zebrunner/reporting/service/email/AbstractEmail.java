package com.zebrunner.reporting.service.email;

import com.zebrunner.reporting.domain.db.Attachment;

import java.util.List;

public abstract class AbstractEmail implements IEmailMessage {

    private final String subject;
    private final String zafiraLogoURL;
    private final String workspaceURL;
    private final EmailType type;

    protected AbstractEmail(String subject, EmailType type, String zafiraLogoURL, String workspaceURL) {
        this.subject = subject;
        this.type = type;
        this.zafiraLogoURL = zafiraLogoURL;
        this.workspaceURL = workspaceURL;
    }

    public String getZafiraLogoURL() {
        return zafiraLogoURL;
    }

    public String getWorkspaceURL() {
        return workspaceURL;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public EmailType getType() {
        return type;
    }

    @Override
    public List<Attachment> getAttachments() {
        return null;
    }
}
