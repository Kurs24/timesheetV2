package com.yasykur.timesheet.service;

import com.yasykur.timesheet.model.Credential;
import com.yasykur.timesheet.repository.CredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CredentialServiceImpl implements CredentialService{

    private final CredentialRepository credentialRepository;

    @Override
    public Credential createCredential(Integer id, String password) {
        Credential newCredential = new Credential();
        newCredential.setId(id);
        newCredential.setPassword(password);
        return credentialRepository.save(newCredential);
    }
}
