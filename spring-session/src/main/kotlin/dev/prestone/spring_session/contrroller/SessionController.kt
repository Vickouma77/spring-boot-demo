package dev.prestone.spring_session.contrroller

import jakarta.servlet.http.HttpSession
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal


@RestController
class SessionController {

    private val VIEW_COUNT = "VIEW_COUNT"

    @GetMapping("/")
    fun getSession(principal: Principal, session: HttpSession): String {

        incrementCount(session, VIEW_COUNT)

        return "Session Controller " + principal.name
    }

    @GetMapping("/count")
    fun getCount(session: HttpSession): String {
        return "View count: " + session.getAttribute(VIEW_COUNT)
    }

    private fun incrementCount(session: HttpSession, key: String) {
        var count = session.getAttribute(key) as Int?
        if (count == null) {
            count = 0
        }
        session.setAttribute(key, count + 1)
    }
}