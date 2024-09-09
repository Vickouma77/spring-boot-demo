package com.secure_auth.pact_cdc_demo

import org.springframework.stereotype.Service

@Service
class EmailVerificationService {

    fun sendVerificationCode(token: String): Boolean {

        return token == "b87f0324-50a6-4c61-94cc-b34593a1fd95"
    }
}