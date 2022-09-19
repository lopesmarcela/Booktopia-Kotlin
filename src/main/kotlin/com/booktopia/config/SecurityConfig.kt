package com.booktopia.config

import com.booktopia.enums.Role
import com.booktopia.config.security.UserDetailsAdminService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userDetails: UserDetailsAdminService,
): WebSecurityConfigurerAdapter() {

    private val publicGetMatchers = arrayOf(
        "/books"
    )

    private val publicPostMatchers = arrayOf(
        "/admin"
    )

    private val adminMatchers = arrayOf(
        "/admin/**"
    )

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetails).passwordEncoder(bCryptPasswordEncoder())
    }

    override fun configure(http: HttpSecurity) {
        http.httpBasic()
        http.cors().and().csrf().disable()
        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, *publicPostMatchers).permitAll()
            .antMatchers(HttpMethod.GET, *publicGetMatchers).permitAll()
            .antMatchers(*adminMatchers).hasAuthority(Role.ADMIN.description)
            .anyRequest().authenticated()
    }

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(
            "/v2/api-docs",
            "/configurations/ui",
            "/swagger-resources/**",
            "/configuration/**",
            "/swagger-ui.html",
            "/webjars/**"
        )
    }

    @Bean
    fun corsCofig(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOriginPattern("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder{
        return BCryptPasswordEncoder()
    }
}