package com.prestone.service

import com.prestone.model.AppUser
import com.prestone.repository.AppUserRepository
import jakarta.inject.Singleton


@Singleton
class AppUserService(
    private val appUserRepository: AppUserRepository
) {
    fun save(appUser: AppUser): AppUser {
        return appUserRepository.save(appUser)
    }

    fun findAll(): List<AppUser> {
        return appUserRepository.findAll().toList()
    }

    fun findById(id: Long): AppUser {
        return appUserRepository.findById(id).orElse(null)
    }

    fun deleteById(id: Long) {
        appUserRepository.deleteById(id)
    }
}