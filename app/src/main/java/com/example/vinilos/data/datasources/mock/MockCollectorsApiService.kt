package com.example.vinilos.data.datasources.mock

import com.example.vinilos.data.datasources.CollectorApiService
import com.example.vinilos.data.entities.Album
import com.example.vinilos.data.entities.AlbumStatus
import com.example.vinilos.data.entities.Collector
import com.example.vinilos.data.entities.CollectorAlbum
import com.example.vinilos.data.entities.Comment
import com.example.vinilos.data.entities.Genre
import com.example.vinilos.data.entities.Performer
import com.example.vinilos.data.entities.PerformerPrize
import com.example.vinilos.data.entities.Prize
import com.example.vinilos.data.entities.RecordLabel
import com.example.vinilos.data.entities.Track
import kotlinx.coroutines.delay

val mockCollectors = listOf(
    Collector(
        id = 100,
        name = "Usuario 1",
        telephone = "3002182532",
        email = "usuario1@example.com",
        comments = listOf(
            Comment(
                id = 100,
                description = "Excelente",
                rating = 5,
            ),
            Comment(
                id = 101,
                description = "Bueno",
                rating = 4,
            ),
        ),
        favoritePerformers = listOf(
            Performer(
                id = 100,
                name = "Ruben Blades",
                image = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bb/Ruben_Blades_by_Gage_Skidmore.jpg/800px-Ruben_Blades_by_Gage_Skidmore.jpg",
                description = "Es un cantante, compositor, músico, actor, abogado, político y activista panameño. Ha desarrollado gran parte de su carrera artística en la ciudad de Nueva York.",
                birthDate = "1948-07-16T00:00:00.000Z",
                albums = listOf(), // Simplified for collector mock
                performerPrizes = listOf(), // Simplified for collector mock
            ),
            Performer(
                id = 101,
                name = "Carlos Vives",
                image = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d3/Carlos_Vives_%28cropped%29.jpg/800px-Carlos_Vives_%28cropped%29.jpg",
                description = "Carlos Alberto Vives Restrepo (Santa Marta, 7 de agosto de 1961), conocido artísticamente como Carlos Vives, es un cantante, actor y compositor colombiano.",
                birthDate = "1961-08-07T00:00:00.000Z",
                albums = listOf(), // Simplified for collector mock
                performerPrizes = listOf(), // Simplified for collector mock
            ),
        ),
        collectorAlbums = listOf(
            CollectorAlbum(
                id = 100,
                price = 10,
                status = AlbumStatus.ACTIVE,
                album = Album(
                    id = 100,
                    name = "Buscando América",
                    cover = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
                    releaseDate = "1984-08-01T00:00:00.000Z",
                    description = "Buscando América es el primer álbum de la banda de Rubén Blades y Seis del Solar lanzado en 1984. La producción, bajo el sello Elektra, fusiona diferentes ritmos musicales tales como la salsa, reggae, rock, y el jazz latino. El disco fue grabado en Eurosound Studios en Nueva York entre mayo y agosto de 1983.",
                    genre = "Salsa",
                    recordLabel = "Elektra",
                    tracks = listOf(), // Simplified for collector mock
                    performers = listOf(), // Simplified for collector mock
                    comments = listOf() // Simplified for collector mock
                )
            ),
            CollectorAlbum(
                id = 101,
                price = 15,
                status = AlbumStatus.INACTIVE,
                album = Album(
                    id = 102,
                    name = "Clásicos de la Provincia",
                    cover = "https://upload.wikimedia.org/wikipedia/en/thumb/e/e2/Clasicos_de_la_Provincia.jpg/220px-Clasicos_de_la_Provincia.jpg",
                    releaseDate = "1993-08-01T00:00:00.000Z",
                    description = "Clásicos de la Provincia is a vallenato album by Colombian singer/composer Carlos Vives. It was released as a double disc album on August 24, 1993.",
                    genre = "Folk",
                    recordLabel = "Sony",
                    tracks = listOf(), // Simplified for collector mock
                    performers = listOf(), // Simplified for collector mock
                    comments = listOf() // Simplified for collector mock
                )
            ),
        )
    ),
    Collector(
        id = 101,
        name = "Usuario 2",
        telephone = "3001234567",
        email = "usuario2@example.com",
        comments = listOf(
            Comment(
                id = 102,
                description = "Regular",
                rating = 3,
            ),
        ),
        favoritePerformers = listOf(
            Performer(
                id = 102,
                name = "Juanes",
                image = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Juanes_%28crop_2%29.jpg/800px-Juanes_%28crop_2%29.jpg",
                description = "Juan Esteban Aristizábal Vásquez (Carolina del Príncipe, Antioquia; 9 de agosto de 1972), más conocido como Juanes, es un cantautor, músico y filántropo colombiano, que fusiona diversos géneros musicales como el rock, el pop y la cumbia.",
                birthDate = "1972-08-09T00:00:00.000Z",
                albums = listOf(), // Simplified for collector mock
                performerPrizes = listOf(), // Simplified for collector mock
            ),
        ),
        collectorAlbums = listOf(
            CollectorAlbum(
                id = 103,
                price = 12,
                status = AlbumStatus.ACTIVE,
                album = Album(
                    id = 103,
                    name = "Fijate Bien",
                    cover = "https://upload.wikimedia.org/wikipedia/en/thumb/d/d8/F%C3%ADjate_Bien.jpg/220px-F%C3%ADjate_Bien.jpg",
                    releaseDate = "2000-10-17T00:00:00.000Z",
                    description = "Fíjate Bien is the debut studio album by Colombian singer-songwriter Juanes, released on October 17, 2000, by Surco Records. The album earned him three Latin Grammy Awards.",
                    genre = "Salsa",
                    recordLabel = "Sony",
                    tracks = listOf(), // Simplified for collector mock
                    performers = listOf(), // Simplified for collector mock
                    comments = listOf() // Simplified for collector mock
                )
            ),
        )
    )
)

object MockCollectorsApiService : CollectorApiService {
    override suspend fun getCollectors(): List<Collector> {
        delay(2000) // Simulate network delay
        return mockCollectors
    }

    override suspend fun getCollectorsDetail(id: Int): Collector {
        delay(2000)
        return mockCollectors[0]
    }
}