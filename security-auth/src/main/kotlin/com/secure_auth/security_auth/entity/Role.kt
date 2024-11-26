package com.secure_auth.security_auth.entity

import jakarta.persistence.*

@Entity
class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: String = ""
    var name: String = ""
}