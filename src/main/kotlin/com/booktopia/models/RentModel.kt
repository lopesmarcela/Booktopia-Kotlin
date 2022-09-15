package com.booktopia.models

import com.booktopia.enums.StatusEnum
import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.*


@Entity(name = "rents")
data class RentModel (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    @Column
    var fine: BigDecimal? = null,
    @Column
    @JsonAlias("total_value")
    var totalValue: BigDecimal? = null,
    @Column
    @JsonAlias("rental_date")
    var rentalDate: LocalDate,
    @Column
    @JsonAlias("return_date")
    var returnDate: LocalDate? = null,
    @Column
    @Enumerated(EnumType.STRING)
    var status: StatusEnum? = null,
    @ManyToOne
    @JoinColumn(name = "client_id")
    var client: ClientModel? = null,
    @ManyToOne
    @JoinColumn(name = "book_id")
    var book: BookModel? = null
){
}