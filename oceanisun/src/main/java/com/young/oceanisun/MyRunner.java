package com.young.oceanisun;

import com.young.oceanisun.netty.WebsocketChatServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        new WebsocketChatServer(9041).run();
    }
}
