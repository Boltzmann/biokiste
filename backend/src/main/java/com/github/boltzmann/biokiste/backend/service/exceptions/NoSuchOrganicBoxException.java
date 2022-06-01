package com.github.boltzmann.biokiste.backend.service.exceptions;

import java.util.NoSuchElementException;

public class NoSuchOrganicBoxException extends NoSuchElementException {
    public NoSuchOrganicBoxException(String s) {
        super(s);
    }
}
