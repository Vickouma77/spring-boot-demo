package com.secure_auth.security_auth.entities

import jakarta.persistence.*
import java.util. *

@Entity
@Table(name = "users")
class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: String = ""
    val name: String = ""

    @Column(nullable = false, unique = true)
    var username: String = ""

    @Column(nullable = false, unique = true)
    var email: String = ""

    @Column(nullable = false)
    var password: String = ""

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "roles",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    var roles: Set<Role> = HashSet()
}