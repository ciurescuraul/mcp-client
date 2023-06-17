package com.example.mcpclient;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestPhoneNumber {

    @Test
    void testValidPhoneNumber() throws NumberParseException {
        // given
        long phone = 34969000001L;
        String numberToParse = "+" + phone;

        // when
        Phonenumber.PhoneNumber msisdn = PhoneNumberUtil.getInstance().parse(numberToParse.strip(), "");

        // then
        Assertions.assertEquals(34,msisdn.getCountryCode());
        Assertions.assertEquals(969000001,msisdn.getNationalNumber());
    }

}
