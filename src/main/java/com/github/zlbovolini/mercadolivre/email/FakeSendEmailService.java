package com.github.zlbovolini.mercadolivre.email;

import org.springframework.stereotype.Service;

@Service
public class FakeSendEmailService implements SendEmailService {

    @Override
    public void send(String email, String message) {
        System.out.println(String.format("Enviando email para %s com a mensagem: %s",
                email, message));
    }
}
