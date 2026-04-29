package com.example.hannibalsguide.presentation.localization

import com.example.hannibalsguide.domain.model.AppLanguage

class UiStrings(private val language: AppLanguage) {
    val appTitle: String
        get() = when (language) {
            AppLanguage.ENGLISH -> "Discover Tunisia"
            AppLanguage.FRENCH -> "Decouvrir la Tunisie"
            AppLanguage.ARABIC -> "اكتشف تونس"
        }

    val searchPlaceholder: String
        get() = when (language) {
            AppLanguage.ENGLISH -> "Search by name, city, or heritage"
            AppLanguage.FRENCH -> "Rechercher par nom, ville ou patrimoine"
            AppLanguage.ARABIC -> "ابحث بالاسم او المدينة او التراث"
        }

    val settingsTitle: String
        get() = when (language) {
            AppLanguage.ENGLISH -> "Settings"
            AppLanguage.FRENCH -> "Parametres"
            AppLanguage.ARABIC -> "الاعدادات"
        }

    val languageLabel: String
        get() = when (language) {
            AppLanguage.ENGLISH -> "Language"
            AppLanguage.FRENCH -> "Langue"
            AppLanguage.ARABIC -> "اللغة"
        }

    val englishLabel: String
        get() = when (language) {
            AppLanguage.ENGLISH -> "English"
            AppLanguage.FRENCH -> "Anglais"
            AppLanguage.ARABIC -> "الانجليزية"
        }

    val frenchLabel: String
        get() = when (language) {
            AppLanguage.ENGLISH -> "French"
            AppLanguage.FRENCH -> "Francais"
            AppLanguage.ARABIC -> "الفرنسية"
        }

    val arabicLabel: String
        get() = when (language) {
            AppLanguage.ENGLISH -> "Arabic"
            AppLanguage.FRENCH -> "Arabe"
            AppLanguage.ARABIC -> "العربية"
        }

    val settingsContentDescription: String
        get() = when (language) {
            AppLanguage.ENGLISH -> "Settings"
            AppLanguage.FRENCH -> "Parametres"
            AppLanguage.ARABIC -> "الاعدادات"
        }

    val backContentDescription: String
        get() = when (language) {
            AppLanguage.ENGLISH -> "Back"
            AppLanguage.FRENCH -> "Retour"
            AppLanguage.ARABIC -> "رجوع"
        }

    val mapTitle: String
        get() = when (language) {
            AppLanguage.ENGLISH -> "Map"
            AppLanguage.FRENCH -> "Carte"
            AppLanguage.ARABIC -> "الخريطة"
        }

    val locationNotAvailable: String
        get() = when (language) {
            AppLanguage.ENGLISH -> "Location not available"
            AppLanguage.FRENCH -> "Emplacement indisponible"
            AppLanguage.ARABIC -> "الموقع غير متاح"
        }

    val landmarkDetailsTitle: String
        get() = when (language) {
            AppLanguage.ENGLISH -> "Landmark details"
            AppLanguage.FRENCH -> "Details du site"
            AppLanguage.ARABIC -> "تفاصيل المعلم"
        }

    val askTarek: String
        get() = when (language) {
            AppLanguage.ENGLISH -> "Ask Tarek"
            AppLanguage.FRENCH -> "Demander a Tarek"
            AppLanguage.ARABIC -> "اسال طارق"
        }

    val viewOnMap: String
        get() = when (language) {
            AppLanguage.ENGLISH -> "View on Map"
            AppLanguage.FRENCH -> "Voir sur la carte"
            AppLanguage.ARABIC -> "عرض على الخريطة"
        }

    val culturalStory: String
        get() = when (language) {
            AppLanguage.ENGLISH -> "Cultural Story"
            AppLanguage.FRENCH -> "Recit culturel"
            AppLanguage.ARABIC -> "القصة الثقافية"
        }

    val echoesOfHistory: String
        get() = when (language) {
            AppLanguage.ENGLISH -> "Echoes of History"
            AppLanguage.FRENCH -> "Echos de l'histoire"
            AppLanguage.ARABIC -> "صدى التاريخ"
        }

    val heritageChips: List<String>
        get() = when (language) {
            AppLanguage.ENGLISH -> listOf("UNESCO Spirit", "Roman Legacy", "Amazigh Roots", "Mediterranean Soul")
            AppLanguage.FRENCH -> listOf("Esprit UNESCO", "Heritage romain", "Racines amazigh", "Ame mediterraneenne")
            AppLanguage.ARABIC -> listOf("روح اليونسكو", "ارث روماني", "جذور امازيغية", "روح متوسطية")
        }

    val chatTitle: String
        get() = when (language) {
            AppLanguage.ENGLISH -> "Chat with Tarek"
            AppLanguage.FRENCH -> "Discussion avec Tarek"
            AppLanguage.ARABIC -> "دردشة مع طارق"
        }

    val chatPlaceholder: String
        get() = when (language) {
            AppLanguage.ENGLISH -> "Ask about this place..."
            AppLanguage.FRENCH -> "Demandez a propos de ce lieu..."
            AppLanguage.ARABIC -> "اسال عن هذا المكان..."
        }

    val youLabel: String
        get() = when (language) {
            AppLanguage.ENGLISH -> "You"
            AppLanguage.FRENCH -> "Vous"
            AppLanguage.ARABIC -> "انت"
        }

    val tarekLabel: String
        get() = when (language) {
            AppLanguage.ENGLISH -> "Tarek"
            AppLanguage.FRENCH -> "Tarek"
            AppLanguage.ARABIC -> "طارق"
        }

    val typing: String
        get() = when (language) {
            AppLanguage.ENGLISH -> "Typing..."
            AppLanguage.FRENCH -> "Saisie..."
            AppLanguage.ARABIC -> "جار الكتابة..."
        }

    val networkError: String
        get() = when (language) {
            AppLanguage.ENGLISH -> "Network/API error. Please try again."
            AppLanguage.FRENCH -> "Erreur reseau/API. Veuillez reessayer."
            AppLanguage.ARABIC -> "خطا في الشبكة او الواجهة. حاول مرة اخرى."
        }

    fun greeting(landmarkName: String): String {
        return when (language) {
            AppLanguage.ENGLISH -> "Hi! I'm Tarek. Ask me anything about $landmarkName."
            AppLanguage.FRENCH -> "Salut ! Je suis Tarek. Demandez-moi tout sur $landmarkName."
            AppLanguage.ARABIC -> "مرحبا! انا طارق. اسالني عن $landmarkName."
        }
    }
}

