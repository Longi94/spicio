package com.tlongdev.spicio.network.converter;

import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.network.model.spicio.request.SpicioUserBody;

/**
 * @author Long
 * @since 2016. 03. 28.
 */
public class SpicioModelConverter {

    public static SpicioUserBody convertToUserBody(User user) {
        SpicioUserBody body = new SpicioUserBody();
        body.setName(user.getName());
        body.setEmail(user.getEmailAddress());
        body.setFacebookId(user.getFacebookId());
        body.setGoogleId(user.getGooglePlusId());
        return body;
    }
}
