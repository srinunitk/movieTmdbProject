package com.example.movie.common.data

interface ApiMapper<Domain,Entity> {
    fun mapToDomain(apiDto:Entity):Domain
}