package com.rig.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

@Getter
@RequiredArgsConstructor
public abstract class AbstractBaseController {
    private final ModelMapper modelMapper;
}
