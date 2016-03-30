package com.tlongdev.spicio.network.converter;

import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.domain.model.UserFull;
import com.tlongdev.spicio.network.model.spicio.request.SpicioUserBody;
import com.tlongdev.spicio.network.model.spicio.response.SpicioUserFullResponse;
import com.tlongdev.spicio.network.model.spicio.response.SpicioUserResponse;

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

    public static UserFull convertToUserFull(SpicioUserFullResponse body) {
        UserFull userFull = new UserFull();
        User user = new User();
        user.setName(body.getName());
        user.setId(body.getId());
        user.setAvatarUrl(null); // TODO: 2016. 03. 30.
        user.setEmailAddress(body.getEmail());
        user.setFacebookId(body.getFacebookId());
        user.setGooglePlusId(body.getGoogleId());
        userFull.setSeries(null); // TODO: 2016. 03. 30.

        userFull.setUser(user);
        return userFull;
    }

    public static User convertToUser(SpicioUserResponse userBody) {
        User user = new User();
        user.setId(userBody.getId());
        user.setName(userBody.getName());
        user.setEmailAddress(userBody.getEmail());
        user.setAvatarUrl(null);// TODO: 2016. 03. 30.
        return user;
    }
}
