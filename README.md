# Assistant Réunion 🎙️

Application Android d'assistant de réunion intelligente avec :
- Enregistrement audio en direct
- Transcription automatique
- Diarisation des locuteurs (qui a parlé)
- Compte-rendu structuré généré par IA (Gemini)
- Export des réunions

Générée avec **Google AI Studio**.

---

## Comment builder l'application

### Méthode 1 : Android Studio (recommandée)

1. Télécharge et installe [Android Studio](https://developer.android.com/studio) (dernière version).
2. Ouvre Android Studio → **Open** → sélectionne ce dossier.
3. Android Studio va automatiquement télécharger le Gradle Wrapper et les dépendances (ça peut prendre 5-15 min la première fois).
4. Crée un fichier `.env` à la racine du projet et ajoute ta clé :
   ```
   GEMINI_API_KEY=ta_vraie_clé_gemini_ici
   ```
   (Tu peux obtenir une clé gratuite sur [Google AI Studio](https://aistudio.google.com/apikey))
5. Clique sur le bouton vert **Run** ▶ (ou Shift+F10).
6. Choisis un émulateur ou ton téléphone en mode développeur.

> **Note** : Si Android Studio te demande de corriger des choses (SDK, etc.), accepte.

### Méthode 2 : GitHub Actions (build automatique)

1. Va dans l'onglet **Actions** de ce dépôt.
2. Lance le workflow **"Build Android APK"** manuellement.
3. Une fois terminé, télécharge l'artefact **Assistant-Reunion-Debug-APK**.

> Note : la première fois, ouvre le projet dans Android Studio pour générer le Gradle Wrapper complet si besoin, puis committe.

### Méthode 3 : Ligne de commande (avancé)

```bash
./gradlew assembleDebug
# L'APK se trouve dans : app/build/outputs/apk/debug/
```

---

## Configuration importante

| Fichier / Élément | Description |
|-------------------|-------------|
| `.env` | Contient `GEMINI_API_KEY=...` (obligatoire pour l'IA) |
| `google-services.json` | Optionnel (Firebase). Le projet fonctionne sans. |
| Clé de signature | Pour une version release (Play Store), tu auras besoin d'un keystore |

---

## Structure du projet

```
app/src/main/java/com/example/
├── MainActivity.kt              # Point d'entrée + navigation
├── ui/screens/                  # Écrans (Home, NewMeeting, Active, Detail...)
├── viewmodel/                   # MeetingViewModel
├── service/                     # Enregistrement audio + service foreground
├── engine/                      # Transcription + Analyse IA
├── firebase/                    # Service Gemini / Firebase AI
├── data/                        # Room Database (réunions, transcripts)
└── export/                      # Export PDF / texte
```

---

## Permissions utilisées

- `RECORD_AUDIO` → enregistrement des réunions
- `FOREGROUND_SERVICE` → enregistrement en arrière-plan
- `POST_NOTIFICATIONS` → notification pendant l'enregistrement

---

Bon build ! 🚀  
