package com.example.vinilos.data.datasources.mock

import com.example.vinilos.data.datasources.PerformerApiService
import com.example.vinilos.data.entities.Album
import com.example.vinilos.data.entities.Genre
import com.example.vinilos.data.entities.Performer
import com.example.vinilos.data.entities.PerformerPrize
import com.example.vinilos.data.entities.RecordLabel
import kotlinx.coroutines.delay

val mockPerformers = listOf(
    Performer(
        id = 100,
        name = "Buscando América",
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
                genre = Genre.SALSA,
                recordLabel = RecordLabel.ELEKTRA,
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
                genre = Genre.SALSA,
                recordLabel = RecordLabel.ELEKTRA,
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

)

object MockPerformersApiService : PerformerApiService {
    override suspend fun getPerformers(): List<Performer> {
        delay(2000)
        return mockPerformers
    }
}
