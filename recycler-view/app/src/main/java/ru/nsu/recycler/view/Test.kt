package ru.nsu.recycler.view

import android.os.Handler
import android.os.Looper
import ru.nsu.recycler.view.adapter.PlayerAdapter
import ru.nsu.recycler.view.item.Item

class Test {

    companion object {
        private const val SONG_IMAGE_URL = "https://tinyurl.com/2n7vmcfj"
        private const val BANNER_IMAGE_URL = "https://tinyurl.com/3zudr9hk"
        private const val SONG_TITLE = "No Hay Ley"
        private const val SONG_AUTHOR = "Kali Uchis"
        private const val SONG_DESCRIPTION =
            "\"NO HAY LEY\" is a song by Kali Uchis released on September 2, 2022, as a " +
                    "stand-alone single. A remix titled \"No Hay Ley Parte 2\" featuring Rauw " +
                    "Alejandro was released on Uchis' fourth studio album ORQUÍDEAS appearing as " +
                    "the twelfth track on the album."
        private const val BANNER_CATEGORY = "Technology"
        private const val BANNER_TITLE = "Twitter has a new boss"
        private const val BANNER_DESCRIPTION = "Big changes are coming"
    }

    private var items = mutableListOf<Item>()

    fun startTest(playerAdapter: PlayerAdapter) {
        val initialSongs = (1..15).map {
            Item.Song(
                it,
                SONG_TITLE,
                SONG_AUTHOR,
                SONG_DESCRIPTION,
                SONG_IMAGE_URL
            )
        }
        items.addAll(initialSongs)
        playerAdapter.updateData(items)

        Handler(Looper.getMainLooper()).postDelayed({
            val banner1 = Item.Banner(
                16,
                BANNER_CATEGORY,
                BANNER_TITLE,
                BANNER_DESCRIPTION,
                BANNER_IMAGE_URL
            )
            val banner2 = Item.Banner(
                17,
                BANNER_CATEGORY,
                BANNER_TITLE,
                BANNER_DESCRIPTION,
                BANNER_IMAGE_URL
            )

            items.add(1, banner1)
            items.add(banner2)

            playerAdapter.updateData(items)
        }, 5000)
    }
}