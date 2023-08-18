package br.com.tjro.supribackend.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageSource messageSource;

    public String message(String code) {
        return messageSource.getMessage(code, null, new Locale("pt", "BR"));
    }

    public String messageWithParams(String code, String... params) {
        return messageSource.getMessage(code, params, new Locale("pt", "BR"));
    }
}

