package com.github.boltzmann.biokiste.backend.service.exceptions;

import java.util.NoSuchElementException;

public class NoSuchOrganicBoxItemException extends NoSuchElementException {

    public NoSuchOrganicBoxItemException(String s) {
        super(s);
    }
}
