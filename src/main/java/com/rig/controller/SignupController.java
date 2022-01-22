package com.rig.controller;

import com.rig.api.SignupApi;
import com.rig.api.StatisticsApi;
import com.rig.model.RIGSignupRequest;
import com.rig.model.RIGSignupResponse;
import com.rig.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignupController extends AbstractBaseController implements SignupApi {

    private final SignupService signupService;

    public SignupController(final ModelMapper modelMapper, final SignupService signupService) {
        super(modelMapper);
        this.signupService = signupService;
    }

    public ResponseEntity<RIGSignupResponse> signup(final RIGSignupRequest request) {
        final RIGSignupResponse response = new RIGSignupResponse();

        signupService.signup(request);

        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }
}
