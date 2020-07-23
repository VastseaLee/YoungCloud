package com.young.authserver.service;

import com.young.common.bean.YoungUser;

public interface AuthService {

    String login(YoungUser user);

    YoungUser selectUser(String token);
}
