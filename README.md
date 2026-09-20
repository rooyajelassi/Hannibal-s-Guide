# Hannibal's Guide

An interactive Android application that showcases Tunisia's historical landmarks and cultural wonders, with an AI-powered tour guide chatbot, to promote cultural awareness and tourism exploration.

## Features

- **Landmark explorer**: browse Tunisia's historical sites and cultural wonders (e.g. Carthage, El Jem, Dougga, Kairouan, Sidi Bou Said) with descriptions and images.
- **AI tour guide chatbot**: a conversational guide powered by the **Groq API** that answers questions about sites, history and travel tips.
- **Multilingual**: the chatbot supports **English, French and Arabic**.
- Native mobile interface built in **Kotlin**.

## Tech stack

| Layer    | Technology                      |
| -------- | ------------------------------- |
| Frontend | Kotlin (Android, Android Studio) |
| AI       | Groq API                        |

## Getting started

### Prerequisites

- Android Studio (latest stable version)
- An Android device or emulator
- A Groq API key (https://console.groq.com)

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/rooyajelassi/Hannibal-s-Guide.git
   ```

2. Open the project in Android Studio and let Gradle sync.
3. Add your Groq API key to `local.properties` (this file is not committed):

   ```
   GROQ_API_KEY=your_api_key_here
   ```

4. Run the app on an emulator or a connected device.

> **Security note:** never commit your API key. An API key bundled inside a mobile app can be extracted from the APK, so for a production release the Groq calls should go through a small backend that keeps the key server-side.


## Author

**Rooya Jelassi** · [GitHub](https://github.com/rooyajelassi) · [LinkedIn](https://linkedin.com/in/rooya-jelassi-49ab75295)
**Roua Smida** 
