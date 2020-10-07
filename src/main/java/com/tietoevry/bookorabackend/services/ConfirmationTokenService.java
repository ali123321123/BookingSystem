package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.EmployeeDTO;

public interface ConfirmationTokenService {
    EmployeeDTO checkToken(String confirmationToken);
}
