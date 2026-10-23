# Assistant Réunion 🎙️

Application Android d'assistant de réunion intelligente.

**Version actuelle : 100 % autonome** — aucune clé API requise.

Fonctionnalités :
- Création et enregistrement de réunions
- Simulation de transcription en direct
- Compte-rendu structuré (mode simulation)
- Historique, recherche et export

---

## Lancer l'application

### Avec Android Studio (recommandé)

1. Ouvre Android Studio
2. **File → Open** → sélectionne ce dossier
3. Laisse Gradle synchroniser (première fois = quelques minutes)
4. Clique sur le bouton vert **Run** ▶

C'est tout. Aucune configuration supplémentaire.

### Build APK via GitHub Actions

1. Va dans l'onglet **Actions**
2. Lance le workflow **Build Android APK**
3. Télécharge l'artefact une fois terminé

---

## Structure

```
app/src/main/java/com/example/
├── MainActivity.kt
├── ui/screens/          # Tous les écrans
├── viewmodel/           # MeetingViewModel
├── data/                # Room + Repository
├── service/             # Enregistrement audio
├── engine/              # Transcription + Analyse (simulation)
└── export/              # Export texte
```

---

Bon test ! 🚀
