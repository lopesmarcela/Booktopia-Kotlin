package com.booktopia.enums

enum class Errors(val code: String, val message: String) {

    //Authorization
    B000("B-000","Access Denied"),
    //Validation
    B001("B-001","Invalid Request"),

    // Client errors 100-199
    B101("B-101","Client id [%s] not exists"),
    B103("B-103","Client id [%s] isn't active"),

    // Book errors 200-299
    B201("B-201","Book id [%s] not exists"),
    B203("B-203","Book id [%s] isn't active"),

    // Rent errors 300-399
    B301("B-301","Rent id [%s] not exists"),
    B302("B-302","Cannot create rent, book id [%s] is inactive"),
    B303("B-303","Cannot create rent, client id [%s] is inactive"),
    B304("B-304","Rent id [%s] has already been returned"),

    //Address errors 400-499
    B401("B-401","Address id [%s] not exists"),
    B403("B-403","Address id [%s] isn't active"),

    // Admin errors 500-599
    B501("B-501","Admin id [%s] not exists"),
    B503("B-503","Admin id [%s] isn't active"),

}