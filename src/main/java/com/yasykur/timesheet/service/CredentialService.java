package com.yasykur.timesheet.service;

import com.yasykur.timesheet.model.Credential;

public interface CredentialService {
    public Credential createCredential(Integer id, String password);
}
