package com.github.zlbovolini.mercadolivre.findproductdetails;

import com.github.zlbovolini.mercadolivre.user.User;

class UserDetailsResponse {

    private final String email;

    UserDetailsResponse(User user) {
        this.email = user.getEmail();
    }

    public String getEmail() {
        return email;
    }
}
