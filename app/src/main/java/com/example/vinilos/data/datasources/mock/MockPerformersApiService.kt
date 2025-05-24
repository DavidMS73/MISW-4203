package com.example.vinilos.data.datasources.mock

import com.example.vinilos.data.datasources.PerformerApiService
import com.example.vinilos.data.entities.Album
import com.example.vinilos.data.entities.Collector
import com.example.vinilos.data.entities.Genre
import com.example.vinilos.data.entities.Performer
import com.example.vinilos.data.entities.PerformerPrize
import com.example.vinilos.data.entities.RecordLabel
import kotlinx.coroutines.delay

val mockPerformers = listOf(
    Performer(
        id = 100,
        name = "Ruben Blades",
        image = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bb/Ruben_Blades_by_Gage_Skidmore.jpg/800px-Ruben_Blades_by_Gage_Skidmore.jpg",
        description = "Es un cantante, compositor, músico, actor, abogado, político y activista panameño. Ha desarrollado gran parte de su carrera artística en la ciudad de Nueva York.",
        birthDate = "1948-07-16T00:00:00.000Z",
        albums = listOf(
            Album(
                id = 100,
                name = "Buscando América",
                cover = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
                releaseDate = "1984-08-01T00:00:00.000Z",
                description = "Buscando América es el primer álbum de la banda de Rubén Blades y Seis del Solar lanzado en 1984. La producción, bajo el sello Elektra, fusiona diferentes ritmos musicales tales como la salsa, reggae, rock, y el jazz latino. El disco fue grabado en Eurosound Studios en Nueva York entre mayo y agosto de 1983.",
                genre = "Salsa",
                recordLabel = "Elektra",
                tracks = listOf(),
                performers = listOf(),
                comments = listOf()
            ),
            Album(
                id = 101,
                name = "Poeta del pueblo",
                cover = "https://cdn.shopify.com/s/files/1/0275/3095/products/image_4931268b-7acf-4702-9c55-b2b3a03ed999_1024x1024.jpg",
                releaseDate = "1984-08-01T00:00:00.000Z",
                description = "Recopilación de 27 composiciones del cosmos Blades que los bailadores y melómanos han hecho suyas en estos 40 años de presencia de los ritmos y concordias afrocaribeños en múltiples escenarios internacionales. Grabaciones de Blades para la Fania con las orquestas de Pete Rodríguez, Ray Barreto, Fania All Stars y, sobre todo, los grandes éxitos con la Banda de Willie Colón",
                genre = "Salsa",
                recordLabel = "Elektra",
                tracks = listOf(),
                performers = listOf(),
                comments = listOf()
            ),

            ),
        performerPrizes = listOf(
            PerformerPrize(
                id = 100,
                premiationDate = "1978-12-10T00:00:00.000Z",
            )
        ),
    ),
    Performer(
        id = 101,
        name = "Carlos Vives",
        image = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d3/Carlos_Vives_%28cropped%29.jpg/800px-Carlos_Vives_%28cropped%29.jpg",
        description = "Carlos Alberto Vives Restrepo (Santa Marta, 7 de agosto de 1961), conocido artísticamente como Carlos Vives, es un cantante, actor y compositor colombiano.",
        birthDate = "1961-08-07T00:00:00.000Z",
        albums = listOf(
            Album(
                id = 102,
                name = "Clásicos de la Provincia",
                cover = "https://upload.wikimedia.org/wikipedia/en/thumb/e/e2/Clasicos_de_la_Provincia.jpg/220px-Clasicos_de_la_Provincia.jpg",
                releaseDate = "1993-08-01T00:00:00.000Z",
                description = "Clásicos de la Provincia is a vallenato album by Colombian singer/composer Carlos Vives. It was released as a double disc album on August 24, 1993.",
                genre = "Folk",
                recordLabel = "Sony",
                tracks = listOf(),
                performers = listOf(),
                comments = listOf()
            ),
        ),
        performerPrizes = listOf(
            PerformerPrize(
                id = 101,
                premiationDate = "2002-11-05T00:00:00.000Z",
            )
        ),
    ),
    Performer(
        id = 102,
        name = "Juanes",
        image = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Juanes_%28crop_2%29.jpg/800px-Juanes_%28crop_2%29.jpg",
        description = "Juan Esteban Aristizábal Vásquez (Carolina del Príncipe, Antioquia; 9 de agosto de 1972), más conocido como Juanes, es un cantautor, músico y filántropo colombiano, que fusiona diversos géneros musicales como el rock, el pop y la cumbia.",
        birthDate = "1972-08-09T00:00:00.000Z",
        albums = listOf(
            Album(
                id = 103,
                name = "Fijate Bien",
                cover = "https://upload.wikimedia.org/wikipedia/en/thumb/d/d8/F%C3%ADjate_Bien.jpg/220px-F%C3%ADjate_Bien.jpg",
                releaseDate = "2000-10-17T00:00:00.000Z",
                description = "Fíjate Bien is the debut studio album by Colombian singer-songwriter Juanes, released on October 17, 2000, by Surco Records. The album earned him three Latin Grammy Awards.",
                genre = "Rock",
                recordLabel = "Sony",
                tracks = listOf(),
                performers = listOf(),
                comments = listOf()
            ),
            Album(
                id = 104,
                name = "Un Día Normal",
                cover = "https://upload.wikimedia.org/wikipedia/en/thumb/9/94/Un_D%C3%ADa_Normal.jpg/220px-Un_D%C3%ADa_Normal.jpg",
                releaseDate = "2002-08-01T00:00:00.000Z",
                description = "Un Día Normal (A Normal Day) is the second studio album by Colombian singer-songwriter Juanes, released on August 6, 2002.",
                genre = "Rock",
                recordLabel = "Discos Fuentes",
                tracks = listOf(),
                performers = listOf(),
                comments = listOf()
            ),
        ),
        performerPrizes = listOf(
            PerformerPrize(
                id = 102,
                premiationDate = "2003-09-03T00:00:00.000Z",
            )
        ),
    )
)

object MockPerformersApiService : PerformerApiService {
    override suspend fun getPerformers(): List<Performer> {
        delay(2000)
        return mockPerformers
    }

    override suspend fun getPerformersDetail(id: Int): Performer {
        delay(2000)
        return mockPerformers[0]
    }
}