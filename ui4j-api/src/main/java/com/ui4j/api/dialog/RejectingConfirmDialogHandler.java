package com.ui4j.api.dialog;

import com.ui4j.api.util.Logger;
import com.ui4j.api.util.LoggerFactory;

class RejectingConfirmDialogHandler implements ConfirmHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RejectingConfirmDialogHandler.class);

    @Override
    public boolean handle(DialogEvent event) {
        LOG.info("Replying [false] to message: " + event.getMessage());
        return false;
    }
}
