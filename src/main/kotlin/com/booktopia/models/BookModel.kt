package com.booktopia.models

import com.booktopia.enums.CategoryEnum
import com.booktopia.enums.StatusEnum
import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "books")
data class BookModel (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    @Column
    var title: String,
    @Column
    var description: String,
    @Column
    @JsonAlias("release_date")
    var releaseDate: String,
    @Column
    @Enumerated(EnumType.STRING)
    var category: CategoryEnum? = null,
    @Column
    var author: String,
    @Column
    var price : BigDecimal,
    @Column
    var inventory: Int,
    @Column
    @Enumerated(EnumType.STRING)
    var status: StatusEnum? = null

){
}