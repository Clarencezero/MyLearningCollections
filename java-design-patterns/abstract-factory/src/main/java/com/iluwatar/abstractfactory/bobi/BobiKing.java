package com.iluwatar.abstractfactory.bobi;

import com.iluwatar.abstractfactory.King;

public class BobiKing implements King {
    public static final String DESCRIPTION = "I'm the bobi king";
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
