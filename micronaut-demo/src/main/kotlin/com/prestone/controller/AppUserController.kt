package com.prestone.controller

import com.prestone.model.Address
import com.prestone.model.AppUser
import com.prestone.model.request.AppUserRequest
import com.prestone.service.AppUserService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post

@Controller("/app-user")
class AppUserController(private val appUserService: AppUserService) {

    @Post
    fun create(appUserRequest: AppUserRequest) =
        appUserService.save(
            appUser = appUserRequest.toModel()
        )

    private fun AppUserRequest.toModel(): AppUser =
        AppUser(
            username = username,
            password = password,
            address = Address(
                street = address.street,
                city = address.city,
                state = address.state,
                zip = address.zip,
            )
        )
}