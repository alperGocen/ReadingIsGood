package com.rig.service.impl;

import com.rig.config.exception.BackendException;
import com.rig.config.exception.ErrorType;
import com.rig.model.RIGSignupRequest;
import com.rig.model.entity.Customer;
import com.rig.model.entity.User;
import com.rig.model.entity.UserRole;
import com.rig.repository.CustomerRepository;
import com.rig.repository.UserRepository;
import com.rig.security.util.PasswordUtil;
import com.rig.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SignupServiceImpl implements SignupService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordUtil passwordUtil;

    @Override
    public void signup(final RIGSignupRequest request) {
        try {
            final User existingUser = userRepository.findByEmail(request.getEmail());

            if (Objects.nonNull(existingUser)) {
                throw new BackendException(ErrorType.BAD_CREDENTIALS, "User already exists.");
            }

            final User user = new User();

            user.setEmail(request.getEmail());
            user.setPassword(passwordUtil.encryptPassword(request.getPassword()));

            final List<UserRole> userRoleList = new ArrayList<>();

            userRepository.save(user);

            final Customer customer = new Customer();
            customer.setName(request.getName());
            customer.setSurname(request.getSurname());
            customer.setUser(user);

            customerRepository.save(customer);
        } catch (final BackendException exception) {
            throw new BackendException(ErrorType.REGISTER_FAIL, exception.getErrorMessage());
        }
    }
}
