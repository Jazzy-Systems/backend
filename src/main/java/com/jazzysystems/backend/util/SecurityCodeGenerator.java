package com.jazzysystems.backend.util;

import java.util.Random;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SecurityCodeGenerator {

    public String generateCode(String Posfix) {
        Random random = new Random();

        String code = random.ints(48, 123)

                .filter(num -> (num < 91 || num > 96))

                .limit(8)

                .mapToObj(c -> (char) c).collect(StringBuffer::new, StringBuffer::append, StringBuffer::append)

                .toString();

        return code + Posfix;

    }

}
